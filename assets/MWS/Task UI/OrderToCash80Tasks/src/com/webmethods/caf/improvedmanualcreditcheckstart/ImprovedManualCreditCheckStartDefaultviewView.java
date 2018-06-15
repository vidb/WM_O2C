/**
 * 
 */
package com.webmethods.caf.improvedmanualcreditcheckstart;

/**
 * @author Administrator
 *
 */

import javax.faces.application.FacesMessage;

public class ImprovedManualCreditCheckStartDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskStartPageBean {


	

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
	public com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider getTaskPriorityMapProvider()  {
		if (taskPriorityMapProvider == null) {
		    taskPriorityMapProvider = (com.webmethods.caf.faces.data.object.TableSelectItemGroupProvider)resolveExpression("#{TaskPriorityMapProvider}");
		}
	    resolveDataBinding(TASKPRIORITYMAPPROVIDER_PROPERTY_BINDINGS, taskPriorityMapProvider, null, false, true);
		return taskPriorityMapProvider;
	}

    /**
     * Queue new task. 
     * If successful adds INFO message to the faces context.
     * If failed adds error messages to the faces context.
     */
	public String queueNewTask() {
		try {
			// do the work
			getImprovedManualCreditCheck().queueNewTask();

			
			
			

			String msgHeader = "The new task has been successfully started";
			String msgText = "Task ID is ";
			getFacesContext().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, 
				msgHeader, 
				msgText + getImprovedManualCreditCheck().getTaskID()));

			return OUTCOME_OK; 
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}	
	}
	
   /**
	 * Go to the URL specified by the 'returnURL' preference
	 */
	public String doReturnURL() {
		try {
			// just redirect to return (finish) url
			String returnURL = (String)getImprovedManualCreditCheckStart().getReturnUrl();
			if (returnURL != null && returnURL.length() > 0) {
				getFacesContext().getExternalContext().redirect(returnURL);
			}
			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
	};
	private com.webmethods.caf.improvedmanualcreditcheckstart.ImprovedManualCreditCheckStart improvedManualCreditCheckStart = null;
	private com.webmethods.caf.taskclient.ImprovedManualCreditCheck improvedManualCreditCheck = null;
	private static final String[][] IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS = new String[][] {
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

	public com.webmethods.caf.improvedmanualcreditcheckstart.ImprovedManualCreditCheckStart getImprovedManualCreditCheckStart()  {
		if (improvedManualCreditCheckStart == null) {
		    improvedManualCreditCheckStart = (com.webmethods.caf.improvedmanualcreditcheckstart.ImprovedManualCreditCheckStart)resolveExpression("#{ImprovedManualCreditCheckStart}");
		}
		return improvedManualCreditCheckStart;
	}

	public com.webmethods.caf.taskclient.ImprovedManualCreditCheck getImprovedManualCreditCheck()  {
		if (improvedManualCreditCheck == null) {
		    improvedManualCreditCheck = (com.webmethods.caf.taskclient.ImprovedManualCreditCheck)resolveExpression("#{ImprovedManualCreditCheck}");
		}
	
	    resolveDataBinding(IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS, improvedManualCreditCheck, "improvedManualCreditCheck", false, false);
		return improvedManualCreditCheck;
	}

}