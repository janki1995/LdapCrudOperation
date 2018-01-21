/*
 * Author: Janki Shah
 * Date: 11/01/2018
 */

package basicoperations;

import java.util.Scanner;
import java.util.logging.*;

//Connection Purpose

import javax.naming.directory.DirContext;


public class LDAPOperations {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(LDAPOperations.class.getName());
	static CommonUtility commonUtility = new CommonUtility();

	public static void main(String args[])
	{
		commonUtility.setLoggingManager();

		logger.info( "Class Name: ldapOperationsTest, Method: main()" ); 

		ConnectionUitility connectionUitility = new ConnectionUitility();
		DirContext directoryContext = connectionUitility.ldapBinding();
		if(directoryContext != null)
		{
			System.out.println("BINDING SUCCESSFUL");
			// Future enhancement : catch InputMissMatchException \
			System.out.println("EXAMPLE OF DN : ou=java,dc=maxcrc,dc=com");
			boolean repeat = true;
			while(repeat)
			{
				System.out.println("Enter DN of entry to be modified:");
				String dn = sc.next();
				System.out.println("Select an operation to perform:");
				System.out.println("1.Add 2.Modify 3.Delete \n4.Search 5.Lookup 6.Exit");
				int operationToPerform = sc.nextInt();
				switch(operationToPerform)
				{
				case 1: 
					AddUtility addUtility = new AddUtility();
					addUtility.addAction(directoryContext, dn);
					break;
				case 2:
					ModifyUtility modifyUtility = new ModifyUtility();
					modifyUtility.modifyAction(directoryContext, dn);
					break;
				case 3:
					DeleteUtility deleteUtility = new DeleteUtility();
					deleteUtility.deleteAction(directoryContext, dn);
					break;
				case 4: 
					SearchUtility searchUtility = new SearchUtility();
					searchUtility.searchAction(directoryContext);
					break;
				case 5:
					LookupUtility lookupUtility = new LookupUtility();
					lookupUtility.lookupAction(directoryContext, dn);
					break;
				case 6:
					connectionUitility.ldapUnBinding(directoryContext);
					System.out.println("Thank you");
					repeat = false;
					break;	
				}
			}
		}
		else
		{
			System.out.println("BINDING FAILED");
		}
	}
}
