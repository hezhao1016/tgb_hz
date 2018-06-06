package com.hz.tgb.xml;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 
/**
 * XML工具类
 * 
 * @author hezhao
 * @since 2014-04-25
 */
public class XmlUtil {
	
	/**
	 * DOM4j解析XML，获取对应节点的值（不含标签）(只适用单（一级）节点)。 
	 * @param protocolXML 需要解析的xml
	 * @param name 节点名
	 * @return 节点的值（不含标签），如果节点内包含子节点，返回空字符串。
	 */
    public static String getValue(String protocolXML,String name) 
    {
    	String value="";
        try {   
             Document doc=(Document)DocumentHelper.parseText(protocolXML);   	             
             Element root = doc.getRootElement(); 
             for(Iterator it=root.elementIterator();it.hasNext();){     
                 Element element = (Element) it.next();
                 if(element.getName().equals(name)){
                	 value=element.getTextTrim();
                	 break;
                 }		
             }  		            
         } catch (Exception e) {   
             e.printStackTrace();   
         }
		return value;           
     } 
    
    /**
     * DOM4j解析XML，获取对应节点的内容（包含标签）(只适用单（一级）节点)。 
     *
     * @param protocolXML
     * @param name
     * @return 节点的xml内容（包含标签）
     */
    public static String getXML(String protocolXML,String name) 
    {
    	String value="";
        try {   
             Document doc=(Document)DocumentHelper.parseText(protocolXML);   	             
             Element root = doc.getRootElement(); 
             for(Iterator it=root.elementIterator();it.hasNext();){     
                 Element element = (Element) it.next();
                 if(element.getName().equals(name)){
                	 value=element.asXML();
                	 break;
                 }		
             }  		            
         } catch (Exception e) {   
             e.printStackTrace();   
         }
		return value;           
     } 
	
    /**
     * 获取根节点
     * 
     * @param doc
     * @return
     */
    public static Element getRootElement(Document doc) {
        if (Objects.isNull(doc)) {
            return null;
        }
        return doc.getRootElement();
    }
 
    /**
     * 获取节点eleName下的文本值，若eleName不存在则返回默认值defaultValue
     * 
     * @param eleName
     * @param defaultValue
     * @return
     */
    public static String getElementValue(Element eleName, String defaultValue) {
        if (Objects.isNull(eleName)) {
            return defaultValue == null ? "" : defaultValue;
        } else {
            return eleName.getTextTrim();
        }
    }
 
