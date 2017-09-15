/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



// $example on$
import scala.Tuple2;

import org.apache.spark.api.java.*;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.util.logging.*;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

import org.apache.spark.mllib.recommendation.Rating;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.spark.SparkConf;

// $example off$

public class recommend {
  public static void main(String[] args) {
    // $example on$
  //  SparkConf conf = new SparkConf().setAppName("ALS-spark-project");
    SparkConf conf = new SparkConf().setAppName("ALS-spark-project").setMaster("local[2]").set("spark.executor.memory","1g");
    JavaSparkContext jsc = new JavaSparkContext(conf);

    // Load and parse the data
   // String path = "data/mllib/als/test.data";
   // String path= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/ratings.txt";
    String path= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsedRatings.txt"; 
    		
    JavaRDD<String> data = jsc.textFile(path);
    JavaRDD<Rating> ratings = data.map(s -> {
      String[] sarray = s.split(",");
      return new Rating(Integer.parseInt(sarray[0]),
        Integer.parseInt(sarray[1]),
        Double.parseDouble(sarray[2]));
    });

    // Build the recommendation model using ALS
    int rank = 10;
    int numIterations = 10;
    MatrixFactorizationModel model = ALS.train(JavaRDD.toRDD(ratings), rank, numIterations, 0.01);
  // System.out.println("Users: "+model.recommendUsers(1, numIterations));
   
   

    // Evaluate the model on rating data
    JavaRDD<Tuple2<Object, Object>> userProducts =
      ratings.map(r -> new Tuple2<>(r.user(), r.product()));
    
    
    
    JavaPairRDD<Tuple2<Integer, Integer>, Double> predictions = JavaPairRDD.fromJavaRDD(
      model.predict(JavaRDD.toRDD(userProducts)).toJavaRDD()
          .map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating()))
    );
    JavaRDD<Tuple2<Double, Double>> ratesAndPreds = JavaPairRDD.fromJavaRDD(
        ratings.map(r -> new Tuple2<>(new Tuple2<>(r.user(), r.product()), r.rating())))
      .join(predictions).values();
    double MSE = ratesAndPreds.mapToDouble(pair -> {
      double err = pair._1() - pair._2();
      return err * err;
    }).mean();
    System.out.println("Mean Squared Error = " + MSE);
    
  
    
    //ravee: pass test data
    String pathTestData= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/test_data.txt";
    File ftest = new File(pathTestData);
    try{
    	Scanner sc = new Scanner(ftest);
    	
    	while(sc.hasNextLine()){  //compare entries from file (user_id, movie_id,rating)
    	
    		try{
          String line=sc.nextLine();
          String[] dataArray=line.split(",");
         
          
          //finding what type of movie this is
          String pathMovieData= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsedMovies.txt";
         
          File fmovie = new File(pathMovieData);
          Scanner movieScanner1=new Scanner(fmovie);
          StringBuilder movieType= new StringBuilder();
          while(movieScanner1.hasNextLine()){
          	String movieInfo= movieScanner1.nextLine();
          	if(Integer.parseInt(movieInfo.split(",")[0])==Integer.parseInt(dataArray[1]))
          	movieType.append(movieInfo);
          }// finding ends here
          
          
          double actualRating= Double.parseDouble(dataArray[2]);
          double predictedRating= model.predict(Integer.parseInt(dataArray[0]), Integer.parseInt(dataArray[1]));//
          
         
          
          System.out.println("Actual Rating that users similar to user "+dataArray[0]+"  gave for movie "+dataArray[1]+" is: "+actualRating);
          System.out.println("Predicted Rating for users similar to user "+dataArray[0]+" is: "+predictedRating);
          
          //diverse recommendation
          if(actualRating-predictedRating>2) {
        	  System.out.println("But movie "+dataArray[1]+ " is: "+movieType+" and system predicts that user "+dataArray[0]+ " might be curious about this!");
          	 // System.out.println("But... ");
        	  System.out.println(dataArray[1]+" is a diverse movie that User "+dataArray[0]+" may like!");
          }
          //normal recommendation
          if(predictedRating>3.5) System.out.println("User "+dataArray[0]+" may like "+dataArray[1]+" according to his/her taste!");
          movieScanner1.close();
          }catch(NoSuchElementException e){System.out.print("Could not retrieve!");}
    		
        }
    	 
    }catch(FileNotFoundException fe){
    		
    	System.out.println(fe);
    }
    
    
    //ravee: check for users here
    System.out.println("Prediction for user 1 : "+model.predict(1, 17));
    String pathMovieData= "/Users/raveekhandagale/Documents/XML_and_WI/Data set/Douban/parsedMovies.txt";
    try{
    File fmovie = new File(pathMovieData);
    Scanner movieScanner=new Scanner(fmovie);
 //   ArrayList<String> movieArray=new ArrayList<String>();
    while(movieScanner.hasNextLine()){
    	String movieInfo= movieScanner.nextLine();
    	if(Integer.parseInt(movieInfo.split(",")[0])==17)
    	System.out.println("But movie 17 is: "+movieInfo+"User 17 does not like that movie!");
    	
//    	int i=0;
//    	if(Integer.parseInt(movieInfo.split(",")[0])==1){
//    		
//    		while(movieInfo.split(",")[i]!=null) movieArray.add(movieInfo.split(",")[i]);
//    		i++;
//    	}
    	
    	
    }//while ends here
    movieScanner.close();
    }catch(FileNotFoundException e){System.out.println(e);}
    
   
    // Save and load model
   
   // model.save(jsc.sc(), "target/tmp/myCollaborativeFilter");//myCollaborativeFilter
  
    
    
//    MatrixFactorizationModel sameModel = MatrixFactorizationModel.load(jsc.sc(),
//      "target/tmp/myCollaborativeFilter");//myCollaborativeFilter

    
    // $example off$

    jsc.stop();
  }
}
