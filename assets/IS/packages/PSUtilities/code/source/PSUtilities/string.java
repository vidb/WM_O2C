package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-01-03 20:42:41 PST
// -----( ON-HOST: eng-114.activesw.com

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.StringTokenizer;
import java.text.*;
import java.util.Date;
// --- <<IS-END-IMPORTS>> ---

public final class string

{
	// ---( internal utility methods )---

	final static string _instance = new string();

	static string _newInstance() { return new string(); }

	static string _cast(Object o) { return (string)o; }

	// ---( server methods )---



    public static final Values addLineSeparator (Values in)
    {
        Values out = in;
		// --- <<IS-START(addLineSeparator)>> ---
		// @sigtype java 3.0
		// [i] field:0:required inString
		// [o] field:0:required outString
		
		String strinString = in.getString("inString");
		String stroutString = "";
		
		String lineSeparator = System.getProperty("line.separator");
		
		if (strinString != null)
		{
			stroutString = strinString + lineSeparator;
		}
		else
		{
			stroutString = lineSeparator;
		}
		out.put("outString", stroutString); 
		// --- <<IS-END>> ---
        return out;
                
	}


    public static final Values compareString (Values in)
    {
        Values out = in;
		// --- <<IS-START(compareString)>> ---
		// @sigtype java 3.0
		// [i] field:0:required string1
		// [i] field:0:required string2
		// [o] field:0:required isEqual
		
		String string1 = in.getString("string1");
		String string2 = in.getString("string2");
		
		if (string1 == null && string2 == null)
		{
			out.put("isEqual", "true");
			return out;
		}
		
		if (string1 == null)
		{
			out.put("isEqual", "false");
			return out;
		}
		
		if (string1.equals(string2))
			out.put("isEqual", "true");
		else  
			out.put("isEqual", "false");
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void convertLineSeparator (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertLineSeparator)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inString
		// [o] field:0:required outString
		
			IDataCursor idcPipeline = pipeline.getCursor();
		
			String inString = null;
			if (idcPipeline.first("inString"))
			{
				inString = (String)idcPipeline.getValue();
			}
			else
			{
				// Input string is null.  Do nothing
				return;
			}
		
			String outString = "";
		
			String lineSeparator = System.getProperty("line.separator");
		
			int currIndex = 0;
			int prevIndex = 0;
			while ((currIndex = inString.indexOf('\n',currIndex))!= -1)
			{
				// If this is part of \r\n, then don't replace
				if ((currIndex != 0) && (inString.charAt(currIndex - 1) == '\r'))
				{
					currIndex = currIndex + 1;
				}
				else
				{
					outString = outString + inString.substring(prevIndex,currIndex) + lineSeparator;
					prevIndex = currIndex + 1;
					currIndex = currIndex + 1;
				}
			}
			outString = outString + inString.substring(prevIndex);
		
			idcPipeline.insertAfter("outString", outString);
		
		// Clean up IData cursors
			idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void isAlphanumeric (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isAlphanumeric)>> ---
		// @sigtype java 3.5
		// [i] field:0:required input
		// [o] field:0:required isAlphanumeric
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		pipelineCursor.first( "input" );
		String input = (String)pipelineCursor.getValue();
		
		String isAlphanumeric = "true";
		
		char c;
		for (int i = 0; i < input.length(); i++)
		{
			c = input.charAt(i);
			if ((c < '0' || c > 'Z') && (c < 'a' || c > 'z'))
			{
				isAlphanumeric = "false";
				break;
			}
		}
		
		pipelineCursor.insertAfter("isAlphanumeric", isAlphanumeric);
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void isDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isDate)>> ---
		// @sigtype java 3.5
		// [i] field:0:required input
		// [i] field:0:required dateFormat
		// [o] field:0:required isDate

	IDataCursor pipelineCursor = pipeline.getCursor();
	pipelineCursor.first( "input" );
	String input = (String)pipelineCursor.getValue();
	pipelineCursor.first( "dateFormat" );
	String dateFormat = (String)pipelineCursor.getValue();

	String isDate = "true";

	try
	{
		// Format the current time.
		SimpleDateFormat sdf;
		if (dateFormat == null || dateFormat.equals(""))
		{
			sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		}
		else
		{
			sdf = new SimpleDateFormat (dateFormat);
		}		
		sdf.setLenient(false);

		Date parsedDate = sdf.parse(input);
	}
	catch (Exception e)
	{
		isDate = "false";
	}

	pipelineCursor.insertAfter("isDate", isDate);
	pipelineCursor.destroy();

