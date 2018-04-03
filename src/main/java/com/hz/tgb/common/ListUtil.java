package com.hz.tgb.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

/**
 * list工具类
 * @author hezhao
 * @Time   2017年8月1日 下午5:49:19
 */
public class ListUtil {
	/*
	 * 排序算法的分类如下： 1.插入排序（直接插入排序、折半插入排序、希尔排序）； 2.交换排序（冒泡排序、快速排序）；
	 * 3.选择排序（直接选择排序、堆排序）； 4.归并排序； 5.分配排序（基数排序）。
	 * 
	 * 关于排序方法的选择： (1)若n较小(如n≤50)，可采用直接插入或直接选择排序。
	 * (2)若文件初始状态基本有序(指正序)，则应选用直接插人、冒泡或随机的快速排序为宜；
	 * (3)若n较大，则应采用时间复杂度为O(nlgn)的排序方法：快速排序、堆排序或归并排序。
	 */

	public static boolean isEmpty(List list) {
		return (list == null) || (list.size() == 0);
	}

	public static boolean isNotEmpty(List list) {
		return (list != null) && (list.size() != 0);
	}
	
	public static List<String> toStringList(List list) {
		List<String> newList = new ArrayList<String>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,String.valueOf(list.get(i)));
		}
		return newList;
	}

	public static List<Integer> toIntList(List list) {
		List<Integer> newList = new ArrayList<Integer>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(int)list.get(i));
		}
		return newList;
	}

	/**
	 * 转 int 集合,[false:0,true:1]
	 * 
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:42:13
	 * @param list
	 * @return
	 */
	public static List<Integer> toIntListByBoolean(List<Boolean> list) {
		List<Integer> newList = new ArrayList<Integer>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == false) {
				newList.add(i,0);
			} else {
				newList.add(i,1);
			}
		}
		return newList;
	}
	
	public static List<Double> toDoubleList(List list) {
		List<Double> newList = new ArrayList<Double>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(double)list.get(i));
		}
		return newList;
	}
	
	public static List<Float> toFloatList(List list) {
		List<Float> newList = new ArrayList<Float>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(float)list.get(i));
		}
		return newList;
	}
	
	public static List<Long> toLongList(List list) {
		List<Long> newList = new ArrayList<Long>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(long)list.get(i));
		}
		return newList;
	}
	
	public static List<Short> toShortList(List list) {
		List<Short> newList = new ArrayList<Short>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(short)list.get(i));
		}
		return newList;
	}
	
	public static List<Byte> toByteList(List list) {
		List<Byte> newList = new ArrayList<Byte>(list.size());
		if (isEmpty(list))
			return newList;
		for (int i = 0; i < list.size(); i++) {
			newList.add(i,(byte)list.get(i));
		}
		return newList;
	}
	
	/**
	 * 将集合转为字符串，逗号分隔，如 [1,2,3]
	 * 
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param list
	 * @return
	 */
	public static String join(List list) {
		if (isEmpty(list))
			return "";
		if (list.size() == 1)
			return "[" + String.valueOf(list.get(0)) + "]";

		StringBuffer result = new StringBuffer("[");

		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				result.append(list.get(i) + "]");
			} else {
				result.append(list.get(i) + ",");
			}
		}
		return result.toString();
	}

	/**
	 * 将集合转为字符串,并以某个字符相连，如 1#2#3
	 * 
	 * @author hezhao
	 * @Time 2017年8月1日 下午5:02:35
	 * @param list
	 * @param splitStr
	 * @return
	 */
	public static String join(List list, String splitStr) {
		if (list == null || list.size() == 0)
			return "";
		if (list.size() == 1){
			return String.valueOf(list.get(0));
		}

		StringBuffer result = new StringBuffer("");

		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				result.append(list.get(i));
			} else {
				result.append(list.get(i) + splitStr);
			}
		}
		return result.toString();
	}

	/**
	 * 随机打乱一个集合
	 * 
	 * @param list
	 * @return
	 */
	public static List shuffle(List list) {
		Collections.shuffle(list);
		return list;
	}

	/**
	 * 集合转SET
	 * 
	 * @param list
	 *            an list of T objects.
	 * @param <T>
	 *            a T object.
	 * @return a {@link Set} object.
	 */
	public static final <T> Set<T> toSet(List<T> list) {
		if (isEmpty(list)) {
			return null;
		}
		return new LinkedHashSet<T>(list);
	}

	public static void main(String[] args) {

	}

}
