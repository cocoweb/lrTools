package com.foresee.test.util.jodd.testng;


import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.xwork.StringUtils;

import jodd.format.Printf;
import jodd.http.HttpBrowser;
import jodd.http.HttpMultiMap;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.io.FileUtil;
import jodd.io.watch.DirWatcher;
import jodd.io.watch.DirWatcherListener;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailMessage;
import jodd.mail.att.ByteArrayAttachment;
import jodd.mail.att.FileAttachment;
import jodd.typeconverter.Convert;
import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManager;
import jodd.util.MimeTypes;

//Jodd 是一个普通开源Java包。你可以把Jodd想象成Java的"瑞士军刀",不仅小，锋利而且包含许多便利的功能。
//http://jodd.org/doc/
//http://jodd.org/doc/quickstart/index.html
public class Demo {

	public static void main(String[] args) throws Exception {

		// sendemail();
		testjson();
		//testhttp();
		testget();
		//testpage();
		testconverter();
		testprintf();
		new Thread(new Runnable() {
			
			public void run() {
				testwatchdir();
			}
		}).start();
		
	}

	private static void testwatchdir() {
		DirWatcher dirWatcher = new DirWatcher("d:/java", "*.txt");

	    dirWatcher.register(new DirWatcherListener() {
	        public void onChange(File file, DirWatcher.Event event) {
	            System.out.println(file.getName() + ":" + event.name());
	        }
	    });

	    dirWatcher.start(10000);
	 
	   
	}

	private static void testprintf() {
		Printf.str("%+i", 173);     // +173
	    Printf.str("%04d", 1);      // 0001
	    Printf.str("%f", 1.7);      // 1.700000
	    Printf.str("%1.1f", 1.7);   // 1.7
	    Printf.str("%.4e", 100.1e10);   // 1.0010e+012
	    Printf.str("%G", 1.1e13);   // 1.1E+013
	    Printf.str("%l", true);     // true
	    Printf.str("%L", 123);      // TRUE
	    Printf.str("%b", 13);       // 1101
	    Printf.str("%,b", -13);     // 11111111 11111111 11111111 11110011
	    Printf.str("%#X", 173);     // 0XAD
	    Printf.str("%,x", -1);      // ffff ffff
	    System.out.println(Printf.str("%s %s", new String[]{"one", "two"}));    // one two
	}

	private static void testconverter() {
		TypeConverter tc = TypeConverterManager.lookup(String.class);
	    System.out.println(tc.convert(Integer.valueOf(123)));
	    System.out.println(Convert.toInteger("1232"));
	    System.out.println(Convert.toBooleanValue(1));
	}

	private static void testpage() {
		HttpBrowser browser = new HttpBrowser();
	    HttpRequest request = HttpRequest.get("www.baidu.com");
	    browser.sendRequest(request);
	    // process the page:
	    String page = browser.getPage();
	    System.out.println(page);
	    // create new request
	    HttpRequest newRequest = HttpRequest.post("www.baidu.com");

	    HttpResponse response=browser.sendRequest(newRequest);
	    //System.out.println(response.bodyText());
	}

	private static void testhttp() {
		HttpRequest request = new HttpRequest();
	    request.method("GET")
	        .protocol("http")
	        .host("www.baidu.com")
	        .port(80)
	        .path("index.php");
	    System.out.println(request.send().bodyText());
	}

	private static void testget() {
		HttpResponse response = HttpRequest.get("http://jodd.org").send();

		System.out.println(response.getHttpRequest());
		// System.out.println(response.body());
		// System.out.println(response);
		
		HttpMultiMap<String> headers = response.headers();
		for (Entry<String, String> entry : headers.entries()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.print(key + "="+value);
//			for (String string : value) {
//				System.out.print(string);
//			}
			System.out.println();
		}
		
//		Map<String, String[]> headers = response.headers();
//		for (Map.Entry<String, String[]> entry : headers.entrySet()) {
//			String key = entry.getKey();
//			String[] value = entry.getValue();
//			System.out.print(key + "=");
//			for (String string : value) {
//				System.out.print(string);
//			}
//			System.out.println();
//		}
	}

	private static void testjson() {
		Book book = new Book();
		book.setNo(120);

		book.setAuthor("namexxx");
		book.setName("java android");
		String json = new JsonSerializer().include("authors").serialize(book);

		System.out.println(json);
		StringUtils.replace(json, "120", "800");
		Book book2 = new JsonParser().parse(StringUtils.replace(json, "120", "800"), Book.class);
		System.out.println(book2);
	}

	// http://jodd.org/doc/email.html
	private static void sendemail() throws Exception {
		Email email = new Email();
		email.from("infoxxx@jodd.org");
		email.to("igorxxxx@gmail.com");
		email.setSubject("test7");

		EmailMessage textMessage = new EmailMessage("Hello!",
				MimeTypes.MIME_TEXT_PLAIN);
		email.addMessage(textMessage);

		EmailMessage htmlMessage = new EmailMessage(
				"<html><META http-equiv=Content-Type content=\"text/html; charset=utf-8\">"
						+ "<body><h1>Hey!</h1><img src='cid:c.png'><h2>Hay!</h2></body></html>",
				MimeTypes.MIME_TEXT_HTML);
		email.addMessage(htmlMessage);

		EmailAttachment embeddedAttachment = new ByteArrayAttachment(FileUtil
				.readBytes("/c.png"), "image/png", "c.png", "c.png");
		embeddedAttachment.setEmbeddedMessage(htmlMessage);
		email.attach(embeddedAttachment);

		EmailAttachment attachment = new FileAttachment(new File("/b.jpg"),
				"b.jpg", "image/jpeg");
		email.attach(attachment);
	}
}
