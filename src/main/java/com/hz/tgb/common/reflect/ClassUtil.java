package com.hz.tgb.common.reflect;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import static com.google.common.reflect.ClassPath.from;

/**
 * Created by hezhao on 16/5/9.
 */
public class ClassUtil {

    /**
     * 是否有clazz的超类superClass，该方法会递归的进行，一直到Object父类
     *
     * @param clazz      Class
     * @param superClass Class
     * @return boolean
     */
    public static boolean hasSuperClass(Class<?> clazz, Class<?> superClass) {
        /** 递归退出 */
        return clazz != null && (clazz.getSuperclass() == superClass || hasSuperClass(clazz.getSuperclass(), superClass));
    }

    /**
     * clazz是否实现了接口iface，该方法会递归的执行。
     *
     * @param clazz Class
     * @param iface Class
     * @return boolean
     */
    public static boolean hasInterface(Class<?> clazz, Class<?> iface) {
        /** 递归退出 */
        if (clazz == null)
            return false;
        Class<?>[] ifaces = clazz.getInterfaces();
        for (Class<?> aClass : ifaces) {
            if (iface == aClass) {
                return true;
            }
        }
        return hasInterface(clazz.getSuperclass(), iface);
    }

    /**
     * clazz上是否有注解标注
     *
     * @param clazz      Class
     * @param annotation Class
     * @param <e>        annotation
     * @return boolean
     */
    public static <e extends Annotation> boolean hasAnnotation(Class clazz, Class<e> annotation) {
        return clazz.getAnnotation(annotation) != null;
    }

    /**
     * 获得系统class loader，等价于：
     * ClassLoader.getSystemClassLoader()
     *
     * @return ClassLoader
     */
    public static ClassLoader defaultLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    /**
     * 递归的获得package下的所有类信息，不包含内部类
     *
     * @param packageName String
     * @return Collection
     */
    public static ImmutableSet<ClassPath.ClassInfo> loadClasses(String packageName) {
        try {
            return from(defaultLoader()).getTopLevelClassesRecursive(packageName);
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return null;
    }

    /**
     * 创建class的实例，注意，必须具有无参数构造器
     *
     * @param clazz Class
     * @param <t>   类型
     * @return t
     */
    public static <t> t newInstance(Class<t> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("检查是否是具有无参构造方法", ex);
        }
    }

    public static List<Class> getSubClassList(Class clazz, String packagePreffix) {
        List<Class> list = Lists.newArrayList();
        try {
            ImmutableSet<ClassPath.ClassInfo> immutableSet = from(clazz.getClassLoader()).getTopLevelClassesRecursive(packagePreffix);
            for (ClassPath.ClassInfo classInfo : immutableSet) {
                if (classInfo.getName().startsWith(packagePreffix)) {
                    try {
                        Class curClazz = classInfo.load();
                        if (clazz.isAssignableFrom(curClazz) && !clazz.equals(curClazz)) {
                            list.add(curClazz);
                        }
                    } catch (Throwable t) {
                        continue;
                    }
                }
            }
        } catch (Throwable t) {

        }
        return list;
    }
}