/**
 * Created by MIREK on 24.1.2015.
 */

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;

public class XMLCreator {

    public Document createDocument() {
        MatlabImport matlabImport = new MatlabImport("0170","01");
        //for (int i=0;i<matlabImport.getAllCarsInDay())





        Document document = DocumentHelper.createDocument();
        Element routes = document.addElement( "routes" );

        Element vType = routes.addElement( "vehicle" )
                .addAttribute( "accel", Double.toString(0.8))
                .addAttribute( "location", "UK" )
                .addText( "James Strachan" )
                ;


        Element author2 = routes.addElement( "author" )
                .addAttribute( "name", "Bob" )
                .addAttribute( "location", "US" )
                .addText( "Bob McWhirter" );

        return document;
    }

    public XMLCreator(){

        try {
            FileWriter out = new FileWriter( "foo.xml" );
            Document document;
            document = createDocument();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( out, format );
            writer.write( document );
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }


   public static void main(String[] args) {
    new XMLCreator();
   }

}
