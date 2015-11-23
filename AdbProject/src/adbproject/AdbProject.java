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
public class AdbProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // TODO code application logic here
        long startTime = System.currentTimeMillis();
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
         ResultSet rs = stmt.executeQuery( "SELECT name FROM ne_110m_lakes;" );
         while ( rs.next() ) {
            
            String  name = rs.getString("name");
             
            System.out.println( "NAME = " + name );
           
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
       long stopTime = System.currentTimeMillis();
      long elapsedTime = stopTime - startTime;
      System.out.println("Time taken:"+elapsedTime+"ms");
    }
}
