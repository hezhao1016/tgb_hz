package com.hz.tgb.common;

import com.hz.tgb.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
  
/** 
 * 通用工具类之按对象中某属性排序 
 * @author hezhao
 *
 * 交流博客:http://blog.csdn.net/lk_blog 
 * http://blog.csdn.net/lk_blog/article/details/12804873
 */  
public class SortListUtil {  
	
	private static final Logger logger = LoggerFactory.getLogger(SortListUtil.class);

    public static final String DESC = "desc";
    public static final String ASC = "asc";
    
    private SortListUtil(){
    	// 私有类构造方法
    }
  
    /** 
     * 对list中的元素按升序排列. 
     *  
     * @param list 
     *            排序集合 
     * @param field 
     *            排序字段 
     * @return 
     */  
    public static List<?> sort(List<?> list, final String field) {  
        return sort(list, field, null);  
    }  
  
    /** 
     * 对list中的元素进行排序. 
     *  
     * @param list 
     *            排序集合 
     * @param field 
     *            排序字段 
     * @param sort 
     *            排序方式: SortList.DESC(降序) SortList.ASC(升序). 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static List<?> sort(List<?> list, final String field,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {  
                int ret = 0;  
                try {  
                    Field f = a.getClass().getDeclaredField(field);  
                    f.setAccessible(true);  
                    Class<?> type = f.getType();  
  
                    if (type == int.class) {  
                        ret = ((Integer) f.getInt(a)).compareTo((Integer) f  
                                .getInt(b));  
                    } else if (type == double.class) {  
                        ret = ((Double) f.getDouble(a)).compareTo((Double) f  
                                .getDouble(b));  
                    } else if (type == long.class) {  
                        ret = ((Long) f.getLong(a)).compareTo((Long) f  
                                .getLong(b));  
                    } else if (type == float.class) {  
                        ret = ((Float) f.getFloat(a)).compareTo((Float) f  
                                .getFloat(b));  
                    } else if (type == Date.class) {  
                        ret = ((Date) f.get(a)).compareTo((Date) f.get(b));  
                    } else if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) f.get(a)).compareTo((Comparable) f  
                                .get(b));  
                    } else {  
                        ret = String.valueOf(f.get(a)).compareTo(  
                                String.valueOf(f.get(b)));  
                    }  
  
                } catch (SecurityException e) {  
                    e.printStackTrace();  
                } catch (NoSuchFieldException e) {  
                    e.printStackTrace();  
                } catch (IllegalArgumentException e) {  
                    e.printStackTrace();  
                } catch (IllegalAccessException e) {  
                    e.printStackTrace();  
                }  
                if (sort != null && sort.toLowerCase().equals(DESC)) {  
                    return -ret;  
                } else {  
                    return ret;  
                }  
  
            }  
        });  
        return list;  
    }  
  
    /** 
     * 对list中的元素按fields和sorts进行排序, 
     * fields[i]指定排序字段,sorts[i]指定排序方式.如果sorts[i]为空则默认按升序排列. 
     *  
     * @param list 
     * @param fields 
     * @param sorts 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static List<?> sort(List<?> list, String[] fields, String[] sorts) {  
        if (fields != null && fields.length > 0) {  
            for (int i = fields.length - 1; i >= 0; i--) {  
                final String field = fields[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        int ret = 0;  
                        try {  
                            Field f = a.getClass().getDeclaredField(field);  
                            f.setAccessible(true);  
                            Class<?> type = f.getType();  
                            if (type == int.class) {  
                                ret = ((Integer) f.getInt(a))  
                                        .compareTo((Integer) f.getInt(b));  
                            } else if (type == double.class) {  
                                ret = ((Double) f.getDouble(a))  
                                        .compareTo((Double) f.getDouble(b));  
                            } else if (type == long.class) {  
                                ret = ((Long) f.getLong(a)).compareTo((Long) f  
                                        .getLong(b));  
                            } else if (type == float.class) {  
                                ret = ((Float) f.getFloat(a))  
                                        .compareTo((Float) f.getFloat(b));  
                            } else if (type == Date.class) {  
                                ret = ((Date) f.get(a)).compareTo((Date) f  
                                        .get(b));  
                            } else if (isImplementsOf(type, Comparable.class)) {  
                                ret = ((Comparable) f.get(a))  
                                        .compareTo((Comparable) f.get(b));  
                            } else {  
                                ret = String.valueOf(f.get(a)).compareTo(  
                                        String.valueOf(f.get(b)));  
                            }  
  
                        } catch (SecurityException e) {  
                            e.printStackTrace();  
                        } catch (NoSuchFieldException e) {  
                            e.printStackTrace();  
                        } catch (IllegalArgumentException e) {  
                            e.printStackTrace();  
                        } catch (IllegalAccessException e) {  
                            e.printStackTrace();  
                        }  
  
                        if (sort != null && sort.toLowerCase().equals(DESC)) {  
                            return -ret;  
                        } else {  
                            return ret;  
                        }  
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * 默认按正序排列 
     *  
     * @param list 
     * @param method 
     * @return 
     */  
    public static List<?> sortByMethod(List<?> list, final String method) {  
        return sortByMethod(list, method, null);  
    }  
  
