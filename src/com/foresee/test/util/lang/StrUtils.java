package com.foresee.test.util.lang;

import java.security.MessageDigest;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * String 操作类，org.apache.commons.lang.StringUtils的补充
 * 
 * @author Javen
 * 
 */
public class StrUtils {

    private static Logger log = LoggerFactory.getLogger(StrUtils.class);

    public static void main(String[] args) {

        System.out.print(underscores("carryingCapacity"));
    }

    /**
     * 转换realName -> real_name
     * 
     * @param str
     * @return
     */
    public static String underscores(String str) {
        // Convert to underscores
        char[] ca = str.toCharArray();
        StringBuilder build = new StringBuilder("" + ca[0]);
        boolean lower = true;
        for (int i = 1; i < ca.length; i++) {
            char c = ca[i];
            if (Character.isUpperCase(c) && lower) {
                build.append("_");
                lower = false;
            } else if (!Character.isUpperCase(c)) {
                lower = true;
            }

            build.append(c);
        }

        str = build.toString().toLowerCase();

        return str;

    }

    /**
     * 转换realName -> real*name
     * 
     * @param str
     * @param sp
     *            分割符号
     * @return
     */
    public static String underscores(String str, String sp) {
        // Convert to underscores
        char[] ca = str.toCharArray();
        StringBuilder build = new StringBuilder("" + ca[0]);
        boolean lower = true;
        for (int i = 1; i < ca.length; i++) {
            char c = ca[i];
            if (Character.isUpperCase(c) && lower) {
                build.append(sp);
                lower = false;
            } else if (!Character.isUpperCase(c)) {
                lower = true;
            }

            build.append(c);
        }

        str = build.toString().toLowerCase();

        return str;

    }

    /**
     * 连接字符串
     * 
     * @param parts
     * @return
     */
    public static String string(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }

    /**
     * 连接字符串
     * 
     * @param parts
     * @return
     */
    public static StringBuilder stringBuilder(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
        }
        return sb;
    }

    /**
     * 字符串分割 ，又见org.apache.commons.lang.StringUtils.split
     * 
     * @param strSrc
     * @param strSep
     * @return
     */
    public static String[] splitStr(String strSrc, String strSep) {
        if (strSrc == null || strSep == null) {
            return null;
        }
        int intPos = 0;
        int intBox = 0;
        Vector<String> vecResult = new Vector<String>();
        String strTmp = null;
        while ((intPos = strSrc.indexOf(strSep, intBox)) != -1) {
            if (intBox == intPos) {
                strTmp = "";
            } else {
                strTmp = strSrc.substring(intBox, intPos);
            }
            vecResult.addElement(strTmp);
            // intBox = intPos + 1;
            intBox = intPos + strSep.length();
        }
        if (intBox < strSrc.length()) {
            vecResult.addElement(strSrc.substring(intBox));
        }
        return (String[]) vecResult.toArray(new String[0]);
    }

    /**
     * 替换字符串
     * 
     * @param origin
     *            原始字符串
     * @param src
     *            需要更换的字符串
     * @param dest
     *            更换后的字符串
     * @return
     */
    public static String replaceString(String origin, String src, String dest) {
        if (origin == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(origin.length());

        int srcLength = src.length();
        @SuppressWarnings("unused")
        int destLength = dest.length();

        int preOffset = 0;
        int offset = 0;
        while ((offset = origin.indexOf(src, preOffset)) != -1) {
            sb.append(origin.substring(preOffset, offset));
            sb.append(dest);
            preOffset = offset + srcLength;
        }
        sb.append(origin.substring(preOffset, origin.length()));

        return sb.toString();
    }

    /**
     * 生成随机数
     * 
     * @param num
     *            位数
     * @return
     */
    public static String getRandom(int num) {
        Random r = new Random();
        String result = "";
        while (result.length() < num) {
            int temp1 = r.nextInt(8);
            if (result.indexOf(temp1 + "") == -1) {
                result = result + temp1;
            }
        }
        return result;
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     * 
     * @param password
     *            Password or other credentials to use in authenticating this
     *            username
     * @param algorithm
     *            Algorithm used to do the digest
     * 
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            log.error("Exception: " + e);

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(anEncodedPassword & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords as
     * cookies.
     * 
     * This is weak encoding in that anyone can use the decodeString routine to
     * reverse the encoding.
     * 
     * @param str
     * @return String
     */
    public static String encodeString(String str) {
        Base64 encoder = new Base64();
        return String.valueOf(encoder.encode(str.getBytes())).trim();
    }

    /**
     * Decode a string using Base64 encoding.
     * 
     * @param str
     * @return String
     */
    public static String decodeString(String str) {
        Base64 dec = new Base64();
        try {
            return String.valueOf(dec.decode(str));
        } catch (DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
}
