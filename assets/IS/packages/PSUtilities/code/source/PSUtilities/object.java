package PSUtilities;

// -----( B2B Java Code Template v1.2
// -----( CREATED: Mon Jun 10 22:38:02 PDT 2002
// -----( ON-HOST: ESU

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<B2B-START-IMPORTS>> ---
// --- <<B2B-END-IMPORTS>> ---

public final class object
{
	// ---( internal utility methods )---

	final static object _instance = new object();

	static object _newInstance() { return new object(); }

	static object _cast(Object o) { return (object)o; }

	// ---( server methods )---




	public static final void className (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(className)>> ---
		// @sigtype java 3.5
		// [i] object:0:required object
		// [o] field:0:required name
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		Object object = IDataUtil.get( pipelineCursor, "object" );
		
		pipelineCursor.insertAfter("name", object.getClass().getName());
		pipelineCursor.destroy();
		// --- <<B2B-END>> ---

                
	}



	public static final void instanceOf (IData pipeline)
        throws ServiceException
	{
		// --- <<B2B-START(instanceOf)>> ---
		// @sigtype java 3.5
		// [i] field:0:required class
		// [i] object:0:required object
		// [o] field:0:required instanceof
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	class_1 = IDataUtil.getString( pipelineCursor, "class" );
		Object	object = IDataUtil.get( pipelineCursor, "object" );
		
		pipelineCursor.insertAfter("instanceof", ""+(object.getClass().getName().equals(class_1)) );
		pipelineCursor.destroy();
		// --- <<B2B-END>> ---

                
	}
}

