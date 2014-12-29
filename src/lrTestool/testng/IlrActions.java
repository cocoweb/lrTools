/**
 * 
 */
package lrTestool.testng;

/**
 * @author Administrator
 *
 */
public abstract class IlrActions {
	public String actionName;
	public boolean bSucc;
	public String OutString;
	
	
	public IlrActions(String actionName) {
		super();
		this.actionName = actionName;
	}


	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	public void doAction(String sstr){};  
}
