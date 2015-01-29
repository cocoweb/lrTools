package com.foresee.test.loadrunner.lrapi4j.testng;
//package com.foresee.test.loadrunner;



import com.foresee.test.loadrunner.base.InnerITrans;
import com.foresee.test.loadrunner.base.LrActionClass;
import com.foresee.test.loadrunner.lrapi4j.lr;
import com.foresee.test.loadrunner.lrapi4j.web;
import com.foresee.test.util.code.EncodeUtils;

/**
 * 这个类里面用了多种方法，来构造LoadRunner的事务代码管理
 * 1、直接copy代码，成为函数，如：run_Sbzs3_upload()
 *    最为简单，但是很多重复代码
 * 2、使用匿名对象，并调用 ，如：run_Sbzs1_Page1()
 *    问题：每次循环执行的时候，都会重复创建该匿名对象
 * 3、使用 addTrans()添加事务对象到map进行缓存，然后再调用 ，如：run_Start()
 * 
 * init()初始化方法，被父类的构造函数调用
 * @author allan.xie
 *
 */ 
public class ActionsDemo3 extends LrActionClass
{
    private static ActionsDemo3 commonObj = new ActionsDemo3();
    static String sZZSXML=null;
    static String sFPYJXML =null;
    
    /**
     * @return 返回实例对象
     */
    public static ActionsDemo3 getInstance(){
    	if (null==commonObj) {
    	    commonObj = new ActionsDemo3();
    	}
    	//commonObj.init();
    	return commonObj;

    }

    @Override
	public void Init() {
		if(sZZSXML == null){  //初始化XML报文(含参数)
	    	//sZZSXML = lrTools.loadXmlByKey("zzssyyybnsr_zb");
		}
		if(sFPYJXML == null){
			//sFPYJXML = lrTools.loadXmlByKey("fpcglcjTyXmldyywbw");
		}
		
		loadTrans();
	}
	static InnerITrans i_sstx  = new InnerITrans("办税服务厅登录"){
		@Override
		public boolean onTrans() throws Throwable {
			
			return true;
			
	}};

	public static InnerITrans i_client = new InnerITrans("办税服务厅登录"){
		@Override
		public boolean onTrans() throws Throwable {
			web.url("CoreService", 
					"URL=http://61.146.43.162:8090/omni//services/CoreService", new String[]{
					"Resource=0", 
					"RecContentType=text/html", 
					"Referer=", 
					"Snapshot=t1.inf", 
					"Mode=HTML", 
					LAST});

				web.custom_request("CoreService_2", 
					"URL=http://61.146.43.162:8090/omni//services/CoreService",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t2.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=\"utf-8\"", 
					"Body=<?xml version=\"1.0\"?>\r\n<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"><SOAP-ENV:Body SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><NS1:invoke xmlns:NS1=\"http://ws.srv.bpm.fbrp.foresee.com\"><xml xsi:type=\"xsd:string\">S88n8Pl66sC1o54DVLtVKmT7m3fLh+"+
					"FUgdjoeCgshSaLE13kXUZHUixJZUHV+SavRXH6zsRGDbzrtokfbfrFBVh4NA6flmXw8Gd0xSI47KnOWycq4MoyFh/PD0RHmj8SmfH5GWPgz8POo8AjipwxuKtA2i7NFoOOGinw232mrCc2vR3ihprcDUqPQvPV5rGa+X/E1ua4pKxNBGIPZx9dCNCz3TXK/CwA0x6eg+V48uEMi1xaeY6x5VQqUX6WrG2nTj8Qgxk+EH7cJpYPknfQCQjC4ECcFF8vouIj+s1bWJlXZTI83eE6Z6ZKP/yutzJMkCy68345JE756+csGAbZ2Ose6qkKReFjdDdVj1gKboHqcmWmNYauScFI5HtH7UGm2npBb35QnLZP8gEvxbsRQOTsWka4DU7GJVvRkpRtZNFpS6bAerhbfd7brbW6T3S4wfHFyXFfRwqrim+ztRjjyhf43JlumWhp</xml><param xsi:type=\"xsd:string\">ENCRYPT:DES;"+
					"COMPRESS:ZIP</param></NS1:invoke></SOAP-ENV:Body></SOAP-ENV:Envelope>\r\n", 
					LAST});

				web.custom_request("sfjrService", 
					"URL=http://61.146.43.162:8090/thirdparty_sjjz/webservices/sfjrService?wsdl",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t3.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=GBK", 
					"Body=<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
					"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;&lt;/sessionId&gt;&lt;service&gt;&lt;serviceId&gt;"+
					"C00.TY.SFYZ.nsrdlyz&lt;/serviceId&gt;&lt;clientNo&gt;99999999999&lt;/clientNo&gt;&lt;tranSeq&gt;frse99999999999000002015010620193801&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;&lt;/"+
					"password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt"+
					";&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"NfnsrdlyzRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlbh=\"\" xmlmc=\"\" bbh=\"\"&gt;&lt;nsrzh&gt;441302730457424&lt;/nsrzh&gt;&lt;"+
					"authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;random&gt;&lt;/random&gt;&lt;signValue&gt;&lt;/signValue&gt;&lt;/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>", 
					LAST});

