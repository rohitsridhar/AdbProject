/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



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

         stmt = c.createStatement();
         startTime = System.currentTimeMillis();
         ResultSet rs = stmt.executeQuery( "select st_area(geom) from ne_110m_lakes;" );
         stopTime = System.currentTimeMillis();
         while ( rs.next() ) {
            
            double  area = rs.getDouble("st_area");
             
            System.out.println( "Area = " + area );
           
            System.out.println();
         }
         rs.close();
         stmt.close();
         c.close();
       } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
       }
       //System.out.println("Operation done successfully");
      
      long elapsedTime = stopTime - startTime;
      System.out.println("Time taken:"+elapsedTime+"ms");
    }
}