    @SuppressWarnings("unchecked")  
    public static List<?> sortByMethod(List<?> list, final String method,  
            final String sort) {  
        Collections.sort(list, new Comparator() {  
            public int compare(Object a, Object b) {  
                int ret = 0;  
                try {  
                    Method m = a.getClass().getMethod(method, null);  
                    m.setAccessible(true);  
                    Class<?> type = m.getReturnType();  
                    if (type == int.class) {  
                        ret = ((Integer) m.invoke(a, null))  
                                .compareTo((Integer) m.invoke(b, null));  
                    } else if (type == double.class) {  
                        ret = ((Double) m.invoke(a, null)).compareTo((Double) m  
                                .invoke(b, null));  
                    } else if (type == long.class) {  
                        ret = ((Long) m.invoke(a, null)).compareTo((Long) m  
                                .invoke(b, null));  
                    } else if (type == float.class) {  
                        ret = ((Float) m.invoke(a, null)).compareTo((Float) m  
                                .invoke(b, null));  
                    } else if (type == Date.class) {  
                        ret = ((Date) m.invoke(a, null)).compareTo((Date) m  
                                .invoke(b, null));  
                    } else if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) m.invoke(a, null))  
                                .compareTo((Comparable) m.invoke(b, null));  
                    } else {  
                        ret = String.valueOf(m.invoke(a, null)).compareTo(  
                                String.valueOf(m.invoke(b, null)));  
                    }  
  
                    if (isImplementsOf(type, Comparable.class)) {  
                        ret = ((Comparable) m.invoke(a, null))  
                                .compareTo((Comparable) m.invoke(b, null));  
                    } else {  
                        ret = String.valueOf(m.invoke(a, null)).compareTo(  
                                String.valueOf(m.invoke(b, null)));  
                    }  
  
                } catch (NoSuchMethodException ne) {  
                	logger.error(ne.toString(),ne);  
                } catch (IllegalAccessException ie) {  
                	logger.error(ie.toString(),ie);  
                } catch (InvocationTargetException it) {  
                	logger.error(it.toString(),it);  
                }  
  
                if (sort != null && sort.toLowerCase().equals(DESC)) {  
                    return -ret;  
                } else {  
                    return ret;  
                }  
            }  
        });  
        return list;  
    }  
  
    @SuppressWarnings("unchecked")  
    public static List<?> sortByMethod(List<?> list, final String methods[],  
            final String sorts[]) {  
        if (methods != null && methods.length > 0) {  
            for (int i = methods.length - 1; i >= 0; i--) {  
                final String method = methods[i];  
                String tmpSort = ASC;  
                if (sorts != null && sorts.length > i && sorts[i] != null) {  
                    tmpSort = sorts[i];  
                }  
                final String sort = tmpSort;  
                Collections.sort(list, new Comparator() {  
                    public int compare(Object a, Object b) {  
                        int ret = 0;  
                        try {  
                            Method m = a.getClass().getMethod(method, null);  
                            m.setAccessible(true);  
                            Class<?> type = m.getReturnType();  
                            if (type == int.class) {  
                                ret = ((Integer) m.invoke(a, null))  
                                        .compareTo((Integer) m.invoke(b, null));  
                            } else if (type == double.class) {  
                                ret = ((Double) m.invoke(a, null))  
                                        .compareTo((Double) m.invoke(b, null));  
                            } else if (type == long.class) {  
                                ret = ((Long) m.invoke(a, null))  
                                        .compareTo((Long) m.invoke(b, null));  
                            } else if (type == float.class) {  
                                ret = ((Float) m.invoke(a, null))  
                                        .compareTo((Float) m.invoke(b, null));  
                            } else if (type == Date.class) {  
                                ret = ((Date) m.invoke(a, null))  
                                        .compareTo((Date) m.invoke(b, null));  
                            } else if (isImplementsOf(type, Comparable.class)) {  
                                ret = ((Comparable) m.invoke(a, null))  
                                        .compareTo((Comparable) m.invoke(b,  
                                                null));  
                            } else {  
                                ret = String.valueOf(m.invoke(a, null))  
                                        .compareTo(  
                                                String.valueOf(m  
                                                        .invoke(b, null)));  
                            }  
  
                        } catch (NoSuchMethodException ne) {  
                        	logger.error(ne.toString(),ne);
                        } catch (IllegalAccessException ie) {  
                        	logger.error(ie.toString(),ie); 
                        } catch (InvocationTargetException it) {  
                        	logger.error(it.toString(),it);
                        }  
  
                        if (sort != null && sort.toLowerCase().equals(DESC)) {  
                            return -ret;  
                        } else {  
                            return ret;  
                        }  
                    }  
                });  
            }  
        }  
        return list;  
    }  
  
    /** 
     * 判断对象实现的所有接口中是否包含szInterface 
     *  
     * @param clazz 
     * @param szInterface 
     * @return 
     */  
    public static boolean isImplementsOf(Class<?> clazz, Class<?> szInterface) {  
        boolean flag = false;  
  
        Class<?>[] face = clazz.getInterfaces();  
        for (Class<?> c : face) {  
            if (c == szInterface) {  
                flag = true;  
            } else {  
                flag = isImplementsOf(c, szInterface);  
            }  
        }  
  
        if (!flag && null != clazz.getSuperclass()) {  
            return isImplementsOf(clazz.getSuperclass(), szInterface);  
        }  
  
        return flag;  
    }  
  
    
    
    public static void main(String[] args) throws Exception {  
        List<Student> list = new ArrayList<Student>();
  
        list.add(new Student(3, "b", 1,true, new Date(11110000)));  
        list.add(new Student(1, "c", 3, false,new Date(44440000)));  
        list.add(new Student(2, "a", 2, false,new Date(22210000)));  
        list.add(new Student(4, "a", 11, true,new Date(33330000)));  
        System.out.println("-------原来序列-------------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // 按age正序排序,注意结果排完后是1,2,3,11. 不是1,11,2,3(如果是String类型正序排序是这样)  
        SortListUtil.sort(list, "age", null);  
        System.out.println("---------测试Integer和正序,按age正序排序-----------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // 按id倒序  
        SortListUtil.sort(list, "id", SortListUtil.DESC);  
        System.out.println("--------测试int和倒序,按id倒序------------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // 先按name正序排序,再按id正序排序  
        SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {});  
        System.out.println("---------测试多个排序字段,先按name正序,name相同时再按id正序-----------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // 先按name正序排序,再按id倒序排序  
        SortListUtil.sort(list, new String[] { "name", "id" }, new String[] {  
                SortListUtil.ASC, SortListUtil.DESC });  
        System.out.println("---------测试多个排序字段,先按name正序,name相同时再按id倒序-----------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // 按birthday排序  
        SortListUtil.sort(list, "birthday");  
        System.out.println("---------测试实现了Comparable接口的对象排序,按birthday正序-----------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
        // sortByMethod  
        SortListUtil.sortByMethod(list, "getId", null);  
        System.out.println("---------测试sortByMethod,按getId方法正序-----------------");  
        for (Student stu : list) {  
            System.out.println(stu.toString());  
        }  
  
    }  
}  