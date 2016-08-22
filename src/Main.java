// Tyrus Malmstrom
// CS410 :: PA1
// AUGUST 20,2016

import java.io.IOException;


public class Main {

	private static final boolean DEBUG = false;
	
	private static void Usage(String alertMessage){
		System.out.println("Message:" + alertMessage);
		System.out.println("Usage for " + "is to read in the file and produce statistics on it.");
		System.out.println("These statistics range from average vector mean, to the max vector and min vector.");
		System.exit(-1);
	}
	
	
	public static void main(String[] args) throws IOException{
		// checking command line arguments:
		if( args.length != 1 ){
			Usage("Wrong number of parameters!");
		}else{
			// System.out.println("The file is: " + args[0]);
		}
		
		PLYimage image = new PLYimage();
		int res = image.readPLY(args[0]);
		if(DEBUG) System.out.println("Exit code: " +  res);
	}
	
	
}
