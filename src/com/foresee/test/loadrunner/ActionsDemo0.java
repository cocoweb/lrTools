package com.foresee.test.loadrunner;



import com.foresee.test.loadrunner.base.InnerITrans;
import com.foresee.test.loadrunner.base.LrActionClass;
import com.foresee.test.loadrunner.helper.LoadrunnerUtil;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.code.EncodeUtils;
import com.foresee.test.util.io.FileUtil;

/**
 * 这个类里面用了多种方法，来构造LoadRunner的事务代码管理
 * 1、直接copy代码，成为函数，如：run_Sbzs3_upload()
 *    最为简单，但是很多重复代码
 * 2、使用匿名对象，并调用 ，如：run_Sbzs1_Page1()
 *    问题：每次循环执行的时候，都会重复创建该匿名对象
 * 3、使用 addTrans()添加事务对象到map进行缓存，然后再调用 ，如：run_Start()
 * 
 * init()初始化方法应该被第一时间使用
 * @author allan.xie
 *
 */
public class ActionsDemo0 extends LrActionClass
{
    private static ActionsDemo0 commonObj = new ActionsDemo0();
    static String sZZSXML=null;
    static String sFPYJXML =null;
    
    /**
     * @return 返回实例对象
     */
    public static ActionsDemo0 getInstance(){
    	if (null==commonObj) {
    	    commonObj = new ActionsDemo0();
    	}
    	return commonObj;

    }


