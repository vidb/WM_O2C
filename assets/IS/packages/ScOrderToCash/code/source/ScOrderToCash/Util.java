package ScOrderToCash;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-10-04 09:58:39 PDT
// -----( ON-HOST: winxpsql

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.*;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class Util

{
	// ---( internal utility methods )---

	final static Util _instance = new Util();

	static Util _newInstance() { return new Util(); }

	static Util _cast(Object o) { return (Util)o; }

	// ---( server methods )---




	public static final void incrementDate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(incrementDate)>> ---
		// @sigtype java 3.5
		// [i] field:0:required startingDate
		// [i] field:0:required startingDateFormat
		// [i] field:0:required endingDateFormat
		// [i] field:0:optional addYears
		// [i] field:0:optional addMonths
		// [i] field:0:optional addDays
		// [i] field:0:optional addHours
		// [i] field:0:optional addMinutes
		// [i] field:0:optional addSeconds
		// [o] field:0:required endingDate

	IDataCursor idcPipeline = pipeline.getCursor();

	String strStartingDate = null;
	if (idcPipeline.first("startingDate"))
	{
		strStartingDate = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("startingDate must be supplied!");
	}
	String strStartingDateFormat = null;
	if (idcPipeline.first("startingDateFormat"))
	{
		strStartingDateFormat = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("startingDateFormat must be supplied!");
	}
	String strEndingDateFormat = null;
	if (idcPipeline.first("endingDateFormat"))
	{
		strEndingDateFormat = (String)idcPipeline.getValue();
	}
	else
	{
		throw new ServiceException("endingDateFormat must be supplied!");
	}

	String strAddYears = null;
	String strAddMonths = null;
	String strAddDays = null;
	String strAddHours = null;
	String strAddMinutes = null;
	String strAddSeconds = null;

	if (idcPipeline.first("addYears"))
	{
		strAddYears = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("addMonths"))
	{
		strAddMonths = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("addDays"))
	{
		strAddDays = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("addHours"))
	{
		strAddHours = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("addMinutes"))
	{
		strAddMinutes = (String)idcPipeline.getValue();
	}
	if (idcPipeline.first("addSeconds"))
	{
		strAddSeconds = (String)idcPipeline.getValue();
	}

	SimpleDateFormat ssdf = new SimpleDateFormat(strStartingDateFormat);

	Date startingDate = null;
	try
	{
		startingDate = ssdf.parse(strStartingDate);
	}
	catch (Exception e)
	{
		throw new ServiceException(e.toString());
	}

	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(startingDate);

	if (strAddYears != null)
	{
		gc.add(Calendar.YEAR, Integer.parseInt(strAddYears));
	}
	if (strAddMonths != null)
	{
		gc.add(Calendar.MONTH, Integer.parseInt(strAddMonths));
	}
	if (strAddDays != null)
	{
		gc.add(Calendar.DAY_OF_MONTH, Integer.parseInt(strAddDays));
	}
	if (strAddHours != null)
	{
		gc.add(Calendar.HOUR_OF_DAY, Integer.parseInt(strAddHours));
	}
	if (strAddMinutes != null)
	{
		gc.add(Calendar.MINUTE, Integer.parseInt(strAddMinutes));
	}
	if (strAddSeconds != null)
	{
		gc.add(Calendar.SECOND, Integer.parseInt(strAddSeconds));
	}

	Date endingDate = gc.getTime();
	SimpleDateFormat esdf = new SimpleDateFormat(strEndingDateFormat);
	String strEndingDate = esdf.format(endingDate);

	idcPipeline.insertAfter("endingDate", strEndingDate);
	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}
}

