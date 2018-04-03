package com.hz.tgb.json;

import java.io.IOException;  
import java.io.StringReader;  
import java.io.StringWriter;  
  
import javax.xml.stream.XMLEventReader;  
import javax.xml.stream.XMLEventWriter;  
import javax.xml.stream.XMLInputFactory;  
import javax.xml.stream.XMLOutputFactory;  
  
import de.odysseus.staxon.json.JsonXMLConfig;  
import de.odysseus.staxon.json.JsonXMLConfigBuilder;  
import de.odysseus.staxon.json.JsonXMLInputFactory;  
import de.odysseus.staxon.json.JsonXMLOutputFactory;  
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;  
  
/** 
 * json and xml converter 
 * @author hezhao
 * @see https://github.com/beckchr/staxon 
 * @see https://github.com/beckchr/staxon/wiki 
 * 
 */  
public class StaxonUtil {  
      
    /** 
     * json string convert to xml string 
     */  
    public static String json2xml(String json){  
        StringReader input = new StringReader(json);  
        StringWriter output = new StringWriter();  
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();  
        try {  
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);  
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);  
            writer = new PrettyXMLEventWriter(writer);  
            writer.add(reader);  
            reader.close();  
            writer.close();  
        } catch( Exception e){  
            e.printStackTrace();  
        } finally {  
            try {  
                output.close();  
                input.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        if(output.toString().length()>=38){//remove <?xml version="1.0" encoding="UTF-8"?>  
            return output.toString().substring(39);  
        }  
        return output.toString();  
    }  
      
    /** 
     * xml string convert to json string 
     */  
    public static String xml2json(String xml){  
        StringReader input = new StringReader(xml);  
        StringWriter output = new StringWriter();  
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();  
        try {  
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);  
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);  
            writer.add(reader);  
            reader.close();  
            writer.close();  
        } catch( Exception e){  
            e.printStackTrace();  
        } finally {  
            try {  
                output.close();  
                input.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return output.toString();  
    }  
}