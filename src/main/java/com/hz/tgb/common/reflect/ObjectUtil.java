package com.hz.tgb.common.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
  
/** 
 * @说明 对象操纵高级方法 
 * @author hezhao
 * @version 1.0 
 * @since 
 */  
public class ObjectUtil {  
      
    /** 
     * 复制对象obj，类似于值传递，非引用 
     */    
    public synchronized static Object cloneObject(Object obj) throws Exception{     
        ByteArrayOutputStream  byteOut = new ByteArrayOutputStream();       
        ObjectOutputStream out = new ObjectOutputStream(byteOut);       
        out.writeObject(obj);              
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());       
        ObjectInputStream in =new ObjectInputStream(byteIn);             
        return in.readObject();     
    }  
      
    /**   
     * 返回一个对象的属性和属性值的JSON字符串 
     * 返回格式({id:1},{name:cc},{pass:null}) 
     */    
    @SuppressWarnings("unchecked")     
    public synchronized static String getProperty(Object entityName) {   
        StringBuffer sb = new StringBuffer("");  
        try {  
            Class c = entityName.getClass();     
            Field field[] = c.getDeclaredFields();     
            for (Field f : field) {     
                Object v = invokeMethod(entityName, f.getName(), null);  
                sb.append("{" + f.getName() + ":" + v + "},");  
            }  
            if(sb.length() > 0){  
                sb.delete(sb.length() - 1, sb.length());  
            }  
        } catch (Exception e) {  
            sb = new StringBuffer("");  
        }  
        return sb.toString();  
    }    
  
    /** 
     * 获得对象属性的值 
     */  
    @SuppressWarnings("unchecked")  
    private synchronized static Object invokeMethod(Object owner, String methodName,  
            Object[] args) throws Exception {  
        Class ownerClass = owner.getClass();  
        methodName = methodName.substring(0, 1).toUpperCase()  
                + methodName.substring(1);  
        Method method = null;  
        try {  
            method = ownerClass.getMethod("get" + methodName);  
        } catch (SecurityException e) {  
        } catch (NoSuchMethodException e) {  
            return " can't find 'get" + methodName + "' method";  
        }  
        return method.invoke(owner);  
    }  
      
    /**     
     * 返回一个对象的属性和属性值 
     */       
    @SuppressWarnings("unchecked")        
    public synchronized static LinkedHashMap<String,String> getPropertyMap(Object entityName) {      
        LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();     
        try {     
            Class c = entityName.getClass();     
            // 获得对象属性     
            Field field[] = c.getDeclaredFields();        
            for (Field f : field) {        
                Object v = invokeMethod(entityName, f.getName(), null);     
                map.put(f.getName(), v.toString());     
            }     
        } catch (Exception e) {     
            map = null;     
        }     
        return map;     
    }     
}  