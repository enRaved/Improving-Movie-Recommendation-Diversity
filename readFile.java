import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class readFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  //read from original file
	    String originalFilePath= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/ratings.txt";
	    File originalFile = new File(originalFilePath);
	    
	    String newFilePath= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsed_ratings.txt";
	    File parsedRatingFile= new File(newFilePath);
	    
	    
	    
	    try{
	    	
	    	Scanner sc = new Scanner(originalFile);
	    	while(sc.hasNext(""))
	    		System.out.println(sc.next("[^\\d.]"));
	    	
	    	//approach 1
//	    	sc.useDelimiter("{user id:",","," movie id:","rating:");
//	    	sc.split("{user id:",","," movie id:","rating:");
//	    	
//	    	int i=0;
//	    	int userId=0;
//	    	int movieId=0;
//	    	double movieRating=0;
	    	
	    	
	    	//approach 2
//	    	while(sc.hasN()){  //compare entries from file (user_id, movie_id,rating)
//	    	if(i%3==0)
//	    		userId=sc.nextInt();
//	    	if(i%3==1)
//	    		movieId=sc.nextInt();
//	    	if(i%3==2){
//	    		movieRating=sc.nextDouble();
//	    		System.out.println("For user: "+userId+"movie is"+movieId+"+movie is"+movieRating);
//	    	}
//	         
//	          i++;
//	          
//	        }
	    	
	    	
	    }catch(FileNotFoundException fe){
	    		
	    	System.out.println(fe);
	    }
	}

}
