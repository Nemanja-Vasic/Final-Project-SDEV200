//THIS PROGRAM MIMICS A REAL WORLD APPLICABLE UTILITY APPLICATION 
//RENT TRUCKS HIRE DRIVERS BILL BROKERS
import java.io.*;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args)
    {
        //INTRODUCTION
        String welcomeString = "Hello! Welcome to the Invoice processing application!\n" +
        "The main function of this application is for creating invoices to: \npay your drivers, " + 
        " pay your rental company for the trucks you rent" + 
        ", and send your customers invoices to pay you.\nPress 'OK' to get started...";
        JOptionPane.showMessageDialog(null, welcomeString, "Getting Started", JOptionPane.INFORMATION_MESSAGE);
        
        //START OF MAIN PROGRAM LOOP
        boolean sentVal = true;
        String input;
        try{
        input = JOptionPane.showInputDialog(null, //OBTAIN SIZE OF FLEET
                 "Please enter how many trucks you have operating: ", "Size of Fleet", JOptionPane.QUESTION_MESSAGE);
        int fleetSize = Integer.parseInt(input);
        Driver[] DriverArray = new Driver[fleetSize];
        while(fleetSize > 10) //ENSURE FLEET IS NOT TOO BIG //INPUT VALIDATION
        {
                input = JOptionPane.showInputDialog(null, 
                "Sorry, this application is not designed for fleets larger than 10 trucks. Enter a smaller fleet");
                fleetSize = Integer.parseInt(input);
        }
        
        //LOAD DRIVERS FOR EACH TRUCK
        for(int x = 0; x < fleetSize; ++x)
        {
                String name = JOptionPane.showInputDialog(null, "Enter Driver #" + (x+1) + "'s full name.");
                double rpm = 0;
                int selection;
                do //DO WHILE TO VERIFY CORRECT SALARY INPUT
                {
                String rate = JOptionPane.showInputDialog(null, "What level salary does this driver have?\nLevel 1 - 0.5 cents per mile\n" + 
                "Level 2 - 0.6 cents per mile\nLevel 3 - 0.75 cents per mile\n Enter the level - (1, 2, 3)", JOptionPane.QUESTION_MESSAGE);
                selection = Integer.parseInt(rate);
                if(selection < 1 || selection > 3)
                JOptionPane.showMessageDialog(null, "ERROR - Please enter salary level 1 -3.", "ERROR", JOptionPane.ERROR_MESSAGE);
                } while(selection < 1 || selection > 3);
                
                if(selection == 1)
                        rpm = 0.5;
                else if (selection == 2)
                        rpm = 0.6;
                else if (selection == 3)
                        rpm = 0.75;

                int trk_id, driver_id;
                trk_id = x + 1;
                driver_id = x + 1;
                Driver tempDriver = new Driver(name, rpm, driver_id, trk_id);
                DriverArray[x] = tempDriver;
                //DriverArray[x].get_driverInfo();
        }
        sentVal = true;
        do {
               input = JOptionPane.showInputDialog(null, "Enter the following commands to use the application: " + 
               "\n1 - Pay a driver\n2 - Pay truck rental\n3 - Bill Broker\n4 - QUIT", JOptionPane.QUESTION_MESSAGE); 
                int MenuInput = Integer.parseInt(input);
               while(MenuInput > 5 || MenuInput < 1)
                {
                        input = JOptionPane.showInputDialog(null, "Enter the following commands to use the application:\n1 - Pay a driver\n2 - Pay truck rental\n3 - Bill Broker\n5 - QUIT", JOptionPane.QUESTION_MESSAGE);
                        MenuInput = Integer.parseInt(input);
                } 
               switch (MenuInput) {
                        case 1:
                        //PAY DRIVER - DRIVER INVOICE
                        String listOfDrivers = "";
                        for(int x = 0; x < fleetSize; ++x)
                        {
                                listOfDrivers += DriverArray[x].get_driverInfo(); //LOAD ALL DRIVERS INTO STRING AND 
                        }                                                                        //ALLOW USER TO SELECT ONE
                        String selection = ""; 
                        selection = JOptionPane.showInputDialog(null, listOfDrivers, "List of Drivers - Choose Driver ID", JOptionPane.INFORMATION_MESSAGE);
                        int chosenDriver = Integer.parseInt(selection);
                        while(chosenDriver > fleetSize || chosenDriver < 1)
                        {
                              selection = JOptionPane.showInputDialog(null, "Please choose a valid ID#\n" + listOfDrivers, "List of Drivers - Choose Driver ID", JOptionPane.INFORMATION_MESSAGE);
                        chosenDriver = Integer.parseInt(selection);  
                        }
                        selection = JOptionPane.showInputDialog(null, "How many miles did " + DriverArray[chosenDriver-1].name + 
                        " drive this week?", "Enter miles driven", JOptionPane.QUESTION_MESSAGE);
                        generateDriverInv(DriverArray[chosenDriver-1], selection, fleetSize);
                        break;
                        case 2:
                        //PAY TRUCK RENTAL
                        String listOfTrucks = "";
                        for(int x = 0; x < fleetSize; ++x)
                        {
                                listOfTrucks += "\n__\nTruck ID: " + DriverArray[x].truck_id + "\n";
                        }
                        selection = JOptionPane.showInputDialog(null, "Select a truck to pay its rental fees: \n" + listOfTrucks, "List of Trucks - Select Truck ID", JOptionPane.QUESTION_MESSAGE);
                        int chosenTruck = Integer.parseInt(selection);
                        String date = JOptionPane.showInputDialog(null, "Please enter the date(1-31)\nDay - ", "Enter Day of the Month");
                        date += "/" + JOptionPane.showInputDialog(null, "Please enter the date(1-12)\nMonth - ", "Enter the Month");
                        date += "/2023";
                        //CREATE RENTAL INVOICE USING SUPERCONSTRUCTOR AND SUBCLASS METHOD
                        rentalPayment rentalInv = new rentalPayment(date, listOfTrucks, chosenTruck);
                        //GENERATE INVOICE - SUBCLASS METHOD
                        String rentalInvoice = rentalInv.generateInvoiceString();
                        JOptionPane.showMessageDialog(null, rentalInvoice, "Rental Amount Due - Inv Preview", JOptionPane.INFORMATION_MESSAGE);
                        break;
                        case 3:
                        int loadNumber, index, total = 0;
                        do {
                                selection = JOptionPane.showInputDialog(null, "How many loads do you wish to bill for?\n(Only 5 at a time are allowed)", "Load Calculator", JOptionPane.QUESTION_MESSAGE);
                                loadNumber = Integer.parseInt(selection);
                        } while (loadNumber < 0 || loadNumber > 5);
                        listOfTrucks = "";
                        for(int x = 0; x < fleetSize; ++x)
                        {
                                listOfTrucks += "\n__\nTruck ID: " + DriverArray[x].truck_id + "\n";
                        }
                        selection = JOptionPane.showInputDialog(null, "Select which truck transported the loads: \n" + listOfTrucks, "Load Calculator - Select Truck ID", JOptionPane.QUESTION_MESSAGE);
                        chosenTruck = Integer.parseInt(selection);
                        String loadsString = "";
                        for(int x = 0; x < loadNumber; ++x)
                        {
                                selection = JOptionPane.showInputDialog(null, "Select the load type for load #" + x + 
                                "\n1 - Dry Van General Goods\n2 - Refrigerated Produce\n3 - Hazardous Material", "Load Type", JOptionPane.QUESTION_MESSAGE);
                                index = Integer.parseInt(selection);
                                if(index == 1)
                                loadsString += "\nLoad #" + x + " - Dry Van General Goods";
                                else if(index == 2)
                                loadsString += "\nLoad #" + x + " - Refrigerated Produce";
                                else if (index == 3)
                                loadsString += "\nLoad #" + x + " - Hazardous Material";

                                total += calculateLoadBill(index);
                        }

                        JOptionPane.showMessageDialog(null, " - " + loadNumber + " Loads Billed -\n" + loadsString + "\n__________\nTotal - " + total, "Load Invoice", JOptionPane.INFORMATION_MESSAGE);

                        loadsString = "";
                        break;
                        case 4:
                        sentVal = false;
                        break;
               }
                
        } while (sentVal);
} catch(InputMismatchException e)
{
        System.out.println("ERROR: " + e.getMessage());
} catch(Exception e)
{
        System.out.println("Invalid Input: " + e.getMessage());
}
    }
    public static void generateDriverInv(Driver driverClass, String miles, int Fleet)
    {
        try{
                String fileName;
                String invNumber = JOptionPane.showInputDialog(null, "Enter the invoice number: ", "Invoice Number");
                int INVNUM = Integer.parseInt(invNumber); //PARSE INV NUMBER TO INTEGER TO VERIFY IF DUPLICATE

                //CREATE ARRAY LIST TO STORE INV NUMBER TO ENSURE NO DUPLICATE INVOICES/FILES
                ArrayList<Integer> invList = new ArrayList<>();
                while(invList.contains(INVNUM))
                {
                        invNumber = JOptionPane.showInputDialog(null, "Invoice number already exists, enter new invoice number: ", "Invoice Number");
                        INVNUM = Integer.parseInt(invNumber);
                }
                invList.add(INVNUM);
                //WHILE LOOP TO ENSURE INPUT IS AN INTEGER FOR VALID INVOICE NUMBER
                while(!invNumber.matches("\\d+"))
                {
                fileName = JOptionPane.showInputDialog(null, "Please enter an integer " + 
                "for the invoice number: ", "Invoice Number");
                }
                //CONCATENATE STRING TO CREATE INVOICE FILE NAME
                fileName = "Invoice_" + invNumber + ".txt";
                int totalMiles = Integer.parseInt(miles);
                double paycheck = totalMiles * driverClass.rpm; //CALCULATE PAYMENT
                FileWriter invoiceFile = new FileWriter(fileName); //CREATE FILE/INVOICE
                String date = JOptionPane.showInputDialog(null, "Please enter the date(1-31)\nDay - ", "Enter Day of the Month");
                date += "/" + JOptionPane.showInputDialog(null, "Please enter the date(1-12)\nMonth - ", "Enter the Month");
                date += "/2023";

                //CREATE STRING TO PREVIEW INVOICE
                String invString = "\n" + 
                " - Driver Payroll - Date: " + date + "\n  - " + driverClass.name + "\n - Total Miles Driven: " + totalMiles + " | Rate Per Mile: " +
                 String.format("%.2f", driverClass.rpm) + "\n___________\n\nTotal: " + String.format("%.2f", paycheck) + 
                 " \n\nYour Invoice has been saved to a file in your folder.";
                 
                //WRITE INVOICE TO FILE
                invoiceFile.write(invString);
                //PREVIEW INVOICE
                JOptionPane.showMessageDialog(null, invString, "- - - - INVOICE #" + invNumber +  "- - - -", JOptionPane.INFORMATION_MESSAGE);
                invoiceFile.close();
        } catch(IOException e)
        {
                System.out.println("IO Exception: " + e.getMessage());
        }
}
        public static int calculateLoadBill(int index)
        {
        int total = 0;
        switch (index) {
                case 1:
                total = 900;
                        break;
                case 2:
                total = 1100;
                        break;
                case 3:
                total = 1500;
                        break;
                default:
                total = 0;
                        break;
        }
        return total;
}
}
