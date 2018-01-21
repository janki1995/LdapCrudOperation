/*
 * Author: Janki Shah
 * Date: 15/01/2018
 */

package basicoperations;


import java.util.Scanner;
import java.util.logging.Logger;

import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidSearchControlsException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class SearchUtility {

	public static Scanner sc= new Scanner(System.in);
	private static final Logger logger = Logger.getLogger(SearchUtility.class.getName());

	public void searchAction(DirContext context)
	{
		CommonUtility commonUtility = new CommonUtility();
		commonUtility.setLoggingManager();

		logger.info( "Class Name: SearchUtility, Method: searchAction()"); 

		SearchControls controls = new SearchControls();

		//SEARCH BASE
		String base = this.selectSearchBase();

		//FILTER
		String filter = this.selectFiletr();

		//SCOPE 
		int scope = this.selectScope();
		controls.setSearchScope(scope);

		//RETURNING ATTRIBUTE
		String[] listOfAttributes = this.selectAttributes();
		controls.setReturningAttributes(listOfAttributes);

		try
		{
			NamingEnumeration<SearchResult> search = context.search(base, filter, controls); 

			if(search != null)
			{
				while(search.hasMore())
				{
					SearchResult searchResult = (SearchResult)search.next();
					System.out.println("Result : " + searchResult.getName());

					Attributes attributes = searchResult.getAttributes();

					NamingEnumeration attributeEnumeration = attributes.getAll();
					while(attributeEnumeration.hasMore())
					{
						Attribute attr = (Attribute)attributeEnumeration.next();
						System.out.println("attribute: " + attr.getID());

						// print each value
						for (NamingEnumeration valueEnumeration = attr.getAll();
								valueEnumeration.hasMore();
								System.out.println("value: " + valueEnumeration.next()));
					}
				}
			}
			else
			{
				System.out.println("No such entry exist.");
			}
		}
		catch(NameNotFoundException nameNotFoundException)
		{
			System.out.println("Check correctness of base entered.");
			logger.warning( "Class Name: SearchUtility, Method: searchAction(), Exception: "+ nameNotFoundException );
		}
		catch(InvalidSearchFilterException filterException)
		{
			System.out.println("Check correctness of filter entered.");
			logger.warning( "Class Name: SearchUtility, Method: searchAction(), Exception: "+ filterException );
		}
		catch(InvalidSearchControlsException controlException)
		{
			System.out.println("Check correctness of scope and returning attributes.");
			logger.warning( "Class Name: SearchUtility, Method: searchAction(), Exception: "+ controlException );
		}
		catch(NamingException namingException)
		{
			System.out.println("Exception occured while searching object.");
			logger.warning( "Class Name: SearchUtility, Method: searchAction(), Exception: "+ namingException );
		}

	}

	public String selectSearchBase()
	{
		logger.info( "Class Name: SearchUtility, Method: selectSearchBase()");
		System.out.println("Enter DN of Search Base:");
		String searchBase = sc.next();

		return searchBase;
	}

	public String selectFiletr()
	{
		logger.info( "Class Name: SearchUtility, Method: selectFiletr()");
		System.out.println("Enter filter as a String:");
		String appliedFilter = sc.next();

		return appliedFilter;
	}

	public int selectScope()
	{
		logger.info( "Class Name: SearchUtility, Method: selectScope()"); 

		System.out.println("Select scope of the Search:");
		System.out.println("a.Object b.One Level c.Subtree");
		String selectedScope = sc.next();

		if(selectedScope.equals("a"))
		{
			return SearchControls.OBJECT_SCOPE;
		}
		else if(selectedScope.equals("b"))
		{
			return SearchControls.ONELEVEL_SCOPE;
		}
		else
		{
			return SearchControls.SUBTREE_SCOPE;
		}

	}

	public String[] selectAttributes()
	{
		logger.info( "Class Name: SearchUtility, Method: selectAttributes()");

		System.out.println("Enter list of attribures to return:");
		String[] attributeList = new String[10];
		String attributeName;
		boolean addAttribute = true;
		int count = 0;

		while(addAttribute)
		{
			System.out.println("Would to like to add attributes to list ? y/n");
			String repeat = sc.next();

			if(repeat.equals("y"))
			{
				System.out.println("Enter attribute name:");
				attributeName = sc.next();
				attributeList[count] = attributeName;
				addAttribute = true;
				count++;
			}
			else
			{
				addAttribute = false;
			}

		}

		return attributeList;
	}


}
