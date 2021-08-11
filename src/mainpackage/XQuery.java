package mainpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import com.saxonica.xqj.SaxonXQDataSource;

public class XQuery {
	
	public static void main(String[] args){
		
		System.out.println("\n**************** SELECTOR ****************");
		
		try {
	         execute();
	      }
		catch (FileNotFoundException e) {
	         e.printStackTrace();
	      }
		catch (XQException e) {
	         e.printStackTrace();
	      }
		catch (IOException e) {
	         e.printStackTrace();
	      }
	}

   private static void execute() throws FileNotFoundException, XQException, IOException{
	   
      InputStream inputStream = new FileInputStream(new File("./files/movie_filters.xqy"));
      XQDataSource ds = new SaxonXQDataSource();
      XQConnection conn = ds.getConnection();
      XQPreparedExpression exp = conn.prepareExpression(inputStream);
      XQResultSequence result = exp.executeQuery();
      
      FileWriter fileWriter = new FileWriter("./files/movies_selected.xml");
      PrintWriter printWriter = new PrintWriter(fileWriter);

      String xml_result = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><catalog>";    
      while (result.next()) {
    	  xml_result = xml_result + result.getItemAsString(null);
      }
      xml_result = xml_result + "</catalog>";
      
      printWriter.print(xml_result);   
      printWriter.close();
      
      System.out.println("\nXML successfully created!");
        
   }	

}
