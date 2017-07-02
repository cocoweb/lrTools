package com.foresee.test.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import com.foresee.test.util.CommonUtil;

public class URIUtil {

	public static URI createURI(String host, int port, String api,
			List<NameValuePair> nvps) {

		URI uri = null;
		try {
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http");
			builder.setHost(host);
			builder.setPort(port);
			builder.setPath(api);	
//			builder.setQuery(URLEncodedUtils.format(nvps, "UTF-8"));
//			builder.setQuery(URIUtil.encodeUrl(URLEncodedUtils.format(nvps, "UTF-8")));
			if(null != nvps)
				for (NameValuePair nvp:nvps){
					builder.setParameter(nvp.getName(), nvp.getValue());
				}
			
			uri = builder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return uri;
	}

	public static String encodeUrl(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String decodeUrl(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    public static String appendParams(String param, String append) {
    	if (CommonUtil.isEmpty(param)) {
    		param = "?" + append;
    	} else {
    		param = param + "&" + append;
    	}
    
    	return param;
    }

}
