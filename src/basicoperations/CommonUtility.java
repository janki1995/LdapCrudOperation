package basicoperations;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CommonUtility {

	private static final Logger logger = Logger.getLogger(CommonUtility.class.getName());

	public void setLoggingManager()
	{
		try
		{
			LogManager.getLogManager().readConfiguration(new FileInputStream("src/properties/logger.properties"));
		}
		catch(IOException ioException)
		{
			System.out.println("Exception occured while loading logger properties. Check logger property file.");
			logger.warning("Classname : CommonUtility, Method: setLoggingManager, Exception: "+ ioException); 
		}
	}

	public String setBindingProperties()
	{
		try
		{
			FileReader reader=new FileReader("src/properties/LDAPOperationTest.properties");  
			Properties properties=new Properties();  
			properties.load(reader);  
			String authentication = "ldap://"+properties.getProperty("host")+":"+properties.getProperty("port"); 
			return authentication;
		}
		catch(IOException ioException)
		{
			System.out.println("Exception occured while loading binding properties. Check Binding property file.");
			logger.warning("Classname : CommonUtility, Method: setBindingProperties, Exception: "+ ioException);
			return null;
		}
	}
}

