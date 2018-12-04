package projet.jee.framework.mvc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.util.Scanner;

public abstract class BddAccess  
{
	private static Connection connection=null;
	private static String url=null;
	private static String jdbcClass=null;
	
	
	static{ loadConfiguration();  }
	 
         
	public static Connection getConnection(){
		try 
		{
			if (connection!=null) return connection;
			loadConfiguration();
			return connection;
		} 
		catch (Exception e) { e.printStackTrace(); return null; }
	}
          
    public void deconnecter(){
        try{      
            getConnection().close();
        } 
        catch(Exception e){}
    }  
    
    private static void loadConfiguration()
    {
    	try 
    	{
    		Scanner scanner=new Scanner(new File("configuration.txt"));
    		jdbcClass = scanner.next().split("=")[1];
    		url = scanner.next().split("=")[1]; 

        	Class.forName (jdbcClass);  
        	connection = (DriverManager.getConnection(url, "root", "")); 
    		scanner.close();
		} 
    	catch (Exception e) { e.printStackTrace(); } 
    }  
}
 