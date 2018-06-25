package com.hz.tgb.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Bean操作助手
 * @Author hezhao
 * @Time 2018-05-04 1:27
 * @Description 无
 * @Version V 1.0
 */
public class BeanHelper {
    private BeanHelper(){}

    /**
     * 给属性复制
     * @param bean
     * @param name
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        BeanUtils.setProperty(bean, name, value);
    }

    /**
     * <用途描述>: 属性拷贝
     * <创建人>:13632540770
     * <创建时间>：2017年12月7日 上午10:32:30
     * <return>:void
     */
    public static void copyProperties(Object toObj, Object fromObj) {
        try {
            BeanUtils.copyProperties(toObj, fromObj);
        } catch (IllegalAccessException a) {
            System.err.println("BeanUtilsExpand 拷贝对象属性出现异常(反射异常)==>> 一般是由于java在反射时调用private方法所致");
            a.printStackTrace();
        } catch (InvocationTargetException b) {
            System.err.println("BeanUtilsExpand 拷贝对象属性出现异常(反射异常)==>> 接收被调用方法内部未被捕获");
            b.printStackTrace();
        }catch(Exception e) {
            System.err.println("BeanUtilsExpand 拷贝对象属性出现异常(反射异常)==>> 其他异常");
            e.printStackTrace();
        }
    }

    /**
     * 增强apache的beanUtils的拷贝属性，注册一些新的类型转换
     * @see :http://blog.csdn.net/yemou_blog/article/details/50292237
     */
    static {
        org.apache.commons.beanutils.ConvertUtils.register(new DateConverter(), java.util.Date.class);
        org.apache.commons.beanutils.ConvertUtils.register(new DateConverter(), java.sql.Date.class);
        ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
    }


    /**
     * <用途描述>: 整理对象的，不能为空的字符串属性
     * <创建人>:张进
     * <创建时间>：2017年10月14日 下午5:05:35
     * <return>:List<String>
     */
    public static List<String> fetchNotEmptyFiledList(String[] arrField) {
        List<String>  list  = new ArrayList<>();
        for (String string : arrField) {
            if (string != null) {
                string = string.replaceAll(" ", "");
                list.add(string);
            }
        }
        return list;
    }

    /**
     * <用途描述>: 判断对象String属性是否 为Empty <创建人>:张进 <创建时间>：2017年10月14日 下午3:53:35
     * <return>:String(empty属性名)
     */
    public static String checkObjFieldIsEmpty(Object obj, List<String> list) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String emptyFiled = null;

        // 获取实体类的所有属性，返回Field数组
        Field[] field = obj.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < field.length; j++) {
            // 获取属性的名字
            String oraginalName = field[j].getName();
            // 将属性的首字符大写，方便构造get，set方法
            String name = oraginalName.substring(0, 1).toUpperCase() + oraginalName.substring(1);
            // 获取属性的类型
            String type = field[j].getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名

            if (type.equals("class java.lang.String")) {
                Method m = obj.getClass().getMethod("get" + name);
                // 调用getter方法获取属性值
                String value = (String) m.invoke(obj);
                if (list.contains(oraginalName) && value==null||list.contains(oraginalName) &&"".equals(value)) {
                    emptyFiled = oraginalName; //得到空字符串对应的属性名字
                    break;
                }
            }
        }
        return emptyFiled;
    }
}
