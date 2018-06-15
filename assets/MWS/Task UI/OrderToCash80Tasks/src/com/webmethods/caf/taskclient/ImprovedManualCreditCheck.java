package com.webmethods.caf.taskclient;


import com.webmethods.caf.faces.data.task.ITaskData;
import com.webmethods.caf.faces.data.ContentProviderException;

/**
 * Task Client bean for 'Improved Manual Credit Check' task.
 */
public class ImprovedManualCreditCheck extends com.webmethods.caf.faces.data.task.impl.TaskContentProviderExtended {

	private static final long serialVersionUID = 1014668741496443904L;
	
	/**
	 * Globally unique task type id
	 */
	private static final String TASK_TYPE_ID = "2AE87CCA-E29C-1AA0-6FE1-DD243B5B031F";

	/**
	 * Default constructor
	 */
	public ImprovedManualCreditCheck() {
		super();
		setTaskTypeID(TASK_TYPE_ID);
	}
	
	/**
	 * Task Data Object
	 */
	public static class TaskData extends com.webmethods.caf.faces.data.task.impl.TaskData {

		private static final long serialVersionUID = 7791504781881984000L;
		
		public static String[][] FIELD_NAMES = new String[][] {{"validatedOrderCreditChecked", "ValidatedOrderCreditChecked"},
		};

		private com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder validatedOrderCreditChecked = null;

		public static final String[] INPUTS = new String[] {"validatedOrderCreditChecked", };

		public static final String[] OUTPUTS = new String[] {"validatedOrderCreditChecked", };

		public TaskData() {
		}

		public com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder getValidatedOrderCreditChecked()  {
			if (validatedOrderCreditChecked == null) {
				validatedOrderCreditChecked = new com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder();
			}
			return validatedOrderCreditChecked;
		}

		public void setValidatedOrderCreditChecked(com.webmethods.caf.is.document.ScOrderToCash_Documents_CreditCheckedOrder validatedOrderCreditChecked)  {
			this.validatedOrderCreditChecked = validatedOrderCreditChecked;
		}
		
	}
	
	/**
	 * Return current task data object
	 * @return current task data
	 */
	public TaskData getTaskData() {
		return (TaskData)getValue(PROPERTY_KEY_TASKDATA);
	}

	/**
	 * Creates new task data object
	 * @return newly created task data object
	 */	
	protected ITaskData newTaskData() throws ContentProviderException {
		return new TaskData();
	}

}