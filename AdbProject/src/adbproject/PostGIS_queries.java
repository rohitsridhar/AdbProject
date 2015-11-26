/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;
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
public class PostGIS_queries {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        long startTime=0;
        long stopTime=0;
        Connection c = null;
       Statement stmt = null;
       try {
       Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/postgis_22_sample",
            "postgres", "rohit123");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");
         
         FileReader reader = new FileReader("src/adbproject/queries.txt");
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         stmt = c.createStatement();
         ResultSet rs=null;
            while ((line = bufferedReader.readLine()) != null) {
                i++;
                startTime = System.currentTimeMillis();
                rs = stmt.executeQuery( line );
                stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Time taken for query" +i+ ":" +elapsedTime+"ms");
                rs.close();
                
            }        
         
         
         
         /*while ( rs.next() ) {
            
            String  name = rs.getString("least");
            String name2=  rs.getString("greatest");
             
            //System.out.println( "Country1 = " + name );
            //System.out.println( "Country2 = " + name2 );
           
            //System.out.println();
         }
         * 
         */
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
