/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author rohit
 */
public class Sqlite_queries_small {
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {
        PrintStream out = new PrintStream(new FileOutputStream("src/adbproject/output_sqlite_small.txt"));
        PrintStream out1 = new PrintStream(new FileOutputStream("src/adbproject/output_sqlite_small_topo.txt"));
        System.setOut(out);
        long startTime=0;
        long stopTime=0;
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection conn = null;
        try
        {
            // enabling dynamic extension loading
            // absolutely required by SpatiaLite
            SQLiteConfig config = new SQLiteConfig();
            config.enableLoadExtension(true);

            // create a database connection
            conn = DriverManager.getConnection("jdbc:sqlite:adbproj.sqlite",
            config.toProperties());
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            stmt.setQueryTimeout(30); // set timeout to 30 sec.
            double total,used;
            // loading SpatiaLite
            stmt.execute("SELECT load_extension('mod_spatialite')");
            
           FileReader reader = new FileReader("src/adbproject/S_Analysis-Spatialite_small");
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         stmt = conn.createStatement();
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
         FileReader reader1 = new FileReader("src/adbproject/Topo_Spatialite_small");
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
         conn.close();
         reader.close();   

        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
    
}
