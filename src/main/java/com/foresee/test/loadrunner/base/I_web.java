package com.foresee.test.loadrunner.base;

import com.foresee.test.util.http.HttpException;

public class I_web {

	public static final String ENDFORM = "ENDFORM";
	public static final String LAST = "LAST";
	public static final String ENDITEM = "ENDITEM";
	public static final String ITEMDATA = "ITEMDATA";
	public static final String STARTHIDDENS = "STARTHIDDENS";
	public static final String ENDHIDDENS = "ENDHIDDENS";
	public static final String CONNECT = "CONNECT";
	public static final String RECEIVE = "RECEIVE";
	public static final String RESOLVE = "RESOLVE";
	public static final String REQUEST = "REQUEST";
	public static final String RESPONSE = "RESPONSE";
	public static final int HTTP_INFO_RETURN_CODE = 1;
	public static final int HTTP_INFO_DOWNLOAD_SIZE = 2;
	public static final int HTTP_INFO_DOWNLOAD_TIME = 3;

	public static final String EXTRARES = "EXTRARES";

	public static int link(String stepName, String textName, String[] options)
			throws HttpException, lrapi.exceptions.HttpException {
		// TODO 直接转发LoadRunner的方法

		return lrapi.web.link(stepName, textName, options);
	}

	public static int url(String stepName, String urlAddress, String[] options)
			throws HttpException, lrapi.exceptions.HttpException {
		// TODO 包装LoadRunner
		return lrapi.web.url(stepName, urlAddress, options);
	}

	public static int reg_save_param(String paramName, String[] args) {
		// TODO 直接转发LoadRunner的方法

		return lrapi.web.reg_save_param(paramName, args);

	}

	public static int reg_find(String text, String[] args) {
		// TODO 包装LoadRunner
		return lrapi.web.reg_find(text, args);
	}

	public static int submit_data(String stepName, String urlAddress,
			String[] options, String[] data) throws HttpException,
			lrapi.exceptions.HttpException {
		// TODO 包装LoadRunner
		return lrapi.web.submit_data(stepName, urlAddress, options, data);

	}

	public static int custom_request(String stepName, String urlAddress,
			String[] optionsAndData) throws HttpException,
			lrapi.exceptions.HttpException {
		// TODO 包装LoadRunner
		return lrapi.web.custom_request(stepName, urlAddress, optionsAndData);

	}

	public static int set_max_html_param_len(String paramValueLength) {
		// TODO 包装LoadRunner
		return lrapi.web.set_max_html_param_len(paramValueLength);
	}

	public static int add_cookie(String cookie) {
		// TODO 包装LoadRunner
		return lrapi.web.add_cookie(cookie);
	}

	public static int add_header(String headerName, String headerValue) {
		// TODO 包装LoadRunner
		return lrapi.web.add_header(headerName, headerValue);
	}

	public static int submit_form(String stepName, String[] options,
			String[] data) throws HttpException, lrapi.exceptions.HttpException {
		// TODO 包装LoadRunner
		return lrapi.web.submit_form(stepName, options, data);

	}

	public I_web() {
		super();
	}

}