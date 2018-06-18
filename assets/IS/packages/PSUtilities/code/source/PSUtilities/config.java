package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2004-01-04 22:53:12 PST
// -----( ON-HOST: esuc610

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
import com.wm.app.b2b.server.*;
import com.wm.lang.ns.*;
import com.wm.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class config

{
	// ---( internal utility methods )---

	final static config _instance = new config();

	static config _newInstance() { return new config(); }

	static config _cast(Object o) { return (config)o; }

	// ---( server methods )---




	public static final void checkParameterValidity (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(checkParameterValidity)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required paramName
		// [i] field:0:required paramValue
		// [o] field:0:required valid

	IDataCursor idcPipeline = pipeline.getCursor();
	String strParamName = null;
	String strParamValue = null;
	if (idcPipeline.first("paramName"))
	{
		strParamName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("Parameter name cannot be null!");
	}
	if (idcPipeline.first("paramValue"))
	{
		strParamValue = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("Parameter value cannot be null!");
	}

	String strAllowedParamValues = (String)properties.get(strParamName);
	StringTokenizer st = new StringTokenizer(strAllowedParamValues, "*");

	String strValid = "false";
	while (st.hasMoreElements())
	{
		String strAllowedParamValue = (String)st.nextElement();
		if (strAllowedParamValue.equals(strParamValue))
		{
			strValid = "true";
			break;
		}
	}

	idcPipeline.insertAfter("valid", strValid);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void checkPathValidity (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(checkPathValidity)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required action {"read","write","delete"}
		// [i] field:0:required path
		// [o] field:0:required valid
		
			IDataCursor idcPipeline = pipeline.getCursor();
			String strPath = null;
			String strAction = null;
			if (idcPipeline.first("path"))
			{
				strPath = (String)idcPipeline.getValue();
			}
			else
			{
				throw new ServiceException ("Path cannot be null!");
			}
			if (idcPipeline.first("action"))
			{
				strAction = (String)idcPipeline.getValue();
			}
		
			String strAllowedPaths = null;
			if (strAction.equals("read"))
			{
				strAllowedPaths = (String)properties.get("allowedReadPaths");
			}
			else if (strAction.equals("write"))
			{
				strAllowedPaths = (String)properties.get("allowedWritePaths");
			}
			else if (strAction.equals("delete"))
			{
				strAllowedPaths = (String)properties.get("allowedDeletePaths");
			}
			else
			{
				throw new ServiceException("Action " + strAction + " invalid!  Valid actions: read, write, delete");
			}
		//System.out.println("strAllowedPaths = " + strAllowedPaths);
			StringTokenizer st = new StringTokenizer(strAllowedPaths, "*");
		
			File fPath = new File(strPath);
			String strValid = "false";
			while (st.hasMoreElements())
			{
				String strAllowedPath = (String)st.nextElement();
		//System.out.println("strPath = " + strPath);
		//System.out.println("strAllowedPath = " + strAllowedPath);
				File fAllowedPath = new File(strAllowedPath);
				String strCanonicalDir = null;
				String strCanonicalAllowedDir = null;
				try
				{
		
					if (fPath.isDirectory())
					{
						strCanonicalDir = fPath.getCanonicalPath();
					}
					else
					{
						// An extra getCanonicalFile call is needed here to get around a Java quirk
						// where getParentFile barfs in certain situations w/o canonical file
						strCanonicalDir = fPath.getCanonicalFile().getParentFile().getCanonicalPath();
					}
		
					if (fAllowedPath.isDirectory())
					{
						strCanonicalAllowedDir = fAllowedPath.getCanonicalPath();
					}
					else
					{
						// An extra getCanonicalFile call is needed here to get around a Java quirk
						// where getParentFile barfs in certain situations w/o canonical file
						strCanonicalAllowedDir = fAllowedPath.getCanonicalFile().getParentFile().getCanonicalPath();
					}
		//System.out.println("strCanonicalDir = " + strCanonicalDir);
		//System.out.println("strCanonicalAllowedDir = " + strCanonicalAllowedDir);
				}
				catch (Exception e)
				{
					throw new ServiceException(e);
				}
		
				if (fPath.isDirectory())
				{
					//Specified path is a directory
					if (strCanonicalDir != null && strCanonicalAllowedDir != null &&
						fAllowedPath.isDirectory() && strCanonicalDir.equals(strCanonicalAllowedDir))
					{
						strValid = "true";
						break;
					}
				}
				else
				{
					// specified path is a filename
					if (fAllowedPath.isDirectory())
					{
						if (strCanonicalDir != null && strCanonicalAllowedDir != null &&
							strCanonicalDir.equals(strCanonicalAllowedDir))
						{
							strValid = "true";
							break;
						}
					}
					else
					{
						String strFilename = fPath.getName();
						String strAllowedFilename = fAllowedPath.getName();
		//System.out.println("strFilename = " + strFilename);
		//System.out.println("strAllowedFilename = " + strAllowedFilename);
						if (strCanonicalDir != null && strCanonicalAllowedDir != null &&
							strCanonicalDir.equals(strCanonicalAllowedDir) &&
							strFilename.equals(strAllowedFilename))
						{
							strValid = "true";
							break;
						}
					}
				}
			} // end while
		
			idcPipeline.insertAfter("valid", strValid);
			idcPipeline.destroy();
		
		// ***End check
		// --- <<IS-END>> ---

                
	}



	public static final void getParameterValue (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getParameterValue)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required paramName
		// [o] field:0:required paramValue

	IDataCursor idcPipeline = pipeline.getCursor();
	String strParamName = null;
	if (idcPipeline.first("paramName"))
	{
		strParamName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("Parameter name cannot be null!");
	}

	String strParamValue = (String)properties.get(strParamName);
	idcPipeline.insertAfter("paramValue", strParamValue);
	idcPipeline.destroy();

		// --- <<IS-END>> ---

                
	}



	public static final void getProperties (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getProperties)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required properties
		
			IDataCursor idcPipeline = pipeline.getCursor();
		//System.out.println("properties = " + properties);
			idcPipeline.insertAfter("properties" , properties);
		// --- <<IS-END>> ---

                
	}



	public static final void loadPSUtilitiesConfig (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(loadPSUtilitiesConfig)>> ---
		// @subtype unknown
		// @sigtype java 3.5
			try
			{
		//		String strFileSeparator = (System.getProperties()).getProperty("file.separator");	
		//		String strCurrentDir = (System.getProperties()).getProperty("user.dir");	
				String strFilename = "packages/PSUtilities/config/PSUtilities.cnf";
		
				File file = new File(strFilename);
				if (file.canRead())
				{
					InputStream stream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
		
					try
					{
						properties.load(stream);
						System.out.println("Successfully loaded PSUtilities configuration file into memory");
					}
					finally
					{
						stream.close();
					}
		
				}
				else
				{
					throw new ServiceException("Could not read properties file: " + strFilename);
				}
			}
			catch( Exception e )
			{
				throw new ServiceException("Error reading properties file: " + e);
			}
		
		// --- <<IS-END>> ---

                
	}



	public static final void setACLs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setACLs)>> ---
		// @subtype unknown
		// @sigtype java 3.5

	String strSet = Config.getProperty("watt.PSUtilities.ACL.set");
	if (strSet == null || !strSet.equals("true"))
	{
		String strBuild = Build.getVersion();
		if (strBuild.indexOf("4.") == -1)
		{
			// v6.x
			System.out.println("Version 6.x or above detected");
			ACLManager.setBrowseAclGroup("PSUtilities", "Developers");
			ACLManager.setReadAclGroup("PSUtilities", "Developers");
			ACLManager.setWriteAclGroup("PSUtilities", "Developers");
			ACLManager.setAclGroup("PSUtilities", "Internal");
		}
		else
		{
			// v4.x
			System.out.println("Version 4.x detected");
			ServiceManager.setAclGroup("PSUtilities", "Internal");
		}
		System.out.println("Successfully configured ACLs for PSUtilities package");
		Config.setProperty("watt.PSUtilities.ACL.set", "true");
	}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static Properties properties = new Properties();
	
	// --- <<IS-END-SHARED>> ---
}

