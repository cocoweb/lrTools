package com.foresee.test.util.exfile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.foresee.test.util.io.File2Util;

/**
 * 使用Dom4j处理XML
 * @author ZMM
 */
public class XmlFileUtil {

	private static Logger logger = Logger.getLogger(XmlFileUtil.class);
	// 文件编码
	private String encoding = "UTF-8";
	private SAXReader saxReader = null;
	private Document document = null;
	private Element rootElement = null;
	private String filePath = null;
	private String xmlContent = null;

	public XmlFileUtil() {

	}

	public XmlFileUtil(String filePath) {
		this.filePath = filePath;
	}
	
	public XmlFileUtil(Document document){
		this.document = document;
	}

	public void initByFile() {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			try {
				saxReader = new SAXReader();
				document = saxReader.read(file);
			} catch (Exception e) {
				e.getMessage();
				e.printStackTrace();
				logger.error(e);
				document = null;
			} finally {
				saxReader = null;
				file = null;
			}
		} else {
			logger.error("指定文件不存在；");
		}
	}

	public void initByString() {
		try {
			document = DocumentHelper.parseText(xmlContent);
		} catch (Exception e) {
			logger.error(e);
			document = null;
		}
	}

	/**
	 * 保存XML文件
	 * @Method:saveXmlFile
	 * @BuildTime:2011-9-19上午11:00:28
	 */
	public final boolean saveXmlFile() {
		boolean booResult = false;
		if (checkDocument()) {
			String strContent = document.asXML();
			booResult = File2Util.stringToFile(strContent, filePath, encoding);
		}
		return booResult;
	}
	
	/**
	 * 保存XML文件到strFilePath文件
	 * @param strFilePath
	 * 			包含文件名及全路径
	 * @Method:saveXmlFile
	 * @BuildTime:2012-10-04上午20:00:28
	 */
	public final boolean saveXmlFile(String strFilePath) {
		boolean booResult = false;
		if (checkDocument()) {
			String strContent = document.asXML();
			booResult = File2Util.stringToFile(strContent, strFilePath, encoding);
		}
		return booResult;
	}

	/**
	 * 查询指定路径的元素列表
	 * @param strXpath
	 *            被查询节点的路径
	 * @return List<Element>
	 */
	@SuppressWarnings("unchecked")
	public List<Element> queryElementAsList(String strXPath) {
		List<Element> listResult = null;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument())
				listResult = (List<Element>)document.selectNodes(strXPath);
		}
		strXPath = null;
		return listResult;
	}

	/**
	 * 查询指定路径的元素列表
	 * @param strXpath
	 *            被查询节点的路径
	 * @return Map<String,String>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryElementAsMap(String strXPath) {
		Map<String, String> mapResult = new HashMap<String, String>();
		List<Element> listResult = null;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument()) {
				listResult = (List<Element>)document.selectNodes(strXPath);
				if (null != listResult) {
					for (Element element : listResult)
						mapResult.put(element.getName(), element.getText()
								.trim());
				}
			}
		}
		strXPath = null;
		return mapResult;
	}

	/**
	 * 查询指定路径的元素, 如果通过strXPath查询的结果多余一个，只返回第一个。
	 * @param strXPath
	 *            元素路径
	 * @return String
	 */
	public Element queryElement(String strXPath) {
		Element element = null;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument()) {
				List<Element> listElement = queryElementAsList(strXPath);
				if (listElement != null)
					element = listElement.get(0);
			}
		}
		strXPath = null;
		return element;
	}

	/**
	 * 查询指定路径的元素, 并以String返回。
	 * @param strXPath
	 *            元素路径
	 * @return String
	 */
	public String queryElementAsString(String strXPath) {
		String strResult = "";
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument()) {
				Element element = queryElement(strXPath);
				if (element != null)
					strResult = element.getTextTrim();
			}
		}
		strXPath = null;
		return strResult;
	}

	/**
	 * 修改元素，如果strXPath查询结果为多值，则修改第一个
	 * @param strXPath
	 *            元素路径
	 * @param strText
	 *            元素所对应值
	 * @return boolean
	 */
	public boolean updateElement(String strXPath, String strText) {
		boolean booResult = false;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			List<Element> listElement = queryElementAsList(strXPath);
			if (listElement != null && listElement.size() > 0) {
				Element element = listElement.get(0);
				element.setText(strText);
			}
			booResult = true;
		}
		strXPath = null;
		strText = null;
		return booResult;
	}

	/**
	 * 修改元素：以数组方式批量修改
	 * @param strArrayXPath
	 * @param strArrayText
	 * @return boolean
	 */
	public boolean updateElementArray(String[] strArrayXPath,
			String[] strArrayText) {
		boolean booResult = false;
		if (strArrayXPath != null) {
			for (int i = 0; i < strArrayText.length; i++) {
				updateElement(strArrayXPath[i], strArrayText[i]);
			}
			booResult = true;
		} else {
			logger.error("查询路径不能为空");
		}
		return booResult;
	}
	
	/**
	 * 修改元素：以List方式批量修改
	 * @param listElement
	 * @param listText
	 * @return boolean
	 */
	public List<Element> updateElementList(List<Element> listElement,
			List<String> listText) {
		
		for(int i=0;i<listElement.size();i++){
			listElement.get(i).setText((null==(String)listText.get(i)?"":(String)listText.get(i)));
		}
		
		return listElement;
	}
	
	
	/**
	 * 删除元素，通过指定XML文件全路径（含文件名）方式。
	 * @param strXPath
	 *            元素路径
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean deleteElement(String strXPath) {
		boolean booResult = false;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument()) {
				List<Element> listElement = (List<Element>)document.selectNodes(strXPath);
				if (listElement != null) {
					Element elementParent = listElement.get(0).getParent();
					for (Element element : listElement) {
						elementParent.remove(element);
					}
					elementParent = null;
				}
				listElement = null;
				booResult = true;
			}
		}
		strXPath = null;
		return booResult;
	}

	/**
	 * 插入元素,通过制定XML文件全路径（含文件名）方式
	 * @param strXPath
	 *            元素路径
	 * @param strArrayElement
	 *            元素数组（二维），String[i][0]:元素名称；String[i][1]:值
	 * @return boolean
	 */
	public boolean insertElement(String strXPath, String[][] strArrayElement) {
		boolean booResult = false;
		if (StringUtils.isBlank(strXPath)) {
			logger.error("查询路径不能为空");
		} else {
			if (checkDocument()) {
				Element element = queryElement(strXPath);
				if (element == null) {
					logger.error("路径不存在:" + strXPath);
					return booResult;
				}
				for (String[] strArrElement : strArrayElement) {
					element.addElement(strArrElement[0]).setText(
							strArrElement[1]);
				}
				booResult = true;
			}
		}
		strXPath = null;
		strArrayElement = null;
		return booResult;
	}

	/**
	 * 根据filePath查询所有的Element，并生成Map
	 * @return Map<String, String>
	 */
	public Map<String, String> queryAllElement() {
		Map<String, String> mapElement = new HashMap<String, String>();
		if (checkDocument()) {
			Element elementRoot = document.getRootElement();
			buildChildElement(mapElement, elementRoot);
		}
		return mapElement;
	}
	
	/**
	 * 添加节点属性
	 * 
	 * @Method:insertElementAttributes
	 * @param strXPath
	 * @param attributes
	 *            节点数据列表
	 * @param element_name
	 * @param content
	 * @param dType
	 *            0：text 1：CDATA
	 */
	public boolean insertElementAttributes(String strXPath,
			String[][] attributes, String element_name, String content,
			int dType) {
		try {
			Element uElt = queryElement(strXPath).addElement(element_name);
			if (attributes != null) {
				for (String[] strArrElement : attributes) {
					uElt.addAttribute(strArrElement[0], strArrElement[1]);
				}
			}
			if (dType == 0 && content != null) {

				uElt.addText(content);
			} else if (dType == 1 && content != null) {
				uElt.addCDATA(content);
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	private final boolean checkDocument() {
		if (document != null)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	private final void buildChildElement(Map<String, String> mapElement,
			Element elementParent) {
		List<Element> listElement = elementParent.elements();
		if (listElement != null && !listElement.isEmpty()) {
			for (Element element : listElement) {
				buildChildElement(mapElement, element);
			}
		} else {
			mapElement.put(elementParent.getPath().replaceAll("/", ".")
					.replaceFirst(".", ""), elementParent.getTextTrim());
		}
	}

	/**
	 * 获得文件编码格式 ，默认为UTF-8
	 * @return String
	 */
	public final String getEncoding() {
		return encoding;
	}

	/**
	 * 设置文件编码
	 * @param encoding
	 */
	public final void setEncoding(String encoding) {
		if (encoding != null && encoding.trim().length() > 0) {
			this.encoding = encoding;
		}
	}

	public Element getRootElement() {
		return rootElement;
	}

	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}

	/**
	 * 获得：文件路径(含文件名)
	 * @return the filePath
	 */
	public final String getFilePath() {
		return filePath;
	}

	/**
	 * 设置：文件路径(含文件名)
	 * @param filePath
	 *            the filePath to set
	 */
	public final void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获得：XML文件内容
	 * @return the xmlContent
	 */
	public final String getXmlContent() {
		return xmlContent;
	}

	/**
	 * 设置：XML文件内容
	 * @param xmlContent
	 *            the xmlContent to set
	 */
	public final void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	/**
	 * 获得：Document文档对象
	 * @return the document
	 */
	public final Document getDocument() {
		return document;
	}

	/**
	 * 设置：Document文档对象
	 * @param document
	 *            the document to set
	 */
	public final void setDocument(Document document) {
		this.document = document;
	}

}