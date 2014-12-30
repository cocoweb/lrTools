/**
 * 
 */
package com.foresee.test.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/**
 * 将一个字符串按照Gzip、Zip方式压缩和解压缩
 * 
 * @author leijp
 * 
 */
public class ZipUtil {

	private static Logger logger = Logger.getLogger(ZipUtil.class);

	/**
	 * 16进制的字符串表示转成字节数组
	 * 
	 * @param hexString
	 *            16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		if (StringUtil.isEmpty(hexString))
			throw new IllegalArgumentException(
					"this hexString must not be empty");

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	/**
	 * 字节数组转成16进制表示格式的字符串
	 * 
	 * @param byteArray
	 *            需要转换的字节数组
	 * @return 16进制表示格式的字符串
	 **/
	public static String toHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1)
			throw new IllegalArgumentException(
					"this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)// 0~F前面不零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toLowerCase();
	}

	public static String gzipCompress(String str) {
		if (str == null)
			return null;
		String compressedStr = null;
		;
		try {
			compressedStr = gzipCompress(str.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressedStr;
	}

	/**
	 * Gzip压缩字符串
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String gzipCompress(byte[] content) throws IOException {
		logger.info("字符串压缩前的长度为：" + content.length);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(content);
		gzip.close();
		String rst = new sun.misc.BASE64Encoder().encodeBuffer(out
				.toByteArray());
		logger.info("字符串压缩后的长度为：" + rst.length());
		return rst;
	}

	/**
	 * Gzip解压缩字符串
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String gzipUncompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(
				new sun.misc.BASE64Decoder().decodeBuffer(str));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString("ISO-8859-1");
	}

	/**
	 * zip压缩字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String zipCompress(String str) {
		if (str == null)
			return null;
		String compressed = null;
		try {
			compressed = zipCompress(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return compressed;
	}

	/**
	 * zip压缩字节数组
	 * 
	 * @param content
	 * @return
	 */
	/**
	 * zip压缩字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String zipCompress(byte[] data) {
		if (data == null)
			return null;

		byte[] compressed;
		String compressedStr = null;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(data);
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder()
					.encodeBuffer(compressed);

		} catch (IOException e) {
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * zip解压缩字符串
	 * 
	 * @param compressed
	 * @return
	 */
	public static String zipUncompress(String compressedStr) {
		if (compressedStr == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	// 测试方法
	public static void main(String[] args) throws IOException {
		String bizXml = "trtytuty ";
		System.out.println("字符串压缩前的长度为：" + bizXml.getBytes().length);
		String s = ZipUtil.gzipCompress(bizXml);
		System.out.println(s);
		System.out.println("字符串压缩后的长度为：" + s.length());
		String rst = ZipUtil.gzipUncompress(s);
		System.out.println("字符串还原：" + rst);

	}

}
