/*
 * Author: Janki Shah
 * Date: 12/01/2018
 */

package basicoperations;

import java.util.Scanner;
import java.util.logging.Logger;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.NoSuchAttributeException;
import javax.naming.directory.SchemaViolationException;

public class ModifyUtility {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(ModifyUtility.class.getName());

	public void modifyAction(DirContext context, String dn)
	{
		CommonUtility commonUtility = new CommonUtility();
		commonUtility.setLoggingManager();

		logger.info( "Class Name: ModifyUtility, Method: subMenuForModify()" );

		ModifyUtility modifyUtility = new ModifyUtility();


		LookupUtility lookupUtility = new LookupUtility();
		Object lookedupObject = lookupUtility.lookupAction(context, dn);

		if(lookedupObject != null)
		{
			//List the attribute of the  given entry
			try
			{
				Attributes list = context.getAttributes(dn);	

				for (NamingEnumeration attributeEnumeration = list.getAll(); attributeEnumeration.hasMore();) 
				{
					Attribute attr = (Attribute)attributeEnumeration.next();
					System.out.println("attribute: " + attr.getID());

					for (NamingEnumeration valueEnumeration = attr.getAll(); valueEnumeration.hasMore();
							System.out.println("value: " + valueEnumeration.next()));
				}
			}
			catch(NamingException exception)
			{
				System.out.println("Exception Occured while modification.");	
				logger.info( "Class Name: ModifyUtility, Method: subMenuForModify(), Exception while fetching list of attributes: "+ exception );
			}

			boolean repeat = true;
			while(repeat)
			{
				System.out.println("Please select one of following options:");
				System.out.println("a. Add Attribute b. Replace Attribute c. Delete Attribute d.exit ");
				String suboperation = sc.next();
				switch(suboperation)
				{
				case "a":
					System.out.println("Add Attribute");
					modifyUtility.addAttribute(context, dn);
					break;
				case "b":
					System.out.println("Replace Attribute");
					modifyUtility.replaceAttribute(context, dn);
					break;
				case "c":
					System.out.println("Delete Attribute");
					modifyUtility.deleteAttribute(context, dn);
					break;
				default:
					System.out.println("exit Modify Operation");
					repeat = false;
				}
			}
		}
		else
		{
			System.out.println("Entered object does not exist.");
		}
	}

	public void addAttribute(DirContext context, String dn)
	{
		logger.info( "Class Name: ModifyUtility, Method: addAttribute()");
		System.out.println("Name of attribute to be added:");
		String attributeName = sc.next();
		System.out.println("Value of attribute added:");
		String attributeValue = sc.next();

		//Encrypt password
		if(attributeName.equals("userPassword"))
		{
			Encryption encryption = new Encryption();
			attributeValue = encryption.encryptString(attributeValue);
		}

		ModificationItem[] modificationItem = new ModificationItem[1];
		modificationItem[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute(attributeName, attributeValue));
		try
		{
			context.modifyAttributes(dn, modificationItem);
			System.out.println("Entry Modified Successfully");
		}
		catch(SchemaViolationException schemaViolationException)
		{
			System.out.println("Check for validity of attribute to be added.");
			logger.info( "Class Name: ModifyUtility, Method: addAttribute(), Exception: " + schemaViolationException  );
		}
		catch(NamingException namingException)
		{
			System.out.println("Exception occured while adding attribute.");
			logger.info( "Class Name: ModifyUtility, Method: addAttribute(), Exception: " + namingException  );
		}

	}

	public void replaceAttribute(DirContext context, String dn)
	{
		logger.info( "Class Name: ModifyUtility, Method: replaceAttribute()" );
		System.out.println("Name of attribute to be replaced:");
		String name = sc.next();
		System.out.println("New Value of attribute :");
		String value = sc.next();

		ModificationItem[] modificationItem = new ModificationItem[1];
		modificationItem[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(name, value));
		try
		{
			context.modifyAttributes(dn, modificationItem);
			System.out.println("Entry Modified Successfully");
		}
		catch(SchemaViolationException schemaViolationException)
		{
			System.out.println("Check for validity of attribute to be replaced.");
			logger.info( "Class Name: ModifyUtility, Method: replaceAttribute(), Exception: " + schemaViolationException  );
		}
		catch(NamingException namingException)
		{
			System.out.println("Exception occured while replacing value of attribute.");
			logger.info( "Class Name: ModifyUtility, Method: replaceAttribute, Exception: " + namingException );
		}

	}

	public void deleteAttribute(DirContext context, String dn)
	{
		logger.info( "Class Name: ModifyUtility, Method: deleteAttribute()");

		System.out.println("Name of attribute to be deleted:");
		String name = sc.next();

		ModificationItem[] modificationItem = new ModificationItem[1];
		modificationItem[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(name));
		try
		{
			context.modifyAttributes(dn, modificationItem);
			System.out.println("Entry Modified Successfully");
		}
		catch(NoSuchAttributeException attributeException)
		{
			System.out.println("Attribute entered does not exists.");
			logger.info( "Class Name: ModifyUtility, Method: deleteAttribute(), Exception: " + attributeException );
		}
		catch(NamingException namingException)
		{
			System.out.println("Exception occured while deleting value of attribute.");
			logger.info( "Class Name: ModifyUtility, Method: deleteAttribute(), Exception: " + namingException );
		}

	}

}
