package com.webmethods.caf.improvedmanualcreditcheckinboxresults;


/**
 * Task Search bean for 'Improved Manual Credit Check' task.
 */
public class ImprovedManualCreditCheckSearchProvider extends com.webmethods.caf.faces.data.task.impl.TaskInboxSearchContentProvider {

	private static final long serialVersionUID = 7379686265698317312L;
	private static final String TASK_TYPE_ID = "2AE87CCA-E29C-1AA0-6FE1-DD243B5B031F";

	public ImprovedManualCreditCheckSearchProvider() {
		super(); // task type id to search
		m_searchQuery = new CustomInboxSearchQuery(); 
	}

	/**
	 * Typed ITaskData getter
	 * @return current task data
	 */
	public com.webmethods.caf.taskclient.ImprovedManualCreditCheck.TaskData getTaskData() {
		return (com.webmethods.caf.taskclient.ImprovedManualCreditCheck.TaskData)getValue(PROPERTY_TASKDATA);
	}

	/**
	 * Typed custom search query
	 */
	public CustomInboxSearchQuery getSearchQuery() {  
		return (CustomInboxSearchQuery)m_searchQuery;
	}

	/**
	 * Custom inbox search query that can be extended
	 **/
	public class CustomInboxSearchQuery extends com.webmethods.caf.faces.data.task.impl.TaskInboxSearchContentProvider.InboxSearchQuery {
		private static final long serialVersionUID = 8383846619919720448L;
		
		public CustomInboxSearchQuery() {
			super();
		}

	}				

}
