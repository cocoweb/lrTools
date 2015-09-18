package com.foresee.test.loadrunner.helper;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_int;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_string;
import static com.foresee.test.util.lang.StringUtil.findStringCount;
import static com.foresee.test.util.lang.StringUtil.parsarKVStr;
import static com.foresee.test.util.lang.StringUtil.parsarKVStrKey;
import static com.foresee.test.util.lang.StringUtil.parsarKVStrValue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;

import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.LabelValue;
import com.foresee.test.util.lang.StringUtil;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitHelper {
    static Logger logger = Logger.getLogger(HtmlUnitHelper.class.getName());
    
    private static HtmlUnitHelper helper =null;
    
    private HtmlUnitHelper() {
        super();
    }
    
    public static HtmlUnitHelper getInstance(){
        // synchronized表示同时只能一个线程进行实例化
        if (helper == null) { // 如果两个进程同时进入时，同时创建很多实例，不符合单例
            synchronized (HtmlUnitHelper.class) {
                if (helper == null) {
                    helper = new HtmlUnitHelper();
                }
            }
        }
        
        return helper;
    }

    private static WebClient webclient = null;
    public WebClient getWebClient() {
        if (null == webclient) {
            webclient = new WebClient();

        }
        WebClientOptions weboptions = webclient.getOptions();
        if (isProxy)
            weboptions.setProxyConfig(proxyhost);

        weboptions.setThrowExceptionOnScriptError(false);
        weboptions.setJavaScriptEnabled(false);
        weboptions.setCssEnabled(false);

        return webclient;

    }

    private ProxyConfig proxyhost = new ProxyConfig("localhost", 8888);
    boolean isProxy = false;

    public HtmlUnitHelper setProxy(String hostname, int port) {
        proxyhost = new ProxyConfig(hostname, port);
        isProxy = true;
        
        return this;

    }
    
    public static HtmlUnitHelper EnabledProxy(String hostname, int port) {
        return getInstance().setProxy(hostname, port);

    }


    public static  Map<String, String> headerMap = new HashMap<String, String>();
    public void addHeader(String headerName, String headerValue) {
        headerMap.put(headerName, headerValue);

    }
    public HtmlUnitHelper addCookie(String[] cookies) {
        for (int i = 0; i < cookies.length; i++) {
            getWebClient()
                .getCookieManager()
                .addCookie(new Cookie(
                            new BasicClientCookie(eval_string(parsarKVStrKey(cookies[i])),
                                                   eval_string(parsarKVStrValue(cookies[i])))));
        }
        
        return this;
    }
    
    /**
     * config http headers
     * 
     * @param httprequest
     * @param options
     */
    public HtmlUnitHelper setHeaders(String[] options) {
        return setHeaders(request,options);

//        // 添加add_header的内容，并清理
//        if(!headerMap.isEmpty()){
//            request.setAdditionalHeaders(headerMap);
//            //headerMap.clear();
//        }
//        
//        for (int i = 0; i < options.length; i++) {
//            if (options[i].indexOf(web.EXTRARES) == 0 || options[i].indexOf(web.LAST) == 0) {
//                break;
//            } else if (options[i].indexOf("Body") == 0) { // parameters
//                setParameters(request, parsarKVStrValue(eval_string(options[i])));
//
//            } else { // header
//                String[] strs = parsarKVStr(options[i]);
//                if (strs.length > 1) {
//                    request.setAdditionalHeader(eval_string(strs[0]), eval_string(strs[1]));
//                } else {
//                    request.setAdditionalHeader(eval_string(strs[0]), "");
//                }
//            }
//        }
//
//        
//        return this;

    }
    public static  HtmlUnitHelper setHeaders(WebRequest xrequest,String[] options) {
        // 添加add_header的内容，并清理
        if(!headerMap.isEmpty()){
            xrequest.setAdditionalHeaders(headerMap);
            //headerMap.clear();
        }
        
        for (int i = 0; i < options.length; i++) {
            if (options[i].indexOf(web.EXTRARES) == 0 || options[i].indexOf(web.LAST) == 0) {
                break;
            } else if (options[i].indexOf("Body") == 0) { // parameters
                xrequest.setRequestBody( parsarKVStrValue(eval_string(options[i])));
                //setParameters(xrequest, parsarKVStrValue(eval_string(options[i])));

            } else { // header
                String[] strs = parsarKVStr(options[i]);
                if (strs.length > 1) {
                    xrequest.setAdditionalHeader(eval_string(strs[0]), eval_string(strs[1]));
                } else {
                    xrequest.setAdditionalHeader(eval_string(strs[0]), "");
                }
            }
        }

                
        return getInstance();
        
    }

    /**
     * 设置post参数
     * 
     * @param datas
     */
    public HtmlUnitHelper setParameters(String datas) {
//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        List<org.apache.http.NameValuePair> formparams = URLEncodedUtils.parse(datas, Consts.UTF_8);
//        for (org.apache.http.NameValuePair nvp : formparams) {
//            params.add(new NameValuePair(eval_string(nvp.getName()), eval_string(nvp.getValue())));
//        }

        request.setRequestBody(datas);
        
        return this;
    }
    
    
    public static HtmlUnitHelper setParameters(WebRequest xrequest, String[] datas) {
         
        List<NameValuePair> formparams = //xrequest.getRequestParameters();
                new ArrayList<NameValuePair>();
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].indexOf("Name") == 0) {
                NameValuePair item = LabelValue.BuildFromString(eval_string(datas[i]), eval_string(datas[i + 1]))
                        .toNameValuePair();
                formparams.add(item);
                
            }
        }
        
        if (!(formparams.isEmpty())) {
            xrequest.setRequestParameters(formparams);
        }
        
        return getInstance();
        
        
    }

    public HtmlUnitHelper setParameters(String[] datas) {
        return setParameters(request,datas);
//        List<NameValuePair> formparams = request.getRequestParameters();
//                //new ArrayList<NameValuePair>();
//        for (int i = 0; i < datas.length; i++) {
//            if (datas[i].indexOf("Name") == 0) {
//                formparams.add(LabelValue.BuildFromString(eval_string(datas[i]), eval_string(datas[i + 1]))
//                        .toNameValuePair());
//            }
//        }
//
//        if (!(formparams.isEmpty())) {
//            request.setRequestParameters(formparams);
//        }
//        return this;
    }

    private Page htmlpage = null;

    public void handleHttp(Page htmlpage) {
        String httpResult = htmlpage.isHtmlPage() ? ((HtmlPage) htmlpage).asXml() : ((TextPage) htmlpage).getContent();

        // 下面处理 reg_string find 的内容
        Iterator<String> iters = RegItemCache.getInstance().getMapCache().keySet().iterator();
        while (iters.hasNext()) {
            String skey = iters.next();
            RegItem xitem = (RegItem) RegItemCache.getInstance().get(skey);
            switch (xitem.type) {
            case FIND: // reg_find
                save_int(findStringCount(httpResult, xitem.Value), xitem.ParaName);

                break;
            case SAVE: // reg_save_param
                // 这里要在reponse中找到结果
                save_string(xitem.locateString(httpResult), xitem.ParaName);
                break;
            default:
                break;

            }
        }

        // 执行完后，清除reg缓存
        RegItemCache.getInstance().clearAll();

    }
    
    private WebRequest request ;
    private void doBeforeExecute(){
        
    }
    private void doBeforeExecute(String urlAddress,String[] optionsAndData) throws MalformedURLException{
          createRequest(urlAddress,
                (optionsAndData[0].indexOf("Method=POST") == 0) ?
                HttpMethod.POST: HttpMethod.GET
                , optionsAndData);
        
    }
    private void doAfterExecute(){
        handleHttp(htmlpage);
    }
    public  Page excute(String urlAddress, String[] optionsAndData) throws FailingHttpStatusCodeException, IOException {
            doBeforeExecute(urlAddress, optionsAndData);
            htmlpage = execute();

        
        return htmlpage;
        
    }
    
    public Page execute() throws FailingHttpStatusCodeException, IOException {
        doBeforeExecute();
        logger.info(">>>[Next Request]:" + request.toString());
        htmlpage = getWebClient().getPage(request);
        doAfterExecute();
        
        return htmlpage;

    }
    public HtmlUnitHelper createRequest(String urlAddress, HttpMethod submitMethod)
            throws MalformedURLException {
        request = new WebRequest(new URL(urlAddress));
        if (submitMethod == HttpMethod.POST) {
            request.setHttpMethod(HttpMethod.POST);
        }
        return this;
    }
    public HtmlUnitHelper createRequest(String urlAddress, HttpMethod submitMethod, String[] options)
            throws MalformedURLException {
        this.createRequest(urlAddress, submitMethod);
        
        if (submitMethod == HttpMethod.POST || options[0].indexOf("Method=POST") == 0) {
            request.setHttpMethod(HttpMethod.POST);
        }
        setHeaders(options);

        return this;
    }
    
    public HtmlUnitHelper createRequest(String urlAddress, HttpMethod submitMethod, String[] options, String[] datas)
            throws MalformedURLException {
        this.createRequest(urlAddress, submitMethod, options);
        
        setParameters(datas);

        return this;
    }

    public static void httpGet(String urlAddress, String[] options) {

        try {
            getInstance().createRequest(urlAddress, HttpMethod.GET).setHeaders(options).execute();
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void httpPost(String urlAddress, String[] options, String[] data) {
        try {
            getInstance().createRequest(urlAddress, HttpMethod.POST).setHeaders(options).setParameters(data).execute();
        } catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void httpPost(String urlAddress, String[] options) {
        httpPost(urlAddress,options,new String[]{});
    }
    
    public static void custom_request(String stepName, String urlAddress, String[] optionsAndData) {
        String url = StringUtil.parsarKVStrValue(eval_string(urlAddress));
        
        if (optionsAndData[0].indexOf("Method=POST") == 0) {
            httpPost(url, optionsAndData);
         } else if (optionsAndData[0].indexOf("Method=GET") == 0) {
            httpGet(url, optionsAndData);
        }
        
    }
    
    public static void submit_data(String stepName, String urlAddress, String[] options, String[] datas)  {

        httpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)), options, datas);
    }
    
    public static String pe_string(String value){
        return parsarKVStrValue(eval_string(value));
        
    }
    
    public static void submit_form(String stepName, String[] options,String[] datas)  {
        
        HtmlPage xpage = (HtmlPage) getInstance().htmlpage;
        HtmlForm xform = xpage.getFormByName(stepName);
        
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].indexOf("Name") == 0 && datas[i+1].indexOf("Value") == 0 ) {
                xform.getInputByName(pe_string(datas[i])).setValueAttribute(pe_string(datas[i+1]));
            }
        }
        
        logger.info(">>>[Next Request]:" + xform.toString());
        try {
            xpage = ((HtmlSubmitInput) xform.getElementsByAttribute(
                    "input","type", "submit").get(0)).click();
            
            getInstance().handleHttp(xpage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        
    }

    
    public static void url(String stepName, String urlAddress, String[] options)  {
        httpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)), options);
    }

    public static void link(String stepName, String textName, String[] options){
        HtmlPage xpage = (HtmlPage) getInstance().htmlpage;
        
        HtmlAnchor xlink = xpage.getAnchorByText(textName);
        logger.info(">>>[Next Request]:" + xlink.toString());
        try {
            xpage = xlink.click();
            getInstance().handleHttp(xpage);
            
            logger.debug(xpage.asXml());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public static void resetHttpClient() {
        // TODO Auto-generated method stub
        
    }
    
    

}
