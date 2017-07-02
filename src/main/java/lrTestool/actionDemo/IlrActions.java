/**
 * 
 */
package lrTestool.actionDemo;


/**
 * @author Administrator
 *
 */
public abstract class IlrActions {
	public String ActionName;
	public boolean bSucc;
	public String OutString;

	
	public IlrActions(String actionName) {
		super();
		this.ActionName = actionName;
	}


	public abstract boolean doAction(String sstr) ;  
	
	public boolean runAction(String sstr){
		System.out.println("==Starting:"+ ActionName);
		
		bSucc = doAction(sstr);
		
		if(bSucc){
			System.out.println("==Success:"+ActionName+OutString);
			
		}else{
			System.out.println("==Failed:"+ActionName+OutString);
			
		}
		
		System.out.println("==Stoped:"+ActionName);
		
		return bSucc;
		
		
	}
}
