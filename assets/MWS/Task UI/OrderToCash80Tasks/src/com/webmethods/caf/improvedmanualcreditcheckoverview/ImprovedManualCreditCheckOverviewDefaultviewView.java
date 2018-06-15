/**
 * 
 */
package com.webmethods.caf.improvedmanualcreditcheckoverview;

/**
 * @author Administrator
 *
 */

import javax.faces.application.FacesMessage;
import com.webmethods.caf.faces.data.task.TaskDisplayProvider;

public class ImprovedManualCreditCheckOverviewDefaultviewView extends com.webmethods.caf.faces.bean.BasePageBean {


	private static final long serialVersionUID = 1L;
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{activePreferencesBean.currentTab}", "TaskData"},
	};

	// binding the Task Display Provider to the current taskID (passed to the Portlet Bean via the URL)
	private TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] { {
			"#{TaskDisplayProvider.taskID}", "#{ImprovedManualCreditCheckOverview.taskID}" }, };
	private com.webmethods.caf.improvedmanualcreditcheckoverview.ImprovedManualCreditCheckOverview improvedManualCreditCheckOverview = null;
	private com.webmethods.caf.taskclient.ImprovedManualCreditCheck improvedManualCreditCheck = null;
	private static final String[][] IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS = new String[][] {
		{"#{ImprovedManualCreditCheck.taskID}", "#{ImprovedManualCreditCheckOverview.taskID}"},
	};

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

	/*
	 * Get the Task Display Provider for the current taskID
	 */
	public TaskDisplayProvider getTaskDisplayProvider() {
		if (taskDisplayProvider == null) {
			taskDisplayProvider = (TaskDisplayProvider) resolveExpression("#{TaskDisplayProvider}");
		}
		resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS,
				taskDisplayProvider, "taskDisplayProvider", false, false);
		return taskDisplayProvider;
	}

	public com.webmethods.caf.improvedmanualcreditcheckoverview.ImprovedManualCreditCheckOverview getImprovedManualCreditCheckOverview()  {
		if (improvedManualCreditCheckOverview == null) {
		    improvedManualCreditCheckOverview = (com.webmethods.caf.improvedmanualcreditcheckoverview.ImprovedManualCreditCheckOverview)resolveExpression("#{ImprovedManualCreditCheckOverview}");
		}
		return improvedManualCreditCheckOverview;
	}

	public com.webmethods.caf.taskclient.ImprovedManualCreditCheck getImprovedManualCreditCheck()  {
		if (improvedManualCreditCheck == null) {
		    improvedManualCreditCheck = (com.webmethods.caf.taskclient.ImprovedManualCreditCheck)resolveExpression("#{ImprovedManualCreditCheck}");
		}
	
	    resolveDataBinding(IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS, improvedManualCreditCheck, "improvedManualCreditCheck", false, false);
		return improvedManualCreditCheck;
	}

}