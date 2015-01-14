package com.foresee.test.loadrunner.lrapi4j.testng;

import static com.foresee.test.loadrunner.lrapi4j.lr.save_string;

import java.io.IOException;

import lrTestool.actionDemo.ActionsDemo3;

import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.http.HttpException;
//import ActionsDemo;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;

public class web_xTest {
  @BeforeTest
  public void beforeTest() {
	  save_string("http://61.146.43.162:9999","p_tycxURL");
	  //save_string("http://61.146.43.162:9595","p_tycxURL");
	  save_string("441302730441107","para_nsrsbh");
  }

  @AfterTest
  public void afterTest() {
  }

  @Test
  public void submit_data() throws HttpException, lrapi.exceptions.HttpException, InterruptedException {
      web.url("tycx.do", 
              "URL={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh={para_nsrsbh}&nsrsmc=惠州市新大众眼镜有限公司&token=", new String[]{ 
              "Resource=0", 
              "RecContentType=text/html", 
              "Referer=", 
              "Snapshot=t11.inf", 
              "Mode=HTML",  
              EXTRARES,  
              "Url=../style/images-common/common/top_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-common/common/bot_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-swzj-01/style12/td_bg.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-swzj-01/style12/top_tb.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-swzj-01/style12/top_right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-swzj-01/style12/top_left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-common/leftnav/left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              "Url=../style/images-common/leftnav/right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
              LAST});

  web.url("tycx.do_2", 
          "URL={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", new String[]{
          "Resource=0", 
          "RecContentType=text/html", 
          "Referer=", 
          "Snapshot=t12.inf", 
          "Mode=HTML", 
          LAST});

  web.reg_save_param ("ret_cxTID", new String []{ 
          "NOTFOUND=ERROR", 
          "LB=<tid>", 
          "RB=</tid>" , 
          LAST} ); 
  web.reg_save_param ("ret_waitTime", new String []{ 
                  "NOTFOUND=ERROR", 
                  "LB=<waitingTime>", 
                  "RB=</waitingTime>" , 
                  LAST} ); 

  web.submit_data("tycx.do_3", 
          "Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
          "Method=POST", 
          "RecContentType=text/plain", 
          "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", 
          "Snapshot=t13.inf", 
          "Mode=HTML", }, new String[]{
          "Name=bizXml", "Value=yhscd", ENDITEM, 
          "Name=sid", "Value=ETax.TY.initMenu", ENDITEM, 
          "Name=action", "Value=queryData", ENDITEM, 
          LAST});
  
  System.out.println(lr.eval_string("ret_cxTID:{ret_cxTID}"));
  System.out.println(lr.eval_string("ret_waitTime:{ret_waitTime}"));
  

      //轮询
      int ii = 0;
      do {
         // lr_x.think_time(lr_x.eval_int("{ret_waitTime}")/1000); 
          Thread.sleep(lr.eval_int("{ret_waitTime}"));
          ii++;

          lr.message(lr.eval_string("{ret_waitTime} ")+String.format("----第%s次请求服务器\n",ii));
              
          web.reg_find("Text=结果尚未返回", 
                  new String[]{ 
                  "SaveCount=ret_lunxunCount", 
                  LAST }); 

          web.reg_save_param ("retStr",
                          new String []{
                              "NOTFOUND=warning",
                              "LB=<cdmc>",
                              "RB=</cdmc>" ,
                              "Search=ALL",
                              LAST} );
          web.add_header ("Accept", "text/plain;charset=UTF-8" ); 

          web.reg_find("Text=<cdmc>综合查询</cdmc>", 
                          new String[]{ 
                          "SaveCount=ret_zhcxCount", 
                          LAST }); 

          web.submit_data("tycx.do_4",
                  "Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
                  "Method=POST",
                  "RecContentType=text/plain",
                  "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS",
                  "Snapshot=t14.inf",
                  "Mode=HTML"},new String[]{
                  "Name=action", "Value=queryHandleResult", ENDITEM,
                  "Name=tid", "Value={ret_cxTID}", ENDITEM,
                  "Name=sid", "Value=ETax.TY.initMenu", ENDITEM,
                  LAST});
              //结果尚未返回
      }while(lr.eval_int("{ret_lunxunCount}") > 0);
      
      System.out.println(lr.eval_string("ret_cxTID:{retStr}"));

  }

  @Test
  public void custom_request() {
      //ActionsDemo.getInstance().Init();
      try {
        ActionsDemo3.i_client.onTrans();// .runTrans("办税服务厅登录");
    } catch (Throwable e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
  @Test
  public void setParameters() {
      HttpPost httpost = new HttpPost("http://www.baidu.com"); 
      
      web.setParameters(httpost, new String[]{
                                "Name=bizXml", "Value=<tycxParam><sqlID>querySwdjxxcx</sqlID><pageFlag>Y</pageFlag><perNum>10</perNum><params><entry><key>nsrsbh</key><value>{para_nsrsbh}</value></entry><entry><key>beignRow</key><value>1</value></entry><entry><key>endRow</key><value>10</value></entry></params></tycxParam>", ENDITEM, 
                                "Name=sid", "Value=ETax.TY.qureydataZG", ENDITEM, 
                                "Name=action", "Value=queryData", ENDITEM, 
                                LAST});
      
      
      try {
        System.out.println(EntityUtils.toString(httpost.getEntity()));
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      
  }

  @Test
  public void setHeaders() {
	HttpGet httpget = new HttpGet("http://www.baidu.com");    
	  
    web.setHeaders(httpget, new String[]{ 
				"Resource=0", 
				"RecContentType=text/html", 
				"Referer=", 
				"Snapshot=t11.inf", 
				"Mode=HTML",  
				web.EXTRARES,  
				"Url=../style/images-common/common/top_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-common/common/bot_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-swzj-01/style12/td_bg.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_tb.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-common/leftnav/left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				"Url=../style/images-common/leftnav/right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web.ENDITEM, 
				web.LAST});
    HeaderIterator iter = httpget.headerIterator();
    while(iter.hasNext()){
    	System.out.println(iter.next().toString());
    	
    }
  }
  String EXTRARES = web.EXTRARES;
  String ENDITEM = web.ENDITEM;
  String LAST = web.LAST;

  @Test
  public void url() {
	    try {
		web.url("tycx.do", 
				"URL={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh={para_nsrsbh}&nsrsmc=惠州市新大众眼镜有限公司&token=", new String[]{ 
				"Resource=0", 
				"RecContentType=text/html", 
				"Referer=", 
				"Snapshot=t11.inf", 
				"Mode=HTML",  
				EXTRARES,  
				"Url=../style/images-common/common/top_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/common/bot_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/td_bg.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_tb.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/leftnav/left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/leftnav/right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				LAST});
                   web.reg_save_param ("ret_cxTID", new String []{ 
                        "NOTFOUND=ERROR", 
                        "LB=<title>", 
                        "RB=</title>" , 
                        LAST} ); 
                   web.reg_save_param ("retStr",
                           new String []{ 
                           "NOTFOUND=warning", 
                           "LB=", 
                           "RB=" ,
                           "Search=BODY",
                           LAST} );

	
		    web.url("tycx.do_2", 
				    "URL={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", new String[]{
				    "Resource=0", 
				    "RecContentType=text/html", 
				    "Referer=", 
				    "Snapshot=t12.inf", 
				    "Mode=HTML", 
				    LAST});
		    
		    System.out.println(lr.eval_string("我是 ： {ret_cxTID}"));
		    System.out.println(lr.eval_string("===========================下面是内容 ： {retStr}"));

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  

}
