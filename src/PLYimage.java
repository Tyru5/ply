// Tyrus Malmstrom
// CS410 :: PA1
// AUGUST 20,2016


/*
 * THIS CLASS IS TO READ THE PLY FILE (IMAGE)
 */


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class PLYimage {

	// for debugging purposes:
	private static final boolean DEBUG = false;
	private static final String KCOMMENT = "comment";
	private static final String KPROPERTY = "property";
	private static final String ENDHEADER = "end_header";

	// class instance variables:
	private String fileHeader;
	private String fileType;
	private int amountOfComments = 0;
	private String[] comments = new String[10];
	private String[] properties = new String[5];
	private int amountOfProps = 0;
	private int verts = 0;
	private int faces = 0;
	private double[] vertices;
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;

	// constructor:
	public PLYimage(){
		// nothing as of the moment.
	}

	public int readPLY(String fileName) throws IOException{

		String line;
		Scanner br = new Scanner( new File(fileName) );
		// according to the documentation, the default buffer size for BufferedReader is 8192 characters.

		// The header of both ASCII and binary files is ASCII text. Only the numerical data that follows the header is different between the two versions.
		fileHeader = br.nextLine();
		// String updateString = fileHeader.trim();
		if( fileHeader.equals("ply") ){
			// System.out.println("The file heading is: " + fileHeader);
		}else{
			System.out.println("Sorry, the file contains the wrong file header!");
			return -1;
		}

		// The second line indicates which variation of the PLY format this is. It should be one of:
		fileType = br.nextLine();
		if(DEBUG)System.out.println("The file type is of: " + fileType);

		// maybe comments, maybe not.
		do{
			line = br.nextLine();
			if(DEBUG)System.out.println("this is the line after the fileType: " + line);
			comments = line.split(" ", 2);
			if(comments[0].equals(KCOMMENT)) amountOfComments++;
		}while( removeComments(comments[0]) );

		if(amountOfComments != 0)System.out.println("There were " + amountOfComments + " comment(s) in the file.");
		// else System.out.println("There were no comments in the .ply file!");

		// what type of element?
		String[] element = line.split(" ", 3);
		verts = Integer.parseInt(element[2]);
		System.out.println("The amount of vertex elements in the file are: " + verts);

		// getting all the properties from the vertex element(s):
		do{
			line = br.nextLine();
			if(DEBUG)System.out.println("this is the line after the fileType: " + line);
			properties = line.split(" ", 2);
			if( properties[0].equals(KPROPERTY) ) amountOfProps++;
		}while( getProperties(properties[0]) );

		System.out.println("The amount of properties in the file are: " + amountOfProps);


		String[] fazes = line.split(" ");
		faces = Integer.parseInt( fazes[2] );
		System.out.println("The amount of faces elements in the file are: " + faces);

		line = br.nextLine();
		line = br.nextLine();

		if(line.equals(ENDHEADER)){
			// System.out.println("Reached the end of the header for the .ply file.");
		}

		vertices = new double[verts];
		for(int i = 0; i < verts; i++){
			vertices[i] = br.nextDouble();
		}

		x = sumXVerts(vertices);
		y = sumYVerts(vertices);
		z = sumZVerts(vertices);

		System.out.println();
		System.out.printf("The Mean Vertex is:\n[%f,\n%f,\n%f]", (x/445),(y/445),(z/445) );

		return 1;
	}

	private double sumXVerts(double[] arry){
		int count = 0;
		double total = 0.0;
		for( int i = 0; i < arry.length; i+=3){
			total += arry[i];
		}
		return total;
	}

	private double sumYVerts(double[] arry){
		int count = 0;
		double total = 0.0;
		for( int i = 1; i < arry.length; i+=3){
			total += arry[i];
		}
		return total;
	}

	private double sumZVerts(double[] arry){
		int count = 0;
		double total = 0.0;
		for( int i = 2; i < arry.length; i+=3){
			total += arry[i];
		}
		return total;
	}

	// method to deal with the comments of the ply file:
	private boolean removeComments(String currentLine){
		//Comments may be placed in the header by using the word comment at the start of the line. Everything from there until the end of the line should then be ignored
		if(DEBUG)System.out.println("In the removeComments method: " + currentLine);
		if( currentLine.toLowerCase().equals(KCOMMENT) ){
			return true;
		}
		return false;
	}

	// method to deal with the properties of the vertex element in the ply file:
	private boolean getProperties(String currentLine){
		if(DEBUG)System.out.println("In the getProperties method: " + currentLine);
		if( currentLine.toLowerCase().equals(KPROPERTY) ){
			return true;
		}
		return false;
	}


}
