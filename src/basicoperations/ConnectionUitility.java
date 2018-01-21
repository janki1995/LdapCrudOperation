package basicoperations;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ConnectionUitility {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(ConnectionUitility.class.getName());
	static CommonUtility commonUtility = new CommonUtility();
	
	public DirContext ldapBinding()
	{
		logger.info( "Class Name: ConnectionUitility, Method: ldapBinding()" ); 

		System.out.println("Enter Bind DN: ");
		String dn = sc.next(); 
		System.out.println("Enter Bind Password:");
		String password = sc.next();

		String authentication = commonUtility.setBindingProperties();

		Hashtable<String, String> credentials = new Hashtable<>();

		credentials.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		credentials.put(Context.PROVIDER_URL, authentication);
		//credentials.put(Context.SECURITY_AUTHENTICATION ,"simple");
		credentials.put(Context.SECURITY_PRINCIPAL, dn);
		credentials.put(Context.SECURITY_CREDENTIALS, password);

		try
		{
			DirContext initialContext = new InitialDirContext(credentials);
			return initialContext;
		}
		catch(InvalidNameException invalidNameException)
		{
			System.out.println("Please check correctness of DN.");
			logger.warning( "Class Name: ConnectionUitility, Method: ldapBinding(), Exception: "+ invalidNameException );
			return null;
		}
		catch(AuthenticationException authenticationException)
		{
			System.out.println("Please check correctness of password for Authentication");
			logger.warning( "Class Name: ConnectionUitility, Method: ldapBinding(), Exception: "+ authenticationException );
			return null;
		}
		catch(CommunicationException communicationException)
		{
			System.out.println("Please check correctness of host and port number for Communication.");
			logger.warning( "Class Name: ConnectionUitility, Method: ldapBinding(), Exception: "+ communicationException );
			return null;
		}
		catch(NamingException namingExceptionexception)
		{
			System.out.println("Exception occured while Bindng connecton.");
			logger.warning( "Class Name: ConnectionUitility, Method: ldapBinding(), Exception: "+ namingExceptionexception );
			return null;
		}

	}

	public void ldapUnBinding(DirContext context) 
	{
		try
		{
			context.close();
			System.out.println("UNBINDING SUCCESSFULL");
		}
		catch(NamingException exception)
		{
			System.out.println("Exception occured while Unbindng connecton.");
			logger.warning( "Class Name: ConnectionUitility, Method: ldapUnBinding(), Exception: "+ exception );
		}
	}

	
	
	
}