    @Override
	public void Init() {
    	if(sZZSXML == null){  //初始化XML报文(含参数)
        	sZZSXML = lrTools.loadXmlByKey("zzssyyybnsr_zb");
    	}
    	if(sFPYJXML == null){
    		sFPYJXML = lrTools.loadXmlByKey("fpcglcjTyXmldyywbw");
    	}
    	
    	this.addTrans(new InnerITrans("打开网厅"){
    		@Override
			public boolean onTrans() throws Throwable {
         
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
		        
		        
		        return true;
		    }});
    	this.addTrans( new InnerITrans("登录提交"){
    		@Override
			public boolean onTrans() throws Throwable {
    			run_Start();
         
    	    	web.reg_find("Text=/admin/pagehomegdgs.do", 
    	        	    new String[]{ 
    	        		"SaveCount=LoginCount", 
    	        		LAST 
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
	        
	        	OutString = EncodeUtils.ConvertCharset(lr.eval_string("{retStr}")) ;
	        	
		        return lr.eval_int("{LoginCount}") > 0;
		    }});
		
	}


	public void run_Sbzs1_Page1()  throws Throwable{
		new InnerITrans("申报征收1_首页"){
			@Override
			public boolean onTrans() throws Throwable {
		    	_webresult = lrapi.web.link("申报征收sbzs", 
		    		"Text=申报征收", new String[]{ 
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
		    		LAST 
		    	    }); 
		       	web.reg_save_param ("retStr",
		        	    new String []{ 
		        		"NOTFOUND=warning", 
		        		"LB=", 
		        		"RB=" ,
		        		"Search=BODY",
		        		LAST} );
		
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
		    
		        this.OutString = EncodeUtils.ConvertCharset(lr.eval_string("{retStr}"));
		    	return lr.eval_int("{cxCount}") > 0;
		}}.RunTrans();


    }
	
    public  void run_Sbzs2_UpPage() throws Throwable{
		new InnerITrans("申报征收2上传页面"){
			@Override
			public boolean onTrans() throws Throwable {
			    
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
		    	
		    	//LoadrunnerUtil.reportOut(true, "申报征收2上传页面",StringUtil.StringUtil.ConvertCharset(lr.eval_string("{retStr}")),timer);
		        
		    	this.OutString= EncodeUtils.ConvertCharset(lr.eval_string("{retStr}"));
		        
		        return true;
		    }}.RunTrans();
	}


    public void run_Sbzs() throws Throwable{
    	run_Sbzs1_Page1();
    
    	run_Sbzs2_UpPage();
    	
    	//run_Sbzs3_upload();
    }
    
    public void run_Sbzs3_upload() throws Throwable{
        long timer = lr.start_timer();
        lr.start_transaction("申报文件上传");
        
        String sbFileName = genZZSFileName();
        String sbFile = genZZSFile(lr.eval_string(sZZSXML),sbFileName);
        
        lr.save_string(sbFile, "sbFile");
        web.reg_save_param ("ret_ZZSUUIDFile",
                new String []{ 
                "NOTFOUND=ERROR", 
                "LB=filePath\":\"", 
                "RB=\",\"realFileName" ,
                "ORD=1",
                LAST} );


        //文件上传
        _webresult = lrapi.web.submit_data("loginUserAttachmentUpload.do", 
                "Action={p_url}/etax/loginUserAttachmentUpload.do?action=upload", new String[]{ 
                "Method=POST", 
                "EncType=multipart/form-data", 
                "RecContentType=text/html", 
                "Referer={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", 
                "Snapshot=t12.inf", 
                "Mode=HTML", 
                }, new String[]{ // ITEM DATA 
                "Name=file", lr.eval_string("Value={sbFile}"), "File=Yes", ENDITEM, 
                "Name=name", "Value=attachment", ENDITEM, 
                "Name=sizeLimit", "Value=10240", ENDITEM, 
                "Name=formId", "Value=", ENDITEM, 
                "Name=submitButton", "Value=提交查询内容", ENDITEM, 
                LAST});
        
        web.reg_save_param ("ret_sbdo3TID", new String []{ 
                "NOTFOUND=ERROR", 
                "LB=<tid>", 
                "RB=</tid>" , 
                LAST} ); 

        _webresult = lrapi.web.submit_data("sb.do_3", 
            "Action={p_url}/etax/gdgs/sb/zzsYbnsr/sb.do", new String[]{ 
            "Method=POST", 
            "RecContentType=text/plain", 
            "Referer={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", 
            "Snapshot=t13.inf", 
            "Mode=HTML", 
            }, new String[]{ // ITEM DATA 
            "Name=fileName", "Value={ret_ZZSUUIDFile}", ENDITEM, 
            "Name=realFileName", "Value={sbFile}", ENDITEM, 
            "Name=sssq_q", "Value={para_skssqq}", ENDITEM, 
            "Name=sssq_z", "Value={para_skssqz}", ENDITEM, 
            "Name=action", "Value=infoUpload", ENDITEM, 
            "Name=sid", "Value=ETax.SB.infoFileUpload.GdgsZzsYbnsr", ENDITEM, 
            LAST});
        web.reg_save_param ("retStr",
            new String []{ 
            "NOTFOUND=warning", 
            "LB=", 
            "RB=" ,
            "Search=BODY",
            LAST} );
        
        
        web.reg_find("Text=<qd_code>0</qd_code>", 
                new String[]{ 
                "SaveCount=ret_sbCount", 
                LAST }); 
         web.reg_save_param ("ret_pzxh",
                new String []{ 
                "NOTFOUND=warning", 
                "LB=<pzxh>", 
                "RB=</pzxh>" ,
                "Search=BODY",
                LAST} );

        _webresult = lrapi.web.submit_data("sb.do_4", 
                "Action={p_url}/etax/gdgs/sb/zzsYbnsr/sb.do", new String[]{ 
                "Method=POST", 
                "RecContentType=text/plain", 
                "Referer={p_url}/etax/gdgs/sb/zzsYbnsr/sbRequest.do", 
                "Snapshot=t13.inf", 
                "Mode=HTML", 
                }, new String[]{ // ITEM DATA 
                "Name=tid", "Value={ret_sbdo3TID}", ENDITEM, 
                "Name=action", "Value=jgcx", ENDITEM, 
                "Name=sid", "Value=ETax.SB.infoFileUpload.GdgsZzsYbnsr", ENDITEM, 
                LAST});
  
        LoadrunnerUtil.reportOut((lr.eval_int("{ret_sbCount}") > 0), "申报文件上传",
        		lr.eval_string("pzxh:[{ret_pzxh}]  ")+
        		EncodeUtils.ConvertCharset(lr.eval_string("{retStr}")),timer);
        
        
    }
    

    public void run_Jk1_Qfxx() throws Throwable{
        
        long timer = lr.start_timer();
        lr.start_transaction("缴款1_获取欠税信息");
    
    
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
        
        web.reg_save_param("ret_yzpzmxxh", new String []{ 
                "NOTFOUND=ERROR", 
                "LB=<yzpzmxxh>", 
                "RB=</yzpzmxxh>" , 
                LAST} ); 
        
        web.reg_save_param ("retStr",
                new String []{ 
                "NOTFOUND=warning", 
                "LB=", 
                "RB=" ,
                "Search=BODY",
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
        
        //lr.message(lr.eval_string("{retStr}"));
    
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
        web.reg_find("Text=<sfxyh>", 
                new String[]{ 
                "SaveCount=ret_sfxyhCount", 
                LAST }); 

    
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
        
        LoadrunnerUtil.reportOut((lr.eval_int("{ret_sfxyhCount}") > 0), "缴款1_获取欠税信息",lr.eval_string("{ret_sfxyh}"),timer);

    }
    
    public  void run_Jk2_Jk() throws Throwable{
		new InnerITrans("缴款2_网上缴税"){
			@Override
			public boolean onTrans() throws Throwable {
	     
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
		    		    "<yzpzmxxhGridlb><yzpzmxxh>{ret_yzpzmxxh}</yzpzmxxh><zsuuid>{ret_zsuuid}</zsuuid><kkje>1</kkje>"+
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
		 
		    	web.reg_find("Text=<dzsphm>", 
		            new String[]{ 
		            "SaveCount=ret_spHMCount", 
		            LAST }); 
	
		    
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
	    
	    	//LoadrunnerUtil.reportOut((lr.eval_int("{ret_spHMCount}") > 0), "缴款2_网上缴税",StringUtil.ConvertCharset(lr.eval_string("{retStr}"),"ISO-8859-1"),timer);
		        
		        this.OutString = EncodeUtils.ConvertCharset(lr.eval_string("{retStr}"),"ISO-8859-1");
		        return lr.eval_int("{ret_spHMCount}") > 0;
		    }}.RunTrans();
	}

    
    public void run_Jk() throws Throwable{
        web.set_max_html_param_len("102400"); 

        run_Sbzs1_Page1();
    
    	run_Jk1_Qfxx();
    	
    	run_Jk2_Jk();
    	

   }

    public void run_Fpyj1_Page() throws Throwable{
    	
    	new InnerITrans("发票验旧1发票管理首页"){
    		@Override
			public boolean onTrans() throws Throwable {
    	    	_webresult = lrapi.web.link("发票管理", 
    	    			"Text=发票管理", new String[]{ 
    	    			"Snapshot=t4.inf", 
    	    			EXTRARES, 
    	    			"Url=/skin/gdgs/Style/yellow/images/wsbs/pic_4400000000.jpg", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmtop_center.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmtop_left.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmtop_right.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_center.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_right.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/bottom_left.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/bottom_center.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/bottom_right.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			"Url=/skin/gdgs/Style/yellow/images/background/lmbottom_left.gif", "Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", ENDITEM, 
    	    			LAST});
    	    	return true;
    	    	
			}
    	}.RunTrans();
    }
     
    public void run_Fpyj2_Import() throws Throwable{
    	new InnerITrans("发票验旧2导入页"){
    		@Override
			public boolean onTrans() throws Throwable {
				_webresult = lrapi.web.link("导入", 
						"Text=导入", new String[]{ 
						"Snapshot=t5.inf", 
						EXTRARES, 
						"Url=/skin/gdgs/images/background/lmtop_right.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/lmtop_center.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/lmtop_left.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/lmbottom_right.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/btnbg_page.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/lmbottom_center.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/skin/gdgs/images/background/lmbottom_left.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						"Url=/etax/script/common/ajaxUpload/loading.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
						LAST});
				return true;
  	
       	}}.RunTrans();
    }
    
    public void run_Fpyj3_upload() throws Throwable{
       	long timer = lr.start_timer();
    	lr.start_transaction("发票验旧3上传文件");
    	
        String yjFileName = genFPYJFileName();
        String yjFile = genFPYJFile(lr.eval_string(sFPYJXML),yjFileName);
        
        lr.save_string(yjFile, "yjFile");
   	
        web.reg_save_param ("path_fpyjUUID",
                new String []{ 
                "NOTFOUND=ERROR", 
                "LB=filePath\":\"", 
                "RB=\",\"realFileName" ,
                "ORD=1",
                LAST} );
		_webresult = lrapi.web.submit_data("loginUserAttachmentUpload.do", 
			"Action={p_url}/etax/loginUserAttachmentUpload.do?action=upload", new String[]{ 
			"Method=POST", 
			"EncType=multipart/form-data", 
			"RecContentType=text/html", 
			"Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", 
			"Snapshot=t6.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA Content-Disposition: form-data; name="file"; filename="D:\dat\cglcj122221.xml"
			"Name=file", "Value={yjFile}", "File=Yes", ENDITEM, 
			"Name=name", "Value=attachment", ENDITEM, 
			"Name=sizeLimit", "Value=10240", ENDITEM, 
			"Name=formId", "Value=", ENDITEM, 
			"Name=submitButton", "Value=提交查询内容", ENDITEM, 
			EXTRARES, 
			"Url=../skin/www/style/images-common/waiting/loading.gif", "Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", ENDITEM, 
			LAST});
		
	    web.reg_save_param ("ret_ptfpdrTID", new String []{ 
	                "NOTFOUND=ERROR", 
	                "LB=<tid>", 
	                "RB=</tid>" , 
	                LAST} ); 


		_webresult = lrapi.web.submit_data("ptfp_dr.do", 
			"Action={p_url}/etax/gdgs/fpyj/ptfp_dr.do", new String[]{ 
			"Method=POST", 
			"RecContentType=text/plain", 
			"Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", 
			"Snapshot=t7.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA 
			"Name=fileName", "Value={path_fpyjUUID}", ENDITEM, 
			"Name=readFileName", "Value=cglcj122221.xml", ENDITEM, 
			"Name=action", "Value=dr", ENDITEM, 
			"Name=sid", "Value=ETax.WS.ptfpDr.Fpyj", ENDITEM, 
			LAST});
		//<lsh>4992737</lsh>
		
	    web.reg_save_param ("ret_fpyjLSH", new String []{ 
                "NOTFOUND=ERROR", 
                "LB=<lsh>", 
                "RB=</lsh>" , 
                LAST} ); 

		_webresult = lrapi.web.submit_data("ptfp_dr.do_2", 
			"Action={p_url}/etax/gdgs/fpyj/ptfp_dr.do", new String[]{ 
			"Method=POST", 
			"RecContentType=text/plain", 
			"Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", 
			"Snapshot=t8.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA 
			"Name=action", "Value=jgcx", ENDITEM, 
			"Name=tid", "Value={ret_ptfpdrTID}", ENDITEM, 
			"Name=sid", "Value=ETax.WS.ptfpDr.Fpyj", ENDITEM, 
			LAST});

		
		//<div class="wbtz_xlh">4992737</div>
		
        web.reg_find("Text=<div class=\"wbtz_xlh\">", 
                new String[]{ 
                "SaveCount=ret_fpyjCount", 
                LAST }); 
         web.reg_save_param ("ret_fpyjStr",
                new String []{ 
                "NOTFOUND=warning", 
                "LB=<td align=\"center\">", 
                "RB=</td>" ,
                "Search=BODY",
                LAST} );

		_webresult = lrapi.web.submit_data("ptfp_dr.do_3", 
			"Action={p_url}/etax/gdgs/fpyj/ptfp_dr.do", new String[]{ 
			"Method=POST", 
			"RecContentType=text/html", 
			"Referer={p_url}/etax/gdgs/fpyj/ptfp_dr.do?action=init", 
			"Snapshot=t9.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA 
			"Name=action", "Value=result", ENDITEM, 
			"Name=lsh", "Value={ret_fpyjLSH}", ENDITEM, 
			LAST});
        LoadrunnerUtil.reportOut((lr.eval_int("{ret_fpyjCount}") > 0),"发票验旧3上传文件",
        		lr.eval_string("受理号:[{ret_fpyjLSH}]  ")+
        		lr.eval_string("{ret_fpyjStr}"),timer);

   	
    }
  
    public void run_Fpyj() throws Throwable{
    	run_Fpyj1_Page();
    	run_Fpyj2_Import();
		lr.think_time(1);
		//run_Fpyj3_upload();

		/*_webresult = lrapi.web.url("ppfp_2011_wh.do", 
			"URL={p_url}/etax/gdgs/fpyj/ppfp_2011_wh.do", new String[]{ 
			"Resource=0", 
			"RecContentType=text/html", 
			"Referer={p_url}/etax/gdgs/menu/fpgl.do?siteName=gd&styleName=yellow&ythFlag=Y", 
			"Snapshot=t10.inf", 
			"Mode=HTML", 
			EXTRARES, 
			"Url=/skin/gdgs/images/background/lmtop_right.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/btnbg_page.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/lmtop_left.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/lmtop_center.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/tb_top.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/lmbottom_left.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/lmbottom_center.gif", ENDITEM, 
			"Url=/skin/gdgs/images/background/lmbottom_right.gif", ENDITEM, 
			"Url=/skin/www/style/images-common/waiting/loading.gif", ENDITEM, 
			LAST});

		_webresult = lrapi.web.submit_data("dataQuery.do", 
			"Action={p_url}/etax/bizfront/dataQuery.do", new String[]{ 
			"Method=POST", 
			"RecContentType=text/plain", 
			"Referer={p_url}/etax/gdgs/fpyj/ppfp_2011_wh.do", 
			"Snapshot=t11.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA 
			"Name=sid", "Value=ETax.WS.ppfpQuery.fpyj", ENDITEM, 
			"Name=requestXml", "Value=<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>"+
					"<taxML><djxh>{p_djxh}</djxh><myForm><timeStart></timeStart><timeEnd></timeEnd>"+
					"<LSH>4992734</LSH><offset></offset><pageCount></pageCount><NSRSBH>{p_nsrsbh}</NSRSBH></myForm></taxML>", ENDITEM, 
			LAST});

		_webresult = lrapi.web.submit_data("rejoinQuery.do", 
			"Action={p_url}/etax/bizfront/rejoinQuery.do", new String[]{ 
			"Method=POST", 
			"RecContentType=text/plain", 
			"Referer={p_url}/etax/gdgs/fpyj/ppfp_2011_wh.do", 
			"Snapshot=t12.inf", 
			"Mode=HTML", 
			}, new String[]{ // ITEM DATA 
			"Name=action", "Value=queryXml", ENDITEM, 
			"Name=tid", "Value=d5ba60116ee34c17bb673f1f807a879b", ENDITEM, 
			"Name=sid", "Value=ETax.WS.ppfpQuery.fpyj", ENDITEM, 
			LAST});*/
    }
    
    public  void run_Fprz1_Choose() throws Throwable{
    	new InnerITrans("发票认证1选择"){
    		@Override
			public boolean onTrans() throws Throwable {
         
    	    	_webresult = lrapi.web.url("fprz_index.do", 
    	        	    "URL={p_url}/etax/admin/fprz/fprz_index.do?siteName=gd&styleName=yellow&ythFlag=Y", new String[]{ 
    	        	    "Resource=0", 
    	        	    "RecContentType=text/html", 
    	        	    "Referer=", 
    	        	    "Snapshot=t4.inf", 
    	        	    "Mode=HTML", 
    	        	    LAST});
		        
		        
		        return true;
		    }}.RunTrans();
		 
    }

    public  void run_Fprz2_Page1() throws Throwable{
    	new InnerITrans("发票认证2首页"){
    		@Override
			public boolean onTrans() throws Throwable {
         
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
		        
		        
		        return true;
		    }}.RunTrans();
		 
    }
    public  void run_Fprz3_UpPage() throws Throwable{
    	new InnerITrans("发票认证3上传页面"){
    		@Override
			public boolean onTrans() throws Throwable {
         
    		   	_webresult = lrapi.web.link("网上认证", 
    		    		"Text=网上认证", new String[]{ 
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
		        
		        
		        return true;
		    }}.RunTrans();
		 
    }

    public void run_Fprz() throws Throwable{

    	run_Fprz1_Choose();
    	run_Fprz2_Page1();
    	run_Fprz3_UpPage();
        	
    	//run_Fprz4_upload();
    }


    public void run_Fprz4_upload() throws Throwable{
    
        long timer = lr.start_timer();
        lr.start_transaction("发票认证4文件上传");
        
        String rzfileName = genRZFileName();
        String rzfilePath = genRZFile(rzfileName);
    
        lr.save_string(rzfilePath,"rzfilePath");
        lr.think_time(1);
    
    //  lr.message("=================="+lr.eval_string("{retStr}"));
    
        web.reg_save_param ("path_UUID",
            new String []{ 
            "NOTFOUND=ERROR", 
            "LB=filePath\":\"", 
            "RB=\",\"realFileName" ,
            "ORD=1",
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
            "Name=submitButton", "Value=提交查询内容", ENDITEM, 
            LAST});
        lr.think_time(1);
    
        //lr.message(lr.eval_string("{retStr}"));
    
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
    
        web.reg_find("Text=成功", 
                new String[]{ 
                "SaveCount=ret_fprzCount", 
                LAST }); 
         web.reg_save_param ("retStr",
                new String []{ 
                "NOTFOUND=warning", 
                "LB=", 
                "RB=" ,
                "Search=BODY",
                LAST} );
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
        
        web.reg_find("Text=如果您提交的文件有了认证结果", 
                new String[]{ 
                "SaveCount=ret_fprzCount", 
                LAST }); 
    
        _webresult = lrapi.web.url("fpuploadInfo.do", 
            "URL={p_url}/etax/gdgs/fprz/fpuploadInfo.do", new String[]{ 
            "Resource=0", 
            "RecContentType=text/html", 
            "Referer=", 
            "Snapshot=t10.inf", 
            "Mode=HTML", 
            LAST});       
        
        boolean bSucc = (lr.eval_int("{ret_fprzCount}") > 0);
        
        LoadrunnerUtil.reportOut(bSucc, "发票认证4文件上传",EncodeUtils.ConvertCharset(lr.eval_string("{retStr}")),timer);
    }
    
    InnerITrans Mainpage = new InnerITrans("大厅首页"){
		@Override
		public boolean onTrans() throws Throwable {
     
	        _webresult = lrapi.web.url("pagehomegdgs.do", 
	                "URL={p_url}/etax/admin/pagehomegdgs.do", new String[]{ 
	                "Resource=0", 
	                "RecContentType=text/html", 
	                "Referer=", 
	                "Snapshot=t3.inf",  
	                "Mode=HTML", 
	                LAST});
	        
	        return true;
	    }};
    
    public  void run_MainPage() throws Throwable{
    	Mainpage.RunTrans();
    }

    public  void run_Start() throws Throwable{
    	this.runTrans("打开网厅");
    }
   
    public  boolean run_Login() throws Throwable{
    	return this.runTrans("登录提交");
 
		 
    }

 	/**
	 * @return 返回发票认证的文件名
	 */
	public String genRZFileName(){  

	    String sDate = lr.eval_string("{p_date}");
	    return lr.eval_string("RZ00100{para_nsrsbh}"+sDate+".dat");

	}

    /**
     * 生成发票认证的文件,并散列保存
     * @param fileName
     * @return
     */
    public String genRZFile(String fileName){
	    //散列文件存放 
	    
	    String rzFile = "D:\\dat\\RZ\\"+
			    distDir(fileName,18)+
			    fileName;

	    if (!FileUtil.FileExist(rzFile)) {   //如果文件不存在,就创建
    		FileUtil.Copy("D:\\dat\\1\\RZ0010044010306111847520141208095327.dat",
    			      rzFile);
    		lr.message("==生成发票认证文件: "+rzFile);
	    }else{
    		lr.message("==认证文件已经存在: "+rzFile);

	    }

	    return rzFile; 

	}//.replaceAll("\\","\\")
    
    public String genZZSFileName(){
        //SB_440683751069869_20141210_GT3.xml
        
        String fileName = lr.eval_string("SB_{para_nsrsbh}_")
                        + lr.eval_string("{p_date}").substring(0,8)
                        + "_GT3.xml";
        
        return fileName;
        
    }

	public String genZZSFile(String sXML,String fileName){  //返回文件路径
	    //散列文件存放
	    //输出到文件路径
	    String path= "D:\\dat\\SB\\"+ 
        			distDir(fileName,14)+
        			fileName;
	    
	    if (FileUtil.NewFile(lr.eval_string(sXML),path)==0){
	        lr.message("==生成文件："+path);
	    }else{
	        lr.message("==文件已经存在："+path);
	    }

	    return path;

	}
	
	//fpcglcjTyXmldyywbw.parameter=fphm,fpzlDm,fpDm
    public String genFPYJFileName(){
        //SB_440683751069869_20141210_GT3.xml   

        
        String fileName = lr.eval_string("cglcj{para_nsrsbh}")
                                + lr.eval_string("{p_date}").substring(8,12)+".xml";
        
        return fileName;
        
    }

	public String genFPYJFile(String sXML,String fileName){  //返回文件路径
	    //散列文件存放
	    //输出到文件路径
	    String path= "D:\\dat\\FPYJ\\"+ 
        			distDir(fileName,20)+
        			fileName;
	    
	    if (FileUtil.NewFile(lr.eval_string(sXML),path)==0){
	        lr.message("==生成文件："+path);
	    }else{
	        lr.message("==文件已经存在："+path);
	    }

	    return path;

	}
	
    public String distDir(String sourceStr, int index) {

        return sourceStr.substring(index, index + 2) + "\\" + sourceStr.substring(index + 2, index + 4) + "\\";

    }

	

}
