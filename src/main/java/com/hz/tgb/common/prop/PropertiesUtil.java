package com.hz.tgb.common.prop;

import com.hz.tgb.entity.Student2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

/** 属性文件读写工具类
 * 
 * 属性文件读写是程序经常要配置的内容.从网上找一些属性文件读写的源码大多数是操作一个键值和读写所有键值的工具类封装.
本文所提供的工具类在原读写的基础上支持了自动转换为实体类对象,这样的封装带来的好处:
    1.更面向对象化, 属性文件和实体类对象无缝转化.
    2.避免了定义键名书写字符串容易书写错误的风险.
    3.扩展性好,使用简单.
    
 * Created by hezhao on 2015/12/26.
 * 
 * http://blog.csdn.net/lk_blog/article/details/50429691
 */  
public class PropertiesUtil {  
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	/** 属性文件名称 */
    private String fileName;

    private PropertiesUtil() {
        // 私有类构造方法
    }
  
    /** 
     * @param fileName 文件全路径 
     */  
    public PropertiesUtil(String fileName) throws IOException {  
        if (fileName == null) {  
            throw new RuntimeException("fileName is null,please set fileName");  
        }
        this.fileName = fileName;  
        getFile();  
    }  
  
    /** 
     * 写入实体类对应的属性值,不属于此类的配置不受影响 
     * 
     * @param clazz  类 
     * @param entity 实体对象 
     * @param <T>    泛型类 
     */  
    public <T> void saveProperties(Class<T> clazz, final T entity) throws IOException {  
        if (clazz == null) {  
            throw new RuntimeException("clazz is null");  
        }  
  
        T newEntity = entity;  
  
        if (entity == null) {  
            try {  
                newEntity = clazz.newInstance();  
            } catch (InstantiationException e) {  
                e.printStackTrace();  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            }  
        }  
  
  
        Properties pps = getAllProperties();  
        Field[] fields = clazz.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
                field.setAccessible(true);  
                Object value = "";  
                if (field.isAnnotationPresent(PropertyAttr.class)) {  
                    //如果存在注解,则使用注解中的键值.  
                    PropertyAttr prop = field.getAnnotation(PropertyAttr.class);  
                    if (entity != null) {  
                        //如果传入的原始对象不空,则取值并设置.  
                        value = field.get(newEntity) == null ? "" : field.get(newEntity);  
                        pps.setProperty(prop.key(), String.valueOf(value));  
                    } else {  
                        //如果传入的原始对象为空则使用注解配置的默认值.  
                        pps.setProperty(prop.key(), prop.defalutValue());  
                    }  
                } else {  
                    value = field.get(newEntity) == null ? "" : field.get(newEntity);  
                    pps.setProperty(field.getName(), String.valueOf(value));  
                }  
            } catch (IllegalAccessException e) {  
                e.printStackTrace();  
            }  
        }  
        FileOutputStream out = null;  
        File file = getFile();  
        try {  
            out = new FileOutputStream(file);  
            pps.store(out, "");  
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
    }  
  
    private File getFile() throws IOException {
        File file = new File(this.fileName);
        if (!file.exists()) {  
            if (!file.createNewFile()) {  
            	logger.error("create file failed!");  
            }  
        }  
        return file;  
    }  
  
    /** 
     * 读取实体属性值 
     * --如果第一次加载文件没找到则返回null对象,并生成属性文件 
     * 
     * @param clazz 类 
     * @param <T>   泛型 
     * @return 泛型实体对象 
     */  
  
    public <T> T loadProperties(Class<T> clazz) throws IOException {  
        if (clazz == null) {  
            throw new RuntimeException("clazz is null");  
        }  
  
        Properties pps = new Properties();  
        InputStream in = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(this.fileName));  
            pps.load(in);  
            if (pps.size() == 0) {  
                return null;  
            }  
        } catch (FileNotFoundException e) {  
            // resetProperties(clazz);//重置时加载默认为空的配置文件  
            e.printStackTrace();  
        } finally {  
            if (in != null) {  
                in.close();  
            }  
        }  
  
        return setEntityValue(clazz, pps);  
    }  
  
    private <T> T setEntityValue(Class<T> clazz, Properties pps) {  
        boolean isNull = true;//判断属性文件中是否包含键名,如果包含任一键名,则对象不为空.  
        try {  
            T entity = clazz.newInstance();  
            Field[] fields = clazz.getDeclaredFields();  
            for (Field field : fields) {  
                field.setAccessible(true);  
                String value = null;  
                if (field.isAnnotationPresent(PropertyAttr.class)) {  
                    //如果存在注解,则使用注解中的键值.  
                    PropertyAttr prop = field.getAnnotation(PropertyAttr.class);  
                    if (pps.containsKey(prop.key())) {  
                        isNull = false;  
                        value = pps.getProperty(prop.key(), prop.defalutValue());  
                    }  
                } else {  
                    if (pps.containsKey(field.getName())) {  
                        isNull = false;  
                        value = pps.getProperty(field.getName());  
                    }  
                }  
  
                if (value == null) {  
                    continue;  
                }  
                Class<?> fieldType = field.getType();  
                if (String.class == fieldType) {  
                    field.set(entity, String.valueOf(value));  
                } else if (Integer.TYPE == fieldType || Integer.class == fieldType) {  
                    field.set(entity, Integer.parseInt(value));  
                } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {  
                    field.set(entity, Boolean.parseBoolean(value));  
                } else if (Long.TYPE == fieldType || Long.class == fieldType) {  
                    field.set(entity, Long.valueOf(value));  
                } else if (Float.TYPE == fieldType || Float.class == fieldType) {  
                    field.set(entity, Float.valueOf(value));  
                } else if (Short.TYPE == fieldType || Short.class == fieldType) {  
                    field.set(entity, Short.valueOf(value));  
                } else if (Double.TYPE == fieldType || Double.class == fieldType) {  
                    field.set(entity, Double.valueOf(value));  
                }  
            }  
            if (isNull) {  
                return null;  
            } else {  
                return entity;  
            }  
        } catch (InstantiationException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 配置注解的值设置为默认值,未设置注解的值清空. 
     * 
     * @return 所有属性为空的对象 
     */  
    public <T> void resetProperties(Class<T> clazz) throws IOException {  
        if (clazz == null) {  
            throw new RuntimeException("clazz is null");  
        }  
        saveProperties(clazz, null);  
    }  
  
    /** 
     * 清空所有属性值 
     */  
    public void clearProperties() throws IOException {  
        Properties pps = new Properties();  
        pps.clear();  
        FileOutputStream out = null;  
        File file = getFile();  
        try {  
            out = new FileOutputStream(file);  
            pps.store(out, "");  
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
    }  
  
    /** 
     * 取出键名的值 
     * 
     * @param key 
     * @return 
     */  
    public String getValueByKey(String key) throws IOException {  
        Properties pps = new Properties();  
        InputStream in = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(fileName));  
            pps.load(in);  
            String value = pps.getProperty(key);  
//            logger.debug(key + " = " + value);  
            return value;  
        } finally {  
            if (in != null) {  
                in.close();  
            }  
        }  
    }  
  
    /** 
     * 设置键名和值 
     * 
     * @param key 
     * @param value 
     */  
    public void setValueByKey(String key, String value) throws IOException {  
        Properties pps = new Properties();  
  
        InputStream in = null;  
        FileOutputStream out = null;  
        try {  
            in = new FileInputStream(fileName);  
            //从输入流中读取属性列表（键和元素对）  
            pps.load(in);  
        } finally {  
            if (in != null) {  
                in.close();  
            }  
        }  
  
        try {  
            out = new FileOutputStream(fileName);  
            pps.setProperty(key, value);  
            pps.store(out, "update " + key + " = " + value + " success!");  
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
  
    }  
  
    /** 
     * 读取Properties的全部信息 
     * 
     * @return 所有属性值对象 
     */  
    public Properties getAllProperties() throws IOException {  
        Properties pps = new Properties();  
        InputStream in = null;  
        try {  
            File file = getFile();  
            in = new BufferedInputStream(new FileInputStream(file));  
            pps.load(in);  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } finally {  
            in.close();  
        }  
  
        return pps;  
    }  
    
    
    
    /** 测试例子 
     * Created by likun on 2015/12/26. 
     */  
    public static void main(String[] args) throws IOException {
        Student2 student = null;
        URL url = PropertiesUtil.class.getClassLoader().getResource("studentConfig.properties");
        String fileName = url.getPath();

        PropertiesUtil util = new PropertiesUtil(fileName);

        student = util.loadProperties(Student2.class);
        if (student == null) {
            //重置对象
            util.resetProperties(Student2.class);
        }
        //加载对象
        student = util.loadProperties(Student2.class);
        System.out.println(student);

        student.setName("李坤 -- " + System.currentTimeMillis());
        student.setAge(30);
        student.setSex("man");
        //保存对象
        util.saveProperties(Student2.class, student);
        System.out.println(util.loadProperties(Student2.class));


        //清空所有属性
        //util.clearProperties();

        //测试单个属性的读取
        util.setValueByKey("student.name", "LiKun~李坤");
        util.getValueByKey("student.name");

    }
  
} 