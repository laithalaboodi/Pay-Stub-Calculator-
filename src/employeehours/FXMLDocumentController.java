/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeehours;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.jnlp.PrintService;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;


/**
 *
 * @author laith
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label totalPay;
    @FXML
    private Label azTaxDeduction;
    @FXML
    private Label fedTaxDeduction;
    @FXML
    private Label ficaTaxDeduction;
    @FXML
    private Label medTaxDeduction;
    @FXML
    private Label startDay;
    @FXML
    private Label endDay;
    @FXML
    private Label afterTax;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField hoursWorked;
    @FXML
    private TextField payRate;
    @FXML
    private TextField taxRateAZ;
    @FXML
    private TextField taxRateFED;
    @FXML
    private TextField ficaE;
    @FXML
    private TextField medE;
    @FXML
    private TextField startDayInput;
    @FXML
    private TextField endDayInput;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
       calcTotalPay();
       saving();
    }
    @FXML
    private void printingMethod(ActionEvent event) throws FileNotFoundException, PrintException, InterruptedException{
            String filename = ("users.doc"); // THIS IS THE FILE I WANT TO PRINT
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE; // MY FILE IS .txt TYPE
            javax.print.PrintService printService[] =
            PrintServiceLookup.lookupPrintServices(flavor, pras);
            javax.print.PrintService defaultService =
            PrintServiceLookup.lookupDefaultPrintService();
            javax.print.PrintService service = ServiceUI.printDialog(null, 200, 200,
            printService, defaultService, flavor, pras);
            if (service != null) {
                DocPrintJob job = service.createPrintJob();
                FileInputStream fis = new FileInputStream(filename);
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                job.print(doc, pras);
                Thread.sleep(10000);
            }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void calcTotalPay(){
        //this allow to change to 2 decimals
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        //-------------------------------------------------------------
        //getting firstName
        String first = firstName.getText();
      if (firstName.getText() == null || firstName.getText().trim().isEmpty()) {
            // your code here
             first="-NO NAME-";
             }
        String last = lastName.getText();
         if (lastName.getText() == null || lastName.getText().trim().isEmpty()) {
            // your code here
             last="-NO NAME-";
             }
         //------------------------------------------------------------
        //reading from textfeild
        String amoutHoursWorked = hoursWorked.getText();
        hoursWorked.setStyle("-fx-text-fill: black;");
            if (hoursWorked.getText() == null || hoursWorked.getText().trim().isEmpty()) {
            // your code here
             amoutHoursWorked="0";
             }
            double hours = Double.parseDouble(amoutHoursWorked); 
                while(hours < 0 || hours > 1000){
                  amoutHoursWorked = hoursWorked.getText();
                  hoursWorked.setStyle("-fx-text-fill: red;");
                    if(hoursWorked.getText() == null || hoursWorked.getText().trim().isEmpty()) {
                         amoutHoursWorked="0";
                    }
        //makign it double to use in calc 
            hours = Double.parseDouble(amoutHoursWorked); 
                System.out.println("ERROR");
                break;
        }
        //------------------------------------------------------------
        String rateOfPay = payRate.getText();
          if (payRate.getText() == null || payRate.getText().trim().isEmpty()) {
            // your code here
             rateOfPay="0";
             }
        double payR = Double.parseDouble(rateOfPay); 
        //------------------------------------------------------------
        String taxAZ = taxRateAZ.getText();
        if (taxRateAZ.getText() == null || taxRateAZ.getText().trim().isEmpty()) {
            // your code here
             taxAZ="0";
             }
        double azTax = Double.parseDouble(taxAZ); 
        azTax = azTax/100;
        //------------------------------------------------------------
        String taxFed = taxRateFED.getText();
        if (taxRateFED.getText() == null || taxRateFED.getText().trim().isEmpty()) {
            // your code here
             taxFed="0";
             }
        double fedTax = Double.parseDouble(taxFed); 
        fedTax = fedTax/100;
        //------------------------------------------------------------
        String taxFica = ficaE.getText();
         if (ficaE.getText() == null || ficaE.getText().trim().isEmpty()) {
            // your code here
             taxFica="0";
             }
        double ficaTax = Double.parseDouble(taxFica); 
        ficaTax = ficaTax/100;
        //------------------------------------------------------------
        String taxMed = medE.getText();
        if (medE.getText() == null || medE.getText().trim().isEmpty()) {
            // your code here
             taxMed="0";
             }
        double medTax = Double.parseDouble(taxMed); 
        medTax = medTax/100;
        //------------------------------------------------------------
        String startingPeriod = startDayInput.getText();
          if (startDayInput.getText() == null || startDayInput.getText().trim().isEmpty()) {
            // your code here
             startingPeriod="-NO DATE ENTERED-";
             }
        //------------------------------------------------------------
        String endingPeriod = endDayInput.getText();
         if (endDayInput.getText() == null || endDayInput.getText().trim().isEmpty()) {
            // your code here
             endingPeriod="-NO DATE ENTERED-";
             }
        //------------------------------------------------------------
        //math 
        double totalPayT=hours*payR;
        double azTaxCut = totalPayT*azTax;
        double fedTaxCut = totalPayT*fedTax;
        double ficaTaxCut = totalPayT*ficaTax;
        double medTaxCut=totalPayT*medTax;
        double totalAfterTax=totalPayT-azTaxCut-fedTaxCut-ficaTaxCut-medTaxCut;
        //-------------------------------------------------------
        //here we convert it to String and we also setting the text to the value
       if(hours<0||hours>1000){
         System.out.println("Error Value");  
       
        firstNameLabel.setText("ERROR: MAKE SURE HOURS is between 0 - 1000");
        firstNameLabel.setStyle("-fx-text-fill: red; ");
        lastNameLabel.setText("");
        totalPay.setText("");
        azTaxDeduction.setText("");
        fedTaxDeduction.setText("");
        ficaTaxDeduction.setText("");
        medTaxDeduction.setText("");
        startDay.setText("");
        endDay.setText("");
        afterTax.setText("");
        
        }else {
        firstNameLabel.setText(first);
        firstNameLabel.setStyle("-fx-text-fill: black; ");
        lastNameLabel.setText(last);
        totalPay.setText(Double.toString(Double.parseDouble(twoDForm.format(totalPayT))));
        azTaxDeduction.setText(Double.toString(Double.parseDouble(twoDForm.format(azTaxCut))));
        fedTaxDeduction.setText(Double.toString(Double.parseDouble(twoDForm.format(fedTaxCut))));
        ficaTaxDeduction.setText(Double.toString(Double.parseDouble(twoDForm.format(ficaTaxCut))));
        medTaxDeduction.setText(Double.toString(Double.parseDouble(twoDForm.format(medTaxCut))));
        startDay.setText(startingPeriod);
        endDay.setText(endingPeriod);
        afterTax.setText(Double.toString(Double.parseDouble(twoDForm.format(totalAfterTax))));
       }
        
    
    }
public void saving(){
       try (BufferedWriter bw = new BufferedWriter(new PrintWriter("users.doc"))) {
                                          
        bw.write("First Name:"+firstNameLabel.getText());
        bw.newLine();
        bw.write("Last Name:"+lastNameLabel.getText());
        bw.newLine();
                                                             //testing and playing with position
        bw.write("Total Pay: $"+totalPay.getText());
        bw.newLine();
        bw.write("AZ TAX: $"+azTaxDeduction.getText());
        bw.newLine();
        bw.write("FED TAX: $"+fedTaxDeduction.getText());
        bw.newLine();
        bw.write("FICA-E TAX: $"+ficaTaxDeduction.getText());
        bw.newLine();
        bw.write("MED-E TAX: $"+medTaxDeduction.getText());
        bw.newLine();
        bw.write("Begning Period: "+startDay.getText());
        bw.newLine();
        bw.write("Ending Period: "+endDay.getText());
        bw.newLine();
        bw.write("This Check: $"+afterTax.getText());
    } catch (IOException e) {
        e.printStackTrace();

    }
}

}