				web.custom_request("sfjrService_2", 
					"URL=http://61.146.43.162:8090/thirdparty_sjjz/webservices/sfjrService?wsdl",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t4.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=GBK", 
					"Body=<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
					"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;BF332426564011744C9B0AC50B50405F&lt;/sessionId&gt;&lt;service&gt;&"+
					"lt;serviceId&gt;C00.TY.SFYZ.nfsjkxtsjcx&lt;/serviceId&gt;&lt;clientNo&gt;NS730457424&lt;/clientNo&gt;&lt;tranSeq&gt;frseNS730457424144132015010620194202&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&"+
					"lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;10114413000129986797&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt"+
					";&lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt;&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"sbztcxrequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlbh=\"\" xmlmc=\"\" bbh=\"\" xsi"+
					":schemaLocation=\"http://www.chinatax.gov.cn/dataspec/TaxMLBw_nfsfzrdxxcx_Request_V1.0.xsd\"&gt;&lt;djxh&gt;10114413000129986797&lt;/djxh&gt;&lt;/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;paramList&gt;&lt;name&gt;swjgDm&lt;/name&gt;&lt;value&gt;14413020000&lt;/value&gt;&lt;/paramList&gt;&lt;/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>", 
					LAST});

				web.custom_request("sfjrService_3", 
					"URL=http://61.146.43.162:8090/thirdparty_sjjz/webservices/sfjrService?wsdl",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t5.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=GBK", 
					"Body=<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
					"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;BF332426564011744C9B0AC50B50405F&lt;/sessionId&gt;&lt;service&gt;&"+
					"lt;serviceId&gt;C00.TY.JGCX.fkjgcx&lt;/serviceId&gt;&lt;clientNo&gt;NS730457424&lt;/clientNo&gt;&lt;tranSeq&gt;frseNS730457424144132015010620194202&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;"+
					"password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;10114413000129986797&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&"+
					"lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt;&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"NffkjgcxRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlbh=\"\" xmlmc=\"\" bbh=\"\"&gt;&lt"+
					";/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;paramList&gt;&lt;name&gt;swjgDm&lt;/name&gt;&lt;value&gt;14413020000&lt;/value&gt;&lt;/paramList&gt;&lt;/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>", 
					LAST});

				web.add_cookie("cookie=20111114; DOMAIN=www.gd-n-tax.gov.cn");

				web.url("index.html", 
					"URL=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052703/index.html?nsrsbh=441302730457424&token=",  new String[]{
					"Resource=0", 
					"RecContentType=text/html", 
					"Referer=", 
					"Snapshot=t6.inf", 
					"Mode=HTML", 
					LAST});

				web.url("index.html_2", 
					"URL=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052701/index.html?nsrsbh=441302730457424&token=",  new String[]{
					"Resource=0", 
					"RecContentType=text/html", 
					"Referer=", 
					"Snapshot=t7.inf", 
					"Mode=HTML", 
					LAST});

				web.url("index.html_3", 
					"URL=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052702/index.html?nsrsbh=441302730457424&token=",  new String[]{
					"Resource=0", 
					"RecContentType=text/html", 
					"Referer=", 
					"Snapshot=t8.inf", 
					"Mode=HTML", 
					LAST});

				web.custom_request("sfjrService_4", 
					"URL=http://61.146.43.162:8090/thirdparty_sjjz/webservices/sfjrService?wsdl",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t9.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=GBK", 
					"Body=<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
					"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;BF332426564011744C9B0AC50B50405F&lt;/sessionId&gt;&lt;service&gt;&"+
					"lt;serviceId&gt;C00.WS.WSSQ.sssxtxlb&lt;/serviceId&gt;&lt;clientNo&gt;NS730457424&lt;/clientNo&gt;&lt;tranSeq&gt;frseNS730457424144132015010620195103&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;"+
					"password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;10114413000129986797&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&"+
					"lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt;&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"NfnsrmmczRequest\" xmlbh=\"\" bbh=\"1.0\" xmlmc=\"\" xsi:schemaLocation=\"http://www.chinatax.gov.cn/dataspec/&amp;quot; xmlns=&amp;quot;http://"+
					"www.chinatax.gov.cn/dataspec/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;paramList&gt;&lt;name&gt;nsrsbh&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&lt;/paramList&gt;&lt;paramList&gt;&lt;name&gt;djxh&lt;/name&gt;&lt;value&gt;10114413000129986797&lt;/value&gt;&lt;/paramList&gt;&lt;paramList&gt;&lt;name&gt;swjgDm&lt;/name&gt;&lt;value&gt;14413020000&lt;/value&gt;&lt;/paramList&gt;&lt;"+
					"/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>", 
					LAST});

				web.custom_request("sfjrService_5", 
					"URL=http://61.146.43.162:8090/thirdparty_sjjz/webservices/sfjrService?wsdl",  new String[]{
					"Method=POST", 
					"Resource=0", 
					"RecContentType=text/xml", 
					"Referer=", 
					"Snapshot=t10.inf", 
					"Mode=HTML", 
					"EncType=text/xml; charset=GBK", 
					"Body=<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
					"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;BF332426564011744C9B0AC50B50405F&lt;/sessionId&gt;&lt;service&gt;&"+
					"lt;serviceId&gt;C00.TY.JGCX.fkjgcx&lt;/serviceId&gt;&lt;clientNo&gt;NS730457424&lt;/clientNo&gt;&lt;tranSeq&gt;frseNS730457424144132015010620195103&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;"+
					"password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;10114413000129986797&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&"+
					"lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt;&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"NffkjgcxRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlbh=\"\" xmlmc=\"\" bbh=\"\"&gt;&lt"+
					";/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;paramList&gt;&lt;name&gt;swjgDm&lt;/name&gt;&lt;value&gt;14413020000&lt;/value&gt;&lt;/paramList&gt;&lt;/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>", 
					EXTRARES, 
					"Url=http://www.gd-n-tax.gov.cn/pub/001012/images/box01_topbg.gif", "Referer=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052701/index.html?nsrsbh=441302730457424&token=", ENDITEM, 
					"Url=http://www.gd-n-tax.gov.cn/pub/001012/images/box01_left.gif", "Referer=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052701/index.html?nsrsbh=441302730457424&token=", ENDITEM, 
					"Url=http://www.gd-n-tax.gov.cn/pub/001012/images/list01_dot.gif", "Referer=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052701/index.html?nsrsbh=441302730457424&token=", ENDITEM, 
					"Url=http://www.gd-n-tax.gov.cn/pub/001012/images/list03_dot.gif", "Referer=http://www.gd-n-tax.gov.cn/pub/001012/misc/E0527/E052702/index.html?nsrsbh=441302730457424&token=", ENDITEM, 
					LAST});			
			
			return true;
			
		}};
		

    static InnerITrans i_tycx_cd = new InnerITrans("TYCX_菜单"){
		@Override
		public boolean onTrans() throws Throwable {
		    
		    //web_x.add_auto_header ("Content-Type", "application/x-www-form-urlencoded; charset=utf-8" ); 

		    web.url("tycx.do", 
				"URL={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh={para_nsrsbh}&nsrsmc=惠州市新大众眼镜有限公司&token=", new String[]{ 
				"Resource=0", 
				"RecContentType=text/html", 
				"Referer=", 
				"Snapshot=t11.inf", 
				"Mode=HTML",  
				EXTRARES,  
				"Url=../style/images-common/common/top_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/common/bot_background.jpg", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/td_bg.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_tb.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-swzj-01/style12/top_left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/leftnav/left.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				"Url=../style/images-common/leftnav/right.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycxFrame&menuId=SBXXCX_HZGS&nsrsbh=441302730457424&nsrsmc=惠州市新大众眼镜有限公司&token=", ENDITEM, 
				LAST});

		    web.url("tycx.do_2", 
			    "URL={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", new String[]{
			    "Resource=0", 
			    "RecContentType=text/html", 
			    "Referer=", 
			    "Snapshot=t12.inf", 
			    "Mode=HTML", 
			    LAST});

		    web.reg_save_param ("ret_cxTID", new String []{ 
			    "NOTFOUND=ERROR", 
			    "LB=<tid>", 
			    "RB=</tid>" , 
			    LAST} ); 
		    web.reg_save_param ("ret_waitTime", new String []{ 
				    "NOTFOUND=ERROR", 
				    "LB=<waitingTime>", 
				    "RB=</waitingTime>" , 
				    LAST} ); 

		    web.submit_data("tycx.do_3", 
			    "Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
			    "Method=POST", 
			    "RecContentType=text/plain", 
			    "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS", 
			    "Snapshot=t13.inf", 
			    "Mode=HTML", }, new String[]{
			    "Name=bizXml", "Value=yhscd", ENDITEM, 
			    "Name=sid", "Value=ETax.TY.initMenu", ENDITEM, 
			    "Name=action", "Value=queryData", ENDITEM, 
			    LAST});

			//轮询
			int ii = 0;
			do {
			   // lr_x.think_time(lr_x.eval_int("{ret_waitTime}")/1000); 
			    Thread.sleep(lr.eval_int("{ret_waitTime}"));
			    ii++;

			    lr.message(lr.eval_string("{ret_waitTime} ")+this.TransName+String.format("----第%s次请求服务器\n",ii));
				
			    web.reg_find("Text=结果尚未返回", 
			            new String[]{ 
			            "SaveCount=ret_lunxunCount", 
			            LAST }); 

			    web.reg_save_param ("retStr",
					    new String []{
						"NOTFOUND=warning",
						"LB=<cdmc>",
						"RB=</cdmc>" ,
						"Search=ALL",
						LAST} );
			    web.add_header ("Accept", "text/plain;charset=UTF-8" ); 
	
	            web.reg_find("Text=<cdmc>综合查询</cdmc>", 
			            new String[]{ 
			            "SaveCount=ret_zhcxCount", 
			            LAST }); 
	
			    web.submit_data("tycx.do_4",
				    "Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
				    "Method=POST",
				    "RecContentType=text/plain",
				    "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toMenuList&menuId=SBXXCX_HZGS",
				    "Snapshot=t14.inf",
				    "Mode=HTML"},new String[]{
				    "Name=action", "Value=queryHandleResult", ENDITEM,
				    "Name=tid", "Value={ret_cxTID}", ENDITEM,
				    "Name=sid", "Value=ETax.TY.initMenu", ENDITEM,
				    LAST});
				//结果尚未返回
			}while(lr.eval_int("{ret_lunxunCount}") > 0);


