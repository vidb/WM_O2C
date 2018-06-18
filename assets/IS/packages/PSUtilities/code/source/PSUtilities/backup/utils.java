package PSUtilities.backup;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-11-09 14:45:57 PST
// -----( ON-HOST: scarcius.berkeley.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.zip.*;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void zipFiles2 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(zipFiles2)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:1:required files
		// [i] field:0:required output
		// [o] field:0:required message
		IDataCursor idc = pipeline.getCursor();
		String[] files = null;
		String output = null;
		String message = "";
		if (idc.first("files")) {
			files = (String[])idc.getValue();
		}
		if (idc.first("output")) {
			output = (String)idc.getValue();
		}
		idc.destroy();
		
		
		// *** Check if paths are on the allowed list ***
		try
		{
			for (int i = 0; i < files.length; i++)
			{
				if (!checkPathValidity(files[i], "read"))
				{
					throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
				}
			}
		
			if (!checkPathValidity(output, "write"))
			{
				throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		// *** End check ***
		
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(output));
			for (int i = 0; i< files.length;i++) {
				System.out.println("Now adding " + files[i]);
		
				zos.putNextEntry(new ZipEntry(files[i]));
				try {
					FileInputStream fis = new FileInputStream(files[i]);
					byte[] b = new byte[65536];
					int byteRead= 0 ;
					while ((byteRead = fis.read(b))!= -1) {  // Till not end of file
						zos.write(b, 0, byteRead) ;
					}
					fis.close();
				} catch(Exception e) {
					message += files[i] + " ";
				}
				zos.closeEntry();
			}
			zos.close();
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!message.equals("")) {
			IDataCursor idco = pipeline.getCursor();
			idc.insertAfter("message", "Can not zip " + message);
			idc.destroy();
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static final boolean checkPathValidity(String strPath, String strAction)
	  throws Exception
	{
		try
		{
			// *** Check if service is on the allowed list ***
			IData in = IDataFactory.create();
			IDataCursor idcIn = in.getCursor();
			idcIn.insertAfter("path", strPath);
			idcIn.insertAfter("action", strAction);
			NSName nsCheckServiceName = NSName.create("PSUtilities.config:checkPathValidity");
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
				return false;
			}
			return true;
			// *** End check ***
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	// --- <<IS-END-SHARED>> ---
}

