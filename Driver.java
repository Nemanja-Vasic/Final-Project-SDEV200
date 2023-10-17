import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.JOptionPane;
public class Driver {
    public int truck_id;
    public String name;
    public double rpm;
    public int driver_id;
    public Driver(String NAME, double RPM, int DRIVER_ID, int TRUCK_ID)
    {
        this.name = NAME;
        this.rpm = RPM;
        this.driver_id = DRIVER_ID;
        this.truck_id = TRUCK_ID;
    }
    public String get_driverInfo()
    {
        String driver_info = "\n____________________\n\nDriver ID: " + driver_id + " | Truck ID: " + truck_id + "\n" + name
        + "\nRate per Mile: " + String.format("%.2f", rpm);
        //JOptionPane.showMessageDialog(null, driver_info, "Driver Information", JOptionPane.INFORMATION_MESSAGE);
        return driver_info;
    }
}
