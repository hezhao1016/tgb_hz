package com.hz.tgb.reflect;

import com.hz.tgb.entity.Student;
import com.hz.tgb.entity.Student2;
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
        org.apache.commons.beanutils.BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
    }

    /**
     * 复制单个对象,使用apache.commons-beanutils工具类库
     *
     * @param targetClazz 目标类
     * @param srcObj 源对象
     * @param <T>
     * @return
     */
    public static <T> T copySingle(Class<T> targetClazz, Object srcObj) {
        try {
            T targetEntity = targetClazz.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(targetEntity, srcObj);
            return targetEntity;
        } catch (Throwable e) {
            String errMsg = String.format("%s复制失败", targetClazz.getCanonicalName());
            logger.error(errMsg, e);
        }
        return null;
    }

    /**
     * 复制列表,使用apache.commons-beanutils工具类库
     *
     * @param targetClazz 目标类
     * @param srcList 源对象列表
     * @param <T>
     * @return
     */
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


    /**
     * 复制单个对象,使用spring-beans工具类库
     *
     * @param targetClazz 目标类
     * @param srcObj 源对象
     * @param <T>
     * @return
     */
    public static <T> T copySingleBySpring(Class<T> targetClazz, Object srcObj) {
        try {
            T targetEntity = targetClazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(srcObj, targetEntity);
            return targetEntity;
        } catch (Throwable e) {
            String errMsg = String.format("%s复制失败", targetClazz.getCanonicalName());
            logger.error(errMsg, e);
        }
        return null;
    }

    /**
     * 复制列表,使用spring-beans工具类库
     *
     * @param targetClazz 目标类
     * @param srcList 源对象列表
     * @param <T>
     * @return
     */
    public static <T> List<T> copyListBySpring(Class<T> targetClazz, List srcList) {
        if (srcList == null) {
            return Collections.emptyList();
        }

        List<T> targetList = new LinkedList<>();
        for (Object o : srcList) {
            T target = copySingleBySpring(targetClazz, o);
            targetList.add(target);
        }
        return targetList;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("bob");

        Student2 student2 = EntityCopyUtils.copySingle(Student2.class, student);
        System.out.println(student2);

        student2 = EntityCopyUtils.copySingleBySpring(Student2.class, student);
        System.out.println(student2);
    }
}
