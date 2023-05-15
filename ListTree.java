package module1_1;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.io.File;



public class ListTree {

	static File directory=null;
	static int fileCount;//=directory.list().length;
	
	public static void main(String[] args) throws Exception{
		
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
	    System.out.println("Enter file path: ");

	    String userInput = myObj.nextLine();  // Read user input
		
		Path currentPath = Paths.get(userInput);

		listDir(currentPath, 0, userInput);
		
	}

	public static void listDir(Path path, int depth, String pathStr) throws Exception {
		
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		
		//if directory, list the files, and traverse down inside each of those
		if (attr.isDirectory()) {
			
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			
			directory = new File(pathStr);
			fileCount = directory.list().length;
			
			System.out.println(spacesForDepth(depth) + " > " + path.getFileName() + " (Number of files/directories:  " + fileCount + ", Folder Size: " + folderSize(directory) + ")");
			
			for(Path tempPath: paths) {
				listDir(tempPath,depth + 1, tempPath.toString());
			}
			
		} else {
			
			directory = new File(pathStr);
			System.out.println(spacesForDepth(depth) + " - - " + path.getFileName() + " (File Size: " + directory.length() + ")");
		}
		
	}
	
	//Depth setter for printing
	public static String spacesForDepth(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i= 0; i < depth; i++) {
			builder.append("  ");
		}
		return builder.toString();
	}
	
	
	//Recursion algorithm I found to help find directory sizes
	
	public static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}
	
}
