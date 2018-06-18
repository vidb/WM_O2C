package PSUtilities.memory;

// -----( B2B Java Code Template v1.2
// -----( CREATED: Fri Jun 07 15:01:44 PDT 2002
// -----( ON-HOST: ESU

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
import java.util.*;
// --- <<B2B-END-IMPORTS>> ---

public final class persistence
{
	// ---( internal utility methods )---

	final static persistence _instance = new persistence();

	static persistence _newInstance() { return new persistence(); }

	static persistence _cast(Object o) { return (persistence)o; }

	// ---( server methods )---




	public static final void clearMemory (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(clearMemory)>> ---
		// @sigtype java 3.5

	// Memory is a Values object that consists of name/value pairs.  Below
	// we are getting all of the names (keys) stored in the Memory object
	// and then loop through them removing the values one at a time until
	// the are all gone.
	Enumeration keys = Memory.keys();
	
	if (keys != null)
	{
 		while (keys.hasMoreElements())
		{
			Memory.remove((String) keys.nextElement());
		}
    
	}
		// --- <<B2B-END>> ---

                
	}



	public static final void getDataFromMemory (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(getDataFromMemory)>> ---
		// @sigtype java 3.5
		// [i] field:0:required MemoryKey
	// Get cursor to pipeline IData object
	IDataCursor idcPipeline = pipeline.getCursor();

	// Initialize variables
	String strMemoryKey = null;
	IData idMemory = null;

	// Get data out of pipeline
	if (idcPipeline.first("MemoryKey"))
	{
		strMemoryKey = (String) idcPipeline.getValue();
	}
	else
	{	
		// Print to standard output an error message and exit processing
		System.out.println("Service: Util.memory.persistence:getDataFromMemory | " +
						   "required parameter 'MemoryKey' missing");
		return;
	}

	idMemory = (IData) Memory.get(strMemoryKey);
	if (idMemory == null)
	{
		// Print to standard output an error message and exit processing
		System.out.println("Service: Util.memory.persistence:getDataFromMemory | " +
						   "Unable to find 'MemoryKey' :" +strMemoryKey + " in Memory");
		return;
	}


	// Put session object into pipeline
	idcPipeline.insertAfter("MemoryData", idMemory);
	
	// Always destroy your cursors
	idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void listKeysInMemory (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(listKeysInMemory)>> ---
		// @sigtype java 3.5
		// [o] field:1:required MemoryKeys
	// Get cursor on pipeline object for data manipulation
	IDataCursor idcPipeline = pipeline.getCursor();

	// Gets a list of all of the keys and places them in a String[]
	Enumeration keys = Memory.keys();
	if (keys != null)
	{
		Vector idVector = new Vector();
		while (keys.hasMoreElements())
		{
			String id = (String) keys.nextElement();
			idVector.addElement(id);
		}
	    
     	int idCount = idVector.size();
	 	if (idCount >0)
	 	{	
			String[] MemoryKeys = new String[idCount];
			idVector.copyInto(MemoryKeys);
			
			// Put data into pipeline
			idcPipeline.insertAfter("MemoryKeys", MemoryKeys);
	 	}
	
	}

	// Always destroy cursor
	idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void removeDataFromMemory (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(removeDataFromMemory)>> ---
		// @sigtype java 3.5
		// [i] field:0:required MemoryKey
	// Get cursor to pipeline IData object
	IDataCursor idcPipeline = pipeline.getCursor();

	// Initialize variables
	String strMemoryKey = null;

	// Get data out of pipeline
	if (idcPipeline.first("MemoryKey"))
	{
		strMemoryKey = (String) idcPipeline.getValue();
	}
	else
	{	
		// Print to standard output an error message and exit processing
		System.out.println("Service: Util.memory.persistence:removeDataFromMemory | " +
						   "required parameter 'MemoryKey' missing");
		return;
	}
	


	IData idMemory = (IData) Memory.get(strMemoryKey);
	if (idMemory == null)
	{
		// Print to standard output an error message and exit processing
		System.out.println("Service: Util.memory.persistence:removeDataFromMemory | " +
						   "Unable to get MemoryData for MemoryKey: " + strMemoryKey);
		return;
	}

	
	// Remove the specified record from memory
	Memory.remove(strMemoryKey);

	// place record object back in pipeline
	idcPipeline.insertAfter("MemoryData", idMemory);
	
	// Always destroy cursor
	idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void saveDataToMemory (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(saveDataToMemory)>> ---
		// @sigtype java 3.5
		// [i] field:0:required MemoryKey
			// Get cursor to pipeline IData object
			IDataCursor idcPipeline = pipeline.getCursor();
		
			// Initialize variables
			String strMemoryKey = null;
			IData idMemory = null;
		
			// Get data out of pipeline
			if (idcPipeline.first("MemoryKey"))
			{
				strMemoryKey = (String) idcPipeline.getValue();
			}
			else
			{	
				// Print to standard output an error message and exit processing
				System.out.println("Service: Util.memory.persistence:saveDataToMemory | " +
								   "required parameter 'MemoryKey' missing");
				return;
			}
			
			if (idcPipeline.first("MemoryData"))
			{
				
		idMemory = (IData) idcPipeline.getValue();
			}
			else
			{	
				// Print to standard output an error message and exit processing
				System.out.println("Service: Util.memory.persistence:saveDataToMemory | " +
								   "Unable to get MemoryData for MemoryKey: " + strMemoryKey);
				return;
			}
		
			
			// Place the MemoryData object into the Memory values object
			Memory.put(strMemoryKey, idMemory);
		
			// Always destroy cursor
			idcPipeline.destroy();
		// --- <<B2B-END>> ---

                
	}

	// --- <<B2B-START-SHARED>> ---
	protected static Values Memory = new Values();
	// --- <<B2B-END-SHARED>> ---
}

