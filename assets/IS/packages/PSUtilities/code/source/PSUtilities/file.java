package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 050323 202948.235 GMT
// -----( ON-HOST: ldndrm02025.intranet.barcapint.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.lang.SecurityException;
import java.util.Properties;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class file

{
	// ---( internal utility methods )---

	final static file _instance = new file();

	static file _newInstance() { return new file(); }

	static file _cast(Object o) { return (file)o; }

	// ---( server methods )---




	public static final void bytesToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(bytesToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] object:0:required bytes
		// [i] field:0:required append {"true","false"}
		// [o] field:0:required fullpath
		// [o] field:0:required length
	IDataCursor idcPipeline = pipeline.getCursor();

	byte[] bytes = null;
	String strFilename = null;

	if (idcPipeline.first("bytes"))
	{
		bytes = (byte[]) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'bytes\' was not found.");
	}
	if (idcPipeline.first("filename"))
	{
		strFilename = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'filename\' was not found.");
	}
	boolean a = idcPipeline.first("append") ? Boolean.valueOf((String) idcPipeline.getValue()).booleanValue() : false;

	File file = new File(strFilename);
	String basename = file.getName();

	String fullpath = null;
	try
	{
		File parent = file.getCanonicalFile().getParentFile();
		File targetDir = parent;

		if ( ! targetDir.exists() )
		{
			targetDir.mkdirs();
		}
		file = new File(targetDir, basename);
		fullpath = file.getCanonicalPath();
		FileOutputStream writer = new FileOutputStream(file.getCanonicalPath(), a);  // append or not
		writer.write(bytes);	
		writer.close();
	}
	catch (IOException e)
	{
		throw new ServiceException(e);
	}

	idcPipeline.last();
	idcPipeline.insertAfter("fullpath", fullpath);
	idcPipeline.insertAfter("length", "" + bytes.length);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void checkFileExistence (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(checkFileExistence)>> ---
		// @sigtype java 3.5
		// [i] field:0:required pathname
		// [o] field:0:required exists?
		// [o] field:0:required isDirectory?
		
		IDataCursor idcPipeline = pipeline.getCursor();
		String strPathname = "";
		if (idcPipeline.first("pathname"))
		{
			strPathname = (String)idcPipeline.getValue();
			File path = new File(strPathname);
		
			// *** Check if path is on the allowed list ***
			try
			{
				if (!checkPathValidity(strPathname, "read"))
				{
					throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
				}
			}
			catch (Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			// *** End check ***
		
			if (!path.exists())
				idcPipeline.insertAfter("exists?", "false");
			else
				idcPipeline.insertAfter("exists?", "true"); 
		
			if (path.isDirectory())
				idcPipeline.insertAfter("isDirectory", "true");
			else
				idcPipeline.insertAfter("isDirectory?", "false");
		}
		else
		{
			idcPipeline.insertAfter("exists?", "false");
			idcPipeline.insertAfter("isDirectory?", "false");
		}
		
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void copyFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(copyFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required sourceDirectory
		// [i] field:0:required targetDirectory
		// [o] field:0:required message

	IDataCursor idcPipeline = pipeline.getCursor();

	String strFilename = "";
	String strSourceDirectory = null;
	String strTargetDirectory = null;

	if (idcPipeline.first("filename"))
	{
		strFilename = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Copy Failed: Filename is null!");
	}
	if (idcPipeline.first("sourceDirectory"))
	{
		strSourceDirectory = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("targetDirectory"))
	{
		strTargetDirectory = (String)idcPipeline.getValue();
	}

	// *** Check if paths are on the allowed list ***
	String strSourceFullPath = strSourceDirectory + System.getProperty("file.separator") + strFilename;
	String strTargetFullPath = strTargetDirectory + System.getProperty("file.separator") + strFilename;
	try
	{
		if (!checkPathValidity(strSourceFullPath, "read"))
		{
			throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
		}
		if (!checkPathValidity(strTargetFullPath, "write"))
		{
			throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	if (strSourceDirectory != strTargetDirectory)
	{
		FileReader frSourceFile = null;
		FileWriter fwTargetFile = null;
		File sourceFile = null;
		File targetFile = null;

		try
		{

			sourceFile = new File(strSourceDirectory, strFilename); 
			targetFile = new File(strTargetDirectory, strFilename); 

			if (!sourceFile.exists() || sourceFile.isDirectory())
			{ 
				throw new ServiceException("MOVE ERROR: Source file not found (" + sourceFile.getAbsolutePath() + ")");
			} 

			frSourceFile = new FileReader(sourceFile); 
			fwTargetFile = new FileWriter(targetFile); 

			int c; 
			while ((c = frSourceFile.read()) != -1) 
			{
				fwTargetFile.write(c);
			}	

			idcPipeline.insertAfter("message", "Copied: " + sourceFile.getAbsolutePath() + " to " + strTargetDirectory);

		}
		catch (Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		finally
		{
			try
			{
				frSourceFile.close();
				fwTargetFile.close();
			}
			catch (Exception e)
			{
			}
			sourceFile = null;
			targetFile = null;
		}
	}

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void deleteFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [o] field:0:required deleteStatus
		// [o] field:0:required length
	IDataCursor cursor = pipeline.getCursor();

	String filename = null;
	if (cursor.first("filename"))
	{
		filename = (String) cursor.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'filename\' was not found.");
	}
	cursor.destroy();


	// *** Check if path is on the allowed list ***
	try
	{
		if (!checkPathValidity(filename, "delete"))
		{
			throw new ServiceException("Specified path is not on the delete allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	File file = new File(filename);
	long len = file.length();
	boolean b = file.delete();

	cursor = pipeline.getCursor();
	cursor.last();
	cursor.insertAfter("deleteStatus", "" + b);
	cursor.insertAfter("length", "" + len);
	cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getBasename (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getBasename)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [o] field:0:required basename
	IDataCursor idcPipeline = pipeline.getCursor();

	String filename = null;
	if (idcPipeline.first("filename"))
	{
		filename = (String) idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'filename\' was not found.");
	}

	File file = new File(filename);
	String basename = file.getName();

	idcPipeline.last();
	idcPipeline.insertAfter("basename", basename);  
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void listFiles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listFiles)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required directory
		// [o] field:1:required fileList
		// [o] field:1:required fileList_fullpathnames
		// [o] field:0:required numFiles
	IDataCursor idcPipeline = pipeline.getCursor();

	String directory = null;
	if (idcPipeline.first("directory"))
	{
		directory = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Directory is null!");
	}

	String strFileSeparator = System.getProperty("file.separator");
	int numFiles = 0;

	// *** Check if path is on the allowed list ***
	try
	{
		if (!checkPathValidity(directory, "read"))
		{
			throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	File dir = new File(directory);

	if (!dir.exists() || !dir.isDirectory())
	{
		throw new ServiceException("Error reading inbox directory!");
	}

	String dir_list[];
	dir_list = dir.list();
	int num_filesanddir = dir_list.length;

	if (num_filesanddir < 1)		//No files to send
	{
		idcPipeline.insertAfter("numFiles", Integer.toString(numFiles));
		return;
	}

	//Determine number of actual files
	File curr_file;
	for (int i = 0; i < num_filesanddir; i++)
	{
		curr_file = new File(directory + strFileSeparator + dir_list[i]);
		if (curr_file.isFile())
		{
			numFiles++;
		}
	}
	curr_file = null;
 
	//Create array with only names of files (excluding directories)
	String [] fileList = new String[numFiles]; 
	String [] fileList_fullpathnames = new String[numFiles]; 
	numFiles = 0;
	for (int i = 0; i < num_filesanddir; i++)
	{
		curr_file = new File(directory + strFileSeparator + dir_list[i]);
		if (curr_file.isFile())
		{
			fileList[numFiles] = dir_list[i];

			// Assemble fully qualified filenames (check UNIX & NT)
			char lastChar = directory.charAt(directory.length() - 1);
			if ((lastChar == '\\') || (lastChar == '/'))
			{
				fileList_fullpathnames[numFiles] = directory + dir_list[i];
			}
			else
			{
				fileList_fullpathnames[numFiles] = directory + strFileSeparator + dir_list[i];
			}
			numFiles++;
		}
	}
	curr_file = null;

	idcPipeline.insertAfter("numFiles", Integer.toString(numFiles));
	idcPipeline.insertAfter("fileList",fileList);
	idcPipeline.insertAfter("fileList_fullpathnames",fileList_fullpathnames);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void moveFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(moveFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required sourcePath
		// [i] field:0:required targetDir
		// [i] field:0:required useTimeStamp {"false","true"}
		// [o] field:0:required status
		// [o] field:0:required targetFilePath
			IDataCursor cursor = pipeline.getCursor();
		
			String sourcePath = null;
			String targetDir = null;
		
			if (cursor.first("sourcePath"))
			{
				 sourcePath = (String) cursor.getValue();
			}
			else
			{   
				throw new ServiceException("Input parameter \'sourcePath\' was not found.");
			}	
			if (cursor.first("targetDir"))
			{
				 targetDir = (String) cursor.getValue();
			}
			else
			{   
				throw new ServiceException("Input parameter \'targetDir\' was not found.");
			}	
			boolean useTimeStamp = cursor.first("useTimeStamp") ? 
				Boolean.valueOf((String) cursor.getValue()).booleanValue() : false;
			cursor.destroy();
		
			// *** Check if paths are on the allowed list ***
			try
			{
				if (!checkPathValidity(sourcePath, "read"))
				{
					throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
				}
				if (!checkPathValidity(targetDir, "write"))
				{
					throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
				}
			}
			catch (Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			// *** End check ***
		
			boolean status = false;
			File original = new File(sourcePath);
		
		//	String dtStamp = new Date().toString().replace(' ', '-').replace(':', '-');
			String dtStamp = null;
			try
			{
				IData d = IDataFactory.create();
				IDataCursor c = d.getCursor();
				c.first();
				c.insertBefore("pattern", "yyyyMMddHHmmss");
				IData d2 = Service.doInvoke("pub.date", "currentDate", d);
				c = d2.getCursor();
				if (c.first("value"))
				{
					dtStamp = (String) c.getValue();
				}
				else
				{
					throw new ServiceException("Missing returned value for Service currentDate.");
				}
			}
			catch (Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			String basename = original.getName();
			if (useTimeStamp)
			{
				basename += "-" + dtStamp;
			}
			File targetFile = new File(targetDir, basename);
			status = original.renameTo(targetFile);
			String targetFilePath = null;
			try
			{
				targetFilePath = targetFile.getCanonicalPath();
			}
			catch (IOException e)
			{
				throw new ServiceException("Move file and obtain target path: " + e.getMessage());
			}
			cursor = pipeline.getCursor();
			cursor.last();
			cursor.insertAfter("status", "" + status);
			cursor.insertAfter("targetFilePath", targetFilePath);
			cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void objectToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(objectToFile)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] object:0:required object

	IDataCursor idcPipeline = pipeline.getCursor();
	if (!idcPipeline.first("object"))
	{
		throw new ServiceException("Object is null!");
	}
	
	Object o = idcPipeline.getValue();
	String strFilename;
	if (idcPipeline.first("filename"))
	{
		strFilename = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("Filename is null!");
	}

	// *** Check if path is on the allowed list ***
	try
	{
		if (!checkPathValidity(strFilename, "write"))
		{
			throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	FileOutputStream fos = null;
	ObjectOutputStream oos = null;
	try
	{
		fos = new FileOutputStream(strFilename);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(o);
		oos.flush();
	}
	catch (Exception e)
	{
		throw new ServiceException("Exception occurred while writing object to file: " + e);
	}
	finally
	{
		idcPipeline.destroy();
		try
		{
			fos.close();
			oos.close();
		}
		catch (Exception e)
		{
			throw new ServiceException("Exception occurred while writing object to file: " + e);
		}
	}
		// --- <<IS-END>> ---

                
	}



	public static final void streamToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(streamToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] object:0:required stream
		// [i] field:0:required append {"true","false"}
		// [o] field:0:required fullpath
		// [o] field:0:required length
	IDataCursor cursor = pipeline.getCursor();
	InputStream instream = null;
	String strFilename = null;
	long len = 0;  // file length
	if (cursor.first("stream"))
	{
		instream = (InputStream) cursor.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'stream\' was not found.");
	}
	
	if (cursor.first("filename"))
	{
		strFilename = (String) cursor.getValue();
	}
	else
	{
		throw new ServiceException("Input parameter \'filename\' was not found.");
	}
	boolean a = cursor.first("append") ? Boolean.valueOf((String) cursor.getValue()).booleanValue() : false;
	cursor.destroy();

	// *** Check if path is on the allowed list ***
	try
	{
		if (!checkPathValidity(strFilename, "write"))
		{
			throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	File file = new File(strFilename);
	String basename = file.getName();
	File dot = null;
	try
	{
		dot = file.getCanonicalFile().getParentFile();
	}
	catch (IOException e)
	{
		throw new ServiceException(e.getMessage());
	}
	File targetDir = dot;  // namely '.' directory

	if ( ! targetDir.exists() )
	{
		targetDir.mkdirs();
	}
	file = new File(targetDir, basename);

	String parentDir = null;
	String dotDir = null;
	String path = null;

	File parent = targetDir.getParentFile();
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	try
	{
		parentDir = parent.getCanonicalPath();
		dotDir = targetDir.getCanonicalPath();
		path = file.getCanonicalPath();		
		fos = new FileOutputStream(path, a);
		int BUFSIZE = 4096;
		bos = new BufferedOutputStream(fos, BUFSIZE);

		byte[] buf = new byte[BUFSIZE];
		// Possible values of r: (1) r > 0, or (2) r < 0 when eof is reached; no r = 0.
		int r = instream.read(buf, 0, BUFSIZE);  
		while (r > 0)
		{
			bos.write(buf, 0, r);
			len += r;
			r = instream.read(buf, 0, BUFSIZE);
		}
		bos.close();
		instream.close();  // reached the end-of-file
	}
	catch (IOException e)
	{
		try {
			if (bos != null) {
				bos.close();
			} else {
				if (fos != null) {
					fos.close();
				}
			}
			instream.close();  
		} catch (IOException e2) {
			throw new ServiceException(e2);
		}
		throw new ServiceException(e);
	}

	cursor = pipeline.getCursor();
	cursor.last();
	cursor.insertAfter("fullpath", "" + path);  // canonical path
	cursor.insertAfter("length", "" + len);
	cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void writeToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required userData
		// [i] field:0:required filename
		// [i] field:0:required appendOverwriteFlag {"append","overwrite","failIfFileExists"}
			// Based on Ryan's writeFile service
		
			IDataCursor idcPipeline = pipeline.getCursor();
			String strUserData = null;
			String strFullFilename = null;
			if (idcPipeline.first("userData"))
			{
				strUserData = (String)idcPipeline.getValue();
			}
			if (idcPipeline.first("filename"))
			{
				strFullFilename = (String)idcPipeline.getValue();
			}
			else
			{
				throw new ServiceException( "filename is null!" );
			}
			idcPipeline.first("appendOverwriteFlag");
			String appendOverwriteFlag = (String)idcPipeline.getValue();
		
			// *** Check if path is on the allowed list ***
			try
			{
				if (!checkPathValidity(strFullFilename, "write"))
				{
					throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
				}
			}
			catch (Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			// *** End check ***
		
			// Separate filename into path and filename
			// This is done so that the directory can be written (if necessary)
			String separator = System.getProperty("file.separator");
			int indexSeparator = strFullFilename.lastIndexOf(separator);
			if (indexSeparator == -1)
			{
				// Account for fact that you can use either '\' or '/' in Windows
				indexSeparator = strFullFilename.lastIndexOf('/');
			}
			String strPathName = strFullFilename.substring(0, indexSeparator+1);
			String strFileName = strFullFilename.substring(indexSeparator+1);
		
			FileWriter fw = null;
			try
			{
				File pathToBeWritten = new File(strPathName);
		//		System.out.println("canonical path = " + pathToBeWritten.getCanonicalPath());
		
				// Write the directory...
				if (pathToBeWritten.exists() == false)
				{
					throw new ServiceException("Path does not exist!");
				}
		
				// Check if file exists
				File fileToBeWritten = new File(strFullFilename);
				if (fileToBeWritten.exists() == true && appendOverwriteFlag != null && appendOverwriteFlag.equals("failIfFileExists"))
				{
					throw new ServiceException("File " + strFullFilename + " already exists!");
				}
		
				// Write the file...
				if (appendOverwriteFlag != null && appendOverwriteFlag.equals("overwrite"))
				{
					// overwrite
					fw = new FileWriter(strFullFilename, false);
				}
				else
				{
					// append
					fw = new FileWriter(strFullFilename, true);
				}
				fw.write(strUserData);
			}
			catch (Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			finally
			{
				// Close the output stream....
				try
				{
					fw.close();
				}
				catch (Exception e)
				{}
		
				idcPipeline.destroy();
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

