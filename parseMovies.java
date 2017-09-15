import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class parseMovies {

	 public static void main(String[] args) {

	        try{
	        	//replace1 { with newline
	        	/*
	        	Path path = Paths.get("/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsedRatings.txt");
	        	Charset charset = StandardCharsets.UTF_8;

	        	String content = new String(Files.readAllBytes(path), charset);
	        	content = content.replaceAll("}", "\n");
	        	Files.write(path, content.getBytes(charset));
	        	*/
	        	//replace1 ends here
	        	
	        	//replace2 {user id: with "" and "  " with "" and  movie id: with "" and rating: with ""
	        	Path path = Paths.get("/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsedMovies.txt");
	        	Charset charset = StandardCharsets.UTF_8;

	        	String content = new String(Files.readAllBytes(path), charset);
	        	
	        	content = content.replaceAll(":", ",");
	        	content = content.replaceAll(" ", "");
	        	content = content.replaceAll("\\{", "");
	        	content = content.replaceAll(",}", "");
	        	
	        	Files.write(path, content.getBytes(charset));
	        	//replace 2 ends here
	System.out.println("done");
	        }catch(IOException e){
	        e.printStackTrace();
	        }
	    }
}
