package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2003-10-12 11:08:25 PDT
// -----( ON-HOST: esuc610

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.regex.*;
// --- <<IS-END-IMPORTS>> ---

public final class regex

{
	// ---( internal utility methods )---

	final static regex _instance = new regex();

	static regex _newInstance() { return new regex(); }

	static regex _cast(Object o) { return (regex)o; }

	// ---( server methods )---




	public static final void find (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(find)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		pipelineCursor.destroy();
		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(input);
		
		int sets = 0;
		
		while (matcher.find())
		{
			sets++;
		}
		
		matcher.reset();
		
		int i = 0;
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		// resultSet
		IData	resultSet = IDataFactory.create();
		IDataCursor resultSetCursor = resultSet.getCursor();
		
		
		// resultSet.result
		IData[]	result = new IData[sets];
		
		while (matcher.find())
		{
			result[i] = IDataFactory.create();
			IDataCursor resultCursor = result[i].getCursor();
			IDataUtil.put( resultCursor, "text", matcher.group() );
			IDataUtil.put( resultCursor, "start", Integer.toString(matcher.start()));
			IDataUtil.put( resultCursor, "end", Integer.toString(matcher.end()));
			resultCursor.destroy();
			IDataUtil.put( resultSetCursor, "result", result );
			resultSetCursor.destroy();
		
			i++;
		}
		
		IDataUtil.put( pipelineCursor_1, "resultSet", resultSet );
		pipelineCursor.destroy();
		
		// --- <<IS-END>> ---

                
	}



	public static final void lookingAt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(lookingAt)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [o] field:0:required match {"true","false"}
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		pipelineCursor.destroy();
		
		String match = "false";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.lookingAt())
		{
			match = "true";
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "match", match );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void matches (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(matches)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [o] field:0:required match
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		pipelineCursor.destroy();
		
		String match = "false";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.matches())
		{
			match = "true";
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "match", match );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void replaceAll (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(replaceAll)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [i] field:0:required replacement
		// [o] field:0:required output
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		String replacement = IDataUtil.getString( pipelineCursor, "replacement" );
		pipelineCursor.destroy();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		String output = matcher.replaceAll(replacement);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "output", output );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void replaceFirst (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(replaceFirst)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [i] field:0:required replacement
		// [o] field:0:required output
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		String replacement = IDataUtil.getString( pipelineCursor, "replacement" );
		pipelineCursor.destroy();
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		String output= matcher.replaceFirst(replacement);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "output", output );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void split (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(split)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [o] field:1:required items
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		pipelineCursor.destroy();
		
		Pattern pattern = Pattern.compile(regex);
		
		String items[] = pattern.split(input);
		for (int i=0; i < items.length; i++)
		{
			System.out.println(items[i]);
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "items", items );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void splitLimit (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(splitLimit)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required regex
		// [i] field:0:required input
		// [i] field:0:required limit
		// [o] field:1:required items
		// Toni Immordino
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String regex = IDataUtil.getString( pipelineCursor, "regex" );
		String input = IDataUtil.getString( pipelineCursor, "input" );
		String limit = IDataUtil.getString( pipelineCursor, "limit" );
		pipelineCursor.destroy();
		
		Pattern pattern = Pattern.compile(regex);
		
		String items[] = pattern.split(input, Integer.parseInt(limit));
		for (int i=0; i < items.length; i++)
		{
			System.out.println(items[i]);
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "items", items );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}
}

