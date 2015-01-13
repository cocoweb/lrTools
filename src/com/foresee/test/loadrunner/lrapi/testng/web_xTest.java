package com.foresee.test.loadrunner.lrapi.testng;

import org.apache.http.HeaderIterator;
import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.foresee.test.http.HttpException;
import com.foresee.test.loadrunner.lrapi.web;
import com.foresee.test.loadrunner.lrapi.web_x;

import static com.foresee.test.loadrunner.lrapi.lr_x.save_string;

import com.foresee.test.util.lang.ArrayUtil;
import com.foresee.test.util.lang.StringUtil;
import com.sdicons.json.validator.impl.predicates.Array;

public class web_xTest {
  @BeforeTest
  public void beforeTest() {
	  save_string("http://61.146.43.162:9595","p_tycxURL");
	  save_string("441302730441107","para_nsrsbh");
  }

  @AfterTest
  public void afterTest() {
  }


  @Test
  public void custom_request() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void setHeaders() {
	HttpGet httpget = new HttpGet("http://www.iteye.com");    
	  
    web_x.setHeaders(httpget, new String[]{ 
				"Resource=0", 
				"RecContentType=text/html", 
				"Referer=", 
				"Snapshot=t11.inf", 
				"Mode=HTML",  
				web_x.EXTRARES,  
				"Url=../style/images-common/common/top_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-common/common/bot_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-swzj-01/style12/td_bg.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_tb.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-common/leftnav/left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				"Url=../style/images-common/leftnav/right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", web_x.ENDITEM, 
				web_x.LAST});
    HeaderIterator iter = httpget.headerIterator();
    while(iter.hasNext()){
    	System.out.println(iter.next().toString());
    	
    }
  }
  String EXTRARES = web_x.EXTRARES;
  String ENDITEM = web_x.ENDITEM;
  String LAST = web_x.LAST;

  @Test
  public void url() {
	    try {
			web_x.url("tycx.do", 
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
			
		    web_x.url("tycx.do_2", 
				    "URL={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", new String[]{
				    "Resource=0", 
				    "RecContentType=text/html", 
				    "Referer=", 
				    "Snapshot=t12.inf", 
				    "Mode=HTML", 
				    LAST});

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}