//			    this.OutString = lr_x.eval_string("{retStr}");
			 this.OutString = EncodeUtils.ConvertCharset(lr.eval_string("{retStr}"),"GBK","UTF-8");
		    //System.out.println(this.OutString);


		   return lr.eval_int("{ret_zhcxCount}") > 0;
		}};
		
		
		static InnerITrans i_cx_jcxxcx = new InnerITrans("CX_基础信息查询"){
			@Override
			public boolean onTrans() throws Throwable {
				web.url("tycx.do", 
						"URL={p_tycxURL}/tycx/tycx/tycxJcxx.do?action=toJcxx&nsrsbh={para_nsrsbh}&nsrsmc=&token=",new String[]{ 
						"Resource=0", 
						"RecContentType=text/html", 
						"Referer=", 
						"Snapshot=t39.inf", 
						"Mode=HTML", 
						EXTRARES, 
						"Url=../style/images-common/waiting/loading.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toJcxx&sjId=swdjxxcx_hzgs", ENDITEM, 
						LAST});
			    web.reg_save_param ("ret_jcxxTID", new String []{ 
					    "NOTFOUND=ERROR", 
					    "LB=<tid>", 
					    "RB=</tid>" , 
					    LAST} ); 
			    
			    web.reg_save_param ("ret_waitTime", new String []{ 
					    "NOTFOUND=ERROR", 
					    "LB=<waitingTime>", 
					    "RB=</waitingTime>" , 
					    LAST} ); 

				
				web.submit_data("tycx.do_2", 
					"Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
					"Method=POST", 
					"RecContentType=text/plain", 
					"Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs", 
					"Snapshot=t40.inf", 
					"Mode=HTML",},new String[]{
					"Name=bizXml", "Value=<tycxParam><sqlID>queryNsrjbxx</sqlID><pageFlag>N</pageFlag><params><entry><key>nsrsbh</key><value>{para_nsrsbh}</value></entry><entry><key>djxh</key><value>null</value></entry></params></tycxParam>", ENDITEM, 
					"Name=sid", "Value=ETax.TY.queryJcxx", ENDITEM, 
					"Name=action", "Value=queryData", ENDITEM, 
					LAST});
				
				//轮询
				int ii = 0;
				do {
				   // lr_x.think_time(lr_x.eval_int("{ret_waitTime}")/1000); 
				    Thread.sleep(lr.eval_int("{ret_waitTime}"));
				    ii++;
				    
				    lr.message(lr.eval_string("{ret_waitTime} ")+this.TransName+String.format("----第%s次请求服务器\n",ii));
					
				    web.reg_find("Text=结果尚未返回", 
				            new String[]{ 
				            "SaveCount=ret_lunxunCount", 
				            LAST }); 
				    web.reg_save_param ("retStr",
						    new String []{
							"NOTFOUND=warning",
							"LB=<taxML>",
							"RB=</taxML>" ,
							"Search=BODY",
							LAST} );
				    web.reg_save_param ("reterrStr",
						    new String []{
							"NOTFOUND=warning",
							"LB=",
							"RB=" ,
							"Search=BODY",
							LAST} );
				    web.add_header ("Accept", "text/plain;charset=UTF-8" ); 
				    				
				    web.reg_find("Text=<taxML><jbxx><pageInfo>", 
				            new String[]{ 
				            "SaveCount=ret_jcxxCount", 
				            LAST }); 
				
					web.submit_data("tycx.do_9", 
						"Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
						"Method=POST", 
						"RecContentType=text/plain", 
						"Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs#", 
						"Snapshot=t41.inf", 
						"Mode=HTML",},new String[]{
						"Name=action", "Value=queryHandleResult", ENDITEM, 
						"Name=tid", "Value={ret_jcxxTID}", ENDITEM, 
						"Name=sid", "Value=ETax.TY.queryJcxx", ENDITEM, 
						LAST});
					
					//结果尚未返回
				}while(lr.eval_int("{ret_lunxunCount}") > 0);
				//this.OutString = lr_x.eval_string("TID[{ret_jcxxTID}]:{retStr}");
				
				if(lr.eval_int("{ret_jcxxCount}") > 0){
					this.OutString = lr.eval_string("TID[{ret_jcxxTID}]:{retStr}")+EncodeUtils.ConvertCharset(lr.eval_string("{reterrStr}"),"GBK","UTF-8");
				}else{
					this.OutString = lr.eval_string("TID[{ret_jcxxTID}]")+EncodeUtils.ConvertCharset(lr.eval_string("{reterrStr}"),"UTF-8","GBK");
				}

				return lr.eval_int("{ret_jcxxCount}") > 0;
			}};

		static InnerITrans i_tycx_djxxcx = new InnerITrans("TYCX_登记信息查询"){
			@Override
			public boolean onTrans() throws Throwable {
			web.set_max_html_param_len("102400");
			web.url("tycx.do_7", 
					"URL={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs",new String[]{ 
					"Resource=0", 
					"RecContentType=text/html", 
					"Referer=", 
					"Snapshot=t39.inf", 
					"Mode=HTML", 
					EXTRARES, 
					"Url=../style/images-common/waiting/loading.gif", "Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs", ENDITEM, 
					LAST});
			
		    web.reg_save_param ("ret_djxxcxTID", new String []{ 
			    "NOTFOUND=ERROR", 
			    "LB=<tid>", 
			    "RB=</tid>" , 
			    LAST} ); 
		    web.reg_save_param ("ret_waitTime", new String []{ 
				    "NOTFOUND=ERROR", 
				    "LB=<waitingTime>", 
				    "RB=</waitingTime>" , 
				    LAST} ); 
		
			web.submit_data("tycx.do_8", 
				"Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
				"Method=POST", 
				"RecContentType=text/plain", 
				"Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs", 
				"Snapshot=t40.inf", 
				"Mode=HTML",},new String[]{
				"Name=bizXml", "Value=<tycxParam><sqlID>querySwdjxxcx</sqlID><pageFlag>Y</pageFlag><perNum>10</perNum><params><entry><key>nsrsbh</key><value>{para_nsrsbh}</value></entry><entry><key>beignRow</key><value>1</value></entry><entry><key>endRow</key><value>10</value></entry></params></tycxParam>", ENDITEM, 
				"Name=sid", "Value=ETax.TY.qureydataZG", ENDITEM, 
				"Name=action", "Value=queryData", ENDITEM, 
				LAST});
			//轮询
			int ii = 0;
			do {
			   // lr_x.think_time(lr_x.eval_int("{ret_waitTime}")/1000); 
			    Thread.sleep(lr.eval_int("{ret_waitTime}"));
			    ii++;

			    lr.message(lr.eval_string("{ret_waitTime} ")+this.TransName+String.format("----第%s次请求服务器\n",ii));
				
			    web.reg_find("Text=结果尚未返回", 
			            new String[]{ 
			            "SaveCount=ret_lunxunCount", 
			            LAST }); 
		
			    web.reg_save_param ("retStr",
					    new String []{
						"NOTFOUND=warning",
						"LB=",
						"RB=" ,
						"Search=BODY",
						LAST} );
			    web.reg_save_param ("reterrStr",
					    new String []{
						"NOTFOUND=warning",
						"LB=<span>",
						"RB=</span>" ,
						"Search=BODY", 
						LAST} );
			    web.add_header ("Accept", "text/plain;charset=UTF-8" ); 
			
			    web.reg_find("Text=<pageInfo><mount>", 
			            new String[]{ 
			            "SaveCount=ret_zhcxCount", 
			            LAST }); 
			
				web.submit_data("tycx.do_9", 
					"Action={p_tycxURL}/tycx/tycx/tycx.do", new String[]{
					"Method=POST", 
					"RecContentType=text/plain", 
					"Referer={p_tycxURL}/tycx/tycx/tycx.do?action=toTycx&sjId=swdjxxcx_hzgs#", 
					"Snapshot=t41.inf", 
					"Mode=HTML",},new String[]{
					"Name=action", "Value=queryHandleResult", ENDITEM, 
					"Name=tid", "Value={ret_djxxcxTID}", ENDITEM, 
					"Name=sid", "Value=ETax.TY.qureydataZG", ENDITEM, 
					LAST});
				//结果尚未返回
			}while(lr.eval_int("{ret_lunxunCount}") > 0);
			
			 //this.OutString = (lr_x.eval_string("{retStr}"));
			
			if(lr.eval_int("{ret_zhcxCount}") > 0){
				this.OutString = lr.eval_string("TID[{ret_djxxcxTID}]:{retStr}");
			}else{
				this.OutString = lr.eval_string("TID[{ret_djxxcxTID}]")+lr.eval_string("{reterrStr}");
			}
		   return lr.eval_int("{ret_zhcxCount}") > 0;
		
	}};

	private void loadTrans(){
		//这里使用匿名对象，而没有嵌入匿名类，是为了方便编辑，可以在outline直接选择
    	this.addTrans(i_tycx_cd);
    	this.addTrans(i_tycx_djxxcx);
    	this.addTrans(i_cx_jcxxcx);
    	this.addTrans(i_client);
		
	}
	
	public static void main(String[] args) {
		String xstr = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r<soap:Header>\r<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\" soap:mustUnderstand=\"1\">\r<wsse:UsernameToken wsu:Id=\"UsernameToken-57\">\r<wsse:Username>frse</wsse:Username>\r<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/"+
				"oasis-200401-wss-username-token-profile-1.0#PasswordText\">123456</wsse:Password>\r</wsse:UsernameToken>\r</wsse:Security>\r</soap:Header>\r<soap:Body>\r<ns1:doService xmlns:ns1=\"http://cn.gov.chinatax.gt3nf.service/\">\r<bizXml>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;tiripPackage xsi:type=\"tiripPackage\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\"&gt;&lt;sessionId&gt;&lt;/sessionId&gt;&lt;service&gt;&lt;serviceId&gt;"+
				"C00.TY.SFYZ.nsrdlyz&lt;/serviceId&gt;&lt;clientNo&gt;99999999999&lt;/clientNo&gt;&lt;tranSeq&gt;frse99999999999000002015010620193801&lt;/tranSeq&gt;&lt;repeatFlag&gt;0&lt;/repeatFlag&gt;&lt;tranReqDate&gt;2015-01-06&lt;/tranReqDate&gt;&lt;/service&gt;&lt;identity&gt;&lt;application&gt;&lt;applicationId&gt;21A3AB7D88C94D9C9FA002FD4CCE80D5&lt;/applicationId&gt;&lt;supplier&gt;frse&lt;/supplier&gt;&lt;version&gt;1&lt;/version&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;&lt;/"+
				"password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;/application&gt;&lt;customer&gt;&lt;customerId&gt;441302730457424&lt;/customerId&gt;&lt;authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;nsrsbh&gt;441302730457424&lt;/nsrsbh&gt;&lt;djxh&gt;&lt;/djxh&gt;&lt;/customer&gt;&lt;/identity&gt;&lt;routerSession&gt;&lt;paramList&gt;&lt;name&gt;SENDER&lt;/name&gt;&lt;value&gt;441302730457424&lt;/value&gt;&lt;/paramList&gt;&lt;/routerSession&gt;&lt;signData&gt"+
				";&lt;signType&gt;0&lt;/signType&gt;&lt;signSource&gt;000&lt;/signSource&gt;&lt;signValue&gt;000&lt;/signValue&gt;&lt;/signData&gt;&lt;businessContent&gt;&lt;subPackage&gt;&lt;id&gt;1&lt;/id&gt;&lt;content&gt;&lt;![CDATA[&lt;?xml version=\"1.0\" encoding=\"UTF-8\" ?&gt;&lt;taxML xsi:type=\"NfnsrdlyzRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.chinatax.gov.cn/dataspec/\" xmlbh=\"\" xmlmc=\"\" bbh=\"\"&gt;&lt;nsrzh&gt;441302730457424&lt;/nsrzh&gt;&lt;"+
				"authenticateType&gt;2&lt;/authenticateType&gt;&lt;password&gt;Abcd1234&lt;/password&gt;&lt;cert&gt;&lt;/cert&gt;&lt;random&gt;&lt;/random&gt;&lt;signValue&gt;&lt;/signValue&gt;&lt;/taxML&gt;\r\n]]&gt;&lt;/content&gt;&lt;/subPackage&gt;&lt;/businessContent&gt;&lt;/tiripPackage&gt;\r\n</bizXml>\r</ns1:doService>\r</soap:Body>\r</soap:Envelope>";
		
		System.out.println(EncodeUtils.unescapeHtml(xstr));
	}
	

}
