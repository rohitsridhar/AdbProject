/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adbproject;

/**
 *
 * @author rohit
 */
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import org.sqlite.SQLiteConfig;

public class Spatialite_queries {
    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {
        
        long startTime=0;
        long stopTime=0;
        double used,total;
        
        PrintStream out = new PrintStream(new FileOutputStream("src/adbproject/output_spatialite.txt"));
        System.setOut(out);
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
            Statement stmt = conn.createStatement();
            //stmt.setQueryTimeout(30); // set timeout to 30 sec.
            stmt.execute("SELECT load_extension('mod_spatialite')");
          

        FileReader reader = new FileReader("src/adbproject/S_Analysis-Spatialite");
         BufferedReader bufferedReader = new BufferedReader(reader);
         int i=0;
         String line;
         
         ResultSet rs=null;
        /*
        String sql="select distinct sk.name as ski_name, co.name as lake_name from ne_10m_lakes co,ski_areas_all sk where Distance(co.geometry,sk.geometry)<100;";
        rs=stmt.executeQuery(sql);
        while(rs.next()) {
                // read the result set
                String msg = "Ski name: ";
                msg += rs.getString("ski_name");
                System.out.println(msg);
                msg = "Lake Name: ";
                msg += rs.getString("lake_name");
                System.out.println(msg);
                
            }
         */
        // checking SQLite and SpatiaLite version + target CPU
        
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