
import javaHttpJ.*;
import javaHttpJ.parsers.*;
import javaHttpJ.replay.*;

import com.foresee.test.util.*;
//import com.thoughtworks.xstream.*;

import lrapi.*;
import lrTestool.lrTools;
import com.foresee.test.util.FileUtil;
import java.util.*;
import java.io.*;

public class ActionsCommon
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
    private static ActionsCommon commonObj = new ActionsCommon();
    
    public static ActionsCommon getInstance(){
	if (null==commonObj) {
	    commonObj = new ActionsCommon();
	}
	return commonObj;

    }

    public static String getUUID(){
       return UUID.randomUUID().toString().trim();
    }

    public void reportOut(boolean bSuccess,String transName,String retXML,long timer){
	    long ltime = lr.end_timer(timer);
	    String stime = ltime>10000 ? Long.toString(ltime):"==too long=="+Long.toString(ltime)+" ";
	    if(bSuccess){
		lr.message(stime+" "+lr.eval_string(transName+" �ɹ�!:DJXH:[{para_djxh}]:����:")+ retXML  );
		lr.end_transaction(transName, lr.AUTO);
	    }else{
		lr.error_message(stime+" "+lr.eval_string(transName+" ʧ��:DJXH:[{para_djxh}]:����:["+retXML+"]"));
		lr.end_transaction(transName, lr.FAIL);

	    }
    }

    public void run_Sbzs_Page1()  throws Throwable{
	long timer = lr.start_timer();
	lr.start_transaction("�걨����1_��ҳ");


	_webresult = lrapi.web.link("�걨����", 
		"Text=�걨����", new String[]{ 
		"Snapshot=t4.inf", 
		EXTRARES, 
		"Url=/skin/gdgs/Style/yellow/images/wsbs/pic_4400000000.jpg", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_center.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_left.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_right.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_left.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_center.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_right.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/btnbg_page.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_left.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_center.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_right.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/www/style/images-common/waiting/loading.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		LAST});

	web.reg_save_param ("ret_sbzsTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 

	_webresult = lrapi.web.submit_data("dataQuery.do", 
		"Action={p_url}/etax/bizfront/dataQuery.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=sid", "Value=ETax.ZS.sbzsMenu.SbzsMenu", ENDITEM, 
		"Name=requestXml", "Value=<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
		    "<taxML><djxh>{para_djxh}</djxh><nsrsbh>{para_nsrsbh}</nsrsbh></taxML>", ENDITEM, 
		LAST});

	web.reg_save_param ("ret_sbcxTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 
	_webresult = lrapi.web.submit_data("dataQuery.do_2", 
		"Action={p_url}/etax/bizfront/dataQuery.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=sid", "Value=ETax.ZS.sbcxMenu.SbzsMenu", ENDITEM, 
		"Name=requestXml", "Value=<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
		    "<taxML><djxh>{para_djxh}</djxh><nsrsbh>{para_nsrsbh}</nsrsbh></taxML>", ENDITEM, 
		LAST});


	_webresult = lrapi.web.submit_data("rejoinQuery.do", 
		"Action={p_url}/etax/bizfront/rejoinQuery.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=queryXml", ENDITEM, 
		"Name=tid", "Value={ret_sbzsTID}", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.sbzsMenu.SbzsMenu", ENDITEM, 
		LAST});

	web.reg_find("Text=<zsxmDm>10101</zsxmDm>", 
	    new String[]{ 
		"SaveCount=cxCount", 
		web.LAST 
	    }); 
	_webresult = lrapi.web.submit_data("rejoinQuery.do_2", 
		"Action={p_url}/etax/bizfront/rejoinQuery.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=queryXml", ENDITEM, 
		"Name=tid", "Value={ret_sbcxTID}", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.sbcxMenu.SbzsMenu", ENDITEM, 
		EXTRARES, 
		"Url=/skin/gdgs/Style/yellow/images/common/dot02.gif", "Referer={p_url}/etax/admin/sbzs/index.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		LAST});

	reportOut((lr.eval_int("{cxCount}") > 0), "�걨����1_��ҳ","",timer);


    }

    public void run_Sbzs() throws Throwable{
	run_Sbzs_Page1();

	
        long timer = lr.start_timer();
	lr.start_transaction("�걨����");



	_webresult = lrapi.web.url("sbRequest.do", 
		"URL={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", new String[]{ 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_center.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_left.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/btnbg_page.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_center.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_left.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_right.gif", ENDITEM, 
		"Url=/etax/script/common/ajaxUpload/loading.gif", ENDITEM, 
		"Url=/skin/www/style/images-common/waiting/loading.gif", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_right.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/ico.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_left.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_l.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_r.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_right.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_center.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/content_bg.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/err.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/btn_bg.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_lb.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_rb.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_b.gif", ENDITEM, 
		LAST});

	web.reg_save_param ("ret_sbdoTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 

	_webresult = lrapi.web.submit_data("sb.do", 
		"Action={p_url}/etax/gdgs/sb/zzsYbnsr/sb.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=init", ENDITEM, 
		"Name=sid", "Value=ETax.SB.sbInit.GdgsZzsYbnsr", ENDITEM, 
		LAST});

	web.reg_save_param ("retStr",
	    new String []{ 
		"NOTFOUND=warning", 
		"LB=", 
		"RB=" ,
		"Search=BODY",
		LAST} );


	_webresult = lrapi.web.submit_data("sb.do_2", 
		"Action={p_url}/etax/gdgs/sb/zzsYbnsr/sb.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=jgcx", ENDITEM, 
		"Name=tid", "Value={ret_sbdoTID}", ENDITEM, 
		"Name=sid", "Value=ETax.SB.sbInit.GdgsZzsYbnsr", ENDITEM, 
		LAST});

	reportOut(true, "�걨����",lr.eval_string("{retStr}"),timer);



    }

    public void run_Jk() throws Throwable{
	long timer = lr.start_timer();
	lr.start_transaction("���Ͻ�˰");


	_webresult = lrapi.web.url("zsController.do", 
		"URL={p_url}/etax/zs/zsController.do", new String[]{ 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=/skin/gdgs/images/background/lmtop_center.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmtop_left.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmtop_right.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/tb_top.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/btnbg_page.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_center.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_left.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_right.gif", ENDITEM, 
		"Url=/skin/www/style/images-common/waiting/loading.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_left.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_right.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/title_bg_center.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_l.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_r.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/content_bg.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/ico.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_lb.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_rb.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/win_b.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/right.gif", ENDITEM, 
		"Url=/skin/etax/script/message/skin/default/images/btn_bg.gif", ENDITEM, 
		LAST});

	web.reg_save_param("ret_qsxxTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 

	_webresult = lrapi.web.submit_data("qsxx.do", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=cx", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.WqjxxCx.GdgsQjsk", ENDITEM, 
		LAST});

	web.reg_save_param("ret_zsuuid", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<zsuuid>", 
				"RB=</zsuuid>" , 
				LAST} ); 
	web.reg_save_param("ret_yzpzxh", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<yzpzxh>", 
				"RB=</yzpzxh>" , 
				LAST} ); 


	_webresult = lrapi.web.submit_data("qsxx.do_2", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=jgcx", ENDITEM, 
		"Name=tid", "Value={ret_qsxxTID}", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.WqjxxCx.GdgsQjsk", ENDITEM, 
		LAST});

	web.reg_save_param("ret_sfxyTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 
	_webresult = lrapi.web.submit_data("qsxx.do_3", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=sfxyCx", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.SfxyCx.GdgsQjsk", ENDITEM, 
		LAST});

	web.reg_save_param("ret_sfxyh", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<sfxyh>", 
				"RB=</sfxyh>" , 
				LAST} );

	_webresult = lrapi.web.submit_data("qsxx.do_4", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=jgcx", ENDITEM, 
		"Name=tid", "Value={ret_sfxyTID}", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.SfxyCx.GdgsQjsk", ENDITEM, 
		LAST});

	lr.think_time(1);

	web.reg_save_param("ret_qjskTID", new String []{ 
				"NOTFOUND=ERROR", 
				"LB=<tid>", 
				"RB=</tid>" , 
				LAST} ); 

	_webresult = lrapi.web.submit_data("qsxx.do_5", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do#", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=bizXml", "Value=<taxML><yzpzxhGrid><yzpzxhGridlb><yzpzxh>{ret_yzpzxh}</yzpzxh><yzpzmxxhGrid>"+
		    "<yzpzmxxhGridlb><yzpzmxxh>1</yzpzmxxh><zsuuid>{ret_zsuuid}</zsuuid><kkje>1</kkje>"+
		    "</yzpzmxxhGridlb></yzpzmxxhGrid></yzpzxhGridlb></yzpzxhGrid><sfxyh>{ret_sfxyh}</sfxyh></taxML>", ENDITEM, 
		"Name=action", "Value=qj", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.qj.GdgsQjsk", ENDITEM, 
		LAST});

	web.reg_save_param ("retStr",
	    new String []{ 
		"NOTFOUND=warning", 
		"LB=", 
		"RB=" ,
		"Search=BODY",
		LAST} );

	_webresult = lrapi.web.submit_data("qsxx.do_6", 
		"Action={p_url}/etax/zs/qsxx.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/zs/zsController.do#", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=jgcx", ENDITEM, 
		"Name=tid", "Value={ret_qjskTID}", ENDITEM, 
		"Name=sid", "Value=ETax.ZS.qj.GdgsQjsk", ENDITEM, 
		 LAST});

	reportOut(true, "���Ͻ�˰",lr.eval_string("{retStr}"),timer);

   }

    public void run_Fprz() throws Throwable{

	long timer = lr.start_timer();
	lr.start_transaction("��Ʊ��֤");
	_webresult = lrapi.web.url("fprz_index.do", 
	    "URL={p_url}/etax/admin/fprz/fprz_index.do?siteName=gd&styleName=yellow&ythFlag=Y", new String[]{ 
	    "Resource=0", 
	    "RecContentType=text/html", 
	    "Referer=", 
	    "Snapshot=t4.inf", 
	    "Mode=HTML", 
	    LAST});

	lr.think_time(1);


	_webresult = lrapi.web.url("fprz.do", 
		"URL={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", new String[]{ 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		EXTRARES, 
		"Url=/skin/gdgs/Style/yellow/images/wsbs/pic_4400000000.jpg", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_center.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_left.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmtop_right.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/btnbg_page.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_center.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_left.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_right.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_left.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_center.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		"Url=/skin/gdgs/Style/yellow/images/background/bottom_right.gif", "Referer={p_url}/etax/gdgs/menu/fprz.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
		LAST});

	_webresult = lrapi.web.link("������֤", 
		"Text=������֤", new String[]{ 
		"Snapshot=t6.inf", 
		EXTRARES, 
		"Url=/skin/gdgs/images/background/lmtop_left.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmtop_center.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmtop_right.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/btnbg_page.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_center.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_left.gif", ENDITEM, 
		"Url=/skin/gdgs/images/background/lmbottom_right.gif", ENDITEM, 
		"Url=/etax/script/common/ajaxUpload/loading.gif", ENDITEM, 
		LAST});

	lr.think_time(1);



	String sDate = lr.eval_string("{p_date}");

	String rzfileName = ActionsCommon.getInstance().genRZFileName();
	String rzfilePath = ActionsCommon.getInstance().genRZFile(rzfileName);

        lr.save_string(rzfilePath,"rzfilePath");
	lr.think_time(1);

//	lr.message("=================="+lr.eval_string("{retStr}"));

	web.reg_save_param ("path_UUID",
	    new String []{ 
		"NOTFOUND=ERROR", 
		"LB=filepath\":\"", 
		"RB=\",\"realFileName" ,
		"ORD=1",
		LAST} );
        web.reg_save_param ("retStr",
	    new String []{ 
		"NOTFOUND=warning", 
		"LB=", 
		"RB=" ,
		LAST} );
	_webresult = lrapi.web.submit_data("loginUserAttachmentUpload.do", 
		"Action={p_url}/etax/loginUserAttachmentUpload.do?action=upload", 
	        new String[]{ 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"RecContentType=text/html", 
		"Referer={p_url}/etax/gdgs/fprz/zzsfplxrz.do", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		}, 
		new String[]{ // ITEM DATA 
		"Name=file", lr.eval_string("Value={rzfilePath}"), "File=Yes", ENDITEM, 
		"Name=name", "Value=attachment", ENDITEM, 
		"Name=sizeLimit", "Value=10240", ENDITEM, 
		"Name=formId", "Value=", ENDITEM, 
		"Name=submitButton", "Value=�ύ��ѯ����", ENDITEM, 
		LAST});
	lr.think_time(1);

	lr.message(lr.eval_string("{retStr}"));

	web.reg_save_param ("para_tid",
	    new String []{ 
		"NOTFOUND=ERROR", 
		"LB=<tid>", 
		"RB=</tid>" , 
		LAST} ); 

	_webresult = lrapi.web.submit_data("zzsfpupload.do", 
		"Action={p_url}/etax/gdgs/fprz/zzsfpupload.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/gdgs/fprz/zzsfplxrz.do", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=upload", ENDITEM, 
		"Name=sid", "Value=ETax.WS.Upload.Fprz", ENDITEM, 
		"Name=bizXml", "Value=<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><taxML><filename>"+
		    rzfileName+"</filename><bszl>{path_UUID}</bszl></taxML>", ENDITEM, 
		LAST});

        web.reg_find("Text=�ɹ�", 
		new String[]{ 
		    "SaveCount=para_SuccCount", 
		    web.LAST 
		}); 


	_webresult = lrapi.web.submit_data("zzsfpupload.do_2", 
		"Action={p_url}/etax/gdgs/fprz/zzsfpupload.do", new String[]{ 
		"Method=POST", 
		"RecContentType=text/plain", 
		"Referer={p_url}/etax/gdgs/fprz/zzsfplxrz.do", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		}, new String[]{ // ITEM DATA 
		"Name=action", "Value=uploadQuery", ENDITEM, 
		"Name=tid", "Value={para_tid}", ENDITEM, 
		"Name=sid", "Value=ETax.WS.Upload.Fprz", ENDITEM, 
		LAST});

	_webresult = lrapi.web.url("fpuploadInfo.do", 
		"URL={p_url}/etax/gdgs/fprz/fpuploadInfo.do", new String[]{ 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST});

	reportOut((lr.eval_int("{LoginCount}") > 0), "��Ʊ��֤","",timer);
    }


    public  void run_Login() throws Throwable{

	long timer = lr.start_timer();
	lr.start_transaction("��¼");



	_webresult = lrapi.web.url("etax_2",
			"URL={p_url}/etax/", new String[]{
			"Resource=0",
			"RecContentType=text/html",
			"Referer=",
			"Snapshot=t2.inf",
			"Mode=HTML",
			EXTRARES,
			"Url=../skin/gdgs/jsp/style/images-common/head/bg.gif", ENDITEM,
			"Url=../skin/gdgs/jsp/style/images-wsbs-01/style-number-01/bgline.gif", ENDITEM,
			"Url=../sso/script/message/skin/default/images/title_bg_left.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/title_bg_right.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/title_bg_center.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/win_l.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/ico.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/content_bg.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/win_lb.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/win_rb.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/win_b.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/script/message/skin/default/images/win_r.gif", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			"Url=../sso/captcha.jpg?n=0.4202206804026295", "Referer={p_url}/sso/login?service=http%3A%2F%2Fapp.gd-n-tax.gov.cn%3A9001%2Fetax%2Fadmin%2FloginFrameGdgs.do", ENDITEM,
			LAST});

	web.reg_find("Text=/admin/pagehomegdgs.do", 
	    new String[]{ 
		"SaveCount=LoginCount", 
		web.LAST 
	    }); 

	web.reg_save_param ("retStr",
	    new String []{ 
		"NOTFOUND=warning", 
		"LB=", 
		"RB=" ,
		"Search=BODY",
		LAST} );

	_webresult = lrapi.web.submit_form("login", new String[]{ 
		"Snapshot=t2.inf", 
		}, new String[]{ // ITEM DATA 
		"Name=username", "Value={para_nsrsbh}", ENDITEM, 
		"Name=password", "Value={para_password}", ENDITEM, 
		"Name=captcha", "Value=1111", ENDITEM, 
		LAST});

	reportOut((lr.eval_int("{LoginCount}") > 0), "��¼",lr.eval_string("{retStr}"),timer);

    }


	public String genRZFileName(){

	    String sDate = lr.eval_string("{p_date}");
	    return lr.eval_string("RZ00100{para_nsrsbh}"+sDate+".dat");

	}

    	public String genRZFile(String fileName){
	    //ɢ���ļ����
	    
	    String rzFile = "C:\\dat\\RZ\\"+
			    distDir(fileName,32)+
			    fileName;

	    if (!fileExist(rzFile)) {   //����ļ�������,�ʹ���
		FileUtil.Copy("C:\\dat\\1\\RZ0010044010306111847520141208095327.dat",
			      rzFile);
		lr.message("==���ɷ�Ʊ��֤�ļ�:"+rzFile);
	    }else{
		lr.message("==��֤�ļ��Ѿ�����:"+rzFile);

	    }

	    return rzFile;

	}//.replaceAll("\\","\\")

	public String genFile(String sXML){  //�����ļ�·��
	    //ɢ���ļ����
	    String sDate = lr.eval_string("{p_date}");

	   
	    //������ļ�
	    //·��
	    String path= "C:\\dat\\SB\\"+
			distDir(lr.eval_string("{para_djxh}"),15)+
			lr.eval_string("{para_djxh}.dat");

	    
	    if (newFile(lr.eval_string(sXML),path)==0){
		lr.message("==�����ļ���"+path);
	    }else{
		lr.message("==�ļ��Ѿ����ڣ�"+path);
	    }

	    return path;

	}

	public String distDir(String sourceStr,int index){

	    return sourceStr.substring(index,index+2)+"\\"+sourceStr.substring(index+2,index+4)+"\\";


	}

	public boolean fileExist(String filePath){
	    //�ж��ļ�orĿ¼�Ƿ���ڣ���������ڣ����Ŀ¼����������
	    File file=new File(filePath);
	    if (!file.exists()) {
		if(!file.getParentFile().exists()){
		    file.getParentFile().mkdirs();
		}
	    }else{
		return true;
	    }
	    return false;

	}

	public  int newFile(String strxml,String path) {
	    File file=new File(path);
	    if (fileExist(path)) {
		return -1;
	    }
	    try{
	      file.createNewFile();
	    }catch(Exception e){
		e.printStackTrace();
	    }

	    try{

	       FileWriter fw=new FileWriter(file,true);
	       BufferedWriter bw=new BufferedWriter(fw);
	       bw.write(strxml);
	       bw.flush();
	       bw.close();
	       fw.close();
	       //file.close();
	    }catch(Exception e){
		e.printStackTrace();
	    }

		return 0;
	}

}
