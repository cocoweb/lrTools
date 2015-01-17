package com.foresee.test.loadrunner.lrapi4j;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_int;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_string;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.foresee.test.loadrunner.lrapi.I_web;
import com.foresee.test.loadrunner.lrapi4j.helper.RegItem;
import com.foresee.test.loadrunner.lrapi4j.helper.RegItem.ItemType;
import com.foresee.test.loadrunner.lrapi4j.helper.RegItemCache;
import com.foresee.test.loadrunner.lrapi4j.helper.webhelper;
import com.foresee.test.util.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

public class web extends I_web {
    static Logger logger = Logger.getLogger ( web.class.getName () );

    public web() {
         
    }


    static CloseableHttpClient httpClient = null;
    // Create a local instance of cookie store
    static CookieStore cookieStore = new BasicCookieStore();
    // Create local HTTP context
    static HttpClientContext localContext = HttpClientContext.create();

    static CloseableHttpClient getHttpClient(boolean isProxy) {
        if (null == httpClient) {
            //isProxy = true;
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
            webhelper.setHeaders(httpget, options);
            
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
    
            webhelper.setHeaders(httpost, options);
            webhelper.setParameters(httpost,data);
    
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
            HttpUriRequest httprequest=null;
            // HttpGet httpget = new
            // HttpGet(StringUtil.parsarKVStrValue(urlAddress));
            if (optionsAndData[0].indexOf("Method=POST")==0){
                 httprequest = new HttpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
            }else if(optionsAndData[0].indexOf("Method=GET")==0){
                httprequest = new HttpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
                
            }
            webhelper.setHeaders(httprequest, optionsAndData);

            CloseableHttpResponse response = getHttpClient(true).execute(httprequest,responseHandler, localContext); 
            
            webhelper.showRESULT(response,cookieStore, localContext,EntityUtils.toString(response.getEntity()));
            // Do not feel like reading the response body
            // Call abort on the request object
            httprequest.abort();
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
    
     // Create a custom response handler
    static ResponseHandler<CloseableHttpResponse> responseHandler = new ResponseHandler<CloseableHttpResponse>() {
        @Override
        public CloseableHttpResponse handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                //HttpEntity entity = response.getEntity();
                
                String httpResult = EntityUtils.toString(response.getEntity());
                webhelper.showRESULT(response,cookieStore, localContext, httpResult);
                
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
    public static int set_max_html_param_len(String paramValueLength) {
        
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
    
    public static Map<String,String> headerMap= new HashMap<String,String>();
    
    public static int add_header(String headerName, String headerValue) {
        headerMap.put(headerName, headerValue);
        return 0;
    }

    public static int add_cookie(String cookieValue) {
        String[] ss = cookieValue.split(";");
        for(int i =0;i<ss.length;i++){
            cookieStore.addCookie(new BasicClientCookie(
                    eval_string(StringUtil.parsarKVStrKey(ss[0])), 
                    eval_string(StringUtil.parsarKVStrValue(ss[1]))
                    ));
            
        }
        
        return 0;
        
    }



}
