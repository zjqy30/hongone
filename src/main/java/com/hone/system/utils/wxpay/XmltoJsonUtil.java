package com.hone.system.utils.wxpay;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

public class XmltoJsonUtil {  
       
     /** 
      * XML格式字符串转换为Map 
      * 
      * @param strXML XML字符串 
      * @return XML数据转换后的Map 
      * @throws Exception 
      */  
     public static Map<String, String> xmlToMap(String strXML) throws Exception {  
         try {  
             Map<String, String> data = new HashMap<String, String>();  
             DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
             String FEATURE = null;
             FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
             documentBuilderFactory.setFeature(FEATURE, true);
             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();  
             InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));  
             org.w3c.dom.Document doc = documentBuilder.parse(stream);  
             doc.getDocumentElement().normalize();  
             NodeList nodeList = doc.getDocumentElement().getChildNodes();  
             for (int idx = 0; idx < nodeList.getLength(); ++idx) {  
                 Node node = nodeList.item(idx);  
                 if (node.getNodeType() == Node.ELEMENT_NODE) {  
                     org.w3c.dom.Element element = (org.w3c.dom.Element) node;  
                     data.put(element.getNodeName(), element.getTextContent());  
                 }  
             }  
             try {  
                 stream.close();  
             } catch (Exception ex) {  
                 // do nothing  
             }  
             return data;  
         } catch (Exception ex) {  
             throw ex;  
         }  
     }  
  

   //拼接xml 请求路径  
    @SuppressWarnings("rawtypes")
 	public static String getRequestXML(SortedMap<Object, Object> parame,String sign){  
         StringBuffer buffer = new StringBuffer();  
         buffer.append("<xml>");  
         Set<Entry<Object, Object>> set = parame.entrySet();  
         Iterator iterator = set.iterator();  
         while(iterator.hasNext()){  
             Entry entry = (Entry) iterator.next();
             String key = (String)entry.getKey();  
             String value = String.valueOf(entry.getValue());
             //过滤相关字段sign  
             if(!"sign".equalsIgnoreCase(key)){  
                 buffer.append("<"+key+">"+"<![CDATA["+value+"]]>"+"</"+key+">");  
             }else{  
                 buffer.append("<"+key+">"+value+"</"+key+">");  
             }             
         }  
         buffer.append("<sign>"+sign+"</sign>");  
//         buffer.append("<sign>"+"<![CDATA["+sign+"]]>"+"</sign>");  
         buffer.append("</xml>");  
         return buffer.toString();  
     }
}