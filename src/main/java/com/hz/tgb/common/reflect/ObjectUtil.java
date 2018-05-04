package com.hz.tgb.common.reflect;

import com.hz.tgb.common.datetime.DateUtil;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

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
     * 拷贝对象方法（适合不同类型的转换）<br/>
     * 前提是，源类中的所有属性在目标类中都存在
     *
     * @param objSource 源对象
     * @param clazzSrc 源对象所属class
     * @param clazzDes 目标class
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <T, K> T copy(Object objSource,Class<K> clazzSrc,Class<T> clazzDes ) throws InstantiationException, IllegalAccessException{

        if(null == objSource) return null;//如果源对象为空，则直接返回null

        T objDes = clazzDes.newInstance();

        return merge((K)objSource, objDes, clazzSrc, clazzDes);

    }

    /**
     * 拷贝对象方法（适合不同类型的转换）<br/>
     * 前提是，源类中的所有属性在目标类中都存在
     *
     * @param objSource 					源对象
     * @param clazzSrc 						源对象所属class
     * @param clazzDes 					目标class
     * @param overrideDefaultValue 	是否重写默认值
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <T, K> T copy(Object objSource,Class<K> clazzSrc,Class<T> clazzDes ,boolean overrideDefaultValue) throws InstantiationException, IllegalAccessException{

        if(null == objSource) return null;//如果源对象为空，则直接返回null

        T objDes = clazzDes.newInstance();

        return merge((K)objSource, objDes, clazzSrc, clazzDes, overrideDefaultValue);

    }

    /**
     * 拷贝对象方法（适合同一类型的对象复制，但结果需强制转换）
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object copy(Object objSource) throws InstantiationException, IllegalAccessException{
        return copy(objSource,objSource.getClass());
    }

    /**
     * 拷贝对象方法（适合同一类型的对象复制）
     *
     * @param objSource 源对象
     * @param clazz 目标类
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T copy(Object objSource,Class<T> clazz) throws InstantiationException, IllegalAccessException{

        if(null == objSource) return null;//如果源对象为空，则直接返回null

        T objDes = clazz.newInstance();

        // 获得源对象所有属性
//		Field[] fields = clazz.getDeclaredFields();
        Field[] fields = ConvertHelper.getFields(clazz);

        // 循环遍历字段，获取字段对应的属性值  
        for ( Field field : fields )
        {
            //去掉final字段
            if((field.getModifiers() & Modifier.FINAL)!=0) continue;

            // 如果不为空，设置可见性，然后返回  
            field.setAccessible( true );

            try
            {
                field.set(objDes, field.get(objSource));
            }
            catch ( Exception e )
            {
                System.err.println("执行"+clazz.getSimpleName()+"类的"+field.getName()+"属性的set方法时出错。"+e.getMessage());
            }
        }
        return objDes;
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

    /**
     * 将SQL查询出来的map对象转成实体对象
     *
     * @param map
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T map2Bean(LinkedHashMap<String,Object> map, Class<T> clazz)
            throws Exception {
        T obj = clazz.newInstance();
        // 获得对象属性     
        Field[]  fields = ConvertHelper.getFields(clazz);
        for (Field field : fields) {
            if(!map.containsKey(field.getName())){
                continue;
            }
            //去掉final字段
            if((field.getModifiers() & Modifier.FINAL)!=0) continue;

            // 如果不为空，设置可见性
            field.setAccessible(true);

            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method writeMethod = pd.getWriteMethod();
                writeMethod.invoke(obj, map.get(field.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    @SuppressWarnings("unused")
    private static void setValue(Field field,Object... v) throws Exception{
        Class<?> clazz = field.getType();
        if(clazz == String.class){
            field.set(v[0], v[1]);
        }else if( clazz.getSimpleName().equals("boolean")){
            field.setBoolean(v[0], (boolean)Boolean.parseBoolean(v[1].toString()));
        }else if(clazz.getSimpleName().equals("byte")){
            field.setByte(v[0], (byte)Byte.parseByte(v[1].toString()));
        }else if(clazz.getSimpleName().equals("char")){
            field.setChar(v[0], (Character)v[1]);
        }else if(clazz==Double.class || clazz.getSimpleName().equals("double")){
            field.setDouble(v[0], (double)Double.parseDouble(v[1].toString()));
        }else if(clazz.getSimpleName().equals("float")){
            field.setFloat(v[0], (float)Float.parseFloat(v[1].toString()));
        }else if(clazz.getSimpleName().equals("int")){
            field.setInt(v[0], (int)Integer.parseInt(v[1].toString()));
        }else if(clazz.getSimpleName().equals("long")){
            field.setLong(v[0], (long)Long.parseLong(v[1].toString()));
        }else if(clazz.getSimpleName().equals("short")){
            field.setShort(v[0], (short)Short.parseShort(v[1].toString()));
        }else{
            String setMethodName ="set";
            String name = clazz.getSimpleName();
            String firstLetter = name.substring(0, 1).toUpperCase();// 获取属性首字母
            // 拼接set方法名
            setMethodName += firstLetter + name.substring(1);
            Method m = Field.class.getMethod(setMethodName, clazz);
            m.invoke(field, v);
        }
    }

    /**
     * 合并对象方法（适合不同类型的转换）<br/>
     * 前提是，源类中的所有属性在目标类中都存在
     *
     * @param objSource 源对象
     * @param clazzSrc 源对象所属class
     * @param clazzDes 目标class
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T, K> T merge(K objSource,T objDes,Class<K> clazzSrc,Class<T> clazzDes) throws InstantiationException, IllegalAccessException{
        return merge(objSource, objDes, clazzSrc,clazzDes, true);
    }

    /**
     * 合并对象方法（适合不同类型的转换）<br/>
     * 前提是，源类中的所有属性在目标类中都存在
     *
     * @param objSource 源对象
     * @param clazzSrc 源对象所属class
     * @param clazzDes 目标class
     * @param overwrite 是否覆盖已存在的属性值
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T, K> T merge(K objSource,T objDes,Class<K> clazzSrc,Class<T> clazzDes,boolean overwrite) throws InstantiationException, IllegalAccessException{
        return merge(objSource,  objDes, clazzSrc,clazzDes, overwrite,null);
    }

    /**
     *
     * @param objSource
     * @param objDes
     * @param clazzSrc
     * @param clazzDes
     * @param overwrite
     * @param ignoreSet
     * @param <T>
     * @param <K>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private static <T, K> T mergeInner(Object objSource, Object objDes, Class<K> clazzSrc, Class<T> clazzDes, boolean overwrite, Set<String> ignoreSet) throws InstantiationException, IllegalAccessException {
        return merge((K)objSource, (T)objDes, clazzSrc, clazzDes, overwrite, ignoreSet);
    }

    /**
     * 合并对象方法（适合不同类型的转换）<br/>
     * 前提是，源类中的所有属性在目标类中都存在
     *
     * @param objSource 源对象
     * @param objDes 目标对象
     * @param clazzSrc 源对象所属class
     * @param clazzDes 目标class
     * @param overwrite 是否覆盖已存在的属性值
     * @param ignoreSet 忽略的属性值
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T, K> T merge(K objSource,T objDes,Class<K> clazzSrc,Class<T> clazzDes,boolean overwrite,Set<String> ignoreSet) throws InstantiationException, IllegalAccessException{

        if(null == objSource) return null;//如果源对象为空，则直接返回null

        //获取目标对象的所有属性
//		Field[] fieldDeses = clazzDes.getDeclaredFields();
        Field[] fieldDeses = ConvertHelper.getFields(clazzDes);
        Map<String,Field> m = new HashMap<String, Field>();
        // 循环遍历字段，获取字段对应的属性值  
        for ( Field field : fieldDeses ) {
            // 如果不为空，设置可见性，然后返回  
            field.setAccessible( true );
            m.put(field.getName(), field);
        }


        // 获得源对象所有属性
//		Field[] fields = clazzSrc.getDeclaredFields();
        Field[] fields = ConvertHelper.getFields(clazzSrc);
        // 循环遍历字段，获取字段对应的属性值  
        for ( Field field : fields ) {
            //如果目标对象不存在该字段，则跳过
            if(!m.containsKey(field.getName())) continue;

            //去掉final字段
            if((field.getModifiers() & Modifier.FINAL)!=0) continue;

            // 如果不为空，设置可见性，然后返回  
            field.setAccessible( true );

            try {
                Object value = field.get(objSource);
                String fieldName = field.getName();// 属性名

                if(Collection.class.isAssignableFrom(field.getType()) && ((Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0])!=Object.class){
                    //如果该字段是Collection，且指定了泛型类型，则将其子类全部进行转换
                    Collection c;
                    if(java.util.List.class.isAssignableFrom(field.getType())){
                        c = new ArrayList();
                    }else if(Set.class.isAssignableFrom(field.getType())){
                        c = new HashSet();
                    }else{
                        c = (Collection) field.getType().newInstance();
                    }
                    Class<?> clsSrc = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
                    Class<?> clsDesc = (Class<?>) ((ParameterizedType)m.get(fieldName).getGenericType()).getActualTypeArguments()[0];
                    int count=((Collection)value).size();
//					count = count<=((Collection) field.get(objDes)).size() ? count : ((Collection)field.get(objDes)).size();
                    Iterator iterator_src = ((Collection)value).iterator();
                    Iterator iterator_desc = ((Collection)m.get(fieldName).get(objDes)).iterator();
                    for (int i = 0; i < count && iterator_src.hasNext() && iterator_desc.hasNext(); i++) {
                        c.add(mergeInner(iterator_src.next(), iterator_desc.next(), clsSrc, clsDesc, overwrite, ignoreSet));
                    }
//					for (Object ele : (Collection)value) {
//						c.add(copy(ele, clsSrc, clsDesc, overwrite));
//					}
                    value = c;
//				}else if(java.util.Map.class.isAssignableFrom(field.getType()) && field.getGenericType()!=Object.class){
                    //如果该字段是Map，且指定了泛型类型，则将其子类全部进行转换
                }else{
                    //如果目标对象当前属性不为空
                    if(null!=m.get(fieldName).get(objDes)){
                        if(overwrite){//如果覆盖当前属性值，但map中存在，则不覆盖，否则覆盖
                            if(null!=ignoreSet && ignoreSet.contains(fieldName.toUpperCase())){//如果map中有值
                                continue;
                            }
                        }else{//如果不覆盖，但是map存在，则必须覆盖，否则不覆盖
                            if(null==ignoreSet || !ignoreSet.contains(fieldName.toUpperCase())){//如果map中没有值
                                continue;
                            }
                        }
                    }
                }

                String firstLetter = fieldName.substring(0, 1).toUpperCase();// 获取属性首字母
                // 拼接set方法名
                String setMethodName = "set" + firstLetter + fieldName.substring(1);
                Class<?> clazz = (new Class[]{m.get(fieldName).getType()})[0];

                // 获取set方法对象
                Method setMethod = clazzDes.getMethod(setMethodName, clazz);
                // 对目标对象调用set方法装入属性值
                setMethod.invoke(objDes, value);
                try {
                    // 对目标对象调用set方法装入属性值
                    setMethod.invoke(objDes, value);
                } catch (Exception e) {
                    // 对目标对象调用set方法装入属性值
                    setMethod.invoke(objDes, convertGt((String) value, clazz));
                }
            } catch ( Exception e ) {
                System.err.println("执行"+clazzDes.getSimpleName()+"类的"+field.getName()+"属性的set方法时出错。"+e.getMessage());
            }
        }
        return objDes;
    }

    /**
     * @param <T>
     * @param value
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertGt(String value, Class<T> clazz) {
        if (value == null) { // 如果值为null,则返回null
            return null;
        } else if (value.equals("") && !clazz.getName().equals(String.class.getName())) { // 如果value值为"",而且要转为的类型不是string类型，那么就统一返回null，也就是空字符串不能转成任何其他类型的实体，只能返回null
            return null;
        } else if (Date.class.getName().equalsIgnoreCase(clazz.getName())) { // 增加对从String类型到Date
            return (T) DateUtil.parseDateTime(value);
        }
        return (T) org.apache.commons.beanutils.ConvertUtils.convert(value, clazz);
    }
}  