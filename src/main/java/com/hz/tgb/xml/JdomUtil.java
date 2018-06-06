package com.hz.tgb.xml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JDOM XML解析工具类
 *
 * @author hezhao
 * @Time 2017年8月16日 下午4:45:50
 * @Description 无
 * @Version V 1.0
 */
public class JdomUtil {

    /**
     * 解析XML文本转为MAP。
     * 可以指定只解析ROOT元素下一级的某些元素的内容，如果不指定则解析所有ROOT元素下的内容。
     * @param xmlStr XML文本
     * @param flags  需要做解析的二级元素名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> returnXMLData(String xmlStr,String...flags){
        Map<String,String> xmlData= new HashMap<String, String>();
        Element root = parseXML(xmlStr);

        if(root != null){
            if(flags == null || flags.length == 0) {
                xmlData = parseXMLNode(root);
            }else{
                List<Element> children = (List<Element>)root.getChildren();
                for (Element child : children) {
                    if(ArrayUtils.contains(flags, child.getName())){
                        xmlData.putAll(parseXMLNode(child));
                    }
                }
            }
        }
        return xmlData;
    }

    /**
     * 将XML文本转为XML元素对象
     * resXml：XML数据流
     * root：最终得到的根元素
     * */
    private static Element parseXML(String resXml){
        SAXBuilder sb=new SAXBuilder();  //初始化SAXBuilder
        //创建一个新的字符串
        StringReader read = new StringReader(resXml);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource inSource = new InputSource(read);
        Document doc=null;  //定义一个Document文本对象
        Element root=null;  //定义一个Element根元素对象
        try {
            doc=sb.build(inSource);     //得到并构建XML文档对象
        } catch (JDOMException e1) {
            System.out.println("解析文本报错");
            e1.printStackTrace();
        } catch (IOException e1) {
            System.out.println("获取输入流报错");
            e1.printStackTrace();
        }

        if(doc!=null){
            root=doc.getRootElement();  //得到XML的根元素
        }else{
            System.out.println("解析root出错");
        }
        return root;
    }

    /**
     * 解析XML元素下的各节点，转为MAP
     * @param e XML元素
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Map<String,String> parseXMLNode(Element e) {
        Map<String, String> result = new HashMap<String, String>();
        Element child = null;   //定义一个Element元素对象

        //下面开始迭代循环
        for (Iterator childs = e.getChildren().iterator(); childs.hasNext(); ) {
            child = (Element) childs.next(); //获取节点下的每一个子元素
            result.put(child.getName(), child.getValue()); //将每一个元素的名称和值都保存到HashMap中，方便以后查询取出
        }
        return result;
    }



    /**
     * @param xmlStr
     * @param multiNode
     * @param flags
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,String> returnXMLDataMulti(String xmlStr,String multiNode,String...flags){
        Map<String,String> xmlData= new HashMap<String, String>();
        Element root = parseXML(xmlStr);

        if(root != null){
            if(flags == null || flags.length == 0) {
                xmlData = parseXMLNode(root);
            }else{
                List<Element> children = (List<Element>)root.getChildren();
                for (Element child : children) {
                    if(ArrayUtils.contains(flags, child.getName())){
                        xmlData.putAll(parseXMLNodeMulti(child, multiNode));
                    }
                }
            }
        }
        return xmlData;
    }

    /**
     * 处理多个相同的子节点，转换成json串
     * @param e
     * @param multiNode
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Map<String,String> parseXMLNodeMulti(Element e,String multiNode) {
        Map<String, String> result = new HashMap<String, String>();
        Element child = null;   //定义一个Element元素对象

        //下面开始迭代循环
        for (Iterator childs = e.getChildren().iterator(); childs.hasNext(); ) {
            child = (Element) childs.next(); //获取节点下的每一个子元素
            if(multiNode.equals(child.getName())){
                JSONArray jsonAll = new JSONArray();
                for(Iterator pciInfos = child.getChildren().iterator();pciInfos.hasNext();){//pciInfo节点
                    jsonAll.add(parseXMLNodeToString((Element) pciInfos.next()));
                }
                result.put(child.getName(),jsonAll.toString());
            }else{
                result.put(child.getName(), child.getValue()); //将每一个元素的名称和值都保存到HashMap中，方便以后查询取出
            }
        }
        return result;
    }

    /**
     * 处理节点内部子节点内容为json格式
     * @param e
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static JSONObject parseXMLNodeToString(Element e) {
        JSONObject jsonObj = new JSONObject();
        Element child;   //定义一个Element元素对象
        //下面开始迭代循环
        for (Iterator childs = e.getChildren().iterator(); childs.hasNext(); ) {
            child = (Element) childs.next(); //获取节点下的每一个子元素
            //将每一个元素的名称和值都保存到json中，方便以后查询取出
            jsonObj.put(child.getName(),child.getValue());
        }
        return jsonObj;
    }


}