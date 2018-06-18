package ScBAMFramework;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-07-06 10:39:41 PDT
// -----( ON-HOST: lab-54.west.webmethods.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.ServerAPI;
import java.util.Random;
import java.text.NumberFormat;
import java.io.*;
// --- <<IS-END-IMPORTS>> ---

public final class Util

{
	// ---( internal utility methods )---

	final static Util _instance = new Util();

	static Util _newInstance() { return new Util(); }

	static Util _cast(Object o) { return (Util)o; }

	// ---( server methods )---




	public static final void nextRandonGaussian (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(nextRandonGaussian)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required min
		// [i] field:0:required max
		// [o] field:0:required gRandom
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	_min = IDataUtil.getString( pipelineCursor, "min" );
		String	_max = IDataUtil.getString( pipelineCursor, "max" );
		
		
		
		double range = Double.parseDouble(_max) - Double.parseDouble(_min);
		double middle = Double.parseDouble(_max) - (range/2);
		
		String result = Double.toString(middle - (rand.nextGaussian() * (range/10)));
		
		int idx= result.indexOf(".");
		result = result.substring(0,idx);
		
		IDataUtil.put( pipelineCursor, "gRandom", result );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void rangedRandomNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(rangedRandomNumber)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required maxValue
		// [o] field:0:required rRandom
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	maxValue = IDataUtil.getString( pipelineCursor, "maxValue" );
		pipelineCursor.destroy();
		
		// pipeline
		Random rand = new Random();
		int rnum = rand.nextInt(Integer.parseInt(maxValue));
		String rnumStr = String.valueOf(rnum);
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "rRandom", rnumStr );
		pipelineCursor_1.destroy();
		
		// --- <<IS-END>> ---

                
	}



	public static final void sleep (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(sleep)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required sleeptime_millis
		String sleeptime_millis = (String) IDataUtil.get(pipeline.getCursor(), "sleeptime_millis");
		long sleeptime;
		try {
		    sleeptime = Long.parseLong(sleeptime_millis);
		} catch (Exception e) {
		    sleeptime = 1000; // default is one second
		}
		
		try {
		    Thread.sleep(sleeptime);
		} catch (Exception e) {
		}
		// --- <<IS-END>> ---

                
	}



	public static final void writeToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required userData
		// [i] field:0:required optimizeHome
		IDataCursor idcPipeline = pipeline.getCursor();
		String strUserData = null;
		String strOptimizeHome = null;
		if (idcPipeline.first("userData"))
		{
		  strUserData = (String)idcPipeline.getValue();
		}
		
		if (idcPipeline.first("optimizeHome")){
		  strOptimizeHome= (String)idcPipeline.getValue();
		} else {
			throw new ServiceException( "Optimize home directory location needed" );
		}
		
		String separator = System.getProperty("file.separator");
		String strPathName = strOptimizeHome+ separator+ "controller"+ separator+
		"conf"+separator+"data"+separator+"process"+separator;
		
		System.out.println(strPathName);
		String strFileName = "businessProcess.process";
		String strFullFileName=strPathName+separator+strFileName;
		FileWriter fw = null;
		try
		{
		 // Write the directory...
		 File pathToBeWritten = new File(strPathName);
		 if (pathToBeWritten.exists() == false){
		   boolean writtenFile = pathToBeWritten.mkdir();
		   if (writtenFile == false)
		   throw new ServiceException("Cannot create the directory indicated");
		  }
		 // Check if file exists
		 File fileToBeWritten = new File(strFullFileName);
		 // Write the file...
		 fw = new FileWriter(strFullFileName, false);
		 fw.write(strUserData);
		} catch (Exception e){
		   throw new ServiceException(e.getMessage());
		}finally {
		 // Close the output stream....
		  try{
		      fw.close();
		  }catch (Exception e)
		   {}
		   idcPipeline.destroy();
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static Random rand = new Random();
	// --- <<IS-END-SHARED>> ---
}

