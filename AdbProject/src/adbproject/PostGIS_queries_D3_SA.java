/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author rohit
 */
public class PostGIS_queries_D3_SA {
     public static void main(String[] args) throws FileNotFoundException {
        
        // TODO code application logic here
      
        long startTime=0;
        long stopTime=0;
        Connection c = null;
       Statement stmt = null;
       PrintStream out = new PrintStream(new FileOutputStream("src/adbproject/output_PostGIS_D3_SA.txt"));
       
        System.setOut(out);
       try {
       Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/postgis_22_sample",
            "postgres", "rohit123");
         c.setAutoCommit(false);
         //System.out.println("Opened database successfully");
         double total;
         double used;
          


         FileReader reader = new FileReader("src/adbproject/S_Analysis-PostGIS_D3");
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         stmt = c.createStatement();
         ResultSet rs=null;
            while ((line = bufferedReader.readLine()) != null) {
                i++;
                long sum=0;
                for(int cnt=0; cnt<5; cnt++){
                    
                
                startTime = System.currentTimeMillis();
                
                rs = stmt.executeQuery( line );
                
                stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                sum+=elapsedTime;
                }
                long avgTime=sum/5;
                System.out.println("Time taken for query " +i+ ": " +avgTime+" ms");
                System.out.println();
                rs.close();
                
            }        
         
         stmt.close();
         c.close();
         reader.close();
       } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
       }
       //System.out.println("Operation done successfully");
      
      
    }
    
}
