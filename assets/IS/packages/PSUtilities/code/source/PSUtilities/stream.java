package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-01-03 18:14:05 PST
// -----( ON-HOST: eng-114.activesw.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import com.wm.data.*;
import com.wm.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class stream

{
	// ---( internal utility methods )---

	final static stream _instance = new stream();

	static stream _newInstance() { return new stream(); }

	static stream _cast(Object o) { return (stream)o; }

	// ---( server methods )---




	public static final void bytesToStream (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(bytesToStream)>> ---
		// @sigtype java 3.5
		// [i] object:0:required buffer
		// [i] field:0:required length
		// [o] object:0:required blockStream
	IDataCursor cursor = pipeline.getCursor();

	byte[] buff = null;
	int len;
	ByteArrayInputStream blockStream = null;

	if (cursor.first("buffer"))
	{
		buff = (byte[]) cursor.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'buffer\' was not found.");
	}
	if (cursor.first("length"))
	{
		len = Integer.parseInt((String) cursor.getValue());
	}
	else
	{
		throw new ServiceException("Input parameter \'length\' was not found.");
	}
	cursor.destroy();

	if (buff != null)
	{
		blockStream = new ByteArrayInputStream(buff, 0, len);
	} 
	// else, no-op;

	cursor = pipeline.getCursor();
	cursor.last();
	cursor.insertAfter("blockStream", blockStream);
	cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void closeStream (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(closeStream)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stream
		// [o] field:0:required message

	//Global declarations
	String message = null;

	// get inputs
	IDataCursor idcPipeline = pipeline.getCursor();

	if ( idcPipeline.first( "stream" ) )
	{
		Object stream = idcPipeline.getValue();

		// The code below can be easily modified to handle Readers as well
		try
		{
			String className = stream.getClass().getName();

			if (className.endsWith("InputStream"))
			{
				InputStream is = (InputStream) stream;
				is.close();
				message = "Input Stream closed successfully.";
			}
			else if (className.endsWith("OutputStream"))
			{
				OutputStream os = (OutputStream) stream;
				os.close();
				message = "Output Stream closed successfully.";
			}
		}
		catch (Exception e)
		{
			throw new ServiceException(e.toString());
		}
	}
	else
	{
		throw new ServiceException("Input stream not found");
	}

	// outputs
	idcPipeline.last();
	idcPipeline.insertAfter( "message", message );
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void streamToBytes (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(streamToBytes)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stream
		// [o] object:0:required bytes
	IDataCursor pipelineCursor = pipeline.getCursor();
	
	ByteArrayInputStream input = null;
	BufferedInputStream input2 = null;
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	//BufferedOutputStream output2 = new BuferedOutputStream();

	if (pipelineCursor.first("stream"))
	{
		Object o = pipelineCursor.getValue();
		if (o instanceof byte[]) {
			byte[] buf = (byte[])pipelineCursor.getValue();
			input = new ByteArrayInputStream(buf); 
			String s = "";
			byte b[] = new byte[1024];
			int amount;
			try {
				while ((amount = input.read(b,0,1024))> 0)
				{
					output.write(b,0,amount);
				}
			}catch(Exception e){
				pipelineCursor.insertAfter("exception", e.toString());
			}
			pipelineCursor.insertAfter("bytes", output.toByteArray());
		}
		else if (o instanceof ByteArrayInputStream) {
			input = (ByteArrayInputStream)pipelineCursor.getValue();
			String s = "";
			byte b[] = new byte[1024];
			int amount;
			try {
				while ((amount = input.read(b,0,1024))> 0)
				{
					output.write(b,0,amount);
				}
			}catch(Exception e){
				pipelineCursor.insertAfter("exception", e.toString());
			}
			pipelineCursor.insertAfter("bytes", output.toByteArray());
		}
		else if (o instanceof BufferedInputStream) {
			input2  = (BufferedInputStream)pipelineCursor.getValue();
			//input2 = new BufferedInputStream(o); 
			String s = "";
			byte b[] = new byte[1024];
			int amount;
			try{
				while ((amount = input2.read(b,0,1024))> 0)
				{
					output.write(b,0,amount);
				}
			}catch(Exception e){
				pipelineCursor.insertAfter("exception", e.toString());
			}
			pipelineCursor.insertAfter("bytes", output.toByteArray());
		}
		
	}
		// --- <<IS-END>> ---

                
	}



	public static final void streamToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(streamToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stream
		// [o] field:0:required string

	IDataCursor idcPipeline = pipeline.getCursor();
	InputStream is=null;
	while (idcPipeline.next() )
	{
		if ( idcPipeline.getKey().equals("stream") )
		{
			is = (InputStream)idcPipeline.getValue();
			break;
		}
	}
 

	if (is != null )
	{
		byte b[] = new byte[8192];

		ByteOutputBuffer out = new ByteOutputBuffer();

		int read;

		try
		{

			while ( (read = is.read(b)) > 0 )
			{

				// System.err.print((char)read);

				out.write(b, 0, read);

			}

			String string = out.toString();

			idcPipeline.last();

			idcPipeline.insertAfter("string", string);
			}
			catch ( IOException ioe )
			{

				throw new ServiceException(ioe.getMessage() );
			}

			idcPipeline.destroy();
		}
		else
		{

			throw new ServiceException("No content");

		}

		// --- <<IS-END>> ---

                
	}



	public static final void stringToStream (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(stringToStream)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string
		// [o] object:0:required stream
		
			IDataCursor idcPipeline = pipeline.getCursor();
		
			idcPipeline.first("string");
			String string = (String)idcPipeline.getValue();
		
			StringBufferInputStream output = new StringBufferInputStream(string);
		
			while (idcPipeline.first("stream"))
			{
				idcPipeline.delete();
			}
		
			
		
		idcPipeline.last();
			
		Object inputStream = output;
			
		idcPipeline.insertAfter("stream", inputStream );
			
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}
}

