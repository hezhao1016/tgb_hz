package com.hz.tgb.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 创建人： 张进 创建时间：2017年10月14日 下午4:40:23 创建目的：检查对象String类型属性，是否为空的工具类
 */
public class CheckEmptyStrFiled {

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
