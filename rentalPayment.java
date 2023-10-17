public class rentalPayment extends InvoiceType{
    private int truck_id;

    rentalPayment(String DATE, String driverName, int TRUCK)
    {
        super(DATE);
        truck_id = TRUCK;
        total = 1200;
    }
    public String generateInvoiceString()
    {
        System.out.println("yes");
        String str = "";
        str = "\n" + 
        " - Truck Rental - Date: " + date + "\n  - Truck ID: " + truck_id + "\n___________\n\nTotal: " + total +
         " \n\nYour Invoice has been saved to a file in your folder.";
        return str;
    }
}
