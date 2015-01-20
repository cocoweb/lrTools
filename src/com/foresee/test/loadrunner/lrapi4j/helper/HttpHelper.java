package com.foresee.test.loadrunner.lrapi4j.helper;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_int;
import static com.foresee.test.loadrunner.lrapi4j.lr.save_string;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
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
import org.apache.log4j.Logger;

import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.lang.StringUtil;

public class HttpHelper {
    static Logger logger = Logger.getLogger(HttpHelper.class.getName());
    
    static HttpHost proxyhost = new HttpHost("localhost", 8888);

    static CloseableHttpClient httpClient = null;
    // Create a local instance of cookie store
    static CookieStore cookieStore = new BasicCookieStore();
    // Create local HTTP context
    static HttpClientContext localContext = HttpClientContext.create();

    public static Map<String, String> headerMap = new HashMap<String, String>();

    // Create a custom response handler
    static ResponseHandler<CloseableHttpResponse> responseHandler = new ResponseHandler<CloseableHttpResponse>() {
        @Override
        public CloseableHttpResponse handleResponse(final HttpResponse response) throws ClientProtocolException,
                IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                // HttpEntity entity = response.getEntity();

                String httpResult = EntityUtils.toString(response.getEntity());
                showRESULT(response, cookieStore, localContext, httpResult);

                // 下面处理 reg_string find 的内容
                Iterator<String> iters = RegItemCache.getInstance().getMapCache().keySet().iterator();
                while (iters.hasNext()) {
                    String skey = iters.next();
                    RegItem xitem = (RegItem) RegItemCache.getInstance().get(skey);
                    switch (xitem.type) {
                    case FIND: // reg_find
                        save_int(StringUtil.findStringCount(httpResult, xitem.Value), xitem.ParaName);

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

                return (CloseableHttpResponse) response;// entity != null ?
                                                        // EntityUtils.toString(entity)
                                                        // : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };

    // public static HttpClient getInstance() {
    // if (httpClient == null) {
    // httpClient = new DefaultHttpClient();
    //
    // // 以下为新增内容
    // httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {
    // public boolean isRedirected(HttpRequest request, HttpResponse response,
    // HttpContext context) {
    // boolean isRedirect=false;
    // try {
    // isRedirect = super.isRedirected(request, response, context);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // if (!isRedirect) {
    // int responseCode = response.getStatusLine().getStatusCode();
    // if (responseCode == 301 || responseCode == 302) {
    // return true;
    // }
    // }
    // return isRedirect;
    // }
    // });
    //
    // }
    // return httpClient;
    // }
    
    static boolean isProxy = false;
    public static void setProxy(String hostname,int port){
          proxyhost = new HttpHost(hostname, port);
          isProxy = true;
        
    }

    public static CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            //isProxy = false;
            if (isProxy) {
                //HttpHost proxy = new HttpHost("localhost", 8888);
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyhost);

                httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // Bind custom cookie store to the local context
            localContext.setCookieStore(cookieStore);

        }
        return httpClient;
    }

    public static void httpGet(String urlAddress, String[] options) {
        try {
            HttpGet httpget = new HttpGet(urlAddress);
            setHeaders(httpget, options);

            // 挂上context、handler
            CloseableHttpResponse response = getHttpClient().execute(httpget, responseHandler, localContext);

            httpget.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            // httpClient.getConnectionManager().shutdown();
        }

    }

    public static void httpPost(String urlAddress, String[] options, String[] data) {
        try {
            HttpPost httpost = new HttpPost(urlAddress);

            setHeaders(httpost, options);
            setParameters(httpost, data);

            CloseableHttpResponse response = getHttpClient().execute(httpost, responseHandler, localContext);

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

    }

    public static void addCookie(String[] cookies) {
        for (int i = 0; i < cookies.length; i++) {
            cookieStore.addCookie(new BasicClientCookie(eval_string(StringUtil.parsarKVStrKey(cookies[0])),
                    eval_string(StringUtil.parsarKVStrValue(cookies[1]))));

        }

    }

    public static void addHeader(String headerName, String headerValue) {
        headerMap.put(headerName, headerValue);

    }

    /**
     * config http headers
     * 
     * @param httprequest
     * @param options
     */
    public static <T extends HttpRequest> void setHeaders(T httprequest, String[] options) {

        for (int i = 0; i < options.length; i++) {
            if (options[i].indexOf(web.EXTRARES) == 0 || options[i].indexOf(web.LAST) == 0) {
                break;
            } else if (options[i].indexOf("Body") == 0) { // parameters
                setParameters((HttpEntityEnclosingRequest) httprequest,
                        StringUtil.parsarKVStrValue(eval_string(options[i])));

            } else { // header
                String[] strs = StringUtil.parsarKVStr(options[i]);
                if (strs.length > 1) {
                    ((HttpRequest) httprequest).setHeader(eval_string(strs[0]), eval_string(strs[1]));
                } else {
                    ((HttpRequest) httprequest).setHeader(eval_string(strs[0]), "");
                }
            }
        }

        // 添加add_header的内容，并清理
        Iterator<String> xiters = headerMap.keySet().iterator();
        while (xiters.hasNext()) {
            String skey = xiters.next();
            ((HttpMessage) httprequest).setHeader(skey, eval_string(headerMap.get(skey)));
        }
        headerMap.clear();

    }

    public static <T extends HttpEntityEnclosingRequest> void setParameters(T httpost, String[] datas) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (int i = 0; i < datas.length; i++) {
            if (datas[i].indexOf("Name") == 0) {
                String sName = StringUtil.parsarKVStrValue(datas[i]);
                String sValue = StringUtil.parsarKVStrValue(datas[i + 1]);

                formparams.add(new BasicNameValuePair(eval_string(sName), eval_string(sValue)));
            }

        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        ((HttpEntityEnclosingRequest) httpost).setEntity(entity);
    }

    /**
     * 设置post参数
     * 
     * @param datas
     */
    public static <T extends HttpEntityEnclosingRequest> void setParameters(T httppost, String datas) {
        List<NameValuePair> formparams = URLEncodedUtils.parse(datas, Consts.UTF_8);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        ((HttpEntityEnclosingRequest) httppost).setEntity(entity);

    }

    public static void showHeader(HttpResponse response) {
        HeaderIterator iter = response.headerIterator();

        while (iter.hasNext()) {
            logger.debug(iter.next().toString());
        }

    }

    public static void showEntity(HttpResponse response) throws ParseException, IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            logger.debug("Response content length: " + entity.getContentLength());
            if (len != -1 && len < 2048) {
                logger.debug(EntityUtils.toString(entity));
            } else {
                InputStream inSm = entity.getContent();
                Scanner inScn = new Scanner(inSm);
                while (inScn.hasNextLine()) {
                    logger.debug(inScn.nextLine());
                }
            }
        }

    }

