package com.foresee.test.loadrunner.lrapi4j;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_int;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_string;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.foresee.test.http.HttpException;
import com.foresee.test.loadrunner.lrapi.I_web;
import com.foresee.test.loadrunner.lrapi4j.RegItem.ItemType;
import com.foresee.test.util.lang.StringUtil;

public class web extends I_web {

    public web() {
         
    }

    // public static HttpRequest createRequest(String[] optionsAndData){
    //
    // }

    /**
     * config http headers
     * @param httpget
     * @param options
     */
    public static <T extends HttpMessage> void setHeaders(T httpget, String[] options) {

        for (int i = 0; i < options.length; i++) {
            if (options[i].indexOf(EXTRARES) == 0 || options[i].indexOf(LAST) == 0) {
                break;
            } else if(options[i].indexOf("Body") == 0){   //parameters
                setParameters(httpget, StringUtil.parsarKVStrValue(eval_string(options[i])));
                
            }else{  //header
                String[] strs = StringUtil.parsarKVStr(options[i]);
                if (strs.length > 1) {
                    ((HttpMessage) httpget).setHeader(eval_string(strs[0]), eval_string(strs[1]));
                } else {
                    ((HttpMessage) httpget).setHeader(eval_string(strs[0]), "");
                }
            }
        }
        
        //添加add_header的内容，并清理
        Iterator<String> xiters = headerMap.keySet().iterator();
        while(xiters.hasNext()){
            String skey = xiters.next();
            ((HttpMessage) httpget).setHeader(skey, eval_string(headerMap.get(skey)));
        }
        headerMap.clear();

    }
    
    /**
     * 设置post参数
     * @param datas
     */
    public static <T extends HttpMessage> void setParameters(T httppost,String  datas){
        List<NameValuePair> formparams = URLEncodedUtils.parse(
                datas,
                Consts.UTF_8);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        ((HttpPost) httppost).setEntity(entity);
        
    }
    
    public static <T extends HttpMessage> void setParameters(T httpost, String[] datas) 
    {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].indexOf("Name")==0){
                String sName = StringUtil.parsarKVStrValue(datas[i]);
                String sValue= StringUtil.parsarKVStrValue(datas[i+1]);
                
                formparams.add(new BasicNameValuePair(eval_string(sName), eval_string(sValue)));
            }
            
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        ((HttpPost) httpost).setEntity(entity);
    }
    
