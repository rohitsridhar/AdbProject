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
public class PostGIS_queries_small {
    public static void main(String[] args) throws FileNotFoundException {
        
        // TODO code application logic here
      
        long startTime=0;
        long stopTime=0;
        Connection c = null;
       Statement stmt = null;
       PrintStream out = new PrintStream(new FileOutputStream("src/adbproject/output_postgis_small.txt"));
       PrintStream out1 = new PrintStream(new FileOutputStream("src/adbproject/output_postgis_small_topo.txt"));
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
          


         FileReader reader = new FileReader("src/adbproject/S_Analysis-Postgis_small");
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         stmt = c.createStatement();
         ResultSet rs=null;
            while ((line = bufferedReader.readLine()) != null) {
                i++;
                startTime = System.currentTimeMillis();
                total  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                rs = stmt.executeQuery( line );
                used  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Time taken for query " +i+ ": " +elapsedTime+" ms");
                System.out.print("Memory used for execution of query " +i+ ": ");
                System.out.printf("%.2f",(used-total));
                System.out.println(" MB");
                System.out.println();
                rs.close();
                
            }        
         System.setOut(out1);
         FileReader reader1 = new FileReader("src/adbproject/Topo_Postgis_small");
         BufferedReader bufferedReader1 = new BufferedReader(reader1);
         i=0;
         String line1;
         
         ResultSet rs1=null;
            while ((line1 = bufferedReader1.readLine()) != null) {
                i++;
                startTime = System.currentTimeMillis();
                total  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                rs1 = stmt.executeQuery( line1 );
                used  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Time taken for query " +i+ ": " +elapsedTime+" ms");
                System.out.print("Memory used for execution of query " +i+ ": ");
                System.out.printf("%.2f",(used-total));
                System.out.println(" MB");
                System.out.println();
                rs1.close();
                
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
