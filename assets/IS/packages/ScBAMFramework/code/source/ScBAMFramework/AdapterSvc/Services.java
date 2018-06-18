package ScBAMFramework.AdapterSvc;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-07-13 13:06:56 PDT
// -----( ON-HOST: wmdemo65

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import java.text.*;
// --- <<IS-END-IMPORTS>> ---

public final class Services

{
	// ---( internal utility methods )---

	final static Services _instance = new Services();

	static Services _newInstance() { return new Services(); }

	static Services _cast(Object o) { return (Services)o; }

	// ---( server methods )---




	public static final void datetoTimestamp (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(datetoTimestamp)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required Date
		// [o] field:0:required value
		IDataCursor idcPipeline = pipeline.getCursor();
		try{
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date dt = null;
		 String strD = IDataUtil.getString( idcPipeline, "Date" );
		 dt = dateFormat.parse(strD);        
		IDataUtil.put(idcPipeline, "value" , String.valueOf(dt.getTime()));
		} catch (Exception ex)
		{
		IDataUtil.put(idcPipeline,"Error",ex.toString());
		}
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void timestampToDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(timestampToDate)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required timestamp
		// [o] field:0:required value
		IDataCursor idcPipeline = pipeline.getCursor();
		long l=Long.parseLong(IDataUtil.getString( idcPipeline,"timestamp"));
		Date dt= new Date(l);
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
		IDataUtil.put(idcPipeline, "value" ,format.format(dt));
		// --- <<IS-END>> ---

                
	}
}

