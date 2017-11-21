
package consumoelectricojsp.web.model;

import java.util.ArrayList;

/**
 * Clase ClientList. Weeee!
 *
 * @author mothcrown
 */
public class ClientList {
    private ArrayList<Client> clients;
    
    public ClientList() {}
    
    public ClientList(ArrayList<Client> clients) {
        this.clients = clients;
    }
    
    public ArrayList<Client> getClientList() {
        return clients;
    }
    
    public void setClientList(ArrayList<Client> clients) {
        this.clients = clients;
    }
}
