/**
 * 
 */
package com.webmethods.caf.improvedmanualcreditcheckinboxresults;

/**
 * @author Administrator
 *
 */

import javax.portlet.PortletPreferences;

public class ImprovedManualCreditCheckInboxResults  extends   com.webmethods.caf.faces.bean.BaseFacesPreferencesBean {

	private com.webmethods.caf.OrderToCash80Tasks orderToCash80Tasks = null;
	/**
	 * List of portlet preference names
	 */
	public static final String[] PREFERENCES_NAMES = new String[] {
		"queryString", "initialPageSize", "initialSortBy", "initialSortAscending", "columnDisplay", "columnWidths", "taskDetailsPage", "taskDetailsPortlet", "currentTaskID"
	};
	/**
	 * Create new preferences bean with list of preference names
	 */
	public ImprovedManualCreditCheckInboxResults() {
		super(PREFERENCES_NAMES);
	}
	
	/**
	 * Call this method in order to persist
	 * Portlet preferences
	 */
	public void storePreferences() throws Exception {
		updatePreferences();
		PortletPreferences preferences = getPreferences();
		preferences.store();
	}

	public com.webmethods.caf.OrderToCash80Tasks getOrderToCash80Tasks()  {
		if (orderToCash80Tasks == null) {
		    orderToCash80Tasks = (com.webmethods.caf.OrderToCash80Tasks)resolveExpression("#{OrderToCash80Tasks}");
		}
		return orderToCash80Tasks;
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getQueryString() throws Exception {
		return (String) getPreferenceValue("queryString", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setQueryString(String queryString) throws Exception {
		setPreferenceValue("queryString", queryString);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public Integer getInitialPageSize() throws Exception {
		return (Integer) getPreferenceValue("initialPageSize", Integer.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setInitialPageSize(Integer initialPageSize) throws Exception {
		setPreferenceValue("initialPageSize", initialPageSize);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getInitialSortBy() throws Exception {
		return (String) getPreferenceValue("initialSortBy", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setInitialSortBy(String initialSortBy) throws Exception {
		setPreferenceValue("initialSortBy", initialSortBy);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public Boolean getInitialSortAscending() throws Exception {
		return (Boolean) getPreferenceValue("initialSortAscending", Boolean.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setInitialSortAscending(Boolean initialSortAscending) throws Exception {
		setPreferenceValue("initialSortAscending", initialSortAscending);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String[] getColumnDisplay() throws Exception {
		return (String[]) getPreferenceValue("columnDisplay", String[].class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setColumnDisplay(String[] columnDisplay) throws Exception {
		setPreferenceValue("columnDisplay", columnDisplay);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getColumnWidths() throws Exception {
		return (String) getPreferenceValue("columnWidths", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setColumnWidths(String columnWidths) throws Exception {
		setPreferenceValue("columnWidths", columnWidths);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getTaskDetailsPage() throws Exception {
		return (String) getPreferenceValue("taskDetailsPage", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setTaskDetailsPage(String taskDetailsPage) throws Exception {
		setPreferenceValue("taskDetailsPage", taskDetailsPage);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getTaskDetailsPortlet() throws Exception {
		return (String) getPreferenceValue("taskDetailsPortlet", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setTaskDetailsPortlet(String taskDetailsPortlet) throws Exception {
		setPreferenceValue("taskDetailsPortlet", taskDetailsPortlet);
	}

	/**
	 * The algorithm for this 'smart' preference getter is:
	 * 1) Check the Request Map (skip this step if it isn't a 'smart' preference)
	 * 2) Check the Member variable
	 * 3) Fall back to the PortletPreferences
	 */
	public String getCurrentTaskID() throws Exception {
		return (String) getPreferenceValue("currentTaskID", String.class);
	}

	/**
	 * Invoke {@link #storePreferences} to persist these changes
	 */
	public void setCurrentTaskID(String currentTaskID) throws Exception {
		setPreferenceValue("currentTaskID", currentTaskID);
	}
}