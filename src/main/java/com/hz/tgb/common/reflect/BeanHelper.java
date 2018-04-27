package com.hz.tgb.common.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 对象赋值工具类，Bean -> BeanDto.
 */
public class BeanHelper {
    private static Logger logger = LoggerFactory.getLogger(BeanHelper.class);
    static {
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
    }

    public static Object convertDto2Bean(Object dto) {
        if (dto == null) {
            return null;
        }

        Object bean = new Object();
        try {
            BeanUtils.copyProperties(bean, dto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("ObjectHelper convertDto2Bean bean复制异常", e);
        }
        return bean;
    }

    public static Object convertBean2Dto(Object bean) {
        if (bean == null) {
            return null;
        }

        Object dto = new Object();
        try {
            BeanUtils.copyProperties(dto, bean);

        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("ObjectHelper convertBean2Dto bean复制异常", e);
        }
        return dto;
    }

    public static List<Object> convertDtoList2BeanList(List<Object> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }

        List<Object> beanList = new LinkedList<>();
        for (Object dto : dtoList) {
            beanList.add(convertDto2Bean(dto));
        }
        return beanList;
    }

    public static List<Object> convertBeanList2DtoList(List<Object> beanList) {
        if (beanList == null) {
            return Collections.emptyList();
        }

        List<Object> dtoList = new LinkedList<>();
        for (Object bean : beanList) {
            dtoList.add(convertBean2Dto(bean));
        }
        return dtoList;
    }
}