    public static void showContext(HttpClientContext localContext) {
        CookieOrigin cookieOrigin = localContext.getCookieOrigin();
        // .getAttribute(ClientContext.COOKIE_ORIGIN);
        logger.debug("Cookie origin: " + cookieOrigin);
        // CookieSpec cookieSpec = (CookieSpec) localContext.getAttribute(
        // ClientContext.COOKIE_SPEC);
        // logger.debug("Cookie spec used: " + cookieSpec);

        CookieStore cookieStore = localContext.getCookieStore();
        // .getAttribute(ClientContext.COOKIE_STORE);
        logger.debug("Cookie Store used: " + cookieStore);

    }

    public static void showCookies(CookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            logger.debug("Local cookie: " + cookies.get(i));
        }
    }

    public static void showRESULT(HttpResponse response, CookieStore xcookieStore, HttpClientContext xlocalContext,
            String httpResult) throws IllegalStateException, IOException {

        if (!(logger.isDebugEnabled()))
            return;

        HttpEntity entity = response.getEntity();
        logger.debug("----------------------------------------");
        logger.debug(response.getStatusLine());
        if (entity != null) {
            logger.debug("Response content length: " + entity.getContentLength());
        }
        logger.debug("----------------------------------------");
        showHeader(response);
        // showContent(httpResult);
        logger.debug(httpResult);

        showContext(xlocalContext);
        showCookies(xcookieStore);

    }
}
