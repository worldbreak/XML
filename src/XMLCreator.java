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



        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "root" );

        Element author1 = root.addElement( "author" )
                .addAttribute( "name", "James" )
                .addAttribute( "location", "UK" )
                .addText( "James Strachan" );

        Element author2 = root.addElement( "author" )
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
