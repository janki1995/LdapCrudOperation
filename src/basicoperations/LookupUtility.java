/*
 * Author: Janki Shah
 * Date: 15/01/2018
 */

package basicoperations;

import java.util.Scanner;
import java.util.logging.Logger;

import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

public class LookupUtility {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(LookupUtility.class.getName());

	public Object lookupAction(DirContext context, String dn)
	{
		CommonUtility commonUtility = new CommonUtility();
		commonUtility.setLoggingManager();

		logger.info("Class Name: LookupUtility, Method: lookup");


		Object lookedupObject = null;

		try
		{
			lookedupObject = context.lookup(dn);
			return lookedupObject;
			
		}
		catch(InvalidNameException invalidNameException)
		{
			System.out.println("Please check for correctness of entry added (DN).");
			logger.warning( "Class Name: LookupUtility, Method: lookup(), lookup: "+ invalidNameException );
			return null;
		}
		catch(NamingException namingException)
		{
			System.out.println("Entered Object does not exist.");
			logger.warning( "Class Name: LookupUtility, Method: lookup(), lookup: "+ namingException );
			return null;
		}
		catch(Exception exception)
		{
			System.out.println("Exception occuered while looking up for an object.");
			logger.warning( "Class Name: LookupUtility, Method: lookup(), lookup: "+ exception );
			return null;
		}
		
	}

}
