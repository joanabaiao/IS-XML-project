package mainpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import generated.Catalog;
import generated.Movie;
import generated.Person;

public class Processor {
	
	private static Scanner scan = new Scanner(System.in);
	
    public static void main(String[] args) {
  
		System.out.println("\n*************** PROCESSOR ****************");
		
    	// UPLOAD
        File XMLfile = new File("./files/movies_selected.xml");
        File schema = new File("./files/movies.xsd");       
        Catalog catalog = jaxbXmlFileToObject(XMLfile, schema);         
          
        HashMap<String, Person> directorMap  = new HashMap<>();
        HashMap<String, Integer> rankingTree = new HashMap<>();
        
        // ADD MOVIES TO DIRECTORS
        for (Movie movie : catalog.getMovie()) { 	
        	for (Person director_person : movie.getDirector().getPerson()) { // itera os vários diretores de um filme
        		
        		Person director = directorMap.getOrDefault(director_person.getName(), new Person(director_person.getName()));
        		director.getMovies().add(movie);
        		directorMap.put(director.getName(), director);
        		
        		// Add best movie: the best movie is the one at index 0
        		director.getMovies().sort(Comparator.comparing(Movie::getTop250Rank));
        		rankingTree.put(director_person.getName(), director.getMovies().get(0).getTop250Rank().intValue());
        	}	     	
        }
       
        // LISTS
        DirectorsList directors_list = new DirectorsList();
        directors_list.getDirectors().addAll(directorMap.values());
        
        List<Map.Entry<String, Integer>> rank_list = new ArrayList<>(rankingTree.entrySet());
        rank_list.sort(Comparator.comparingInt(Map.Entry::getValue));
            
        
        // SELECT N DIRECTORS
        int N = 0;
		boolean valid = false;
		while (!valid){
			System.out.println("\nNumber of directors N (0-" + rank_list.size() + "): ");
			try {
				N = scan.nextInt();
				if (N >= 0 && N <= rank_list.size())
					valid = true;
				else {
					System.out.println("ERROR: Number not valid!");
				}
			}
			catch (Exception e) {
				System.out.println("ERROR: Number not valid!");
			}
			scan.nextLine();
		}

        rank_list = rank_list.subList(0, N);

        
        // STATISTICS
        
        // Number of directors
        directors_list.getStatistics().setNumber_directors(directors_list.getDirectors().size()); 
        
        // Number of movies
        directors_list.getStatistics().setNumber_movies(catalog.getMovie().size()); 
        
        // Ranking of each director
        for (Map.Entry<String, Integer> entry : rank_list) {
        	directors_list.getStatistics().getBestDirectors().add(new Ranking(entry.getKey(), entry.getValue()));
        }
        
        
        // CONVERT CATALOG
        jaxbObjectToXML(directors_list);

    }
    

    // CONVERT XML FILE TO OBJECT
 	public static Catalog jaxbXmlFileToObject(File XMLfile, File XSDfile) {
 		
 		JAXBContext jc;
 		Catalog catalog = new Catalog();
 		
 		try {
 			
 			jc = JAXBContext.newInstance(Catalog.class);
 	        Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();         
 	        SchemaFactory schmFact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
 	        File schemaFile = XSDfile;
 	        Schema schemaXSD;
 			
 	        try {
 	        	schemaXSD = schmFact.newSchema(schemaFile);
 				jaxbUnmarshaller.setSchema(schemaXSD);
 			} 
 	        catch (SAXException e) {
 				e.printStackTrace();
 			}
 	        
 	        catalog = (Catalog) jaxbUnmarshaller.unmarshal(XMLfile);
 	        
 	        System.out.println("\nXML successfully converted to object!");
 	
 		}
 		catch (JAXBException e) {
 			e.printStackTrace();
 		}
 		
 		return catalog;
 	}
 	

 	// CONVERT OBJECT TO XML FILE
 	public static void jaxbObjectToXML(DirectorsList list) {
 	    try {
 	        //Create JAXB Context
 	        JAXBContext jaxbContext = JAXBContext.newInstance(DirectorsList.class);
 	         
 	        //Create Marshaller
             Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
  
             //Required formatting
             jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 	 
             //Store XML to File
 	        File file = new File("./files/movies_processed.xml");
 	         
 	        //Writes XML file to file-system
 	        jaxbMarshaller.marshal(list, file); 
 	        
 	        System.out.println("\nObject successfully converted to XML!");
 	    } 
 	    
 	    catch (JAXBException e) {
 	        e.printStackTrace();
 	    }
 	} 

}
