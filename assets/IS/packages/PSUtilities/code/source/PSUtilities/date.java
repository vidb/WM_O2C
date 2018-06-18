package PSUtilities;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2004-02-29 18:22:17 PST
// -----( ON-HOST: 192.168.0.3

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.text.*;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class date

{
	// ---( internal utility methods )---

	final static date _instance = new date();

	static date _newInstance() { return new date(); }

	static date _cast(Object o) { return (date)o; }

	// ---( server methods )---




	public static final void calculateDateDifference (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateDateDifference)>> ---
		// @sigtype java 3.5
		// [i] field:0:required startDateTime
		// [i] field:0:required endDateTime
		// [i] field:0:required startDateFormat
		// [i] field:0:required endDateFormat
		// [o] field:0:required dateDifferenceSec
		// [o] field:0:required dateDifferenceMin
		// [o] field:0:required dateDifferenceHr
		// [o] field:0:required dateDifferenceDay
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	startDateTime = "";
		String	endDateTime = "";
		String startDateFormat = "";
		String endDateFormat = "";
		if (pipelineCursor.first("startDateTime"))
		{
			startDateTime = (String) pipelineCursor.getValue();
		}
		if (pipelineCursor.first("endDateTime"))
		{
			endDateTime = (String) pipelineCursor.getValue();
		}
		if (pipelineCursor.first("startDateFormat"))
		{
			startDateFormat = (String) pipelineCursor.getValue();
		}
		if (pipelineCursor.first("endDateFormat"))
		{
			endDateFormat = (String) pipelineCursor.getValue();
		}
		
		if (startDateTime.equals("") || endDateTime.equals(""))
			throw new ServiceException("Dates cannot be null");
		if (startDateFormat.equals("") || endDateFormat.equals(""))
			throw new ServiceException("Date formats cannot be null");
		
		pipelineCursor.destroy();
		
		try {
				SimpleDateFormat sdf = new SimpleDateFormat(startDateFormat);
				Date sdt = sdf.parse(startDateTime);
				SimpleDateFormat edf = new SimpleDateFormat(endDateFormat);
				Date edt = edf.parse(endDateTime);
				long  timediff = edt.getTime() - sdt.getTime();
		
		//		SimpleDateFormat ssdf = new SimpleDateFormat("HH:mm:ss");
		//		Calendar cal = Calendar.getInstance();
		//		cal.clear();
		//		cal.set(Calendar.SECOND, (int) timediff /1000);
		
		//		Date newDateTime = cal.getTime();
		//		String displayTime=null;
		
		//		if (cal.get(Calendar.DAY_OF_YEAR) > 1 )
		//		    displayTime = ssdf.format(newDateTime) + " and " + (cal.get(Calendar.DAY_OF_YEAR)-1) + " Day(s)" ;
		//		else 
		//		    displayTime = ssdf.format(newDateTime);
		
				String displayTimeSec = Long.toString(timediff/1000);
				String displayTimeMin = Long.toString(timediff/60000);
				String displayTimeHr = Long.toString(timediff/3600000);
				String displayTimeDay = Long.toString(timediff/86400000);
		
				// pipeline
				IDataCursor pipelineCursor_1 = pipeline.getCursor();
				pipelineCursor_1.last();
				pipelineCursor_1.insertAfter( "dateDifferenceSec", displayTimeSec);
				pipelineCursor_1.insertAfter( "dateDifferenceMin", displayTimeMin);
				pipelineCursor_1.insertAfter( "dateDifferenceHr", displayTimeHr);
				pipelineCursor_1.insertAfter( "dateDifferenceDay", displayTimeDay);
				pipelineCursor_1.destroy();
			} catch (ParseException e) {
				throw new ServiceException("Error parsing the date time: " + e);
			}
		// --- <<IS-END>> ---

                
	}



	public static final void compareDates (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compareDates)>> ---
		// @sigtype java 3.5
		// [i] field:0:required dateString1
		// [i] field:0:required dateFormat1
		// [i] field:0:required dateString2
		// [i] field:0:required dateFormat2
		// [o] field:0:required result
	IDataCursor idcPipeline = pipeline.getCursor();
	String ds1 = "";
	String ds2 = "";
	String df1 = "";
	String df2 = "";
	if (idcPipeline.first("dateString1"))
		ds1 = (String)idcPipeline.getValue();
	
	if (idcPipeline.first("dateString2"))
		ds2 = (String)idcPipeline.getValue();

	if (idcPipeline.first("dateFormat1"))
		df1 = (String)idcPipeline.getValue();

	if (idcPipeline.first("dateFormat2"))
		df2 = (String)idcPipeline.getValue();

	if (df1.equals("") || df2.equals(""))
		throw new ServiceException("Date formats must be specified");

	if (ds1.equals("") || ds2.equals(""))
		throw new ServiceException("Dates cannot be null");

	SimpleDateFormat sdf1 = new SimpleDateFormat(df1);
	SimpleDateFormat sdf2 = new SimpleDateFormat(df2);
	Date d1 = null;
	Date d2 = null;
	try
	{
		d1 = sdf1.parse(ds1);
		d2 = sdf2.parse(ds2);
	}
	catch (Exception e)
	{
		throw new ServiceException(e.toString());
	}

	boolean isafter = d1.after(d2);
	boolean isbefore = d2.after(d1);
	if (isafter)
	{
		idcPipeline.insertAfter("result", "1");
	}
    else if (isbefore)
	{
		idcPipeline.insertAfter("result", "-1");
	}
	else //dates are equal
	{
		idcPipeline.insertAfter("result", "0");
	}

	idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



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

