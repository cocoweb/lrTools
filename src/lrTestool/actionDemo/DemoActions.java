package lrTestool.actionDemo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DemoActions {
	
	
	static Map<String, IlrActions> cacheMap = new ConcurrentHashMap<String, IlrActions>();

    public IlrActions addTrans(IlrActions xITrans){
    	return cacheMap.put(xITrans.ActionName, xITrans);
    	
    }
    
    public boolean runTrans(String transName,String more) throws Throwable{
    	return cacheMap.get(transName).runAction(more);
    }
	
	
	public void init(){
		addTrans(new IlrActions("AName1"){public boolean doAction(String sstr) {
			
				System.out.println(ActionName+"&&&&&&&&&&&&&&&&"+sstr+"================");
				
				OutString = "action1 is OK";
				return true;
				
			}});
		
		//.runAction("Come on baby1!!");
		
		addTrans(new IlrActions("AName2"){
			@Override
			public boolean doAction(String sstr) {
				System.out.println(ActionName+"^^^^^^^^^^^^^^^^^"+sstr+"))))))))))))))))))))))");
				bSucc = false;
				OutString = "action2 is fail";
				
				return false;
				
			}});
		//runAction("Come on baby2!!");
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DemoActions demo= new DemoActions();
		demo.init();
		
		try {
			demo.runTrans("AName1", "come on baby1");
			demo.runTrans("AName2", "come on baby2");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
