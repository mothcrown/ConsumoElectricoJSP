
package consumoelectricojsp.web.model;

/**
 * Clase cliente!
 * 
 * @author mothcrown
 */
public class Client {
    private int id = 0;
    private String name = null;
    private String surname = null;
    private String streetName = null;
    private String number = null;
    private int postalCode = 0;
    private String town = null;
    private String province = null;
    
    public Client() {}
    
    public Client(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
    
    public Client (int id, String name, String surname, String streetName, 
            String number, int postalCode, String town, String province) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.streetName = streetName;
        this.number = number;
        this.postalCode = postalCode;
        this.town = town;
        this.province = province;
    }
    
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    
    public String getStreetName() {
        return streetName;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getNumber() {
        return number;
    }
    
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    
    public int getPostalCode() {
        return postalCode;
    }
    
    public void setTown(String town) {
        this.town = town;
    }
    
    public String getTown() {
        return town;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getProvince() {
        return province;
    }
}