		// --- <<IS-END>> ---

                
	}


    public static final Values isNullOrBlank (Values in)
    {
        Values out = in;
		// --- <<IS-START(isNullOrBlank)>> ---
		// @sigtype java 3.0
		// [i] field:0:required inString
		// [o] field:0:required result
		/** Service receives an input String. If input String has value of null or "", returns "result" of 
		  * "true"; otherwise, returns "result" of "false".
		  *
		  * @author Tom Tan, Professional Services, webMethods, Inc.
		  * @version 1.0
		  */
		
		String inStr = in.getString("inString");
		if( inStr == null )
		{
			out.put( "result", "true" );
			return out;
		}
		
		inStr.trim();
		
		if( inStr.equals("") )
		{
			out.put( "result", "true" );
			return out;
		}
		
		out.put( "result", "false" ); 
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void isNumeric (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isNumeric)>> ---
		// @sigtype java 3.5
		// [i] field:0:required input
		// [o] field:0:required isNumeric
		
		IDataCursor pipelineCursor = pipeline.getCursor();
		pipelineCursor.first( "input" );
		String input = (String)pipelineCursor.getValue();
		
		String isNumeric = "true";
		
		try
		{
			float fNumber = Double.valueOf(input).floatValue();
		}
		catch (Exception e)
		{
			isNumeric = "false";
		}
		finally
		{
			pipelineCursor.insertAfter("isNumeric", isNumeric);
			pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}


    public static final Values multiConcat (Values in)
    {
        Values out = in;
		// --- <<IS-START(multiConcat)>> ---
		// @sigtype java 3.0
		// [i] field:0:required inStr1
		// [i] field:0:required inStr2
		// [i] field:0:required inStr3
		// [i] field:0:required inStr4
		// [i] field:0:required inStr5
		// [i] field:0:required inStr6
		// [i] field:0:required inStr7
		// [i] field:0:required inStr8
		// [i] field:0:required inStr9
		// [i] field:0:required inStr10
		// [o] field:0:required outStr
		/** Service takes in up to ten strings, checks them for null (see Shared tab method checkNull), and
		  * concatenates all of them together. checkNull returns a "" if the String is null, effectively
		  * cancelling out its effect on the concatenation. Returns the concatenated String as "outStr".
		  *
		  * @author Tom Tan, Professional Services, webMethods, Inc.
		  * @version 1.0
		  */
		
		String str1 = checkNull(in.getString("inStr1"));
		String str2 = checkNull(in.getString("inStr2"));
		String str3 = checkNull(in.getString("inStr3"));
		String str4 = checkNull(in.getString("inStr4"));
		String str5 = checkNull(in.getString("inStr5"));
		String str6 = checkNull(in.getString("inStr6"));
		String str7 = checkNull(in.getString("inStr7"));
		String str8 = checkNull(in.getString("inStr8"));
		String str9 = checkNull(in.getString("inStr9"));
		String str10 = checkNull(in.getString("inStr10"));
		
		String outStr = str1 + str2 + str3 + str4 + str5 + str6 + str7 + str8 + str9 + str10;
		
		out.put( "outStr", outStr );
			
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void removeLineSeparators (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeLineSeparators)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inString
		// [o] field:0:required outString
		
			IDataCursor idcPipeline = pipeline.getCursor();
		
			String inString = null;
			if (idcPipeline.first("inString"))
			{
				inString = (String)idcPipeline.getValue();
			}
			else
			{
				// Input string is null.  Do nothing
				return;
			}
		
			StringBuffer tempString = new StringBuffer(inString.length());
			String outString = "";
		
			// Strip string of all "\r\n" occurrences
		    for(int i=0;i<inString.length();i++)
			{
				char c;
				c = inString.charAt(i);
				if (c != '\r' && c != '\n')
					tempString.append(c);
		    }
		
		
			idcPipeline.insertAfter("outString", tempString.toString());
		
		// Clean up IData cursors
			idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void substituteVariables (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(substituteVariables)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inputString
		// [o] field:0:required outputString
		
		// Get input objects from pipeline
			IDataCursor idcPipeline = pipeline.getCursor();
		
			String strInputString = null;
			if (idcPipeline.first("inputString"))
			{
				strInputString = (String)idcPipeline.getValue();
			}
			else
			{
				// Input string is null
				return;
			}
		//	int intInputStringLen = strInputString.length();
			String strOutputString = "";
		
			int intCurrentIndex = 0;
			int intStartIndex = 0;
			int intEndIndex = 0;
		//System.out.println("pipeline = " + pipeline);
			while ((intStartIndex = strInputString.indexOf('%',intCurrentIndex)) != -1)
			{
				// If \%, then ignore...don't perform variable substitution
				if ((intStartIndex != 0) &&
				    (strInputString.charAt(intStartIndex - 1) == '\\'))
				{
					strOutputString = strOutputString + strInputString.substring(intCurrentIndex, intStartIndex - 1) + '%';
					intCurrentIndex = intStartIndex + 1;
					continue;
				}
		
				// Find the ending %
				intEndIndex = strInputString.indexOf('%', intStartIndex + 1);
				if (intEndIndex == -1)
				{
					break;
				}
		
				// Print out everything before the %
				strOutputString = strOutputString + strInputString.substring(intCurrentIndex, intStartIndex);
				// Find the string to substitute
				String strStringToSubstitute = null;
				String strVariableName = strInputString.substring(intStartIndex + 1, intEndIndex);
		//System.out.println("strVariableName = " + strVariableName);
		
		
				if (strVariableName.equals(""))
				{
					// We had occurence of "%%"
					intCurrentIndex = intEndIndex + 1;
					continue;
				}
		
				// Perform variable substitution
				StringTokenizer tokenizedString = new StringTokenizer(strVariableName, "/", false);
				int maxTokens = tokenizedString.countTokens();
				IData currentRecord = null;
				int intTokenIndex = 1;
				while (tokenizedString.hasMoreTokens())
				{
					String strCurrentToken = tokenizedString.nextToken();
					System.out.println("strCurrentToken = " + strCurrentToken);
		
					IDataCursor idc = null;
					if (currentRecord == null)
					{
						// New search - look in pipeline for record/string
						idc = idcPipeline;
		//System.out.println("Searching in pipeline");
					}
					else
					{
		//System.out.println("Searching in record = " + currentRecord);
						idc = currentRecord.getCursor();
					}
		
					if (idc.first(strCurrentToken))
					{
						Object o = idc.getValue();
		//System.out.println("intTokenIndex = " + intTokenIndex);
		//if (o instanceof String)
		//{
		//System.out.println("String!");
		//}
						if ((intTokenIndex == maxTokens) && (o instanceof String))
						{
							// This is the last token.  Look for a string
							// Variable found in pipeline
							strStringToSubstitute = (String)o;
							strOutputString = strOutputString + strStringToSubstitute;
							intCurrentIndex = intEndIndex + 1;
		//System.out.println("String found");
		//System.out.println("new strOutputString = " + strOutputString);
						}
						else if ((intTokenIndex != maxTokens) && (o instanceof IData))
						{
							// Look for a IData (record)
							currentRecord = (IData)o;
		//System.out.println("Record found = " + currentRecord);
						}
						else
						{
							// Type mismatch - variable not found in pipeline
							strOutputString = strOutputString + strInputString.substring(intStartIndex, intEndIndex + 1);
							intCurrentIndex = intEndIndex + 1;
							break;  // Ignore other tokens
						}
					}
					else
					{
						// Variable not found in pipeline
						strOutputString = strOutputString + strInputString.substring(intStartIndex, intEndIndex + 1);
						intCurrentIndex = intEndIndex + 1;
						break;  // Ignore other tokens
					}
		
					intTokenIndex++;
				}
		
			}
		
			strOutputString = strOutputString + strInputString.substring(intCurrentIndex);
		
			idcPipeline.insertAfter("outputString", strOutputString);
			idcPipeline.destroy();
		
		// --- <<IS-END>> ---

                
	}


    public static final Values truncateString (Values in)
    {
        Values out = in;
		// --- <<IS-START(truncateString)>> ---
		// @sigtype java 3.0
		// [i] field:0:required inString
		// [i] field:0:required maxLength
		// [o] field:0:required outString
		
		String strInput = in.getString( "inString" );
		String strMaxLength = in.getString( "maxLength" );
		int intMaxLength = Integer.parseInt( strMaxLength );
		
		if ( strInput.length() < intMaxLength )
		{
			// String length < maxLength.  No need to truncate string
			out.put( "outString", strInput);
		}
		else
		{
			out.put( "outString", strInput.substring(0, intMaxLength) );
		} 
		// --- <<IS-END>> ---
        return out;
                
	}

	// --- <<IS-START-SHARED>> ---
	/** Used by "multiConcat"
	  * 
	  * @author Tom Tan, Professional Services, webMethods, Inc.
	  * @version 1.0
	  */
	private static String checkNull(String inputString)
	{
		if( inputString == null )
			return "";
		else
			return inputString;
	}
	// --- <<IS-END-SHARED>> ---
}

