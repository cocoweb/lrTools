package com.foresee.test.loadrunner.lrapi4j;

import static com.foresee.test.loadrunner.lrapi4j.lr.eval_string;

import org.apache.log4j.Logger;

import com.foresee.test.loadrunner.base.I_web;
import com.foresee.test.loadrunner.helper.HttpHelper;
import com.foresee.test.loadrunner.helper.RegItem;
import com.foresee.test.loadrunner.helper.RegItemCache;
import com.foresee.test.loadrunner.helper.RegItem.ItemType;
import com.foresee.test.util.http.HttpException;
import com.foresee.test.util.lang.StringUtil;

public class web extends I_web {
    static Logger logger = Logger.getLogger(web.class.getName());

    public static int url(String stepName, String urlAddress, String[] options) throws HttpException {
        HttpHelper.httpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)), options);
        return 0;
    }

    public static int link(String stepName, String textName, String[] options) throws HttpException {
        // TODO 直接转发LoadRunner的方法

        return 0;
    }

    public static int submit_data(String stepName, String urlAddress, String[] options, String[] data)
            throws HttpException {

        HttpHelper.httpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)), options, data);

        return 0;

    }

    public static int custom_request(String stepName, String urlAddress, String[] optionsAndData) throws HttpException {
        if (optionsAndData[0].indexOf("Method=POST") == 0) {
            HttpHelper.httpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)), optionsAndData, new String[] {});
            // httprequest = new
            // HttpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
        } else if (optionsAndData[0].indexOf("Method=GET") == 0) {
            HttpHelper.httpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)), optionsAndData);
            // httprequest = new
            // HttpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)));

        }

        // try {
        // HttpUriRequest httprequest=null;
        // // HttpGet httpget = new
        // // HttpGet(StringUtil.parsarKVStrValue(urlAddress));
        // if (optionsAndData[0].indexOf("Method=POST")==0){
        // httprequest = new
        // HttpPost(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
        // }else if(optionsAndData[0].indexOf("Method=GET")==0){
        // httprequest = new
        // HttpGet(StringUtil.parsarKVStrValue(eval_string(urlAddress)));
        //
        // }
        // HttpHelper.setHeaders(httprequest, optionsAndData);
        //
        // CloseableHttpResponse response =
        // getHttpClient(true).execute(httprequest,responseHandler,
        // localContext);
        //
        // HttpHelper.showRESULT(response,cookieStore,
        // localContext,EntityUtils.toString(response.getEntity()));
        // // Do not feel like reading the response body
        // // Call abort on the request object
        // httprequest.abort();
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // // When HttpClient instance is no longer needed,
        // // shut down the connection manager to ensure
        // // immediate deallocation of all system resources
        // // httpClient.getConnectionManager().shutdown();
        // }

        return 0;

    }

    /**
     * 注册到RegitemCache web.reg_save_param ("ret_cxTID", new String []{
     * "NOTFOUND=ERROR", "LB=<tid>", "RB=</tid>" , LAST} );
     * 
     * @param paramName
     * @param args
     * @return
     */
    public static int reg_save_param(String paramName, String[] args) {
        RegItem xitem = new RegItem();
        xitem.ParaName = paramName;
        for (int i = 0; i < args.length; i++) {
            if (args[i].indexOf("LB") == 0) {
                xitem.LeftStr = StringUtil.parsarKVStrValue(args[i]);
            } else if (args[i].indexOf("RB") == 0) {
                xitem.RightStr = StringUtil.parsarKVStrValue(args[i]);
            }
        }

        RegItemCache.getInstance().put(paramName, xitem);

        // HttpRequestInterceptor xInterceptor = new HttpRequestInterceptor() {
        // public void process(
        // final HttpRequest request,
        // final HttpContext context) throws HttpException, IOException {
        // AtomicInteger count = (AtomicInteger) context.getAttribute("count");
        // request.addHeader("Count",
        // Integer.toString(count.getAndIncrement()));
        // }
        // };

        return 0;

    }

    public static int set_max_html_param_len(String paramValueLength) {

        return 0;
    }

    /**
     * web.reg_find("Text=/admin/pagehomegdgs.do", new String[]{
     * "SaveCount=LoginCount", LAST });
     * 
     * @param text
     * @param args
     * @return
     */
    public static int reg_find(String text, String[] args) {
        RegItem xitem = new RegItem();
        xitem.type = ItemType.FIND;
        xitem.Value = StringUtil.parsarKVStrValue(eval_string(text));
        for (int i = 0; i < args.length; i++) {
            if (args[i].indexOf("SaveCount") == 0) {
                xitem.ParaName = StringUtil.parsarKVStrValue(args[i]);
            }
        }

        RegItemCache.getInstance().put(xitem.ParaName, xitem);
        return 0;

    }

    // public static Map<String,String> headerMap= new HashMap<String,String>();

    public static int add_header(String headerName, String headerValue) {
        HttpHelper.addHeader(headerName, headerValue);
        return 0;
    }

    public static int add_cookie(String cookieValue) {
        HttpHelper.addCookie(cookieValue.split(";"));
        return 0;

    }

}
