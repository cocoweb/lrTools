package com.foresee.test.util.exfile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.FlyweightCDATA;

import com.foresee.test.util.CommonUtil;
import com.foresee.test.util.lang.DateFormatException;
import com.foresee.test.util.lang.DateUtil;

public class XMLUtil {

	/**
     * 缺省字符集
     * */
    public static final String DEFAULT_ENCODING = "UTF-8";
	
	public static String createCDATA(String input){
		StringBuffer sb = new StringBuffer();
		sb.append("<![CDATA[").append(input).append("]]>");
		
		return sb.toString();
	}
	
	public static String getCDATA(String raw){
		return raw.replaceAll("<![CDATA[", "").replaceAll("]]>", "");
	}

	public static Element getRoot(Document doc) {
		Element root = null;

		try {
			root = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root;
	}

	@SuppressWarnings("unchecked")
	public static List<Element> getNodeList(Element root, String xpath) {
		if (null == root)
			return null;
		
		List<Element> elms = null;
           
		try {
			elms = root.selectNodes(xpath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return elms;
	}

	public static String getValue(Element node, String att) {
		String attValue = null;
		attValue = node.attributeValue(att);
		try {
			if (attValue != null) {
				return attValue.trim();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static int getIntValue(Element node, String att) {
		String value = getValue(node, att);
		if (null != value)
			return Integer.valueOf(value).intValue();
		else
			return -1;
	}
	
	public static long getLongValue(Element node, String att) {
		String value = getValue(node, att);
		if (null != value)
			return Long.parseLong(value);
		else
			return -1;
	}

	public static Element getNode(Element root, String xpath) {
		Element node = null;

		List<Element> nodes = getNodeList(root, xpath);
		if (null != nodes && nodes.size() > 0) {
			node = nodes.get(0);
			return node;
		}
		return null;

	}

	public static String getTagConent(Element node) {
		String content = null;

		try {
			content = node.getText();
			if (null != content)
				content = content.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;

	}

	public static String getTagConent(Element root, String xpath) {
		if (null == root)
			return null;
		
		String content = null;
		Node node;

		try {
			node = root.selectSingleNode(xpath);
			
			if (null != node){
				content = node.getText();
				if (null != content)
					content = content.trim();
				else
					content = "";
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;

	}
	
	public static int getConentInt(Element root, String xpath) {
		if (null == root)
			return -1;
		
		String content = null;
		Node node;
		
		try {
			node = root.selectSingleNode(xpath);
			if (null != node){
				content = node.getText();
				if (null != content)
					content = content.trim();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (CommonUtil.isEmpty(content))
			return -1;

		return Integer.valueOf(content).intValue();

	}
	
	public static long getConentLong(Element root, String xpath) {
		if (null == root)
			return -1;
		
		String content = null;
		Node node;
		

		try {
			node = root.selectSingleNode(xpath);
			if (null != node){
				content = node.getText();
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (CommonUtil.isEmpty(content))
			return -1;

		return Long.parseLong(content);

	}
    /**
     * Return the child element with the given name.  The element must be in
     *   the same name space as the parent element.
     *  @param element The parent element
     *  @param name The child element name
     *  @return The child element
     */
    public static Element child(Element element, String name) {
        return element.element(new QName(name, element.getNamespace()));
    }
 
    /**
     * 得到给定结点下的孩子节点
     * @param element 节点
     * @param name 子节点名称
     * @param optional 是否是可选的
     * @return 子节点
     * @throws XMLDocException
     */
    public static Element child(Element element,
                                String name,
                                boolean optional)
        throws XMLDocException {
        Element child = element.element(new QName(name, element.getNamespace()));
        if (child == null && !optional) {
            throw new XMLDocException(name + " element expected as child of " +
                                      element.getName() + ".");
        }
        return child;
    }
 
    /** Return the child elements with the given name.  The elements must be in
        the same name space as the parent element.
        @param element The parent element
        @param name The child element name
        @return The child elements
     */
    public static List<?> children(Element element, String name) {
        return element.elements(new QName(name, element.getNamespace()));
    }
     
     
    /**
     * 得到某个节点下的属性信息
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static String getAttribute(Element element,
                                      String name,
                                      boolean optional)
        throws XMLDocException {
        Attribute attr = null;
        if(element!=null)
            attr = element.attribute(name);
        if (attr == null && !optional) {
            if(element!=null)
                throw new XMLDocException("Attribute " + name + " of " +
                                      element.getName() + " expected.");
            else
                return null;
        } else if (attr != null) {
            return attr.getValue();
        }
        else {
            return null;
        }
    }
 
    /**
     * 得到节点属性值，并且作为日期型返回
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
 
    public static java.util.Date getAttributeAsDate(Element element,
        String name,
        boolean optional)
        throws XMLDocException {
        String value = getAttribute(element, name, optional);
        if ( (optional) && ( (value == null) || (value.equals("")))) {
            return null;
        }
        else {
            try {
                //如果可选就不抛出异常
                return DateUtil.getInstance().parse(value, !optional);
            }
            catch (DateFormatException exception) {
                throw new XMLDocException(element.getName() + "/@" + name +
                                          " attribute: value format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以字符串的形式返回
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static String getAttributeAsString(Element element,
                                              String name,
                                              boolean optional)
        throws XMLDocException {
        return getAttribute(element, name, optional);
    }
 
    /**
     * 得到某个节点下的属性信息，值以整数的形式返回。
     * 如果没有值或是转化为整形，那么抛出异常。
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static int getAttributeAsInt(Element element,
                                        String name,
                                        boolean optional)
        throws XMLDocException {
        try {
            return Integer.parseInt(getAttribute(element, name, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() + "/@" + name +
                                      " attribute: value format error.",
                                      exception);
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以整数的形式返回。
     * 如果该值是可选的，并且没有该值的话，就返回调用者提供缺省值。
     * @param element 节点
     * @param name 属性名
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static int getAttributeAsInt(Element element,
                                        String name,
                                        int defaultValue,
                                        boolean optional)
        throws XMLDocException {
        String value = getAttribute(element, name, optional);
        if ( (optional) && ( (value == null) || (value.equals("")))) {
            return defaultValue;
        }
        else {
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() + "/@" + name +
                                          " attribute: value format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以float的形式返回。
     * 如果没有值或是转化为float，那么抛出异常。
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static float getAttributeAsFloat(Element element,
                                            String name,
                                            boolean optional)
        throws XMLDocException {
        try {
            return Float.parseFloat(getAttribute(element, name, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() + "/@" + name +
                                      " attribute: value format error.",
                                      exception);
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以float的形式返回。
     * 如果没有值,返回缺省值；如果有，那么转化为float，如果不能转化那么抛出异常。
     * @param element 节点
     * @param name 属性名
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static float getAttributeAsFloat(Element element,
                                            String name,
                                            float defaultValue,
                                            boolean optional)
        throws XMLDocException {
        String value = getAttribute(element, name, optional);
        if ( (optional) && ( (value == null) || (value.equals("")))) {
            return defaultValue;
        }
        else {
            try {
                return Float.parseFloat(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() + "/@" + name +
                                          " attribute: value format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以长整数的形式返回。
     * 如果没有值或是转化为整形，那么抛出异常。
     * @param element 节点
     * @param name 属性名
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static long getAttributeAsLong(Element element,
                                          String name,
                                          boolean optional)
        throws XMLDocException {
        try {
            return Long.parseLong(getAttribute(element, name, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() + "/@" + name +
                                      " attribute: value format error.",
                                      exception);
        }
    }
 
    /**
     * 得到某个节点下的属性信息，值以整数的形式返回。
     * 如果该值是可选的，并且没有该值的话，就返回调用者提供缺省值。
     * @param element 节点
     * @param name 属性名
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static long getAttributeAsLong(Element element,
                                          String name,
                                          long defaultValue,
                                          boolean optional)
        throws XMLDocException {
        String value = getAttribute(element, name, optional);
        if ( (optional) && ( (value == null) || (value.equals("")))) {
            return defaultValue;
        }
        else {
            try {
                return Long.parseLong(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() + "/@" + name +
                                          " attribute: value format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到某个节点下的某名字的第一个孩子节点
     * @param element 节点
     * @param name 子节点名称
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static Element getFirstChild(Element element,
                                        String name,
                                        boolean optional)
        throws XMLDocException {
        java.util.List<?> list = element.elements(new QName(name,
            element.getNamespace()));
        //如果数目大于0，那么直接取第一个就可以了
        if (list.size() > 0) {
            return (Element) list.get(0);
        }
        else {
            if (!optional) {
                throw new XMLDocException(name +
                    " element expected as first child of " +
                    element.getName() + ".");
            }
            else {
                return null;
            }
        }
    }
 
    /**
     * 得到同名兄弟节点,同名的第一个节点，可以是自己
     * @param element 节点
     * @param optional 是否是可选的
     * @return 节点
     * @throws XMLDocException
     */
    public static Element getSibling(Element element, boolean optional)
        throws XMLDocException {
        return getSibling(element, element.getName(), optional);
    }
 
    /**
     * 按名称得到兄弟节点
     * @param element 节点
     * @param name 子节点名称
     * @param optional 是否是可选的
     * @return 节点
     * @throws XMLDocException
     */
    public static Element getSibling(Element element,
                                     String name,
                                     boolean optional)
        throws XMLDocException {
        List<?> list = element.getParent().elements(name);
        if (list.size() > 0) {
            return (Element) list.get(0);
        }
        else {
            if (!optional) {
                throw new XMLDocException(name + " element expected after " +
                                          element.getName() + ".");
            }
            else {
                return null;
            }
        }
    }
 
    /**
     * 得到给定节点的值,以字符串返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static String getContent(Element element, boolean optional)
        throws XMLDocException {
        String content = null;
        if(element!=null)
             content =element.getText();
        if (content == null && !optional) {
            if(element!=null)
                throw new XMLDocException(element.getName() +
                                      " element: content expected.");
            else
                return null;
        } else {
            return content;
        }
    }
 
    /**
     * 得到给定节点的值,以字符串返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static String getContentAsString(Element element, boolean optional)
        throws XMLDocException {
        return getContent(element, optional);
    }
 
    /**
     * 得到给定节点的值,以整数类型返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static int getContentAsInt(Element element, boolean optional)
        throws XMLDocException {
        try {
            return Integer.parseInt(getContent(element, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() +
                                      " element: content format error.",
                                      exception);
        }
    }
 
    /**
     * 得到给定节点的值,以整数类型返回
     * @param element 节点
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static int getContentAsInt(Element element,
                                      int defaultValue,
                                      boolean optional)
        throws XMLDocException {
        String value = getContent(element, optional);
        if ( (optional) && (value == null || value.equals(""))) {
            return defaultValue;
        }
        else {
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() +
                                          " element: content format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到给定节点的值,以长整类型返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static long getContentAsLong(Element element, boolean optional)
        throws XMLDocException {
        try {
            return Long.parseLong(getContent(element, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() +
                                      " element: content format error.",
                                      exception);
        }
    }
 
    /**
     * 得到给定节点的值,以整数类型返回
     * @param element 节点
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static long getContentAsLong(Element element,
                                      long defaultValue,
                                      boolean optional)
        throws XMLDocException {
        String value = getContent(element, optional);
        if ( (optional) && (value == null || value.equals(""))) {
            return defaultValue;
        }
        else {
            try {
                return Long.parseLong(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() +
                                          " element: content format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到给定节点的值,以浮点类型返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static float getContentAsFloat(Element element, boolean optional)
        throws XMLDocException {
        try {
            return Float.parseFloat(getContent(element, optional));
        }
        catch (NumberFormatException exception) {
            throw new XMLDocException(element.getName() +
                                      " element: content format error.",
                                      exception);
        }
    }
 
    /**
     * 得到给定节点的值,以浮点类型返回
     * @param element 节点
     * @param defaultValue 缺省值
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static float getContentAsFloat(Element element,
                                          float defaultValue,
                                          boolean optional)
        throws XMLDocException {
        String value = getContent(element, optional);
        if ( (optional) && (value == null || value.equals(""))) {
            return defaultValue;
        }
        else {
            try {
                return Float.parseFloat(value);
            }
            catch (NumberFormatException exception) {
                throw new XMLDocException(element.getName() +
                                          " element: content format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 得到给定节点的值,以日期类型返回
     * @param element 节点
     * @param optional 是否是可选的
     * @return 值
     * @throws XMLDocException
     */
    public static java.util.Date getContentAsDate(Element element,
                                                  boolean optional)
        throws XMLDocException {
        String value = getContent(element, optional);
        if ( (optional) && (value == null || value.equals(""))) {
            return null;
        }
        else {
            try {
                return DateUtil.getInstance().parse(value, !optional);
            }
            catch (DateFormatException exception) {
                throw new XMLDocException(element.getName() +
                                          " element: content format error.",
                                          exception);
            }
        }
    }
 
    /**
     * 给定父节点和子节点名称，得到子节点值
     * @param root 父节点
     * @param subTagName 子节点
     * @return 值
     */
    public static String getSubTagValue(Element root, String subTagName) {
        String returnString = root.elementText(subTagName);
        return returnString;
    }
 
    /**
     * 给定父节点，子节点名称，孙节点名称；得到值
     * @param root   父节点
     * @param tagName 子节点名称
     * @param subTagName 孙节点名称
     * @return 值
     */
    public static String getSubTagValue(Element root,
                                        String tagName,
                                        String subTagName) {
        Element child = root.element(tagName);
        String returnString = child.elementText(subTagName);
        return returnString;
    }
 
    /**
     * 新Element节点，值为String类型
     * @param parent 父节点
     * @param name 新节点名称
     * @param value 新节点值
     * @return element
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent,
                                      String name,
                                      String value) {
        Element element = parent.addElement(new QName(name, parent.getNamespace()));
        if (value != null) {
            element.addText(value);
        }
        return element;
    }
 
    /**
     * 增加新Element节点，无值
     * @param parent 父节点
     * @param name 新节点名称
     * @return Element 新建节点
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent, String name) {
        return parent.addElement(new QName(name, parent.getNamespace()));
    }
 
    /**
     * 增加新Element节点，值为int类型
     * @param parent 父节点
     * @param name 新节点名称
     * @param value 新节点值
     * @return element
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent,
                                      String name,
                                      int value) {
        return appendChild(parent, name, String.valueOf(value));
    }
 
    /**
     * 增加新Element节点，值为长整形
     * @param parent 父节点
     * @param name 新节点名称
     * @param value 新节点值
     * @return element
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent,
                                      String name,
                                      long value) {
        return appendChild(parent, name, String.valueOf(value));
    }
 
    /**
     * 新加一个float值类型的节点，值为浮点型
     * @param parent 父节点
     * @param name 新节点的名称
     * @param value 新节点的值
     * @return element
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent,
                                      String name,
                                      float value) {
        return appendChild(parent, name, String.valueOf(value));
    }
 
    /**
     * 增加新Element节点，值为日期型
     * @param parent 父节点
     * @param name 新节点名称
     * @param value 新节点值
     * @return element
     * @throws XMLDocException
     */
    public static Element appendChild(Element parent,
                                      String name,
                                      java.util.Date value) {
        return appendChild(parent, name,
                           DateUtil.getInstance().format(value));
    }
 
    /**
     * 检查文档dtd定义是否正确
     * @param document 文档节点
     * @param dtdPublicId dtd定义
     * @return boolean  相同返回true,否则false
     */
    public static boolean checkDocumentType(Document document,
                                            String dtdPublicId) {
        DocumentType documentType = document.getDocType();
        if (documentType != null) {
            String publicId = documentType.getPublicID();
            return publicId != null && publicId.equals(dtdPublicId);
        }
        return true;
    }
 
    /**
     * 新建文档
     * @return Document  文档节点
     * @throws XMLDocException
     */
    public static Document createDocument()
        throws XMLDocException {
        DocumentFactory factory = new DocumentFactory();
        Document document = factory.createDocument();
        return document;
    }
 
    /**
     * 通过Reader读取Document文档
     * 如果encodingStr为null或是""，那么采用缺省编码GB2312
     * @param in Reader器
     * @param encoding 编码器
     * @return documment
     * @throws XMLDocException
     */
    public static Document fromXML(Reader in, String encoding)
        throws XMLDocException {
        try {
            if (encoding == null || encoding.equals("")) {
                encoding = DEFAULT_ENCODING;
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(in, encoding);
            return document;
        }
        catch (Exception ex) {
            throw new XMLDocException(ex);
        }
    }
 
    /**
     * 给定输入流读取XML的Document。
     * 如果encodingStr为null或是""，那么采用缺省编码GB2312
     * @param inputSource 输入源
     * @param encoding 编码器
     * @return document
     * @throws XMLDocException
     */
    public static Document fromXML(InputStream inputSource, String encoding)
        throws XMLDocException {
        try {
            if (encoding == null || encoding.equals("")) {
                encoding = DEFAULT_ENCODING;
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputSource, encoding);           
            return document;
        }
        catch (Exception ex) {
            throw new XMLDocException(ex);
        }
    }
 
    /**
     * 直接从字符串得到XML的Document
     * @param source 把一个字符串文本转化为XML的Document对象
     * @param encoding 编码器
     * @return <code>Document</code>
     * @throws XMLDocException
     */
    public static Document fromXML(String source, String encoding)
        throws XMLDocException {
        return fromXML(new StringReader(source), encoding);
    }
 
    /**
     * 把XML的Document转化为java.io.Writer输出流
     * 不支持给定Schema文件的校验
     * @param document XML文档
     * @param outWriter 输出写入器
     * @param encoding 编码类型
     * @throws XMLDocException 如果有任何异常转化为该异常输出
     */
    public static void toXML(Document document, java.io.Writer outWriter,
                             String encoding)
        throws XMLDocException {
        //
        OutputFormat outformat = OutputFormat.createPrettyPrint();
        if (encoding == null || encoding.trim().equals("")) {
            encoding = DEFAULT_ENCODING;
        }
        //设置编码类型
        outformat.setEncoding(encoding);
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(outWriter, outformat);
            xmlWriter.write(document);
            xmlWriter.flush();
        }
        catch (java.io.IOException ex) {
            throw new XMLDocException(ex);
        }
        finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                }
                catch (java.io.IOException ex) {
                }
            }
        }
    }
 
    /**
     * 把XML的Document转化为java.io.Writer输出流
     * 不支持给定Schema文件的校验
     * @param document XML文档
     * @param outStream 输出写入器
     * @param encoding 编码类型
     * @throws XMLDocException 如果有任何异常转化为该异常输出
     */
    public static void toXML(Document document, java.io.OutputStream outStream,
                             String encoding)
        throws XMLDocException {
        //
        OutputFormat outformat = OutputFormat.createPrettyPrint();
        if (encoding == null || encoding.trim().equals("")) {
            encoding = DEFAULT_ENCODING;
        }
        //设置编码类型
        outformat.setEncoding(encoding);
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(outStream, outformat);
            xmlWriter.write(document);
            xmlWriter.flush();
        }
        catch (java.io.IOException ex) {
            throw new XMLDocException(ex);
        }
        finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                }
                catch (java.io.IOException ex) {
                }
            }
        }
    }
 
    /**
     * 把XML文档转化为String返回
     * @param document 要转化的XML的Document
     * @param encoding 编码类型
     * @return <code>String</code>
     * @throws XMLDocException 如果有任何异常转化为该异常输出
     */
    public static String toXML(Document document, String encoding)
        throws XMLDocException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        toXML(document, stream, encoding);
        if (stream != null) {
            try {
                stream.close();
            }
            catch (java.io.IOException ex) {
            }
        }
        return stream.toString();
    }
 
    //自测试代码
    public static void main(String[] args) {
        try {
            String ss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<address><name id=\"100.001\">莫愁新寓郁金里</name><city>南京</city><state>江苏</state><sysDate>2003-07-05 12:53:24</sysDate><intValue>100</intValue><tt><aa>AA</aa><bb>BB</bb></tt></address>";
 
            System.out.println("开始时间:" + System.currentTimeMillis());
            Document document = XMLUtil.fromXML(ss, null);
            Element root = document.getRootElement();
             
            List<?> ll = root.elements();
            System.out.println("count:" + ll.size());
             
            Element eee = (Element)ll.get(5);
            Document testDoc = XMLUtil.createDocument();
            testDoc.add((Node)eee.clone());
            System.out.println("testDoc:" + testDoc.asXML());
             
            Node node = document.selectSingleNode("//address/name");
            System.out.println(node.valueOf("@id"));    
            System.out.println(((Element)node).getName());
            System.out.println(((Element)node).getText());
             
            System.out.println(root.getName());
 
            System.out.println("中间时间:" + System.currentTimeMillis());
 
            Element element = root.element("name");
            element.setText("咖啡色卡发大");
            System.out.println(XMLUtil.getContentAsString(element, false));
            System.out.println(XMLUtil.getAttributeAsFloat(element, "id", false));
            System.out.println("id:" + element.attributeValue("id"));
            System.out.println(XMLUtil.getAttributeAsFloat(element, "id1",
                0, true));
 
            System.out.println(XMLUtil.getContentAsString(root.element(
                "city"), false));
            System.out.println(XMLUtil.getContentAsString(root.element(
                "state"), false));
            System.out.println(XMLUtil.getContentAsDate(root.element(
                "sysDate"), false));
            System.out.println(XMLUtil.getContentAsInt(root.element(
                "intValue"), false));
            System.out.println(XMLUtil.getSubTagValue(root, "intValue"));
             
            System.out.println(document.asXML());
 
            Document doc = XMLUtil.createDocument();
            root = doc.addElement("address");
            //XMLUtil.appendChild(root, "name", "莫愁新寓郁金里");
            root.addElement("Test").setText("test");
             
            Element items = XMLUtil.appendChild(root, "items");
            items.addAttribute("ttt", "ggg");
            Element ee = XMLUtil.appendChild(items, "item1", "");
             
            //CharacterData cd = new FlyweightText("tttDDD");
            //ee.add(cd);
             
            CDATA cddata = new FlyweightCDATA("HHHHH");
            ee.add(cddata);
            CDATA cddata1 = new FlyweightCDATA("JJJJJ");
            ee.add(cddata1);
             
            System.out.println("CDATA:" + ee.getText());
             
            XMLUtil.appendChild(items, "item2", "bbb");
            XMLUtil.appendChild(root, "city", "南京");
            XMLUtil.appendChild(root, "state", "江苏");
            XMLUtil.appendChild(root, "sysDate", new java.util.Date());
            XMLUtil.appendChild(root, "intValue", 100);
            System.out.println(XMLUtil.toXML(doc, null));
            System.out.println(doc.asXML());
            System.out.println("..................");
             
             
            List<?> list = root.elements();
            for(int i=0; i<list.size(); i++){
                //Element e = (Element)list.get(i);
                //add your code here
            }
             
            System.out.println(list.size());
             
            Document doc1 = XMLUtil.createDocument();
            XMLUtil.appendChild(doc1.addElement("result"), "row", "aaaaa");
            System.out.println(doc1.asXML());
            Node node1 = doc1.selectSingleNode("//result");
            System.out.println("aaaaaaaaaaaaaaaaaaaaa");
            System.out.println(node1.asXML());
             
            Document doc2 = XMLUtil.fromXML(node1.asXML(), null);
            System.out.println(doc2.asXML());
             
            root.add(node1);
            XMLUtil.appendChild(root, "rowCount", 1);
            System.out.println("..................");
            System.out.println(doc.asXML());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
}
