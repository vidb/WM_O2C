package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2004-01-27 11:09:29 PST
// -----( ON-HOST: corp-183.west.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.wm.util.Debug;
import java.io.*;
import java.util.*;
import java.lang.System;
import com.wm.app.b2b.server.*;
import com.wm.util.Table;
import java.text.*;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class misc

{
	// ---( internal utility methods )---

	final static misc _instance = new misc();

	static misc _newInstance() { return new misc(); }

	static misc _cast(Object o) { return (misc)o; }

	// ---( server methods )---




	public static final void appendObjectList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(appendObjectList)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [i] object:1:required objectList
		// [o] object:1:required newObjectList
	IDataCursor idcPipeline = pipeline.getCursor();
	Object[] ol = null;
	
	idcPipeline.first("object");
	Object o = idcPipeline.getValue();

	if (idcPipeline.first("objectList"))
		ol = (Object[])idcPipeline.getValue();
	int length = 0;
	if (ol instanceof Object[])
		length = ol.length +1;
	else
		length = 1;
	Object[] newOL = new Object[length];   
	int i=0;
	if (ol instanceof Object[])
	{
		for (; i<ol.length; i++)
			newOL[i] = ol[i];
	}

 	newOL[i] = o;

	idcPipeline.insertAfter("newObjectList", newOL);
		// --- <<IS-END>> ---

                
	}



	public static final void appendStringListToStringTable (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(appendStringListToStringTable)>> ---
		// @sigtype java 3.5
		// [i] field:1:required fromStringList
		// [i] field:2:required toStringTable
		// [o] field:2:required toStringTable
		
			IDataCursor idcPipeline = pipeline.getCursor();
		
			idcPipeline.first("fromStringList");
			String fromStringList[] = (String [])idcPipeline.getValue();
			String toStringTable[][] = null;
			if (idcPipeline.first("toStringTable"))
			{
				idcPipeline.delete();
				toStringTable = (String[][]) idcPipeline.getValue();
			}
		
			Values duh = new Values();
			int x = toStringTable.length;
			int y = fromStringList.length;
			if ((x >= 1) && (y != toStringTable[0].length))
			{
				throw new ServiceException("Array sizes are inconsistent");
			}
		//System.out.println("x = " + x + ", y = " + y);
			String toStringTableOut[][] = new String[x+1][y];
		
			//Copy toStringTable to output table
			int i, j;
			for (i = 0; i < x; i++)
			{
				for (j = 0; j < y; j++)
				{
					toStringTableOut[i][j] = toStringTable[i][j];
				}
			}
		
			//Append new stringList
			for (j = 0; j < fromStringList.length; j++)
			{
				toStringTableOut[x][j] = fromStringList[j];
			}
		
			idcPipeline.insertAfter("toStringTable", toStringTableOut);
			idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void collectGarbage (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(collectGarbage)>> ---
		// @sigtype java 3.5


	System.runFinalization();
	System.gc(); 
		// --- <<IS-END>> ---

                
	}


    public static final Values deepConvert (Values in)
    {
        Values out = in;
		// --- <<IS-START(deepConvert)>> ---
		// @sigtype java 3.0
		// [i] object:0:required hashtable
		// [o] record:0:required convertedValues
		/** This method will deep convert a Hashtable to a Values object.
		  * That is, it will be designed to convert all Hashtable objects within
		  * a hashtable, nested or not, to a values object.
		  *
		  * @author Ryan Johnston, Professional Services, webMethods, Inc.
		  * @version 1.0
		  */
		
		//Get the inbound Hashtable object.
		Hashtable hT = (Hashtable)in.get("hashtable");
		
		boolean nullFlag = false;
		Values outbound = new Values();
		try{
			//Following statement gets all arrays in this object.
			Object[] hTArray = hT.values().toArray();
			Enumeration hTEnumeration = hT.keys();
		
			for(int i=0;i<hTArray.length;i++){
				String key = (String)hTEnumeration.nextElement();
				if(hTArray[i] instanceof java.lang.String){
					outbound.put(key,(String)hTArray[i]);
				}
				else if(hTArray[i] instanceof java.util.Hashtable){
					Values internalObject = convert((Hashtable)hTArray[i]);	
					if(internalObject == null){
						nullFlag = true;
						out.put("convertedValues", null);
						return out;
					}
					outbound.put(key,internalObject);		
				}
				else{
					System.out.println("Conversion Failure:" + "unsupported type within inbound Hashtable.");
					out.put("convertedValues", null);
					return out;
				}
			}
		}
		catch(java.lang.Exception ex){
			System.out.println("Conversion Failure:" + ex.getMessage());
			out.put("convertedValues", null);
			return out;
		}
		out.put("convertedValues",outbound);
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void deepCopy (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deepCopy)>> ---
		// @sigtype java 3.5
		// [i] object:0:required originalObject
		// [o] object:0:required clonedObject
		
			IDataCursor idcPipeline = pipeline.getCursor();
			if (!idcPipeline.first("originalObject"))
			{
				throw new ServiceException("originalObject is null!");
			}
			Object originalObject = (Object)idcPipeline.getValue();
			Object clonedObject = new Object();
		
		//	throw new ServiceException("blah" + (originalObject.getClass()).isArray());
		
			// This code is taken from http://www.javaworld.com/javaworld/javatips/jw-javatip76.html
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
		
			try
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream(); // A
				oos = new ObjectOutputStream(bos); // B
		
				// serialize and pass the object
				oos.writeObject(originalObject); // C
				oos.flush(); // D
		
				ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); // E
				ois = new ObjectInputStream(bin); // F
		
				// return the new object
				idcPipeline.insertAfter("clonedObject", ois.readObject()); // G
			}
			catch (Exception e)
			{
				throw new ServiceException("Exception making deep copy of object: " + e);
			}
			finally
			{
				try
				{
					oos.close();
					ois.close();
				}
				catch (Exception e)
				{
					throw new ServiceException("Exception making deep copy of object: " + e);
				}
				idcPipeline.destroy();
			}
		// --- <<IS-END>> ---

                
	}



	public static final void doInvoke (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(doInvoke)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceNS
		// [i] record:0:required input
		// [o] record:0:required output

	IDataCursor idcPipeline = pipeline.getCursor();
	IData inputData = null;
	IData outputData = null;
	String strServiceName = null;

	try
	{	
		// *** Check if service is on the allowed list ***
		IData in = IDataFactory.create();
		IDataCursor idcIn = in.getCursor();
		idcIn.insertAfter("paramName", "allowedServices");
		idcIn.insertAfter("paramValue", strServiceName);
		NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkParameterValidity");
		idcIn.destroy();
		IData out;
		out = Service.doInvoke(nsCheckServiceName, in);
		IDataCursor idcOut = out.getCursor();
		String strValid = null;
		if (idcOut.first("valid"))
		{
			strValid = (String)idcOut.getValue();
		}
		idcOut.destroy();

		if (strValid.equals("false"))
		{
			throw new ServiceException("Specified service is not on the allowed list in the PSUtilities configuration file!");
		}
		// *** End check ***
		
		if (idcPipeline.first("serviceNS"))
		{
			strServiceName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("missing service name");
		}

		if (strServiceName.length() == 0 )
			throw new ServiceException("Invalid service name");
		
		if (idcPipeline.first("input"))
			inputData = (IData)idcPipeline.getValue();

		
		NSName nsName = NSName.create(strServiceName);

		outputData = Service.doInvoke(nsName, inputData);

		if (outputData != null)
			idcPipeline.insertAfter("output", outputData);

	}
	catch(Exception e)
	{
		throw new ServiceException(e);
	}
	finally
	{
		idcPipeline.destroy();		
	}
		// --- <<IS-END>> ---

                
	}



	public static final void getHostInformation (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getHostInformation)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required host
		// [o] field:0:required ipAddress
		// [o] field:0:required hostname
		
			
		IDataCursor idcPipeline = pipeline.getCursor();
			String strHost = null;
			if (idcPipeline.first("host"))
			{
				strHost = (String)idcPipeline.getValue();
			}
			else
			{
				throw new ServiceException("Host is null!");
			}
		
			try
			{
				InetAddress address = null;
				if (strHost == null)
				{
					// Get local host name
					address = InetAddress.getLocalHost();
				}
				else 
				{
					address = InetAddress.getByName(strHost);
				}
		
				String strHostname = address.getHostName();
				String strIpAddress = address.getHostAddress();
		
				idcPipeline.insertAfter("hostname", strHostname);
				idcPipeline.insertAfter("ipAddress", strIpAddress);
			}
			catch (UnknownHostException e)
			{
				throw new ServiceException(e.toString());
		
			}
			finally
			{
				idcPipeline.destroy();
			}
		// --- <<IS-END>> ---

                
	}



	public static final void getRecordElements (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRecordElements)>> ---
		// @sigtype java 3.5
		// [i] record:0:required record
		// [o] field:1:required recordElementKeys
		// [o] field:1:required recordElementValues

	IDataCursor idcPipeline = pipeline.getCursor();

	IData record = null;
	if (idcPipeline.first("record"))
	{
		record = (IData)idcPipeline.getValue();
	}

	Vector vecRecordElementKeys = new Vector();
	Vector vecRecordElementValues = new Vector();
	IDataCursor idcRecord = record.getCursor();
	while (idcRecord.hasMoreData())
	{
		idcRecord.next();
		vecRecordElementKeys.addElement(idcRecord.getKey());
		vecRecordElementValues.addElement(idcRecord.getValue());
	}
	idcRecord.destroy();

	String recordElementKeys[] = new String[vecRecordElementKeys.size()];
	String recordElementValues[] = new String[vecRecordElementValues.size()];

	Enumeration keysEnum = vecRecordElementKeys.elements();
	Enumeration valuesEnum = vecRecordElementValues.elements();
	int i = 0;
	while (keysEnum.hasMoreElements())
	{
		recordElementKeys[i] = (String)keysEnum.nextElement();
		recordElementValues[i] = (String)valuesEnum.nextElement();
		i++;
	}

	idcPipeline.insertAfter("recordElementKeys", recordElementKeys);
	idcPipeline.insertAfter("recordElementValues", recordElementValues);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getServerConfigParameters (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServerConfigParameters)>> ---
		// @sigtype java 3.5
		// [i] field:1:required parameters
		// [o] record:1:required paramList
		// [o] - field:0:required parameter
		// [o] - field:0:required paramValue
		// [o] field:0:required paramCount
		//global declarations
		char[] cbuf = null;
		String cf = null;
		String line = null;
		int lineCount = 0;
		StringBuffer cfContents = new StringBuffer();
		String[] parameters = null;
		Hashtable cfParams = new Hashtable();
		
		//inputs
		IDataCursor pCursor = pipeline.getCursor();
		if ( pCursor.first( "parameters" ) )
		{
		  parameters = (String[])pCursor.getValue();
		  java.util.Arrays.sort(parameters);
		}
		
		try
		{
		  File configDir = com.wm.app.b2b.server.ServerAPI.getServerConfigDir();
		
		  File configFile = new File(configDir, "server.cnf");
		  FileReader cfReader = new FileReader(configFile);
		  BufferedReader cfBReader = new BufferedReader(cfReader);
		
		  while (cfBReader.ready())
		  {
		    line = cfBReader.readLine();
		
		    if (line != null && !line.startsWith("#") )
		    {
		      StringTokenizer st = new StringTokenizer(line,"=");
		
		      // if parameters == null, return everything
		      if (parameters == null)
		      {
		        if (st.countTokens()==2)
		        {
		          cfParams.put(st.nextToken(), st.nextToken());
		        } else
		        {
		          cfParams.put(st.nextToken(), "");
		        }
		
		      //if parameters exist, only return their values
		      } else 
		      {
		        String myParam = st.nextToken();
		        if (java.util.Arrays.binarySearch(parameters, myParam)>=0)
		        {
		          if (st.hasMoreTokens())
		          {
		            cfParams.put(myParam, st.nextToken());
		          } else
		          {
		            cfParams.put(myParam, "");
		          }
		        }
		      }
		      lineCount++;
		    }
		  }
		
		} catch (Exception e)
		{
		  Service.throwError("Error extracting parameters from server.cnf file:\n"+e.getMessage());
		}
		
		// paramList
		IData[]	paramList = new IData[cfParams.size()];
		String[] sortedKeys = new String[cfParams.size()];
		Enumeration params = cfParams.keys();
		
		//sort parameters
		int paramCount = 0;
		while (params.hasMoreElements() )
		{
		   sortedKeys[paramCount] = (String) params.nextElement();
		   paramCount++;   
		}
		java.util.Arrays.sort(sortedKeys);
		
		for (paramCount=0; paramCount < cfParams.size(); paramCount++)
		{
		  String key = sortedKeys[paramCount];
		  paramList[paramCount] = IDataFactory.create();
		  IDataCursor paramListCursor = paramList[paramCount].getCursor();
		  paramListCursor.last();
		  paramListCursor.insertAfter( "parameter", key );
		  paramListCursor.last();
		  paramListCursor.insertAfter( "paramValue", (String) cfParams.get(key) );
		  paramListCursor.destroy();
		}
		
		//java.util.Arrays.sort(paramList);
		
		// pipeline
		pCursor.last();
		pCursor.insertAfter( "paramList", paramList );
		pCursor.insertAfter( "paramCount", java.lang.Integer.toString(paramCount) );
		pCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getServerInformation (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServerInformation)>> ---
		// @sigtype java 3.5
		// [o] field:0:required serverName
		// [o] field:0:required primaryPort
		// [o] field:0:required currentPort
		
			IDataCursor idcPipeline = pipeline.getCursor();
		
			String strServerName = ServerAPI.getServerName();
			int intCurrentPort = ServerAPI.getCurrentPort();
		
			IData listenerInfo = null;
			Integer intPrimaryPort = null;
		
			try
			{
				IData results = Service.doInvoke("wm.server.net.listeners", "getPrimaryListener", pipeline);
				IDataUtil.merge(results, pipeline);
		
			}
			catch(Exception e)
			{
				throw new ServiceException("Could not invoke wm.server.net.listeners:getPrimaryListener: " + e);
			}
		
			if (idcPipeline.first("primary"))
			{
				listenerInfo = (IData)idcPipeline.getValue();
				idcPipeline.delete();
			}
			IDataCursor idcListenerInfo = listenerInfo.getCursor();
		
			if (idcListenerInfo.first("port"))
			{
				intPrimaryPort = (Integer)idcListenerInfo.getValue();
			}
		
			idcPipeline.insertAfter("serverName", strServerName);
			idcPipeline.insertAfter("primaryPort", intPrimaryPort.toString());
			idcPipeline.insertAfter("currentPort", Integer.toString(intCurrentPort));
		
		// Clean up IData cursors
			idcListenerInfo.destroy();
			idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getServiceName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getServiceName)>> ---
		// @sigtype java 3.5
		// [o] field:0:required folderName
		// [o] field:0:required serviceName
		// [o] field:0:required fullName
		// [o] field:0:required successFlag
		// [o] field:0:required errorMessage
		/** Service is designed to return the current service name.
		  * Output: folderPath - the folder path to the service
		  *			serviceName - the service name
		  *			fullName - the folder path + ":" + service
		  *			successFlag - true or false
		  *			errorMessage - error detail. This is set to "None" if no error occurs.
		  *
		  * NOTE 1: Because this service relies on a method invocation that retrieves the NSService
		  * 	object relating to the calling service, it will *NOT* work as desired if run independently.
		  * 	Instead, it will return information on the current service (this one) and set the
		  *		"successFlag" and "errorMessage" values to indicate an error.
		  * NOTE 2: This service uses non-public APIs within the webMethods Integration Server. These may 
		  * 	change in future releases of the product *without* notice. These method invocations are
		  *		marked as such.
		  *
		  * @author Ryan Johnston, Professional Services, webMethods, Inc.
		  * @version 1.0
		  */
		
		//Instantiate a cursor for access to the pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		//Working & Output Variables - Create all output & working variables
		NSService callingService = null;
		StringTokenizer strTok = null;
		String folderPath = new String();
		String serviceName = new String();
		String fullName = new String();
		String successFlag = "true";
		String errorMessage = "None";
		
		//Input Variables
		//None
		
		//Service Body
		//This publicly documented method gets the NSService object associated with the calling service.
		callingService = Service.getCallingService();
		
		/** If the callingService is not available (meaning that the service was run directly, set the 
		  * "callingService" to hold the values for the current service (this one). Additionally, set the
		  * "successFlag" and "errorMessage" fields to indicate a problem.
		  */
		if (callingService == null)
		{
			callingService = InvokeState.getCurrentService();
			successFlag = "false";
			errorMessage = "No calling service found... Returning information for this service instead.";
		}
		
		/** Non-public API. However, this call is relatively safe, as "toString" is a standard method that
		  * is normally overriden from the root java.lang.Object.
		  */
		fullName = callingService.toString();
		
		/** Use StringTokenizer, from the java.util package, to extract the folderPath and serviceName 
		  * values.
		  */
		try
		{
			strTok = new StringTokenizer(fullName, ":");
			folderPath = strTok.nextToken();
			serviceName = strTok.nextToken();
		}
		catch(java.lang.Exception ex)
		{
			System.out.println("CRITICAL ERROR: Check the toString() method within NSService to ensure that it is returning Folder:ServiceName as desired.");
			successFlag = "false";
			errorMessage = "CRITICAL ERROR: Check the toString() method within NSService to ensure that it is returning Folder:ServiceName as desired.";
		
		}
		
		//Populate service output
		if (pipelineCursor.first("folderPath"))
			pipelineCursor.delete();
		pipelineCursor.insertAfter("folderPath", folderPath);
		
		if (pipelineCursor.first("serviceName"))
			pipelineCursor.delete();
		pipelineCursor.insertAfter("serviceName", serviceName);
		
		if (pipelineCursor.first("fullName"))
			pipelineCursor.delete();
		pipelineCursor.insertAfter("fullName", fullName);
		
		if (pipelineCursor.first("successFlag"))
			pipelineCursor.delete();
		pipelineCursor.insertAfter("successFlag", successFlag);
		
		if (pipelineCursor.first("errorMessage"))
			pipelineCursor.delete();
		pipelineCursor.insertAfter("errorMessage", errorMessage);
		// --- <<IS-END>> ---

                
	}



	public static final void getUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getUser)>> ---
		// @sigtype java 3.5
		// [o] field:0:required username

	InvokeState is = InvokeState.getCurrentState();
	User user = is.getCurrentUser();
	String strUsername = user.getName();

	IDataCursor idcPipeline = pipeline.getCursor();
	idcPipeline.insertAfter("username", strUsername);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void invoke (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(invoke)>> ---
		// @sigtype java 3.5
		// [i] field:0:required folder
		// [i] field:0:required service

	IDataCursor idcPipeline = pipeline.getCursor();
	String strServiceName = null;
	String strFolderName = null;
	try
	{	
		if (idcPipeline.first("service"))
		{
			strServiceName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid service name");
		}

		if (idcPipeline.first("folder"))
		{
			strFolderName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid folder name");
		}
	
		if (strServiceName.length() == 0 || strFolderName.length() == 0)
			throw new ServiceException("Invalid service name");
		
		String strFullServiceName = strFolderName + ":" + strServiceName;

		// *** Check if service is on the allowed list ***
		IData in = IDataFactory.create();
		IDataCursor idcIn = in.getCursor();
		idcIn.insertAfter("paramName", "allowedServices");
		idcIn.insertAfter("paramValue", strFullServiceName);
		NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkParameterValidity");
		idcIn.destroy();
		IData out;
		out = Service.doInvoke(nsCheckServiceName, in);
		IDataCursor idcOut = out.getCursor();
		String strValid = null;
		if (idcOut.first("valid"))
		{
			strValid = (String)idcOut.getValue();
		}
		idcOut.destroy();

		if (strValid.equals("false"))
		{
			throw new ServiceException("Specified service is not on the allowed list in the PSUtilities configuration file!");
		}
		// *** End check ***
		
		NSName nsName = NSName.create(strFullServiceName);

		IData results = Service.doInvoke(nsName, pipeline);
		IDataUtil.merge(results, pipeline);

	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	finally
	{
		idcPipeline.destroy();		
	}
		// --- <<IS-END>> ---

                
	}



	public static final void invokeAndCatchErrors (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(invokeAndCatchErrors)>> ---
		// @sigtype java 3.5
		// [i] field:0:required folder
		// [i] field:0:required service

	IDataCursor idcPipeline = pipeline.getCursor();
	String strServiceName = null;
	String strFolderName = null;
	try
	{	
		if (idcPipeline.first("service"))
		{
			strServiceName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid service name");
		}

		if (idcPipeline.first("folder"))
		{
			strFolderName = (String)idcPipeline.getValue();
		}
		else
		{
			throw new ServiceException("Invalid folder name");
		}
	
		if (strServiceName.length() == 0 || strFolderName.length() == 0)
			throw new ServiceException("Invalid service name");
		
		String strFullServiceName = strFolderName + ":" + strServiceName;

		// *** Check if service is on the allowed list ***
		IData in = IDataFactory.create();
		IDataCursor idcIn = in.getCursor();
		idcIn.insertAfter("paramName", "allowedServices");
		idcIn.insertAfter("paramValue", strFullServiceName);
		NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkParameterValidity");
		idcIn.destroy();
		IData out;
		out = Service.doInvoke(nsCheckServiceName, in);
		IDataCursor idcOut = out.getCursor();
		String strValid = null;
		if (idcOut.first("valid"))
		{
			strValid = (String)idcOut.getValue();
		}
		idcOut.destroy();

		if (strValid.equals("false"))
		{
			throw new ServiceException("Specified service is not on the allowed list in the PSUtilities configuration file!");
		}
		// *** End check ***
		
		NSName nsName = NSName.create(strFullServiceName);

		IData results = Service.doInvoke(nsName, pipeline);
		IDataUtil.merge(results, pipeline);

	}
	catch(Exception e)
	{
		idcPipeline.insertAfter("error", e.toString());
		idcPipeline.insertAfter("errorMessage", e.getMessage());
	}
	finally
	{
		idcPipeline.destroy();		
	}
		// --- <<IS-END>> ---

                
	}



	public static final void makeSystemCall (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(makeSystemCall)>> ---
		// @sigtype java 3.5
		// [i] field:0:required command
		// [i] field:0:required synchronous {"true","false"}
		// [o] field:0:required status
		// [o] field:0:required output
		// [o] field:0:required error
		
		// DOS Syntax: cmd.exe /c 'command'
		//             Example: cmd.exe /c copy log.txt log2.txt
		
			IDataCursor idcPipeline = pipeline.getCursor();
			String strOutput = "";
			String strError = "";
			String strCommand = null;
			if (idcPipeline.first("command"))
			{
				strCommand = (String)idcPipeline.getValue();
			}
			else
			{
				return;
			}
		
			// *** Check if command is on the allowed list ***
			IData in = IDataFactory.create();
			IDataCursor idcIn = in.getCursor();
			idcIn.insertAfter("paramName", "allowedCommands");
			idcIn.insertAfter("paramValue", strCommand);
			NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkParameterValidity");
			idcIn.destroy();
			IData out;
			try
			{
				out = Service.doInvoke(nsCheckServiceName, in);
			}
			catch (Exception e)
			{
				throw new ServiceException(e);
			}
			IDataCursor idcOut = out.getCursor();
			String strValid = null;
			if (idcOut.first("valid"))
			{
				strValid = (String)idcOut.getValue();
			}
			idcOut.destroy();
		
			if (strValid.equals("false"))
			{
				throw new ServiceException("Specified command is not on the allowed list in the PSUtilities configuration file!");
			}
			// *** End check ***
		
			idcPipeline.first("synchronous");
			String strSynchronous = (String)idcPipeline.getValue();
		
			Process process = null;
		
			try
			{
				process = (Runtime.getRuntime()).exec(strCommand);
		
				if (strSynchronous.equals("true"))
				{
		
				// Provide an outlet for IO for the process
				String line; 
				BufferedReader ir = new BufferedReader(new InputStreamReader(process.getInputStream())); 
				BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream())); 
				while ((line = ir.readLine()) != null) 
				{
					System.out.println(line);
					strOutput += line + '\n';
				} 
		
				while ((line = er.readLine()) != null) 
				{
					System.out.println(line);
					strError += line + '\n';
				} 
				ir.close(); 
				er.close(); 
		
				process.waitFor();
		
				}
		
			}
			catch (Exception e)
			{
				throw new ServiceException(e.toString());
			}
			finally
			{
				if (strSynchronous.equals("true") && process != null)
				{
					int status = process.exitValue();
					idcPipeline.insertAfter("status", Integer.toString(status));
				}
				idcPipeline.insertAfter("output", strOutput);
				idcPipeline.insertAfter("error", strError);
				process.destroy();
				idcPipeline.destroy();
			}
		// --- <<IS-END>> ---

                
	}


    public static final Values matchServiceName (Values in)
    {
        Values out = in;
		// --- <<IS-START(matchServiceName)>> ---
		// @sigtype java 3.0
		// [i] record:1:required SvcStats
		// [i] field:0:required serviceName
		// [o] field:0:required firstNumber
		// [o] field:0:required name
	Values stats[] = (Values[])in.get("SvcStats");
	String match2 = in.getString("serviceName");

	for (int k = 0; k < stats.length ; k++)
	{
		String match1 = stats[k].getString("name");

		if (match1.equalsIgnoreCase(match2))
		{
    		out.put("firstNumber", stats[k].getString("sRunning"));
			out.put("name", stats[k].getString("name"));
		}

	} 
	out.remove("SvcStats");
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void println (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(println)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	string = IDataUtil.getString( pipelineCursor, "string" );
		System.out.println(string);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void sleep (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(sleep)>> ---
		// @sigtype java 3.5
		// [i] field:0:required millis
	IDataCursor cursor = pipeline.getCursor();

	long millis = 0;
	if (cursor.first("millis"))
	{
		 millis = Long.parseLong((String) cursor.getValue());
	}
	else
	{
		throw new ServiceException("Input parameter \'millis\' was not found.");
	}
	cursor.destroy();

	// *** Check if service is on the allowed list ***
	IData in = IDataFactory.create();
	IDataCursor idcIn = in.getCursor();
	idcIn.insertAfter("paramName", "maxSleep");
	NSName nsCheckServiceName = NSName.create("PSUtilities.config:getParameterValue");
	idcIn.destroy();
	IData out;
	try
	{
		out = Service.doInvoke(nsCheckServiceName, in);
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	IDataCursor idcOut = out.getCursor();
	String strParamValue = null;
	if (idcOut.first("paramValue"))
	{
		strParamValue = (String)idcOut.getValue();
	}
	idcOut.destroy();

	if (millis > Long.parseLong(strParamValue))
	{
		throw new ServiceException("Specified sleep interval has exceeded the maximum allowed sleep interval " +
									strParamValue + " specified by the PSUtiltiies configuration file");
	}
	// *** End check ***

	if (millis < 0)
	{
		throw new ServiceException("Parameter \'millis\' must have a positive value.");
	}
	try
	{
		Thread.sleep(millis);
	}
	catch (InterruptedException e)
	{
	}
		// --- <<IS-END>> ---

                
	}



	public static final void spawnThread (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(spawnThread)>> ---
		// @sigtype java 3.5
		// [i] field:0:required serviceName
		// [o] object:0:required threadHandle

	IDataCursor idcPipeline = pipeline.getCursor();
	String serviceName = null;
	if (idcPipeline.first("serviceName"))
	{
		serviceName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("service cannot be null");
	}

	// *** Check if service is on the allowed list ***
	IData in = IDataFactory.create();
	IDataCursor idcIn = in.getCursor();
	idcIn.insertAfter("paramName", "allowedServices");
	idcIn.insertAfter("paramValue", serviceName);
	NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkParameterValidity");
	idcIn.destroy();
	IData out;
	try
	{
		out = Service.doInvoke(nsCheckServiceName, in);
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	IDataCursor idcOut = out.getCursor();
	String strValid = null;
	if (idcOut.first("valid"))
	{
		strValid = (String)idcOut.getValue();
	}
	idcOut.destroy();

	if (strValid.equals("false"))
	{
		throw new ServiceException("Specified service is not on the allowed list in the PSUtilities configuration file!");
	}
	// *** End check ***
	
	try
	{
		// Split the interface and service from serviceName
		if (serviceName.indexOf(":") == -1)
		{
			throw new ServiceException("Service name must be in the format <interface>:<service>");
		}
		String ifc = serviceName.substring(0, serviceName.indexOf(":"));
		String service = serviceName.substring(serviceName.indexOf(":") + 1, serviceName.length());

		// Invoke service
		ServiceThread threadHandle = Service.doThreadInvoke(ifc,service,pipeline);
	    idcPipeline.insertAfter("threadHandle", threadHandle);
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	finally
	{
		idcPipeline.destroy();
	}
		// --- <<IS-END>> ---

                
	}



	public static final void throwError (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(throwError)>> ---
		// @sigtype java 3.5
		// [i] field:0:required errorMessage
		
		IDataCursor idcPipeline = pipeline.getCursor();
		
		String strErrorMessage = null;
		if (idcPipeline.first("errorMessage"))
		{
			strErrorMessage = (String)idcPipeline.getValue();
		}
		
		
		idcPipeline.destroy();
		
		throw new ServiceException(strErrorMessage);
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	/** Used by "deepConvert"
	  * 
	  * @author Ryan Johnston, Professional Services, webMethods, Inc.
	  * @version 1.0
	  */
	private static final Values convert(Hashtable hT){
		//Following statement gets all arrays in this object.
		boolean nullFlag = false;
		Object[] hTArray = hT.values().toArray();
		Enumeration hTEnumeration = hT.keys();
		Values outbound = new Values();
	
		for(int i=0;i<hTArray.length;i++){
			String key = (String)hTEnumeration.nextElement();
			if(hTArray[i] instanceof java.lang.String){
				outbound.put(key,(String)hTArray[i]);
			}
			else if(hTArray[i] instanceof java.util.Hashtable){
				Values internalObject = convert((Hashtable)hTArray[i]);
				if(internalObject == null){
					nullFlag = true;
					return null;
				}
				outbound.put(key,internalObject);						
			}
			else{
				System.out.println("Conversion Failure:" + "unsupported type within inbound Hashtable.");
				return null;
			}
		}
		return outbound; 
	}
	
	public static final int MAXSLEEP = 3600000;  // one hr
	// --- <<IS-END-SHARED>> ---
}