//    public static void setCookies(){
//        
//    }


    public static void showHeader(HttpResponse response) {
        HeaderIterator iter = response.headerIterator();

        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }

    }

    public static void showEntity(HttpResponse response) throws ParseException, IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            System.out.println("Response content length: " + entity.getContentLength());
            if (len != -1 && len < 2048) {
                System.out.println(EntityUtils.toString(entity));
            } else {
                InputStream inSm = entity.getContent();
                Scanner inScn = new Scanner(inSm);
                while (inScn.hasNextLine()) {
                    System.out.println(inScn.nextLine());
                }
            }
        }

    }

    public static void showContext(HttpClientContext localContext) {
        CookieOrigin cookieOrigin = localContext.getCookieOrigin();
        // .getAttribute(ClientContext.COOKIE_ORIGIN);
        System.out.println("Cookie origin: " + cookieOrigin);
        // CookieSpec cookieSpec = (CookieSpec) localContext.getAttribute(
        // ClientContext.COOKIE_SPEC);
        // System.out.println("Cookie spec used: " + cookieSpec);

        CookieStore cookieStore = localContext.getCookieStore();
        // .getAttribute(ClientContext.COOKIE_STORE);
        System.out.println("Cookie Store used: " + cookieStore);

    }

    public static void showCookies(CookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            System.out.println("Local cookie: " + cookies.get(i));
        }
    }

    
    public static void showRESULT(HttpResponse response, String httpResult) throws IllegalStateException, IOException{
        HttpEntity entity = response.getEntity();
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
        }
        System.out.println("----------------------------------------");
        showHeader(response);
        //showContent(httpResult);
        System.out.println(httpResult);
        showContext(localContext);
        showCookies(cookieStore);

    }

    static CloseableHttpClient httpClient = null;
    // Create a local instance of cookie store
    static CookieStore cookieStore = new BasicCookieStore();
    // Create local HTTP context
    static HttpClientContext localContext = HttpClientContext.create();

    static CloseableHttpClient getHttpClient(boolean isProxy) {
        if (null == httpClient) {
            isProxy = true;
            if (isProxy) {
                HttpHost proxy = new HttpHost("localhost", 8888);
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
                httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // Bind custom cookie store to the local context
            localContext.setCookieStore(cookieStore);

        }
        return httpClient;
    }

    public static int url(String stepName, String urlAddress, String[] options) throws HttpException {
        // cookieStore.addCookie(cookie);

        try {
            HttpGet httpget = new HttpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
            setHeaders(httpget, options);
            
            //挂上context、handler
            CloseableHttpResponse response = getHttpClient(true).execute(httpget,responseHandler, localContext);  

            httpget.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            // httpClient.getConnectionManager().shutdown();
        }

        return 0;
    }

    public static int link(String stepName, String textName, String[] options) throws HttpException {
        // TODO 直接转发LoadRunner的方法

        return 0;
    }
    public static int submit_data(String stepName, String urlAddress,
            String[] options, String[] data) throws HttpException{
        try{
            HttpPost httpost = new HttpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
    
            setHeaders(httpost, options);
            setParameters(httpost,data);
    
            CloseableHttpResponse response = getHttpClient(true).execute(httpost,responseHandler, localContext); 
            
            // Do not feel like reading the response body
            // Call abort on the request object
            httpost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            // httpClient.getConnectionManager().shutdown();
        }
        return 0;
    
    }


    public static int custom_request(String stepName, String urlAddress, String[] optionsAndData) throws HttpException  {
        try {
            HttpPost httpost=null;
            // HttpGet httpget = new
            // HttpGet(StringUtil.parsarKVStrValue(urlAddress));
            if (optionsAndData[0].indexOf("Method=POST")==0){
                 httpost = new HttpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
            }
            setHeaders(httpost, optionsAndData);

            CloseableHttpResponse response = getHttpClient(true).execute(httpost,responseHandler, localContext); 
            
            showRESULT(response,EntityUtils.toString(response.getEntity()));
            // Do not feel like reading the response body
            // Call abort on the request object
            httpost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            // httpClient.getConnectionManager().shutdown();
        }

        return 0;

    }
    
    void findstr(String respHtml){
        //<p class="mid_tit">我的爱与你分享</p><p></p>
        Pattern titleP = Pattern.compile("<p\\s+class=\"mid_tit\">(.+?)</p>");
        //Pattern titleP = Pattern.compile("mid_tit");
        //Pattern titleP = Pattern.compile("saveSongName");
        Matcher matchedTitle = titleP.matcher(respHtml);
        //Boolean foundTitle = matchedTitle.matches();
        Boolean foundTitle = matchedTitle.find();
        if(foundTitle){
            String title = matchedTitle.group(1);
            System.out.println(title);
        }
        else
        {
           // Toast.makeText(getApplicationContext(), "找不到Songtaste歌曲的标题!", Toast.LENGTH_SHORT).show();
        }
    }
     // Create a custom response handler
    static ResponseHandler<CloseableHttpResponse> responseHandler = new ResponseHandler<CloseableHttpResponse>() {
        @Override
        public CloseableHttpResponse handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                //HttpEntity entity = response.getEntity();
                
                String httpResult = EntityUtils.toString(response.getEntity());
                showRESULT(response,httpResult);
                
                //下面处理 reg_string find 的内容
                Iterator<String> iters = RegItemCache.getInstance().getMapCache().keySet().iterator();
                while(iters.hasNext()){
                    String skey = iters.next();
                    RegItem xitem = (RegItem) RegItemCache.getInstance().get(skey);
                    switch(xitem.type){
                    case FIND:   //reg_find
                        save_int( StringUtil.findStringCount(httpResult, xitem.Value),
                                xitem.ParaName);
                        
                        break;
                    case SAVE:  //reg_save_param
                        // 这里要在reponse中找到结果
                        save_string(xitem.locateString(httpResult),
                                xitem.ParaName);
                        break;
                    default:
                        break;
                    
                    }
                }
                
                
                //执行完后，清除reg缓存
                RegItemCache.getInstance().clearAll();
                
                return (CloseableHttpResponse) response;//entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };
   
    /**
     * 注册到RegitemCache
     *              web.reg_save_param ("ret_cxTID", new String []{ 
                            "NOTFOUND=ERROR", 
                            "LB=<tid>", 
                            "RB=</tid>" , 
                            LAST} ); 

     * 
     * 
     * @param paramName
     * @param args
     * @return
     */
    public static int reg_save_param(String paramName, String[] args) {
        RegItem xitem = new RegItem();
        xitem.ParaName = paramName;
        for (int i = 0; i < args.length; i++) {
            if(args[i].indexOf("LB")==0){
                xitem.LeftStr = StringUtil.parsarKVStrValue(args[i]);
            }else if (args[i].indexOf("RB")==0){
                xitem.RightStr = StringUtil.parsarKVStrValue(args[i]);
            }
        }
        
        RegItemCache.getInstance().put(paramName, xitem);
        
        
//        HttpRequestInterceptor xInterceptor = new HttpRequestInterceptor() {
//            public void process(
//            final HttpRequest request,
//            final HttpContext context) throws HttpException, IOException {
//            AtomicInteger count = (AtomicInteger) context.getAttribute("count");
//            request.addHeader("Count", Integer.toString(count.getAndIncrement()));
//            }
//            };
            
        
        //httpClient.
        
        return 0;

    }
    
    /**
     * web.reg_find("Text=/admin/pagehomegdgs.do", 
                            new String[]{ 
                                "SaveCount=LoginCount", 
                                LAST 
                            }); 
     * @param text
     * @param args
     * @return
     */
    public static int reg_find(String text, String[] args) {
        RegItem xitem = new RegItem();
        xitem.type = ItemType.FIND;
        xitem.Value = StringUtil.parsarKVStrValue(eval_string(text));
        for (int i = 0; i < args.length; i++) {
            if(args[i].indexOf("SaveCount")==0){
                xitem.ParaName = StringUtil.parsarKVStrValue(args[i]);
            }
        }
        
        RegItemCache.getInstance().put(xitem.ParaName, xitem);
        return 0;
            
    }
    
    static Map<String,String> headerMap= new HashMap<String,String>();
    
    public static int add_header(String headerName, String headerValue) {
        headerMap.put(headerName, headerValue);
        return 0;
    }

    public static int add_cookie(String cookieValue) {
        String[] ss = cookieValue.split(";");
        for(int i =0;i<ss.length;i++){
            cookieStore.addCookie(new BasicClientCookie(
                    eval_string(StringUtil.parsarKVStrKey(ss[0])), 
                    eval_string(StringUtil.parsarKVStrValue(ss[1]))));
            
        }
        
        return 0;
        
    }



}
