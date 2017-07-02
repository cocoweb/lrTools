package com.foresee.test.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;


public class HttpRequester {
	private static String DEFAULT_ENCODING = "utf-8";

	public static String sendDataGet(String strUrl, List<String> proxies) {
		if (proxies != null) {
			Proxy proxy = getProxyFromHost(strUrl, proxies);
			return sendDataGet(strUrl, null, proxy);
		}
		return sendDataGet(strUrl, null, null);
	}

	public static String sendDataGet(String strUrl) {
		return sendDataGet(strUrl, null, null);
	}

	public static String sendDataGet(String strUrl, String codeStr) {
		return sendDataGet(strUrl, codeStr, null);
	}

	public static String sendDataGet(String strUrl, String codeStr, Proxy proxy) {
		String xmlStr = null;
		HttpURLConnection httpConn = null;
		try {
			if (codeStr == null) {
				codeStr = DEFAULT_ENCODING;
			}
			if (proxy != null) {
				httpConn = (HttpURLConnection) new URL(strUrl).openConnection(proxy);
			} else {
				httpConn = (HttpURLConnection) new URL(strUrl).openConnection();
			}
			httpConn.setReadTimeout(45000);
			httpConn.setConnectTimeout(45000);
			httpConn.setRequestProperty("Accpet-Encoding", "gzip");
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			xmlStr = null;
			// receive the response
			int rspCode = httpConn.getResponseCode();
			// process HTTP 200-OK
			if (rspCode == HttpsURLConnection.HTTP_OK) {
				xmlStr = readInputStreamToString(httpConn.getInputStream(), codeStr);
			} else {
				xmlStr = "rspCode " + rspCode + ":" + readInputStreamToString(httpConn.getErrorStream(), codeStr);
			}
		} catch (Exception e) {
			//Exceptions.checked(e);
		} finally {
			httpConn.disconnect();
		}
		return xmlStr;
	}

	public static String sendDataPost(String strUrl, String outData) {
		return sendDataPost(strUrl, outData, null);
	}

	public static String sendDataPost(String strUrl, String outData, List<String> proxies) {
		return sendDataPost(strUrl, outData, null, proxies);
	}

	public static String sendDataPost(String strUrl, String outData, String codeStr, List<String> proxies) {
		String xmlStr = null;
		HttpURLConnection httpConn = null;
		try {
			if (codeStr == null) {
				codeStr = DEFAULT_ENCODING;
			}
			if (proxies != null) {
				Proxy proxy = getProxyFromHost(strUrl, proxies);
				httpConn = (HttpURLConnection) new URL(strUrl).openConnection(proxy);
			} else {
				httpConn = (HttpURLConnection) new URL(strUrl).openConnection();
			}
			HttpURLConnection.setFollowRedirects(false);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setReadTimeout(45000);
			httpConn.setConnectTimeout(45000);
			httpConn.setRequestProperty("Accept-Charset", "utf-8");
			// httpConn.setRequestProperty("Content-Type", "html/xml");
			httpConn.setRequestProperty("User-Agent", "systempatch");
			if (!outData.contains("=")) {
				httpConn.setRequestProperty("Content-Type", "text/plan; charset:utf-8");
			}
			httpConn.setRequestProperty("Accpet-Encoding", "gzip");
			// send out data using POST method
			httpConn.setRequestMethod("POST");
			httpConn.connect();

			if (outData != null)
				httpConn.getOutputStream().write(outData.getBytes(codeStr));
			// receive the response
			int rspCode = httpConn.getResponseCode();
			// process HTTP 200-OK
			if (rspCode == HttpsURLConnection.HTTP_OK) {
				xmlStr = readInputStreamToString(httpConn.getInputStream(), codeStr);
			} else {
				xmlStr = "rspCode " + rspCode + ":" + readInputStreamToString(httpConn.getErrorStream(), codeStr);
			}
		} catch (Exception e) {
			//Exceptions.checked(e);
		} finally {
			httpConn.disconnect();
		}

		return xmlStr;

	}

	private static Proxy getProxyFromHost(String url, List<String> proxies) {
		Map<String, String> map = getProxiesPairs(proxies);
		for (Entry<String, String> entry : map.entrySet()) {
			String ip = entry.getValue();
			String host = entry.getKey();
			if (url.contains(host)) {
				String str[] = ip.split("\\.");
				byte[] b = new byte[str.length];
				for (int i = 0, len = str.length; i < len; i++) {
					b[i] = (byte) (Integer.parseInt(str[i], 10));
				}
				try {
					int port = getPort(url);
					if (port < 0) {
						port = 80;
					}
					return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(InetAddress.getByAddress(b), port));
				} catch (Exception e) {
					//Exceptions.checked(e);
				}
			}
		}
		return null;
	}

	private static int getPort(String urlStr) throws MalformedURLException {
		URL url = new URL(urlStr);
		return url.getPort();
	}

	public static Map<String, String> getProxiesPairs(List<String> proxies) {
		Map<String, String> map = new HashMap<String, String>();
		for (String proxy : proxies) {
			String[] pair = proxy.replaceAll("\\s+", "||").split("\\|\\|");
			if (pair.length > 1) {
				map.put(pair[1].trim(), pair[0].trim());
			}
		}
		return map;
	}

	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 缃戦〉鐨勪簩杩涘埗鏁版嵁
		outStream.close();
		inStream.close();
		return data;
	}

	private static String readInputStreamToString(InputStream inStream, String codeStr) throws Exception {
		byte[] buffer = readInputStream(inStream);
		String s = new String(buffer, codeStr);
		return s;
	}

	public static void main(String[] args) {

	}
}

