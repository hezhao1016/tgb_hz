package com.hz.tgb.common.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

/**
 * @describe：增强apache的beanUtils的拷贝属性，注册一些新的类型转换 
 * @see :http://blog.csdn.net/yemou_blog/article/details/50292237
 * @author： 13632540770
 * @createTime：2017年12月7日 上午9:52:49
 */
public class BeanUtilsExpand extends BeanUtils {
    
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

    static {
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new DateConverter(), java.sql.Date.class);
        ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
    }
}
