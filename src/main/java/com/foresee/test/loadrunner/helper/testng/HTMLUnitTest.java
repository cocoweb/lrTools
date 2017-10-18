package com.foresee.test.loadrunner.helper.testng;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.foresee.test.loadrunner.helper.HtmlUnitHelper;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HTMLUnitTest {
    final WebClient webclient = new WebClient();

    private static String TARGET_URL = "http://databank.worldbank.org/ddp/home.do";
   @BeforeTest
    public void beforeTest() {
       WebClientOptions weboptions = webclient.getOptions();
       weboptions.setProxyConfig(new ProxyConfig("localhost", 8888));
        weboptions.setThrowExceptionOnScriptError(false);
        weboptions.setJavaScriptEnabled(false);
        weboptions.setCssEnabled(true);
        
        webclient.setAjaxController(new NicelyResynchronizingAjaxController());
        weboptions.setTimeout(35000);

    }
   
    @Test
    public void getLink(){
        
    }
   

    @Test
    public void browseBaiduAdv() {
       try {
           
           final HtmlPage htmlpage = webclient.getPage("http://www.baidu.com/gaoji/advanced.html");

           //搜索按钮
           final HtmlForm form = htmlpage.getFormByName("f1");
           final HtmlSubmitInput button = form.getInputByValue("百度一下");

           //搜索结果-关键词
           final HtmlTextInput textField = form.getInputByName("q1");
           textField.setValueAttribute("HTML我帮您");

           //分页条数
           final HtmlSelect htmlSelet=form.getSelectByName("rn");
           htmlSelet.setDefaultValue("10");

           //网页的时间
           final HtmlSelect htmlSeletlm=form.getSelectByName("lm");
           htmlSeletlm.setDefaultValue("0");

           //语言
           final List<HtmlRadioButtonInput> radioButtonCts = form.getRadioButtonsByName("ct");
           radioButtonCts.get(0).setChecked(true);
           radioButtonCts.get(1).setChecked(false);
           radioButtonCts.get(2).setChecked(false);

           //文档格式
           final HtmlSelect htmlSeletft=form.getSelectByName("ft");
           htmlSeletft.setDefaultValue("");

           //关键词位置
           final List<HtmlRadioButtonInput> radioButtonq5s = form.getRadioButtonsByName("q5");
           radioButtonq5s.get(0).setChecked(true);
           radioButtonq5s.get(1).setChecked(false);
           radioButtonq5s.get(2).setChecked(false);

           //站内搜索      限定要搜索指定的网站
           final HtmlTextInput htmlTextInputq6 = form.getInputByName("q6");
           htmlTextInputq6.setDefaultValue("html580.com");

           //隐藏值
           final HtmlHiddenInput hiddenInputtn = form.getInputByName("tn");
           hiddenInputtn.setDefaultValue("baiduadv");

           final HtmlPage page2 = button.click();
           //String result = page2.asXml();
           System.out.println(page2.asXml());
           //webclient.closeAllWindows();
       } catch (FailingHttpStatusCodeException e) {
           e.printStackTrace();
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (ElementNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } 
   }

    @Test
    public void f() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        // 设置webClient的相关参数

        // 模拟浏览器打开一个目标网址
        HtmlPage rootPage = webclient.getPage(TARGET_URL);
        // 获取第一个数据库
        HtmlSelect hs = (HtmlSelect) rootPage.getElementById("lstCubes");
        // 按要求选择第一个数据库
        hs.getOption(0).setSelected(true);
        // 模拟点击Next按钮，跳转到第二个页面
        System.out.println("正在跳转…");
        // 执行按钮出发的js事件
        ScriptResult sr = rootPage.executeJavaScript("javascript:setCubeData(2,-1,4,'/ddp');");

        // 跳转到第二个页面，选择国家
        HtmlPage countrySelect = (HtmlPage) sr.getNewPage();
        // 获得包含全部国家信息的选择框页面
        HtmlPage framePage = (HtmlPage) countrySelect.getFrameByName("frmTree1").getEnclosedPage();
        // 获得selectAll按钮，触发js事件
        framePage
                .executeJavaScript("javascript:TransferListAll('countrylst','countrylstselected','no');SetSelectedCount('countrylstselected','tdcount');");
        // 获取Next按钮，触发js事件
        ScriptResult electricityScriptResult = framePage.executeJavaScript("javascript:wrapperSetCube('/ddp')");

        System.out.println("正在跳转…");
        // 跳转到下一个页面electricitySelect
        HtmlPage electricitySelect = (HtmlPage) electricityScriptResult.getNewPage();
        // 获得electricity选择的iframe
        HtmlPage electricityFrame = (HtmlPage) electricitySelect.getFrameByName("frmTree1").getEnclosedPage();
        // 获得选择框
        HtmlSelect seriesSelect = (HtmlSelect) electricityFrame.getElementById("countrylst");
        // 获得所有的选择框内容
        List<HtmlOption> optionList = seriesSelect.getOptions();
        // 将指定的选项选中
        optionList.get(1).setSelected(true);
        // 模拟点击select按钮
        electricityFrame
                .executeJavaScript("javascript:TransferList('countrylst','countrylstselected','no');SetSelectedCount('countrylstselected','tdcount');");
        // 获取选中后，下面的选择框
        HtmlSelect electricitySelected = (HtmlSelect) electricityFrame.getElementById("countrylstselected");
        List<HtmlOption> list = electricitySelected.getOptions();
        // 模拟点击Next按钮，跳转到选择时间的页面
        ScriptResult timeScriptResult = electricityFrame.executeJavaScript("javascript:wrapperSetCube('/ddp')");

        System.out.println("正在跳转…");
        HtmlPage timeSelectPage = (HtmlPage) timeScriptResult.getNewPage();
        // 获取选中时间的选择框
        timeSelectPage = (HtmlPage) timeSelectPage.getFrameByName("frmTree1").getEnclosedPage();
        // 选中所有的时间
        timeSelectPage
                .executeJavaScript("javascript:TransferListAll('countrylst','countrylstselected','no');SetSelectedCount('countrylstselected','tdcount');");
        // 点击Next按钮
        ScriptResult exportResult = timeSelectPage.executeJavaScript("javascript:wrapperSetCube('/ddp')");

        System.out.println("正在跳转…");
        // 转到export页面
        HtmlPage exportPage = (HtmlPage) exportResult.getNewPage();
        // 点击页面上的Export按钮,进入下载页面
        ScriptResult downResult = exportPage
                .executeJavaScript("javascript:exportData('/ddp' ,'EXT_BULK' ,'WDI_Time=51||WDI_Series=1||WDI_Ctry=244||' );");

        System.out.println("正在跳转…");
        HtmlPage downLoadPage = (HtmlPage) downResult.getNewPage();
        // 点击Excel图标，开始下载
        ScriptResult downLoadResult = downLoadPage.executeJavaScript("javascript:exportData('/ddp','BULKEXCEL');");
        // 下载Excel文件
        InputStream is = downLoadResult.getNewPage().getWebResponse().getContentAsStream();

        OutputStream fos = new FileOutputStream("d://test.xls");
        byte[] buffer = new byte[1024 * 30];
        int len = -1;
        while ((len = is.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        fos.close();
        System.out.println("Success!");
    }
    
    @Test
    public void helperTest(){
        //HtmlUnitHelper.getInstance()..createRequest(urlAddress, submitMethod, options)
    }

    @AfterTest
    public void afterTest() {
    }

}
