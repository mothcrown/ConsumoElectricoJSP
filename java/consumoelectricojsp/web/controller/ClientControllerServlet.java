
package consumoelectricojsp.web.controller;

import consumoelectricojsp.web.model.Client;
import consumoelectricojsp.web.model.ClientList;
import consumoelectricojsp.web.model.DataBase;
import consumoelectricojsp.web.model.Measurement;
import consumoelectricojsp.web.model.MeasurementList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Client Controller Servlet v1.00!
 * Chulísimo.
 *
 * @author mothcrown
 */
public class ClientControllerServlet extends HttpServlet {
    private DataBase db;
    private String PAGE_SIZE_COOKIE_NAME = "pageSizeCookie";
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String configBundleName = config.getInitParameter("app.config");
        ResourceBundle rb = ResourceBundle.getBundle(configBundleName);
        db = new DataBase(rb);
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Valido el pageSize y me aseguro de que no me entra nulo, en todo 
        // caso siempre sale 0. Si no es 0, guardo el valor en la cookie!
        int pageSize = validatePageSize(request.getParameter("pageSize"));
        if (pageSize != 0) {
            Cookie pageSizeCookie = new Cookie(PAGE_SIZE_COOKIE_NAME, Integer.toString(pageSize));
            
            pageSizeCookie.setMaxAge(24 * 60 * 60);
            pageSizeCookie.setPath(request.getContextPath());
            response.addCookie(pageSizeCookie);
        }
        
        // Si no hay page=X, vamos a la página 1 por defecto
        int page = (request.getParameter("page") != null) ? Integer.parseInt(request.getParameter("page")) : 1;
        
        // Si el usuario mete nombre y apellido me aseguro de guardar ambos datos
        String tmpName = request.getParameter("nameToSearch");
        tmpName = tmpName.trim();
        String[] fullName = tmpName.split(" ");
        
        Client client = new Client();
        client.setName(fullName[0]);
        // TRUCO: Si el usuario no ha introducido apellido, le pongo .*
        // para no tender que andar toqueteando la consulta SQL!
        client.setSurname((fullName.length > 1) ? fullName[1] : ".*");
        
        request.setAttribute("clientBean", client);
        request.setAttribute("myclientsBean", loadClients(page, pageSize));
        request.setAttribute("measureBean", loadMeasurements(client));
        getServletContext().getRequestDispatcher("/misclientesList.jsp").forward(request, response);
    }
    /**
     * Cargo Clientes mediante consulta SQL en la Base de datos
     * 
     * @param page
     * @param pageSize
     * @return ClientList
     */
    protected ClientList loadClients(int page, int pageSize) {
        Connection connection = null;
        ArrayList<Client> clients = new ArrayList<Client>();
        ClientList clientList = new ClientList();
        
        try {
            db.open();
            connection = db.getConnection();
            String query = "select * from consumoelectrico.misclientes limit ?, ?";

            try (PreparedStatement pstmt = connection.prepareStatement(query)){
                // Página 1 = 0!
                int actPage = (page - 1) * pageSize;
                pstmt.setInt(1, actPage);
                pstmt.setInt(2, pageSize);
                
                try (ResultSet rs = pstmt.executeQuery()){
                    
                    while (rs.next()) {
                        clients.add(new Client(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("NombreCalle"),
                            rs.getString("Numero"),
                            rs.getInt("CodPostal"),
                            rs.getString("Poblacion"),
                            rs.getString("Provincia")));
                    }
                    
                } catch (SQLException e) {
                    Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                }
                
            } catch (SQLException e) {
                Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    db.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Cargo ArrayList<Cliente> en clientList
        clientList.setClientList(clients);
        
        return clientList;
    }
    /**
     * Cargamos las mediciones para el cliente consultado. Si el cliente no
     * existe, retorno null!
     * 
     * @param client
     * @return 
     */
    protected MeasurementList loadMeasurements(Client client) {
        Connection connection = null;
        ArrayList<Measurement> measurements = new ArrayList<Measurement>();
        MeasurementList measurementList = new MeasurementList();
        
        try {
            db.open();
            connection = db.getConnection();
            // Uso regexp para lo del apellido. Clever! 
            // Bueno, no mucho
            String query = "select * from consumoelectrico.mediciones as m "
                                + "join misclientes as c "
                                + "on c.id = m.cliente "
                                + "where c.nombre=? "
                                + " and c.apellido regexp ?";

            // '.*' <- ALL    
            try (PreparedStatement pstmt = connection.prepareStatement(query)){
                pstmt.setString(1, client.getName());
                pstmt.setString(2, client.getSurname());
                try (ResultSet rs = pstmt.executeQuery()){
                    
                    while (rs.next()) {
                        measurements.add(new Measurement(
                            rs.getInt("idMedicion"),
                            rs.getInt("cliente"),
                            rs.getDate("FechaHora"),
                            rs.getFloat("KW")));
                    }
                    
                } catch (SQLException e) {
                    Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, e);
                }
                
            } catch (SQLException e) {
                Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    db.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        measurementList.setMeasurementList(measurements);
        return measurementList;
    }
    /**
     * Simple función de validación que se encarga de que, si alguien mete Pepe
     * me meta 0.
     * 
     * @param input
     * @return 
     */
    protected int validatePageSize(String input) {
        int pageSize = 0;
            try {
                pageSize = Integer.parseInt(input);
            } catch (Exception e) {
                Logger.getLogger(ClientControllerServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        return pageSize;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