    /**
     * 获取节点的值
     * @author hezhao
     * @Time   2017年8月1日 上午11:31:57
     * @param eleName
     * @param parentElement
     * @return
     */
    public static String getElementValue(String eleName, Element parentElement) {
        if (Objects.isNull(parentElement)) {
            return null;
        } else {
            Element element = parentElement.element(eleName);
            if (Objects.isNotNull(element)) {
                return element.getTextTrim();
            } else {
                try {
                    throw new Exception("找不到节点" + eleName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
 
    /**
     * 获取节点eleName下的文本值
     * 
     * @param eleName
     * @return
     */
    public static String getElementValue(Element eleName) {
        return getElementValue(eleName, null);
    }
 
    public static Document findCDATA(Document body, String path) {
        return XmlUtil.stringToXml(XmlUtil.getElementValue(path,
                body.getRootElement()));
    }
    
    /**
     * 读取文档
     * @author hezhao
     * @Time   2017年8月1日 上午11:31:50
     * @param file
     * @return
     */
    public static Document read(File file) {
    	return read(file, null);
    }
    
    /**
     * 读取文档
     * @author hezhao
     * @Time   2017年8月1日 上午11:31:37
     * @param xmlPath
     * @return
     */
    public static Document read(String xmlPath) {
    	return read(new File(xmlPath));
    }
 
    /**
     * 读取文档
     * @param file
     * @param charset 字符编码
     * @return
     * @throws DocumentException
     */
    public static Document read(File file, String charset) {
        if (Objects.isNull(file)) {
            return null;
        }
        SAXReader reader = new SAXReader();
        if (Objects.isNotNull(charset)) {
            reader.setEncoding(charset);
        }
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
 
    public static Document read(URL url) {
        return read(url, null);
    }
 
    /**
     * 读取URL上的文档
     * @param url
     * @param charset
     * @return
     * @throws DocumentException
     */
    public static Document read(URL url, String charset) {
        if (Objects.isNull(url)) {
            return null;
        }
        SAXReader reader = new SAXReader();
        if (Objects.isNotNull(charset)) {
            reader.setEncoding(charset);
        }
        Document document = null;
        try {
            document = reader.read(url);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /** 
	 * 解析文件，获得根元素
	 * @param xmlPath
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static Element parse(String xmlPath,String encoding)throws Exception{
		//文件是否存在
		File file = new File(xmlPath);
        if(!file.exists()){
        	throw new Exception("找不到xml文件："+xmlPath);
        }
        
		//解析
		SAXReader reader = new SAXReader(false);
		Document doc = reader.read(new FileInputStream(file),encoding);
		Element root = doc.getRootElement();
		return root;
	}
	
	/**
	 * 字符串转换成xml
	 * @author hezhao
	 * @Time   2017年8月1日 上午11:29:45
	 * @param text
	 * @return
	 */
    public static Document stringToXml(String text) {
        try {
            return DocumentHelper.parseText(text);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 将文档树转换成字符串
     * 
     * @param doc
     * @return
     */
    public static String xmltoString(Document doc) {
        return xmltoString(doc, null);
    }
 
    /**
     * xml转换成字符串
     * @param doc
     * @param charset
     * @return
     * @throws IOException
     */
    public static String xmltoString(Document doc, String charset) {
        if (Objects.isNull(doc)) {
            return "";
        }
        if (Objects.isNull(charset)) {
            return doc.asXML();
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter strWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(strWriter, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strWriter.toString();
    }
 
    /**
     * 保存xml
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, File file, String charset)
            throws Exception {
        if (Objects.isNull(doc)) {
            throw new NullPointerException("doc cant not null");
        }
        if (Objects.isNull(charset)) {
            throw new NullPointerException("charset cant not null");
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        FileOutputStream os = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(os, charset);
        XMLWriter xmlWriter = new XMLWriter(osw, format);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
            if (osw != null) {
                osw.close();
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 保存xml
     * @param doc
     * @param charset
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static void xmltoFile(Document doc, String filePath, String charset)
            throws Exception {
        xmltoFile(doc, new File(filePath), charset);
    }
    
    /**
	 * 修改xml某节点的值
	 * @param inputXml 原xml文件
	 * @param nodes 要修改的节点
	 * @param attributename 属性名称
	 * @param value 新值
	 * @param outXml 输出文件路径及文件名 如果输出文件为null，则默认为原xml文件
	 */
	public static void modifyDocument(File inputXml, String nodes, String attributename, String value, String outXml) {
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(inputXml);
			List list = document.selectNodes(nodes);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Attribute attribute = (Attribute) iter.next();
				if (attribute.getName().equals(attributename))
					attribute.setValue(value);
			}
			XMLWriter output;
			if (outXml != null){ //指定输出文件
				output = new XMLWriter(new FileWriter(new File(outXml)));
			}else{ //输出文件为原文件
				output = new XMLWriter(new FileWriter(inputXml));
			}
			output.write(document);
			output.close();
		}

		catch (DocumentException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}	
 
    /**
     * 创建文档
     * @author hezhao
     * @Time   2017年8月1日 上午10:21:33
     * @return
     */
    public static Document createDocument() {
        return DocumentHelper.createDocument();
    }
     
    public static void main(String[] args) {
        // System.out.println(XmlTool.xmltoString(Disconnect.getDisconnectDocument(),
        // "UTF-8"));
    }
    
    
    
     /**
     * 判断对象是否为空工具类
     * 
     * @author lihaoguo
     * @since 2014-05-01
     */
    static class Objects
    {
     
        public static boolean isNull(Object obj)
        {
            return obj == null;
        }
     
        public static boolean isNotNull(Object obj)
        {
            return !isNull(obj);
        }
     
        @SuppressWarnings("rawtypes")
        public static boolean isEmpty(Object obj)
        {
            if (obj == null)
            {
                return true;
            }
            if (obj instanceof CharSequence)
            {
                return ((CharSequence) obj).length() == 0;
            }
            if (obj instanceof Collection)
            {
                return ((Collection) obj).isEmpty();
            }
            if (obj instanceof Map)
            {
                return ((Map) obj).isEmpty();
            }
            if (obj.getClass().isArray())
            {
                return Array.getLength(obj) == 0;
            }
            return false;
        }
     
        public static boolean isNotEmpty(Object obj)
        {
            return !isEmpty(obj);
        }
    }
}