/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Madhumitha
 */
public class PostGIS_queries_D1_SA {
    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author rohit
 */


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        // TODO code application logic here
      
        long startTime=0;
        long stopTime=0;
        Connection c = null;
       Statement stmt = null;
       PrintStream out = new PrintStream(new FileOutputStream("src/adbproject/output_PostGIS_D1_SA.txt"));
        //PrintStream out1 = new PrintStream(new FileOutputStream("src/adbproject/TOPoutput_vlarge_postgis.txt"));
        System.setOut(out);
       try {
       Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/postgres",
            "postgres", "nikhilagani");
         c.setAutoCommit(false);
         //System.out.println("Opened database successfully");
         double total;
         double used;
          


         FileReader reader = new FileReader("src/adbproject/S_Analysis-PostGIS_D1");
         
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         stmt = c.createStatement();
         ResultSet rs=null;
            while ((line = bufferedReader.readLine()) != null) {
                i++;long sum=0;
                for(int cnt=1;cnt<=5;cnt++)
                {
                startTime = System.currentTimeMillis();
                total  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                rs = stmt.executeQuery( line );
                used  = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
                stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                 sum=sum+elapsedTime;
                }
               long avgTime= sum/5;
               System.out.println("Time taken for query " +i+ ": " +avgTime+" ms");
                System.out.println();
                rs.close();
                
            }        
        /* FileReader reader1 = new FileReader("src/adbproject/Topo_Postgis_Vlarge");
            
         BufferedReader bufferedReader1 = new BufferedReader(reader1);

         String line1;
         ResultSet rs1=null; 
         System.setOut(out1);
         i=0;
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
                
            }  */
         
    
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


