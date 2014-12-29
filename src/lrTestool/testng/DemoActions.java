package lrTestool.testng;

public class DemoActions {
	
	public void showSome(IlrActions xDo){
		System.out.println("==Starting:"+xDo.getActionName());
		
		xDo.doAction("Come on baby!!");
		
		if(xDo.bSucc){
			System.out.println("==Success:"+xDo.getActionName()+xDo.OutString);
			
		}else{
			System.out.println("==Failed:"+xDo.getActionName()+xDo.OutString);
			
		}
		
		System.out.println("==Stoped:"+xDo.getActionName());
	}
	
	public void runAction1(){
		showSome(new IlrActions("Action1"){public void doAction(String sstr) {
			
				System.out.println(getActionName()+"&&&&&&&&&&&&&&&&"+sstr+"================");
				bSucc = true;
				OutString = "action1 is OK";
				
			}});
		
	}
	public void runAction2(){
		showSome(new IlrActions("Action2"){
			@Override
			public void doAction(String sstr) {
				System.out.println(getActionName()+"^^^^^^^^^^^^^^^^^"+sstr+"))))))))))))))))))))))");
				bSucc = false;
				OutString = "action2 is fail";
				
			}
			
		});
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DemoActions demo= new DemoActions();
		demo.runAction1();
		demo.runAction2();
		
		
		
	}

}
