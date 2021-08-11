package mainpackage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class HTMLViewer {
	
    public static void main(String[] args) {
    	
    	System.out.println("\n************** HTML VIEWER ***************");
    	
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Source xml = new StreamSource("./files/movies_processed.xml");
            Source xsl = new StreamSource("./files/transformer.xsl");

            OutputStream os = new FileOutputStream("./files/movie_catalog.html");
            Transformer transform = transformerFactory.newTransformer(xsl);
            transform.transform(xml, new StreamResult(os));
            
            System.out.println("\nHTML created!");
        }   
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    
}
