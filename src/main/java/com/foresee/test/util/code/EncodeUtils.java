package com.foresee.test.util.code;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 */
public class EncodeUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * Hex编码.
     */
//    public static String hexEncode(byte[] input) {
//        return Hex.encodeHexString(input);
//    }

    /**
     * Hex解码.
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
     */
//    public static String base64UrlSafeEncode(byte[] input) {
//        return Base64.encodeBase64URLSafeString(input);
//    }
//
//    /**
//     * Base64解码.
//     */
//    public static byte[] base64Decode(String input) {
//        return Base64.decodeBase64(input);
//    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        return urlEncode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 编码.
     */
    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        return urlDecode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码.
     */
    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * Html 转码.
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * Html 解码.
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 解码.
     */
    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
    
    /**
     * 将字符串转义以方便html输出
     */
    public static String escapeHtml(String s) {

        return org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
    }

    public static String unescapeHtml(String s) {
        return org.apache.commons.lang.StringEscapeUtils.unescapeHtml(s);
    }

    public static String ConvertCharset(String inStr,String srcCharset){
        return ConvertCharset(inStr,srcCharset,"UTF-8");
    }

    public static String ConvertCharset(String inStr){
        return ConvertCharset(inStr,"GBK");
    }

    public static String ConvertCharset(String inStr,String srcCharset,String targetCharset){
        String xstr =null;
        try {
            
            xstr = new String(inStr.getBytes(srcCharset),targetCharset);
        } catch (UnsupportedEncodingException e) {
            
            e.printStackTrace();
        }
        return xstr;
    }

    /**   
     * unicode 转换成 中文   
     */   
    
    public static String decodeUnicode(String theString) {    
    
    	char aChar;    
    	int len = theString.length();  
    	
    	StringBuffer outBuffer = new StringBuffer(len);    
    	
    	for (int x = 0; x < len;) {    
    
    		aChar = theString.charAt(x++);    
    		if (aChar == '\\') {    
    
    			aChar = theString.charAt(x++);    
    
    			if (aChar == 'u') {    
    				// Read the xxxx  
    				int value = 0;  
    				
    				for (int i = 0; i < 4; i++) {  
    					aChar = theString.charAt(x++);   
    					switch (aChar) {    
    						case '0':  
    						case '1':   
    						case '2': 
    						case '3':  
    						case '4': 
    						case '5':
    						case '6':    
    			          	case '7':    
    			          	case '8':    
    			          	case '9':    
    			          		value = (value << 4) + aChar - '0';    
    			          		break;    
    			          	case 'a':    
    			          	case 'b':    
    			          	case 'c':    
    			          	case 'd':    
    			          	case 'e':    
    			          	case 'f':    
    			          		value = (value << 4) + 10 + aChar - 'a';    
    			          		break;    
    			          	case 'A':    
    			          	case 'B':    
    			          	case 'C':    
    			          	case 'D':    
    			          	case 'E':    
    			          	case 'F':    
    			          		value = (value << 4) + 10 + aChar - 'A';    
    			          		break;    
    			          	default:    
    			          		throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");    
    					}    
    
    				}    
    				
    				outBuffer.append((char) value);    
    				
    			} else {    
    				if (aChar == 't')    
    					aChar = '\t';    
    				else if (aChar == 'r')    
    					aChar = '\r';    
    				else if (aChar == 'n')    
    					aChar = '\n';    
    				else if (aChar == 'f')    
    					aChar = '\f';    
    
    				outBuffer.append(aChar);    
    
    			}    
    
            } else   
    
            	outBuffer.append(aChar);    
    
           	}    
    
           	return outBuffer.toString();    
      }

    /**
     * unicode 转换成 utf-8
     * 
     * @param str
     * @return
     */
    public static String unicodeToUtf8(String str) {
    	if(str==null||str.length()<1){
    		return str;
    	}
    	char aChar;
    	int len = str.length();
    	StringBuffer outBuffer = new StringBuffer(len);
    	for (int x = 0; x < len;) {
    		aChar = str.charAt(x++);
    		if (aChar == '\\') {
    			aChar = str.charAt(x++);
    			if (aChar == 'u') {
    				int value = 0;
    				for (int i = 0; i < 4; i++) {
    					aChar = str.charAt(x++);
    					switch (aChar) {
    					case '0':
    					case '1':
    					case '2':
    					case '3':
    					case '4':
    					case '5':
    					case '6':
    					case '7':
    					case '8':
    					case '9':
    						value = (value << 4) + aChar - '0';
    						break;
    					case 'a':
    					case 'b':
    					case 'c':
    					case 'd':
    					case 'e':
    					case 'f':
    						value = (value << 4) + 10 + aChar - 'a';
    						break;
    					case 'A':
    					case 'B':
    					case 'C':
    					case 'D':
    					case 'E':
    					case 'F':
    						value = (value << 4) + 10 + aChar - 'A';
    						break;
    					default:
    						throw new IllegalArgumentException(
    								"Malformed   \\uxxxx   encoding.");
    					}
    				}
    				outBuffer.append((char) value);
    			} else {
    				if (aChar == 't')
    					aChar = '\t';
    				else if (aChar == 'r')
    					aChar = '\r';
    				else if (aChar == 'n')
    					aChar = '\n';
    				else if (aChar == 'f')
    					aChar = '\f';
    				outBuffer.append(aChar);
    			}
    		} else
    			outBuffer.append(aChar);
    	}
    	return outBuffer.toString();
    }

    public static String GBKEncode(String moviename) {
    	String str = moviename;
    	String strGBK = null;
    	try {
    		strGBK = URLEncoder.encode(str, "GBK");
    
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
    	return strGBK;
    }

    public static String GBKEncodeReplacePercent(String moviename) {
    	String str = moviename;
    	String strGBK = null;
    	try {
    		strGBK = URLEncoder.encode(str, "GBK");
    		strGBK = strGBK.replaceAll("%", "__");
    
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
    	return strGBK;
    }

}
