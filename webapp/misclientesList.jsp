
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="consumoelectricojsp.web.model.Measurement"%>
<%@page import="consumoelectricojsp.web.model.ClientList"%>
<%@page import="consumoelectricojsp.web.model.Client"%>

<jsp:useBean id="clientBean" class="consumoelectricojsp.web.model.Client" scope="request"/>
<jsp:useBean id="myclientsBean" class="consumoelectricojsp.web.model.ClientList" scope="request"/>
<jsp:useBean id="measureBean" class="consumoelectricojsp.web.model.MeasurementList" scope="request"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css"/>
        <title>Lista de clientes y mediciones</title>
    </head>
    <body>
        <div class="container">
            <nav>
                <a href="index.jsp"><h1>Consumo Eléctrico JSP</h1></a>
                <p>por mothcrown</p>
            </nav>
            <div class="section">
                <%
                    // Cargamos todo a palo, que para eso me creé mis clases chupis!
                    Client client = clientBean;
                    ArrayList<Client> clients = myclientsBean.getClientList();
                    ArrayList<Measurement> measurements = measureBean.getMeasurementList();
                %>
                <table>
                    <caption>Lista de clientes</caption>
                    <%
                        // Cargamos clientFields para mostrar campos en <th>
                        String[] clientFields = new String[] { 
                            "Nº", "Nombre y apellidos", "Ciudad", "Provincia" 
                        }; 
                    %>
                    <tr>
                        <% for (String field : clientFields) { %>
                            <th><%= field %></th>
                        <% } %>   
                    </tr>
                    <% for (Client client1 : clients) { %>
                        <tr>
                            <td class="id"><%= client1.getId() %></td>
                            <td><%= client1.getName() + 
                                    " " + client1.getSurname()%></td>
                            <td><%= client1.getTown() %></td>
                            <td><%= client1.getProvince() %></td>
                        </tr>
                    <% } %>
                    </table>
                    <% 
                        // El dbLimit es trampa y me siento muy decepcionado conmigo mismo.
                        // Lo siento mucho, migo mismo :(
                        int dbLimit = 187;
                        String name = request.getParameter("nameToSearch");

                        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
                        int currentPage = Integer.parseInt(request.getParameter("page"));
                        int lastPage = (dbLimit / pageSize) + 1;

                        // Ready to launch
                        String pageSizeName = "pageSize=" + pageSize + "&nameToSearch=" + name;
                        String bFirst = "?" + pageSizeName + "&page=1";
                        String bPrev = "?" + pageSizeName + "&page=" + (currentPage - 1);
                        String bNext = "?" + pageSizeName + "&page=" + (currentPage + 1);
                        String bLast = "?" + pageSizeName + "&page=" + lastPage;

                    %>
                <div class="buttons">
                    <!-- Esto es arte, oiga -->
                    <a href="<%= currentPage - 1 == 0 ? "" : bFirst %>">|&lt;&lt;</a>
                    <a href="<%= currentPage - 1 == 0 ? "" : bPrev %>">&lt;&lt;</a>
                    <a href="<%= currentPage == lastPage ? "" : bNext%>">&gt;&gt;</a>
                    <a href="<%= currentPage == lastPage ? "" : bLast%>">&gt;&gt;|</a>
                </div>
                <%  if (measurements.size() == 0) {%>
                    <h2>No se ha podido encontrar al cliente 
                        <%= (client.getName()) %> <%= (client.getSurname() != ".*") ? (client.getSurname()) : "" %>
                    </h2>
                <% } %>

                <%  if (measurements.size() != 0) {%>
                    <h2>Estas son las mediciones del cliente 
                        <%= (client.getName()) %> <%= (client.getSurname() != ".*") ? (client.getSurname()) : "" %>
                    </h2>

                    <table>
                        <caption>Lista de mediciones</caption>
                        <%
                            String[] measureFields = new String[] { 
                                "Id Medición", "Fecha/Hora", "Medición de KW"
                            }; 
                        %>
                        <tr>
                        <% for (String field : measureFields) { %>
                            <th><%= field %></th>
                        <% } %>   
                        </tr>
                        <% for (Measurement measurement : measurements) { %>
                            <tr>
                                <td class="id"><%= measurement.getId() %></td>
                                <td><%= measurement.getTime() %></td>
                                <td><%= measurement.getKW() %></td>
                            </tr>
                        <% } %>
                    </table>
                <% } %>
            </div>
        </div>
    </body>
</html>
