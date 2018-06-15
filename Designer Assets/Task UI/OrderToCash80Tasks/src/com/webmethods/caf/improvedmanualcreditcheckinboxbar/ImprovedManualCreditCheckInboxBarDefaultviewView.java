/**
 * 
 */
package com.webmethods.caf.improvedmanualcreditcheckinboxbar;

/**
 * @author Administrator
 *
 */

import com.webmethods.caf.faces.data.object.DateRange;
import com.webmethods.caf.faces.search.saved.DboSavedSearchProvider;
import com.webmethods.caf.faces.search.saved.ISavedSearchProvider;
import com.webmethods.caf.faces.search.query.ISearchQuery;

public class ImprovedManualCreditCheckInboxBarDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskInboxBarPageBean {
 
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;

	private com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider taskPriorityMapProvider = null;

	private static final String[][] TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskPriorityMapProvider.labelFieldName}", "label"},
		{"#{TaskPriorityMapProvider.array}", "#{TaskDisplayProvider.taskPriorityItems}"},
		{"#{TaskPriorityMapProvider.valueFieldName}", "value"},
	};
	
	public static String[][] SEARCH_QUERY_PROPERTIES = new String[][] {};
	
	public com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider getTaskPriorityMapProvider()  {
		if (taskPriorityMapProvider == null) {
		    taskPriorityMapProvider = (com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider)resolveExpression("#{TaskPriorityMapProvider}");
		}
	
	    resolveDataBinding(TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS, taskPriorityMapProvider, null, false, true);
		return taskPriorityMapProvider;
	}

	/* (non-Javadoc)
	 * @see com.webmethods.caf.faces.bean.search.BaseSearchBarPageBean#getSavedSearchProvider()
	 */
	public ISavedSearchProvider getSavedSearchProvider() {
		if (fSavedSearchProvider == null) {
			fSavedSearchProvider = new DboSavedSearchProvider(getSearchQueryFactory(), "Improved Manual Credit Check Saved Searches" );
		}
		return fSavedSearchProvider;
	}
	
	@SuppressWarnings("unchecked")
	public DateRange getCreatedRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "createdDateRange" );
	}

	@SuppressWarnings("unchecked")
	public void setCreatedRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "createdDateRange", newVal );
	}

	@SuppressWarnings("unchecked")
	public DateRange getModifiedRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "modifiedDateRange" );
	}
	
	@SuppressWarnings("unchecked")
	public void setModifiedRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "modifiedDateRange", newVal );
	}
	
	@SuppressWarnings("unchecked")
	public DateRange getExpiredRange() {
		return (DateRange)getActiveSearchQuery().getRefineFields().get( "expirationDateRange" );
	}
	
	@SuppressWarnings("unchecked")
	public void setExpiredRange( DateRange newVal ) {
		getActiveSearchQuery().getRefineFields().put( "expirationDateRange", newVal );
	}
	
	@SuppressWarnings("unchecked")
	public Boolean getAcceptedByCurrentUser() {
		Boolean retVal = null;
		Object o = getActiveSearchQuery().getRefineFields().get( "acceptedByCurrentUser" ); //$NON-NLS-1$
		if( o != null && o instanceof Boolean ) {
			retVal = ((Boolean)o);
		}
		return retVal;
	}
	
	@SuppressWarnings("unchecked")
	public void setAcceptedByCurrentUser( Boolean newVal ) {
		// Only store a true value, false value is stored as null
		// (to prevent only displaying search results where acceptedByCurrent is false)
		if( newVal != null && newVal.booleanValue() ) {
			// 
			getActiveSearchQuery().getRefineFields().put( "acceptedByCurrentUser", newVal ); //$NON-NLS-1$
		}
		else {
			getActiveSearchQuery().getRefineFields().remove( "acceptedByCurrentUser" ); //$NON-NLS-1$
		}
	}
	
	protected boolean isDateRangeSet( DateRange range ) {
		if( range == null ) {
			return false;
		}
		if( range.getRelativeRange() == null ) {
			return false;
		}
		// range is non empty if it isn't set to "All"
		boolean setToAll = range.getRelativeRange() == DateRange.FIXED && range.getFixedRange() == 0L;
		return !setToAll;
	}
	
	public String doSearch() {
		return getSearchBarControl().getControlBean().constructSearchQueryString();
	}

	public String getPortletResource( String key ) {
	    return (String)getActivePreferencesBean().getPortletResourcesProvider().getValue( key );
	}
	
	@SuppressWarnings("unchecked")
	public void loadSearchQueryDefaults(ISearchQuery searchQuery) {
		super.loadSearchQueryDefaults(searchQuery);
		// assign default search query properties
		for (int i = 0; i < SEARCH_QUERY_PROPERTIES.length; i++) {
			String[] properties = SEARCH_QUERY_PROPERTIES[i];
			searchQuery.getRefineFields().put(properties[0], properties[1]);
		}
	}	
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};

	private com.webmethods.caf.improvedmanualcreditcheckinboxbar.ImprovedManualCreditCheckInboxBar improvedManualCreditCheckInboxBar = null;

	/**
	 * Initialize page
	 */
	public String initialize() {
		try {
		    resolveDataBinding(INITIALIZE_PROPERTY_BINDINGS, null, "initialize", true, false);

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}	
	}

	public com.webmethods.caf.improvedmanualcreditcheckinboxbar.ImprovedManualCreditCheckInboxBar getImprovedManualCreditCheckInboxBar()  {
		if (improvedManualCreditCheckInboxBar == null) {
		    improvedManualCreditCheckInboxBar = (com.webmethods.caf.improvedmanualcreditcheckinboxbar.ImprovedManualCreditCheckInboxBar)resolveExpression("#{ImprovedManualCreditCheckInboxBar}");
		}
		return improvedManualCreditCheckInboxBar;
	}
	
}