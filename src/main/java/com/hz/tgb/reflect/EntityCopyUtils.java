package com.hz.tgb.reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 对象赋值工具类，Bean -> BeanDto.
 */
public class EntityCopyUtils {
    private static Logger logger = LoggerFactory.getLogger(EntityCopyUtils.class);
    static {
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
    }

    public static <T> T copySingle(Class<T> targetClazz, Object src) {
        try {
            T targetEntity = targetClazz.newInstance();
            BeanUtils.copyProperties(targetEntity, src);
            return targetEntity;
        } catch (Throwable e) {
            String errMsg = String.format("%s复制失败", targetClazz.getCanonicalName());
            logger.error(errMsg, e);
        }
        return null;
    }

    public static <T> List<T> copyList(Class<T> targetClazz, List srcList) {
        if (srcList == null) {
            return Collections.emptyList();
        }

        List<T> targetList = new LinkedList<>();
        for (Object o : srcList) {
            T target = copySingle(targetClazz, o);
            targetList.add(target);
        }
        return targetList;
    }
}
