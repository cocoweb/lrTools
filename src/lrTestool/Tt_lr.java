package lrTestool;
/*
 * LoadRunner Java script. (Build: _build_number_)
 * 
 * Script Description: 
 *                     
 */

import gt3.esb.ejb.adapter.client.IEsbXmlMessageReceiver;

import com.foresee.etax.ejbclient.*;
import com.foresee.test.loadrunner.lrapi4j.lr;

import lrTestool.lrTools;
import utils.LRUtils;


public class Tt_lr
{	
    //报文名字
    private static String tran_key = "SWZJ.HXZG.SB.XFSSBSQJKJHQQCS";
    private static String tran_name ="";
    private static IEsbXmlMessageReceiver esbClient = null;
    String sXML = "";

	public int init() throws Throwable {
	    //EjbClient ejb = new EjbClient();
            //esbClient = ejb.getCurrentEsb(); 

	    tran_key= lrTools.getDefault();
	    tran_name =lrTools.getTranNameByKey(tran_key);

	    sXML = lrTools.loadXmlByKey(tran_key);
	    
	    lr.save_string("123456","p_qshm");
	    lr.save_string("123499","p_zzhm");
            
	    lr.save_string("123456","iters");
            

             return 0; 
	}//end of init


	public int action() throws Throwable {     //+ (String.valueOf(System.currentTimeMillis())+"0000000000").substring(0,9),
		System.out.println(sXML);
		
		System.out.println(LRUtils.locateSupportClassesDir().getPath());
		// LRUtils.loadLRLibrary("lrapi");
		
		lr.message("aaaaaaaaaaaaaa");
		
	    lr.save_string("C000BNFZC15001201411159{tran_lsh}",
			   "p_tran_seq");

	    lr.save_string("000000000","p_nsrsbh");
	    lr.save_string("111111111","p_skssqq");
	    lr.save_string("222222222","p_skssqz");


	    int offset = 0;//(Integer.parseInt(lr.eval_string("{iters}"))-1)/4;

	    long timer = lr.start_timer(); 

	    if (Integer.parseInt(lr.eval_string("{p_qshm}"))+offset >Integer.parseInt(lr.eval_string("{p_zzhm}"))) {

		lr.message(String.format("起始号码大于终止号码了:  %d:%03d:%03d",
					 offset, 
					 Integer.parseInt(lr.eval_string("{p_qshm}"))+offset ,
                                         Integer.parseInt(lr.eval_string("{p_zzhm}"))));
		return 0;
	    }
	    lr.start_transaction(tran_name);
	    lr.message(String.format("%d:%03d:%03d",offset, 
				     Integer.parseInt(lr.eval_string("{p_qshm}"))+offset,
				     Integer.parseInt(lr.eval_string("{p_qshm}"))+offset));



	    //lr.think_time(1);

            //lr.output_message(lr.eval_string(sXML));

//lr.message(lr.eval_string(xytools.getXML()));
           // String retXml = esbClient.receiveMessageXML(lr.eval_string(sXML));

	    //System.out.println(retXml);

	    String stime = Long.toString(lr.end_timer(timer));               
	    //lr.message("time:"+stime+lr.eval_string(":{p_djxh} "));

	    //lr.output_message("{p_skssqq}");
	    //lr.output_message("{p_skssqz}");
    
	    lr.end_transaction(tran_name, lr.AUTO);

		return 0;
	}//end of action


	public int end() throws Throwable {
		return 0;
	}//end of end
	
	public int runAction() throws Throwable {
		init();
		action();
		end();

		return 0;
	}//end of end
	
	
	public static void main(String[] args) 
	{
		Tt_lr tt= new Tt_lr();
		try {
			
			tt.runAction();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
