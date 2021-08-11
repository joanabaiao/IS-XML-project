package mainpackage;

import java.util.Scanner;

public class Main {
	
	private static Scanner scan = new Scanner(System.in);
	
    public static void main(String[] args) {
    	
    	System.out.println("**************** Movie Catalog ****************");
    	
    	System.out.println("\n1- Java\n2- XQuery");

    	int option = 0;
		boolean valid = false;
		while (!valid){
			System.out.println("\nChoose the type of Selector:");
			try {
				option = scan.nextInt();
				if (option == 1 || option == 2)
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
        

		if (option == 1) {
			Selector.main(null);
		}		
		else {
			XQuery.main(null);
		}
		
    	Processor.main(null);
    	HTMLViewer.main(null);
    }
}
