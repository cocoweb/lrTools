package com.foresee.test.loadrunner.lrapi4j.helper;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.lang.StringUtil;

public class webhelper {
    static Logger logger = Logger.getLogger ( web.class.getName () );
    
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

    public static void showRESULT(HttpResponse response,CookieStore xcookieStore,
            HttpClientContext xlocalContext, String httpResult) 
                    throws IllegalStateException, IOException{
        
        if (!(logger.isDebugEnabled())) return;
        
        HttpEntity entity = response.getEntity();
        logger.debug("----------------------------------------");
        logger.debug(response.getStatusLine());
        if (entity != null) {
            logger.debug("Response content length: " + entity.getContentLength());
        }
        logger.debug("----------------------------------------");
        showHeader(response);
        //showContent(httpResult);
        logger.debug(httpResult);
        
        showContext(xlocalContext);
        showCookies(xcookieStore);
    
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

    /**
     * config http headers
     * @param httprequest
     * @param options
     */
    public static <T extends HttpRequest> void setHeaders(T httprequest, String[] options) {
    
        for (int i = 0; i < options.length; i++) {
            if (options[i].indexOf(web.EXTRARES) == 0 || options[i].indexOf(web.LAST) == 0) {
                break;
            } else if(options[i].indexOf("Body") == 0){   //parameters
                setParameters((HttpEntityEnclosingRequest)httprequest, StringUtil.parsarKVStrValue(eval_string(options[i])));
                
            }else{  //header
                String[] strs = StringUtil.parsarKVStr(options[i]);
                if (strs.length > 1) {
                    ((HttpRequest) httprequest).setHeader(eval_string(strs[0]), eval_string(strs[1]));
                } else {
                    ((HttpRequest) httprequest).setHeader(eval_string(strs[0]), "");
                }
            }
        }
        
        //添加add_header的内容，并清理
        Iterator<String> xiters = web.headerMap.keySet().iterator();
        while(xiters.hasNext()){
            String skey = xiters.next();
            ((HttpMessage) httprequest).setHeader(skey, eval_string(web.headerMap.get(skey)));
        }
        web.headerMap.clear();
    
    }

    public static <T extends HttpEntityEnclosingRequest> void setParameters(T httpost, String[] datas) 
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
        ((HttpEntityEnclosingRequest) httpost).setEntity(entity);
    }

    /**
     * 设置post参数
     * @param datas
     */
    public static <T extends HttpEntityEnclosingRequest> void setParameters(T httppost,String  datas){
        List<NameValuePair> formparams = URLEncodedUtils.parse(
                datas,
                Consts.UTF_8);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        ((HttpEntityEnclosingRequest) httppost).setEntity(entity);
        
    }

}
