package lrTestool.actionDemo;

public class DemoActions {
	
	
	public void runAction1(){
		(new IlrActions("Action1"){public boolean doAction(String sstr) {
			
				System.out.println(ActionName+"&&&&&&&&&&&&&&&&"+sstr+"================");
				
				OutString = "action1 is OK";
				return true;
				
			}}).runAction("Come on baby!!");
		
	}
	public void runAction2(){
		(new IlrActions("Action2"){
			@Override
			public boolean doAction(String sstr) {
				System.out.println(ActionName+"^^^^^^^^^^^^^^^^^"+sstr+"))))))))))))))))))))))");
				bSucc = false;
				OutString = "action2 is fail";
				
				return false;
				
			}
			
		}).runAction("Come on baby!!");
		
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
