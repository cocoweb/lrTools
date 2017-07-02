/*
 * LoadRunner Java script. (Build: 3020)
 * 
 * Script Description: 
 *                     
 */

import lrapi.*;

public class Actions
{
    String ENDFORM      =  "ENDFORM";
    String LAST         =  "LAST";
    String ENDITEM      =  "ENDITEM";
    String ITEMDATA     =  "ITEMDATA";
    String STARTHIDDENS =  "STARTHIDDENS";
    String ENDHIDDENS   =  "ENDHIDDENS";
    String CONNECT	    =  "CONNECT";
    String RECEIVE      =  "RECEIVE";
    String RESOLVE	    =  "RESOLVE";
    String REQUEST      =  "REQUEST";
    String RESPONSE	    =  "RESPONSE";
    String EXTRARES     =  "EXTRARES";
    int _webresult; 

        public int init() throws Throwable {
            web.set_max_html_param_len("102400"); 

	    //lr.save_string("http://app.gd-n-tax.gov.cn:9001","p_url");
		return 0;
	}//end of init



	public int end() throws Throwable {
		return 0;
	}//end of end
        public int action() throws Throwable {

      	    lr.save_string(lr.eval_string("440402999999{lsh}"),"para_djxh");
	    lr.save_string(lr.eval_string("44040299{lsh}"),"para_nsrsbh");
	    lr.save_string("123456","para_password");

 
	try{

    
    

	     ActionsCommon.getInstance().run_Login();
	    



  	    _webresult = lrapi.web.url("pagehomegdgs.do", 
		    "URL={p_url}/etax/admin/pagehomegdgs.do", new String[]{ 
		    "Resource=0", 
		    "RecContentType=text/html", 
		    "Referer=", 
		    "Snapshot=t3.inf", 
		    "Mode=HTML", 
		    LAST});

	    ActionsCommon.getInstance().run_Sbzs();
	    //ActionsCommon.getInstance().run_Fprz();
	    //ActionsCommon.getInstance().run_Jk();


    



	}catch(Exception e){
		e.printStackTrace();
		return -1;
	}
	return 0;
    }//end of action

}
