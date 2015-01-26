package com.foresee.test.demo.testng;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParserXml {

    public List<Map<String, Map<String, String>>> parser3Xml(String fileName) {
        File inputXml = new File(fileName);    
        List<Map<String, Map<String, String>>> list=new ArrayList<Map<String, Map<String, String>>>();                
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator<?> i = employees.elementIterator(); i.hasNext();) {
                Element employee = (Element) i.next();
                Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
                Map<String, String> tempMap = new HashMap<String, String>();
                for (Iterator<?> j = employee.elementIterator(); j.hasNext();) {
                    Element node = (Element) j.next();                    
                    tempMap.put(node.getName(), node.getText());                    
                }
                map.put(employee.getName(), tempMap);
                list.add(map);
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }    
     

}