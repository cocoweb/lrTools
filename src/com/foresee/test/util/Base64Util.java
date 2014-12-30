package com.foresee.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public static InputStream readPlayList(String xmlData) throws Exception {
		// Log.d(TAG, "--------------" + xmlData + "");
		if (xmlData != null && !xmlData.trim().equals("")) {
			xmlData = xmlData.replaceAll("&amp;amp;", "&amp;");
			ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
					xmlData.getBytes("UTF-8"));
			return tInputStringStream;
		}
		return null;
	}

	public static String getStringFromInputStream(InputStream is)
			throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new BufferedInputStream(is))));
		StringBuffer sb = new StringBuffer();
		try {
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getFileBase64Str(InputStream path) {
		// InputStream in = null;
		byte[] data = null;
		try {
			// in = new FileInputStream(path);
			data = new byte[path.available()];
			path.read(data);
			path.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static byte[] getFromBase64Str(String path) throws IOException {
		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(path.getBytes("UTF-8"));
		BASE64Decoder base64De = new BASE64Decoder();
		// InputStream in = null;
		byte[] data = base64De.decodeBuffer(tInputStringStream);
		return data;
	}

	public static String toHexString(String s) {
		/*String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}*/
				
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			buf.append(s4);
		}
		String str = buf.toString();
		return str;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static void main(String[] args) throws IOException {
		String xmlData = "c://Message.xml";
		InputStream in = null;
		String inputXml = "";
		try {
			in = new FileInputStream(xmlData);
			inputXml = getStringFromInputStream(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String insertNote = "<returnCode>00000</returnCode><returnMessage>自动业务受理提交处理成功</returnMessage>";
		// String returnxml = VtdXmlOperateUtil.insertNoteToInputXml(inputXml,
		// insertNote, "/tiripPackage/returnType/body");
//		String returnxml = VtdXmlOperateUtil.updateNoteToInputXml(inputXml,
//				insertNote, "/tiripPackage/returnType/body");
		// String returnxml = VtdXmlOperateUtil.removeNoteToInputXml(inputXml,
		// insertNote,"/tiripPackage/returnType/body" );
		// String returnxml = (String)
		// VtdXmlOperateUtil.selectByXpath(inputXml,"/tiripPackage/head/Username");
//		System.out.println(returnxml);
	}
}
