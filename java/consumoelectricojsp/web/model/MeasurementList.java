
package consumoelectricojsp.web.model;

import java.util.ArrayList;

/**
 * Clase MeasurementList con sus get sets y demás. Muy guay todo.
 *
 * @author mothcrown
 */
public class MeasurementList {
    private ArrayList<Measurement> measurements;
    
    public MeasurementList() {}
    
    public MeasurementList(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }
    
    public ArrayList<Measurement> getMeasurementList() {
        return measurements;
    }
    
    public void setMeasurementList(ArrayList<Measurement> measurements) {
        this.measurements = measurements;
    }
}
