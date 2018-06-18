package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-01-03 13:24:56 PST
// -----( ON-HOST: eng-114.activesw.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.Session;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class session

{
	// ---( internal utility methods )---

	final static session _instance = new session();

	static session _newInstance() { return new session(); }

	static session _cast(Object o) { return (session)o; }

	// ---( server methods )---




	public static final void clearSession (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(clearSession)>> ---
		// @sigtype java 3.5
		// [o] field:0:required deleteCount

	Session session = Service.getSession();

	int intDeleteCount = 0;
	IDataCursor idcSession = session.getCursor();
	while (idcSession.hasMoreData())
	{
		idcSession.next();
		session.remove((String)idcSession.getKey());
		intDeleteCount++;
	}
	idcSession.destroy();

	IDataCursor idcPipeline = pipeline.getCursor();
	idcPipeline.insertAfter("deleteCount", Integer.toString(intDeleteCount));
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getObjectFromSession (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getObjectFromSession)>> ---
		// @sigtype java 3.5
		// [i] field:0:required objectName
		// [o] object:0:required object

	IDataCursor idcPipeline = pipeline.getCursor();
	String strObjectName = "";
	if (idcPipeline.first("objectName"))
	{
		strObjectName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("objectName is null!");
	}

	Session session = Service.getSession();
	Object obj = session.get(strObjectName);

	idcPipeline.insertAfter("object", obj);

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getSessionID (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSessionID)>> ---
		// @sigtype java 3.5
		// [o] field:0:required SessionID
	IDataCursor idcPipeline = pipeline.getCursor();

	Session session = Service.getSession();
	String strSessionID = session.getSessionID();

	idcPipeline.insertAfter("SessionID", strSessionID);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getSessionKeys (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSessionKeys)>> ---
		// @sigtype java 3.5
		// [o] field:0:required keyCount
		// [o] field:1:required sessionKeys

	Session session = Service.getSession();

	Vector vecKeys = new Vector();
	IDataCursor idcSession = session.getCursor();
	while (idcSession.hasMoreData())
	{
		idcSession.next();
		vecKeys.addElement((String)idcSession.getKey());
	}
	idcSession.destroy();

	int intKeyCount = vecKeys.size();
	String sessionKeys[] = new String[intKeyCount];

	Enumeration enum = vecKeys.elements();
	int i = 0;
	while (enum.hasMoreElements())
	{
		sessionKeys[i] = (String)enum.nextElement();
		i++;
	}

	IDataCursor idcPipeline = pipeline.getCursor();
	idcPipeline.insertAfter("keyCount", Integer.toString(intKeyCount));
	idcPipeline.insertAfter("sessionKeys", sessionKeys);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void putObjectInSession (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(putObjectInSession)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [i] field:0:required objectName

	IDataCursor idcPipeline = pipeline.getCursor();
	Object obj = null;
	String strObjectName = "";
	if (idcPipeline.first("object"))
	{
		obj = idcPipeline.getValue();
	}

	if (idcPipeline.first("objectName"))
	{
		strObjectName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("objectName is null!");
	}

	Session session = Service.getSession();
	session.put(strObjectName, obj);

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void removeObjectFromSession (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeObjectFromSession)>> ---
		// @sigtype java 3.5
		// [i] field:0:required objectName
		// [o] object:0:required object

	IDataCursor idcPipeline = pipeline.getCursor();
	String strObjectName = "";
	if (idcPipeline.first("objectName"))
	{
		strObjectName = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException ("objectName is null!");
	}

	Session session = Service.getSession();
	Object obj = session.remove(strObjectName);

	idcPipeline.insertAfter("object", obj);

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}
}

