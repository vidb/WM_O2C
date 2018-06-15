/**
 * 
 */
package com.webmethods.caf.improvedmanualcreditcheckview;

/**
 * @author Administrator
 *
 */

import com.webmethods.caf.faces.data.dir.PrincipalModel;
import com.webmethods.caf.faces.data.dir.PrincipalModelList;
import com.webmethods.caf.faces.data.task.impl.TaskContentProvider;

import java.util.ArrayList;
import java.util.List;

public class ImprovedManualCreditCheckViewDefaultviewView extends com.webmethods.caf.faces.bean.task.BaseTaskDetailsPageBean {

 
	/**
	 * Determines if a de-serialized file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. See Sun docs
	 * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization/spec/version.html> 
	 * details. </a>
	 */
	private static final long serialVersionUID = 1L;
	private com.webmethods.caf.faces.data.task.TaskDisplayProvider taskDisplayProvider = null;
	private static final String[][] TASKDISPLAYPROVIDER_PROPERTY_BINDINGS = new String[][] {
		{"#{TaskDisplayProvider.taskID}", "#{activePreferencesBean.taskID}"},
	};

	public com.webmethods.caf.faces.data.task.TaskDisplayProvider getTaskDisplayProvider()  {
		if (taskDisplayProvider == null) {
		    taskDisplayProvider = (com.webmethods.caf.faces.data.task.TaskDisplayProvider)resolveExpression("#{TaskDisplayProvider}");
		}
	    resolveDataBinding(TASKDISPLAYPROVIDER_PROPERTY_BINDINGS, taskDisplayProvider, "taskDisplayProvider", false, false);
		return taskDisplayProvider;
	}

	/**
	 * Cancel button action handler
	 */
	public String cancelView() {
		try {
			// just redirect to return (finish) url
			String url = getImprovedManualCreditCheckView().getFinishUrl();
			if (url != null && url.length() > 0) {
				getFacesContext().getExternalContext().redirect(url);
			}
			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}
	
	/**
	 * Complete button action handler
	 */
	public String completeTask() {
		try {
			if( !getImprovedManualCreditCheck().isUpdateable() ){
				String errMsg = "You must accept a task before updating it";
				error(errMsg);
				return OUTCOME_ERROR; 
			}

			// do the work
			getImprovedManualCreditCheck().completeTask(); 
		
			// then redirect to finish url
			String url = getImprovedManualCreditCheckView().getFinishUrl(); 
			if (url != null && url.length() > 0) {
				getFacesContext().getExternalContext().redirect(url);
			}

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}	

	/**
	 * Submit button action handler
	 */
	public String submitTask() {
		try {
			if( !getImprovedManualCreditCheck().isUpdateable() ){
				String errMsg = "You must accept a task before updating it";
				error(errMsg);
				return OUTCOME_ERROR; 
			}


			// do the work
			getImprovedManualCreditCheck().applyChanges();

			return OUTCOME_OK;
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR; 
		}
	}

	private PrincipalModelList selectedPrincipalList;

	public PrincipalModelList getPrincipalList() {
		return selectedPrincipalList;
	}
	
	public void setPrincipalList(PrincipalModelList value) {
		this.selectedPrincipalList = value;
	}
	
	/**
	 * Action Event Handler for the control with id='dialogPrincipalAssignTo'
	 */
	public String assignToPrincipals() {
		try {

			// get the current assigned principals for this task
			TaskContentProvider tp = getImprovedManualCreditCheck();

			List<String> currentPrincipalList = new ArrayList<String>();
			String[] currentPrincipalIDs = tp.getTaskInfo().getAssignedToList();
			if (currentPrincipalIDs != null && currentPrincipalIDs.length > 0) {
				for (int ix = 0; ix < currentPrincipalIDs.length; ix++) {
					String principalID = currentPrincipalIDs[ix];
					currentPrincipalList.add( principalID );
				}
			}
			
			// get the selected principals from the picker
			if (selectedPrincipalList != null && selectedPrincipalList.size() > 0) {
				// loop and add the selected principals to the existing list
				for (int ix = 0; ix < selectedPrincipalList.size(); ix++) {
					PrincipalModel principalModel = (PrincipalModel) selectedPrincipalList.get(ix);
					String principalID = principalModel.getPrincipalID(); 
					if( !currentPrincipalList.contains( principalID)) {
						currentPrincipalList.add( principalID );
					}
				}
			}
				
			currentPrincipalIDs = currentPrincipalList.toArray( currentPrincipalIDs);
			tp.getTaskInfo().setAssignedToList(currentPrincipalIDs);
			tp.applyChangesNoAccept();
				
			// then redirect to finish url
			//String url = getRonTask36TaskView().getFinishUrl(); 
			//if (url != null && url.length() > 0) {
			//	getFacesContext().getExternalContext().redirect(url);
			//}				
		} catch (Exception e) {
			error(e);
			log(e);
			return OUTCOME_ERROR;
		}		
		return OUTCOME_OK;
	}
	
	private static final String[][] INITIALIZE_PROPERTY_BINDINGS = new String[][] {
		{"#{ImprovedManualCreditCheck.reset}", null}
	};
	private com.webmethods.caf.improvedmanualcreditcheckview.ImprovedManualCreditCheckView improvedManualCreditCheckView = null;
	private com.webmethods.caf.taskclient.ImprovedManualCreditCheck improvedManualCreditCheck = null;
	private static final String[][] IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS = new String[][] {
		{"#{ImprovedManualCreditCheck.taskID}", "#{ImprovedManualCreditCheckView.taskID}"},
		{"#{ImprovedManualCreditCheck.autoAccept}", "true"},
		{"#{ImprovedManualCreditCheck.adhocRouting}", "true"},
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
	
	@Override
	protected void beforeRenderResponse() {
		super.beforeRenderResponse();
	}

	public com.webmethods.caf.improvedmanualcreditcheckview.ImprovedManualCreditCheckView getImprovedManualCreditCheckView()  {
		if (improvedManualCreditCheckView == null) {
		    improvedManualCreditCheckView = (com.webmethods.caf.improvedmanualcreditcheckview.ImprovedManualCreditCheckView)resolveExpression("#{ImprovedManualCreditCheckView}");
		}
		return improvedManualCreditCheckView;
	}

	public com.webmethods.caf.taskclient.ImprovedManualCreditCheck getImprovedManualCreditCheck()  {
		if (improvedManualCreditCheck == null) {
		    improvedManualCreditCheck = (com.webmethods.caf.taskclient.ImprovedManualCreditCheck)resolveExpression("#{ImprovedManualCreditCheck}");
		}
	
	    resolveDataBinding(IMPROVEDMANUALCREDITCHECK_PROPERTY_BINDINGS, improvedManualCreditCheck, "improvedManualCreditCheck", false, false);
		return improvedManualCreditCheck;
	}
	
	
}