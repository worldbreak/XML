/**
 * Created by MIREK on 24.1.2015.
 */

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import java.io.FileWriter;
import java.util.Random;

public class XMLCreator {
    String day = "01";
    String month = "12";
    String year = "2012";
    MatlabImport matlabImport = new MatlabImport("0170",day,month,year);

    public static int getPoisson(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }


    public Element createVehicle(Element element, int actualCar, double time, int iTime, int lane){
        Random rnd = new Random();
        double randomSpeed;
        double speed;
        do {
            randomSpeed = rnd.nextGaussian();

        }
        while (randomSpeed>0 || randomSpeed<-5);

        speed = randomSpeed + matlabImport.getSpeed(iTime, lane) / 3.6;
        if (speed>36) speed=36;
         element.addAttribute("id", Integer.toString(actualCar))
                .addAttribute("type", "type1")
                .addAttribute("route", "wholeHighway")
                .addAttribute("depart", Double.toString(time))
                .addAttribute("departPos", Double.toString(95))
                .addAttribute("departSpeed", Double.toString(speed))
                .addAttribute("departLane",Integer.toString(lane));
         ;

        return element;

    }

    public Document createRouteDocument() {
        Document document = DocumentHelper.createDocument();
        Element routes = document.addElement( "routes" );


        Element vType = routes.addElement( "vType" )
                .addAttribute( "id" , "type1")
                .addAttribute("accel", Double.toString(0.8))
                .addAttribute( "decel", Double.toString(4.5))
                .addAttribute( "sigma", Double.toString(0.5))
                .addAttribute( "length", Double.toString(5))
                .addAttribute( "maxSpeed", Double.toString(70))
                ;

        Element route = routes.addElement( "route" )
                .addAttribute("id", "wholeHighway")
                .addAttribute( "color", "1,1,0" )
                .addAttribute("edges", "7b 8b 9b 10b 11b");


        int actualCar = 0;

        for (int i=0;i<1440;i++){
            double time = 60*i;
            for (int j=0;j<matlabImport.getNumCars(i,0);j++) {
                time = time+getPoisson(60/matlabImport.getNumCars(i,0));
                Element vehicle = routes.addElement("vehicle");
                vehicle = createVehicle(vehicle,actualCar,time,i,0);
                actualCar++;
            }

            for (int k=0;k<matlabImport.getNumCars(i,1);k++) {
                time = time+getPoisson(60/matlabImport.getNumCars(i,1));
                Element vehicle = routes.addElement("vehicle");
                vehicle = createVehicle(vehicle,actualCar,time,i,1);
                actualCar++;
            }
       //  pocet = pocet + matlabImport.getNumCars(i,1);

        }
       // System.out.println(pocet);




        return document;
    }






    public Document createAdditionalDocument() {
        Document document = DocumentHelper.createDocument();
        Element additional = document.addElement( "additional" );

        Element loop = additional.addElement("inductionLoop")
                .addAttribute("id","myLoop1")
                .addAttribute("lane","3a_1")
                .addAttribute("pos", "10")
                .addAttribute("freq", "60")
                .addAttribute("file", "3a_1.xml");

        return document;
    }


    public XMLCreator(){

        try {
            FileWriter outRou = new FileWriter( "auta.rou.xml" );
            Document RouteDocument = createRouteDocument();

            FileWriter outAdd = new FileWriter( "additional.xml" );
            Document AddDocument = createAdditionalDocument();

            OutputFormat format = OutputFormat.createPrettyPrint();

            XMLWriter writerRou = new XMLWriter( outRou, format );
            writerRou.write(RouteDocument);
            writerRou.close();

            XMLWriter writerAdd = new XMLWriter( outAdd, format );
            writerAdd.write(AddDocument);
            writerAdd.close();

        }
        catch (Exception e){
            System.out.println(e);
        }

    }


   public static void main(String[] args) {
    new XMLCreator();
   }

}
