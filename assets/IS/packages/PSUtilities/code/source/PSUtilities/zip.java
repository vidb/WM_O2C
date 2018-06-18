package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-01-03 21:03:14 PST
// -----( ON-HOST: eng-114.activesw.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.zip.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import com.wm.lang.ns.*;
// --- <<IS-END-IMPORTS>> ---

public final class zip

{
	// ---( internal utility methods )---

	final static zip _instance = new zip();

	static zip _newInstance() { return new zip(); }

	static zip _cast(Object o) { return (zip)o; }

	// ---( server methods )---




	public static final void unzipFiles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(unzipFiles)>> ---
		// @sigtype java 3.5
		// [i] field:0:required zipFileName
		// [i] field:0:required targetDirectory
		// [o] field:0:required numFilesProcessed
		// [o] field:1:required unzippedFileNames
	IDataCursor idcPipeline = pipeline.getCursor();
	
	String strZipFileName = "";
	String strTargetDirectory = "";
	
	if (idcPipeline.first("zipFileName"))
		strZipFileName = (String)idcPipeline.getValue();
	if (idcPipeline.first("targetDirectory"))
		strTargetDirectory = (String)idcPipeline.getValue();

	// *** Check if paths are on the allowed list ***
	try
	{
		if (!checkPathValidity(strZipFileName, "read"))
		{
			throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
		}
		if (!checkPathValidity(strTargetDirectory, "write"))
		{
			throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	FileInputStream fileis = null;
	try 
	{
		fileis = new FileInputStream(strZipFileName);
	}
	catch (FileNotFoundException e)
	{
		throw new ServiceException("Could not find zip file: " + strZipFileName);
	}

	BufferedInputStream buffileis = new BufferedInputStream(fileis);
	ZipInputStream zipis = new ZipInputStream(buffileis);

	Vector vecUnzippedFileNames = new Vector();
	BufferedOutputStream buffileos = null;
	FileOutputStream fileos = null;
	int intNumFilesProcessed = 0;
	ZipEntry ze = null;

	try
	{ 
		while ((ze = zipis.getNextEntry()) != null)
		{
			if (ze.isDirectory())
			{
				continue;
			}

			// System.out.println("ZIP entry: " + ze.getName()); //debug

			// Create output file
			String strUnzippedFileName = strTargetDirectory + "/" + ze.getName();
		    fileos = new FileOutputStream(strUnzippedFileName); 
			buffileos = new BufferedOutputStream(fileos);

			int intNumBytesRead;
			byte data[] = new byte[4096];
			
			while ((intNumBytesRead = zipis.read(data))!=-1)
			{
				buffileos.write(data, 0, intNumBytesRead);
			}

			vecUnzippedFileNames.addElement(strUnzippedFileName);
			intNumFilesProcessed++;
		}//end while
	}
	catch (IOException e)
	{
		throw new ServiceException("Exception occurred while handling ZIP file: " + e);
	}
	finally
	{
		if (buffileos != null)
		{
			try
			{
				buffileos.flush();
				buffileos.close();
			}
			catch (IOException e)
			{}
		}
		if (fileos != null)
		{
			try
			{ fileos.close(); }
			catch (IOException e)
			{}
		}
		if (zipis != null)
		{
			try
			{ zipis.close(); }
			catch (IOException e)
			{}
		}
		if (fileis != null)
		{
			try
			{ fileis.close(); }
			catch (IOException e)
			{}
		}
		if (buffileis != null)
		{
			try
			{ buffileis.close(); }
			catch (IOException e)
			{}
		}
	}

	String arrStrUnzippedFileNames[] = new String[vecUnzippedFileNames.size()];
	Enumeration enum = vecUnzippedFileNames.elements();
	int i = 0;
	while (enum.hasMoreElements())
	{
		arrStrUnzippedFileNames[i] = (String)enum.nextElement();
		i++;
	}

	System.gc();
	idcPipeline.insertAfter("numFilesProcessed", Integer.toString(intNumFilesProcessed));
	idcPipeline.insertAfter("unzippedFileNames", arrStrUnzippedFileNames);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void zipFiles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(zipFiles)>> ---
		// @sigtype java 3.5
		// [i] field:0:required sourcePath
		// [i] field:0:required zipFileName
		// [o] field:0:required numFilesZipped

	IDataCursor idcPipeline = pipeline.getCursor();
	String strSourcePath = "";
	String strZipFileName = "default.zip";

	if (idcPipeline.first("sourcePath"))
		strSourcePath = (String)idcPipeline.getValue();
	if (idcPipeline.first("zipFileName"))
		strZipFileName = (String)idcPipeline.getValue();

	// *** Check if paths are on the allowed list ***
	try
	{
		if (!checkPathValidity(strSourcePath, "read"))
		{
			throw new ServiceException("Specified path is not on the read allowed list in the PSUtilities configuration file!");
		}
		if (!checkPathValidity(strZipFileName, "write"))
		{
			throw new ServiceException("Specified path is not on the write allowed list in the PSUtilities configuration file!");
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	// *** End check ***

	FileOutputStream fileos = null;
	BufferedOutputStream buffileos = null;
	ZipOutputStream zipos = null;

	int intNumFiles = 0;
	try
	{
		fileos = new FileOutputStream(strZipFileName);
		buffileos = new BufferedOutputStream(fileos);
		zipos = new ZipOutputStream(buffileos);

		File fileSourcePath = new File(strSourcePath);
		if (fileSourcePath.isDirectory())
		{
			intNumFiles = addDirectoryToZip(strSourcePath, "", zipos);
		}
		else	//file
		{
			// Get filename, without path
			int index = strSourcePath.lastIndexOf(System.getProperty("file.separator"));
			String strFilename = strSourcePath.substring(index + 1);

			addFileToZip(strSourcePath, strFilename, zipos);
			intNumFiles++;
		}
	}
	catch (IOException e)
	{
		throw new ServiceException("Exception occurred while creating ZIP file: " + e);
	}
	finally
	{
		if (zipos != null)
		{
			try
			{
				zipos.close();
			}
			catch (IOException e)
			{
				throw new ServiceException("Exception occurred while creating ZIP file: " + e);
			}
		}
		if (fileos != null)
		{
			try
			{
				fileos.close();
			}
			catch (IOException e)
			{
				throw new ServiceException("Exception occurred while creating ZIP file: " + e);
			}
		}
		if (buffileos != null)
		{
			try
			{
				buffileos.flush();
				buffileos.close();
			}
			catch (IOException e)
			{
				throw new ServiceException("Exception occurred while creating ZIP file: " + e);
			}
		}
	}

	idcPipeline.insertAfter("numFilesZipped", Integer.toString(intNumFiles));
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static int addDirectoryToZip(String strAbsoluteDirectory, String strRelativePath, ZipOutputStream zipos)
	{
		//System.out.println("Absolute directory: " + strAbsoluteDirectory);
		//System.out.println("Relative Path: " + strRelativePath);
		File fileCurrentFile = null;
		int intNumFiles = 0;
	
		File fileSourceDir = new File(strAbsoluteDirectory + "/" + strRelativePath);
	
		// Check if source directory name is valid
		if (!fileSourceDir.exists() || !fileSourceDir.isDirectory())
		{
			Service.throwError("Error reading inbox directory!");
		}
	
		String arrstrSourceDirectoryList[] = fileSourceDir.list();
		int intNumFilesAndDirectories = arrstrSourceDirectoryList.length;
	
		if (intNumFilesAndDirectories < 1)
		{
			//No files to send
			return 0;
		}
	
		//Determine number of actual files
		for (int i = 0; i < intNumFilesAndDirectories; i++)
		{
			String strCurrentFileName = strAbsoluteDirectory + "/" + strRelativePath + "/" + arrstrSourceDirectoryList[i];
			String strCurrentRelativeFileName = null;
			if (strRelativePath == "")
			{
				strCurrentRelativeFileName = arrstrSourceDirectoryList[i];
			}
			else
			{
				strCurrentRelativeFileName = strRelativePath + "/" + arrstrSourceDirectoryList[i];
			}
	
			//System.out.println("currentRelativeFileName: " + strCurrentRelativeFileName);
			//System.out.println("currentFileName: " + strCurrentFileName);
				
			fileCurrentFile = new File(strCurrentFileName);
			if (fileCurrentFile.isDirectory())
			{
				//System.out.println("directory found: " + strCurrentFileName);
				intNumFiles = intNumFiles + addDirectoryToZip(strAbsoluteDirectory, strCurrentRelativeFileName, zipos);
			}
			else //File
			{
				intNumFiles++;
				addFileToZip(strCurrentFileName, strCurrentRelativeFileName, zipos);
			}
		}
	
		fileCurrentFile = null;
		return intNumFiles;
	}
	
	static void addFileToZip(String strCurrentFileName, String strCurrentRelativeFileName, ZipOutputStream zipos)
	{
		FileInputStream fileis = null;
		BufferedInputStream buffileis = null;
		try
		{
			fileis = new FileInputStream(strCurrentFileName);
			buffileis = new BufferedInputStream(fileis);
	
			// Zip File and add to archive
			ZipEntry ze = new ZipEntry(strCurrentRelativeFileName);
			zipos.putNextEntry(ze);
	
			int intNumBytesRead;
			byte data[] = new byte[4096];
			
			while ((intNumBytesRead = buffileis.read(data)) != -1)
			{
				zipos.write(data, 0, intNumBytesRead);
			}
			zipos.closeEntry();
		}
		catch (FileNotFoundException e)
		{
			Service.throwError("Could not open file: " + e);
		}
		catch (IOException e)
		{
			Service.throwError("Could create new zip entry: " + e);
		}
		finally
		{
			if (buffileis != null)
			{
				try
				{
					buffileis.close();
				}
				catch (IOException e)
				{}
			}
			if (fileis != null)
			{
				try
				{ fileis.close(); }
				catch (IOException e)
				{}
			}
		}
	}
	
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

