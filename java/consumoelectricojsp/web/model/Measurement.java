
package consumoelectricojsp.web.model;

import java.sql.Date;

/**
 *
 * @author mothcrown
 */
public class Measurement {
    private int id;
    private int client;
    private Date time;
    private float kw;
    
    public Measurement() {}
    
    public Measurement(int id, int client, Date time, float kw) {
        this.id = id;
        this.client = client;
        this.time = time;
        this.kw = kw;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setClient(int client) {
        this.client = client;
    }
    
    public int getClient() {
        return client;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setKW(float kw) {
        this.kw = kw;
    }
    
    public float getKW() {
        return kw;
    }
}
