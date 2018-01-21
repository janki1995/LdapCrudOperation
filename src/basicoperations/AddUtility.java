/*
 * Author: Janki Shah
 * Date: 12/01/2018
 */

package basicoperations;

import java.util.Scanner;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

public class AddUtility {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(AddUtility.class.getName());

	public void addAction(DirContext context, String dn)
	{
		logger.info( "Class Name: AddUtility, Method: subMenuForAdd()" );

		CommonUtility commonUtility = new CommonUtility();
		commonUtility.setLoggingManager();

		LookupUtility lookupUtility = new LookupUtility();
		Object lookedupObject = lookupUtility.lookupAction(context, dn);

		if(lookedupObject == null)
		{
			AddUtility addUtility = new AddUtility();
			boolean repeat = true;
			while(repeat)
			{
				System.out.println("Please select one of following options to perform add operation:");
				System.out.println("a. Add Container/ou b. Add Group c. Add Person d.exit");
				String suboperation = sc.next();

				switch(suboperation)
				{
				case "a":
					System.out.println("add container");
					addUtility.addContainer(context,dn);
					break;
				case "b":
					System.out.println("add group");
					addUtility.addGroup(context,dn);
					break;
				case "c":
					System.out.println("add Person i.e. inetOrgPerson");
					addUtility.addPerson(context,dn);
					break;
				default:
					System.out.println("exit add operation");
					repeat=false;
				}
			}
		}
		else
		{
			System.out.println("Please check for duplicate entry.");
		}

	}

	public void addContainer(DirContext context, String dn)
	{

		logger.info( "Class Name: AddUtility, Method: addContainer()"); 

		System.out.println("Enter name of Organizational Unit:");
		String ouName = sc.next();

		// Must Attributes
		Attribute objectclass = new BasicAttribute(Constants.objectClass);
		objectclass.add("organizationalUnit");
		objectclass.add("top");
		Attribute ou = new BasicAttribute("ou", ouName);

		Attributes entry = new BasicAttributes();
		entry.put(objectclass);
		entry.put(ou);

		// create Subcontext
		try
		{
			context.createSubcontext(dn, entry);
			System.out.println("Container added Successfully");
		}
		catch(NamingException exception)
		{
			System.out.println("Error occured while adding container.");
			logger.warning( "Class Name: AddUtility, Method: addContainer(), Exception: "+ exception );
		}
	}

	public void addGroup(DirContext context, String dn)
	{
		logger.info( "Class Name: AddUtility, Method: addGroup()"); 

		System.out.println("Enter Common name of Group:");
		String cn = sc.next();
		System.out.println("Add Unique Member to the Group:");
		String uniqueMember = sc.next();

		// Must Attributes
		Attribute objectclass = new BasicAttribute(Constants.objectClass);
		objectclass.add("groupOfUniqueNames");
		objectclass.add("top");

		Attribute cnAttr = new BasicAttribute("cn", cn);
		Attribute uniqueMemberAttr = new BasicAttribute("uniqueMember", uniqueMember);


		Attributes entry = new BasicAttributes();
		entry.put(objectclass);
		entry.put(cnAttr);
		entry.put(uniqueMemberAttr);

		// May Attributes
		String addMayAttr = "";
		System.out.println("Would you like to add other optional Attributes? y/n");	
		addMayAttr = sc.next();
		while(addMayAttr.equals("y"))
		{
			System.out.println("Enter Name of Attribute");
			String attributeName = sc.next();
			System.out.println("Enter Value of Attribute");
			String attributeValue = sc.next();

			Attribute attr = new BasicAttribute(attributeName, attributeValue);
			entry.put(attr);

			System.out.println("Would you like to add other optional Attributes? y/n");	
			addMayAttr = sc.next();
		}

		// create Subcontext
		try
		{
			context.createSubcontext(dn, entry);
			System.out.println("Group added Successfully");
		}
		catch(NamingException exception)
		{
			System.out.println("Error occured while adding group.");
			logger.warning( "Class Name: AddUtility, Method: addGroup(), Exception: "+ exception );
		}
	}

	public void addPerson(DirContext context, String dn)
	{
		logger.info( "Class Name: AddUtility, Method: addPerson()"); 

		System.out.println("Enter Common name of Person:");
		String cn = sc.next();
		System.out.println("Enter Surname name of Person:");
		String sn = sc.next();
		System.out.println("Enter uid of Person:");
		String uid = sc.next();

		// Must Attributes
		Attribute objectclass = new BasicAttribute(Constants.objectClass);
		objectclass.add("inetOrgPerson");
		objectclass.add("top");
		objectclass.add("person");
		objectclass.add("organizationalPerson"); 
		Attribute cnAttr = new BasicAttribute("cn", cn);
		Attribute snAttr = new BasicAttribute("sn", sn);
		Attribute uidAttr = new BasicAttribute("uid", uid);

		Attributes entry = new BasicAttributes();
		entry.put(objectclass);
		entry.put(cnAttr);
		entry.put(snAttr);
		entry.put(uidAttr);

		// May Attributes
		String addMayAttr = "";
		System.out.println("Would you like to add other optional Attributes? y/n");	
		addMayAttr = sc.next();
		while(addMayAttr.equals("y"))
		{
			System.out.println("Enter Name of Attribute");
			String attributeName = sc.next();
			System.out.println("Enter Value of Attribute");
			String attributeValue = sc.next();

			//encrypt password
			if(attributeName.equals("userPassword"))
			{
				Encryption encryptionObject = new Encryption();
				attributeValue = encryptionObject.encryptString(attributeValue);
			}
			Attribute attr = new BasicAttribute(attributeName, attributeValue);
			entry.put(attr);

			System.out.println("Would you like to add other optional Attributes? y/n");	
			addMayAttr = sc.next();
		}

		// create Subcontext
		try
		{
			context.createSubcontext(dn, entry);
			System.out.println("Person added Successfully");
		}
		catch(NamingException exception)
		{
			System.out.println("Error occured while adding person.");
			logger.warning( "Class Name: AddUtility, Method: addPerson(), Exception: "+ exception );
		}

	}
}
