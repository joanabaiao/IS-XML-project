package mainpackage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import generated.Catalog;

public class Selector {
	
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {

		
		System.out.println("\n**************** SELECTOR ****************");
		
    	// UPLOAD
        File XMLfile = new File("./files/movies.xml");
        File schema = new File("./files/movies.xsd");       
        Catalog catalog = jaxbXmlFileToObject(XMLfile, schema);         
        
        // FILTERS 
        ArrayList<String> filters = new ArrayList<String>();
        filters.addAll(Arrays.asList("Year", "Director", "Rating", "Votes", "Rank Position", "Genre", "Title", "Actor"));          
        
        System.out.println("\n================= Filters ================");
        for (int i=0; i<filters.size(); i++) {
        	System.out.println((i+1) + "- "+ filters.get(i));
        }
        
        System.out.println("\nChoose the filters (separated by ,):");
        String[] chosen_options = scan.nextLine().split(", "); 
        ArrayList<String> chosen_filters = options_validation(chosen_options, filters.size());
       
        //System.out.println("\nChosen filters: " + chosen_filters);
        
        for (int i=0; i<chosen_filters.size(); i++) {
        	switch (chosen_filters.get(i)) {
        	
		    	// Year
		    	case "1":
		    		
		    		System.out.println("\nYear (preceded by >, < or =): ");
		    		String[] condition1 = scan.nextLine().split(" ");
		    		
            		Date date = new Date();
            		SimpleDateFormat format = new SimpleDateFormat("yyyy");
            		String current_year = format.format(date);
            		
		    		if (condition1.length > 1 && isInt(condition1[1]) == true && Integer.valueOf(condition1[1]) <= Integer.valueOf(current_year) && Integer.valueOf(condition1[1]) >= 1900)  {
		    			
		    			if (condition1[0].equals(">"))   				
		    				catalog.getMovie().removeIf(movie -> {
		    					if (movie.getYear() == null) return true;
		                        return movie.getYear().intValue() <= Integer.parseInt(condition1[1]);
		                    });
		    			else if (condition1[0].equals("<"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getYear() == null) return true;
		                        return movie.getYear().intValue() >= Integer.parseInt(condition1[1]);
		                	});
		    			else if (condition1[0].equals("="))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getYear() == null) return true;
		                        return movie.getYear().intValue() != Integer.parseInt(condition1[1]);
		                	});
		            }
		    		else {
		    			System.out.println("\nInvalid input!");
		    		}
		    		break;
		    	
				// Director
		    	case "2":
		    		System.out.println("\nDirector name: ");
		    		String condition2 = scan.nextLine();	    		
		    		
		            catalog.getMovie().removeIf(movie -> !movie.getDirector().getListDirectors().contains(condition2));
		            break;
	            	
                // Rating
		    	case "3":
		    		System.out.println("\nRating (preceded by > or <): ");
		    		String[] condition3 = scan.nextLine().split(" "); 

		    		if (condition3.length > 1 && isFloat(condition3[1]) == true && Float.valueOf(condition3[1]) <= 10 && Float.valueOf(condition3[1]) >= 0) {
		    			
		    			if (condition3[0].equals(">"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getRating() == null) return true;
		                        return movie.getRating().floatValue() <= Float.parseFloat(condition3[1]);
		                    });
		    			else if (condition3[0].equals("<"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getRating() == null) return true;
		                        return movie.getRating().floatValue() >= Float.parseFloat(condition3[1]);
		                	});
		            }
		    		else {
		    			System.out.println("\nInvalid input!");
		    		}
		    		break;
		    		
	            // Votes
		    	case "4":
		    		System.out.println("\nVotes (preceded by > or <): ");
		    		String[] condition4 = scan.nextLine().split(" ");

		    		if (isInt(condition4[1]) == true && Integer.valueOf(condition4[1]) >= 0) {
		    			if (condition4[0].equals(">"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getVotes() == null) return true;
		                        return movie.getVotes().intValue() < Integer.parseInt(condition4[1]);
		                    });
		    			else if (condition4[0].equals("<"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getVotes() == null) return true;
		                        return movie.getVotes().intValue() > Integer.parseInt(condition4[1]);
		                	});
		            }
		    		else {
		    			System.out.println("\nInvalid input!");
		    		}
		    		break;
		    		
	            // Rank position
		    	case "5":
		    		System.out.println("\nRank position (preceded by > or <): ");
		    		String[] condition5 = scan.nextLine().split(" ");

		    		if (condition5.length > 1 && isInt(condition5[1]) == true && Integer.valueOf(condition5[1]) <= 250 && Integer.valueOf(condition5[1]) >= 0) {
		    			if (condition5[0].equals(">"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getTop250Rank() == null) return true;
		                        return movie.getTop250Rank().intValue() < Integer.parseInt(condition5[1]);
		                    });
		    			else if (condition5[0].equals("<"))
		    				catalog.getMovie().removeIf(movie -> {
		                        if (movie.getTop250Rank() == null) return true;
		                        return movie.getTop250Rank().intValue() > Integer.parseInt(condition5[1]);
		                	});
		            }
		    		else {
		    			System.out.println("\nInvalid input!");
		    		}
		    		break;
		    	
	    		// Genre
            	case "6":
            		System.out.println("\nGenre (preceded by ANY or ALL): ");
            		String[] condition6 = scan.nextLine().split(" ");
            		List<String> chosen_genres = splitAndStrip(condition6);	
            		
            		if (condition6[0].toUpperCase().equals("ANY")) {
            			catalog.getMovie().removeIf(movie -> {
            				return !movie.getGenres().getItem().stream().anyMatch(genre -> chosen_genres.contains(genre));
            			});
            		}
            		else if (condition6[0].toUpperCase().equals("ALL")) {
            			catalog.getMovie().removeIf(movie -> {
            				return !movie.getGenres().getItem().stream().allMatch(genre -> chosen_genres.contains(genre));
            			});
            		}
		    		else {
		    			System.out.println("\nInvalid input!");
		    		}
                    break;
                
                // Title
            	case "7":
            		System.out.println("\nTitle: ");
            		String condition7 = scan.nextLine();
            		
                    catalog.getMovie().removeIf(movie -> !movie.getTitle().equals(condition7));
                    break;
                
                // Actor
            	case "8":
            		System.out.println("\nActor: ");
            		String condition8 = scan.nextLine();
            		
                    catalog.getMovie().removeIf(movie -> !movie.getCast().getListActors().contains(condition8));
                    break;
        	}
        }

        System.out.println("\n" + catalog.toString());
        
        jaxbObjectToXML(catalog);

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
	public static void jaxbObjectToXML(Catalog rg) {
	    try {
	        //Create JAXB Context
	        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
	         
	        //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	 
            //Store XML to File
	        File file = new File("./files/movies_selected.xml");
	         
	        //Writes XML file to file-system
	        jaxbMarshaller.marshal(rg, file); 
	        
	        System.out.println("\nObject successfully converted to XML!");
	    } 
	    
	    catch (JAXBException e) {
	        e.printStackTrace();
	    }
	} 
	
	// INTEGER VALIDATION
	public static boolean isInt(String request) {
		boolean is_integer = true;
			try {
				Integer.parseInt(request);
			} 
			catch (NumberFormatException e) {
				is_integer = false;
			}
		return is_integer;
	}
	
	// FLOAT VALIDATION
	public static boolean isFloat(String request) {
		boolean is_float = true;
			try {
				Float.parseFloat(request);
			} 
			catch (NumberFormatException e) {
				is_float = false;
			}
		return is_float;
	}
	
	// OPTIONS VALIDATION
	public static ArrayList<String> options_validation(String[] chosen_options, int n_filters) {
			
		ArrayList<String> chosen_filters = new ArrayList<String>();
	
		for (int i=0; i<chosen_options.length; i++) {
			if (isInt(chosen_options[i]) && chosen_filters.contains(chosen_options[i]) == false 
					&& Integer.valueOf(chosen_options[i]) > 0 && Integer.valueOf(chosen_options[i]) <= n_filters) {
				chosen_filters.add(chosen_options[i]);
			}		
		}
		return chosen_filters;
	}
	
	// SPLIT AND STRIP
    public static List<String> splitAndStrip(String[] condition) {
    	
		List<String> list = new ArrayList<String>(Arrays.asList(condition));
		list.remove(0); 
		
		return list.stream().collect(Collectors.toList());
    }
}

