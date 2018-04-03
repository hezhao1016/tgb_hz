package com.hz.tgb.common;

import java.util.*;

/**
 * 数组工具类
 *
 * @author hezhao
 * @Time 2016年8月18日 下午4:45:50
 * @Description 无
 * @Version V 1.0
 */
public class ArrayUtil {
	/*
	 * 排序算法的分类如下： 1.插入排序（直接插入排序、折半插入排序、希尔排序）； 2.交换排序（冒泡排序、快速排序）；
	 * 3.选择排序（直接选择排序、堆排序）； 4.归并排序； 5.分配排序（基数排序）。
	 *
	 * 关于排序方法的选择： (1)若n较小(如n≤50)，可采用直接插入或直接选择排序。
	 * (2)若文件初始状态基本有序(指正序)，则应选用直接插人、冒泡或随机的快速排序为宜；
	 * (3)若n较大，则应采用时间复杂度为O(nlgn)的排序方法：快速排序、堆排序或归并排序。
	 */

	public static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(String[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(long[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(int[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(short[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(char[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(byte[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(double[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(float[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(boolean[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isNotEmpty(Object[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(String[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(long[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(int[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(short[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(char[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(byte[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(double[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(float[] array) {
		return (array != null) && (array.length != 0);
	}

	public static boolean isNotEmpty(boolean[] array) {
		return (array != null) && (array.length != 0);
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Object[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(String[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Integer[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(int[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Double[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(double[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Float[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(float[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Long[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(long[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Short[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}
	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(short[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Character[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(char[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != '\u0000';
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Byte[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(byte[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != 0;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(Boolean[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != null;
		}
		return false;
	}

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static boolean isFull(boolean[] array) {
		if(isNotEmpty(array)){
			return array[array.length - 1 ] != false;
		}
		return false;
	}

	public static String[] toStringArray(int[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(double[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(float[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(short[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(byte[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(long[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(boolean[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(char[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static String[] toStringArray(Object[] array) {
		String[] newArray = new String[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = String.valueOf(array[i]);
		}
		return newArray;
	}

	public static int[] toIntArray(String[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Integer.valueOf(array[i]);
		}
		return newArray;
	}

	public static int[] toIntArray(double[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (int) array[i];
		}
		return newArray;
	}

	public static int[] toIntArray(float[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (int) array[i];
		}
		return newArray;
	}

	public static int[] toIntArray(short[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static int[] toIntArray(byte[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static int[] toIntArray(long[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (int) array[i];
		}
		return newArray;
	}

	public static int[] toIntArray(char[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (int) array[i];
		}
		return newArray;
	}

	/**
	 * 转 int 数组,[false:0,true:1]
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:42:13
	 * @param array
	 * @return
	 */
	public static int[] toIntArray(boolean[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == false) {
				newArray[i] = 0;
			} else {
				newArray[i] = 1;
			}
		}
		return newArray;
	}

	public static int[] toIntArray(Object[] array) {
		int[] newArray = new int[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (int) array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(String[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Double.valueOf(array[i]);
		}
		return newArray;
	}

	public static double[] toDoubleArray(int[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(float[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(long[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(short[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(byte[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(char[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static double[] toDoubleArray(Object[] array) {
		double[] newArray = new double[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (double) array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(String[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Float.valueOf(array[i]);
		}
		return newArray;
	}

	public static float[] toFloatArray(int[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(double[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (float) array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(short[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(byte[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(long[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(char[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static float[] toFloatArray(Object[] array) {
		float[] newArray = new float[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (float) array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(String[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Long.valueOf(array[i]);
		}
		return newArray;
	}

	public static long[] toLongArray(int[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(double[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (long) array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(float[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (long) array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(short[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(byte[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(char[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static long[] toLongArray(Object[] array) {
		long[] newArray = new long[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (long) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(String[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Short.valueOf(array[i]);
		}
		return newArray;
	}

	public static short[] toShortArray(int[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(double[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(float[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(long[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(byte[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(char[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static short[] toShortArray(Object[] array) {
		short[] newArray = new short[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (short) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(String[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = Byte.valueOf(array[i]);
		}
		return newArray;
	}

	public static byte[] toByteArray(int[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(double[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(float[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(long[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(short[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(char[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	public static byte[] toByteArray(Object[] array) {
		byte[] newArray = new byte[array.length];
		if (isEmpty(array))
			return newArray;
		for (int i = 0; i < array.length; i++) {
			newArray[i] = (byte) array[i];
		}
		return newArray;
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(String[] array) {
		if (isEmpty(array))
			return "";
		if (array.length == 1)
			return "[" + array[0] + "]";

		StringBuffer result = new StringBuffer("[");

		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				result.append(array[i] + "]");
			} else {
				result.append(array[i] + ",");
			}
		}

		return result.toString();
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(int[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(double[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(float[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(long[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(short[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(byte[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(boolean[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(Object[] array) {
		return join(toStringArray(array));
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午5:02:35
	 * @param array
	 * @param splitStr
	 * @return
	 */
	public static String join(String[] array, String splitStr) {
		StringBuffer result = new StringBuffer("");
		if (isEmpty(array)) {
			return result.toString();
		}
		if (array.length == 1)
			return array[0];

		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				result.append(array[i]);
			} else {
				result.append(array[i] + splitStr);
			}
		}

		return result.toString();
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(int[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(double[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(float[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(long[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(short[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(byte[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(boolean[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static String join(Object[] array, String splitStr) {
		return join(toStringArray(array), splitStr);
	}

	/**
	 * 拼接Set集合，并以某个字符相连，如 1#2#3
	 * @param set
	 * @return
	 */
	public static String join_set(Set<String> set, String splitStr) {
		if (set == null || set.size() == 0)
			return "";
		if (set.size() == 1){
			for (String s : set) {
				return String.valueOf(s);
			}
		}

		StringBuffer result = new StringBuffer("");

		for (String s : set) {
			result.append(s + splitStr);
		}
		return result.toString().substring(0,result.length() - 1);
	}

	/**
	 * 拼接List集合,并以某个字符相连，如 1#2#3
	 * @param list
	 * @return
	 */
	public static String join_list(List list, String splitStr) {
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
	 * 交换数组中两元素
	 *
	 * @since 1.1
	 * @param ints
	 *            需要进行交换操作的数组
	 * @param x
	 *            数组中的位置1
	 * @param y
	 *            数组中的位置2
	 * @return 交换后的数组
	 */
	private static int[] swap(int[] ints, int x, int y) {
		int temp = ints[x];
		ints[x] = ints[y];
		ints[y] = temp;
		return ints;
	}

	/**
	 * 交换数组中两元素
	 *
	 * @since 1.1
	 * @param ints
	 *            需要进行交换操作的数组
	 * @param x
	 *            数组中的位置1
	 * @param y
	 *            数组中的位置2
	 * @return 交换后的数组
	 */
	private static double[] swap(double[] ints, int x, int y) {
		double temp = ints[x];
		ints[x] = ints[y];
		ints[y] = temp;
		return ints;
	}

	/**
	 * <strong>插入排序</strong> <br>
	 * 方法：将一个记录插入到已排好序的有序表（有可能是空表）中,从而得到一个新的记录数增1的有序表。 性能：比较次数O(n^2),n^2/4
	 * 复制次数O(n),n^2/4 比较次数是前两者的一般，而复制所需的CPU时间较交换少，所以性能上比冒泡排序提高一倍多，而比选择排序也要快。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_insert(int[] source) {
		if (source == null || source.length < 2)
			return source;

		for (int i = 1; i < source.length; i++) {
			for (int j = i; (j > 0) && (source[j] < source[j - 1]); j--) {
				swap(source, j, j - 1);
			}
		}
		return source;
	}

	/**
	 * <strong>插入排序</strong> <br>
	 * 方法：将一个记录插入到已排好序的有序表（有可能是空表）中,从而得到一个新的记录数增1的有序表。 性能：比较次数O(n^2),n^2/4
	 * 复制次数O(n),n^2/4 比较次数是前两者的一般，而复制所需的CPU时间较交换少，所以性能上比冒泡排序提高一倍多，而比选择排序也要快。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_insert(double[] source) {
		if (source == null || source.length < 2)
			return source;

		for (int i = 1; i < source.length; i++) {
			for (int j = i; (j > 0) && (source[j] < source[j - 1]); j--) {
				swap(source, j, j - 1);
			}
		}
		return source;
	}

	/**
	 * <strong>希尔排序（最小增量排序）</strong> <br>
	 * 基本思想：算法先将要排序的一组数按某个增量 d（n/2,n为要排序数的个数）分成若 干组，每组中记录的下标相差
	 * d.对每组中全部元素进行直接插入排序，然后再用一个较小 的增量（d/2）对它进行分组，在每组中再进行直接插入排序。当增量减到 1 时，进行直接
	 * 插入排序后，排序完成。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_shell(int[] source) {
		if (source == null || source.length < 2)
			return source;

		double d1 = source.length;
		int temp = 0;

		while (true) {
			d1 = Math.ceil(d1 / 2);
			int d = (int) d1;

			for (int x = 0; x < d; x++) {

				for (int i = x + d; i < source.length; i += d) {
					int j = i - d;
					temp = source[i];

					for (; j >= 0 && temp < source[j]; j -= d) {
						source[j + d] = source[j];
					}
					source[j + d] = temp;
				}
			}
			if (d == 1) {
				break;
			}
		}
		return source;
	}

	/**
	 * <strong>希尔排序（最小增量排序）</strong> <br>
	 * 基本思想：算法先将要排序的一组数按某个增量 d（n/2,n为要排序数的个数）分成若 干组，每组中记录的下标相差
	 * d.对每组中全部元素进行直接插入排序，然后再用一个较小 的增量（d/2）对它进行分组，在每组中再进行直接插入排序。当增量减到 1 时，进行直接
	 * 插入排序后，排序完成。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_shell(double[] source) {
		if (source == null || source.length < 2)
			return source;

		double d1 = source.length;
		double temp = 0;

		while (true) {
			d1 = Math.ceil(d1 / 2);
			int d = (int) d1;

			for (int x = 0; x < d; x++) {

				for (int i = x + d; i < source.length; i += d) {
					int j = i - d;
					temp = source[i];

					for (; j >= 0 && temp < source[j]; j -= d) {
						source[j + d] = source[j];
					}
					source[j + d] = temp;
				}
			}
			if (d == 1) {
				break;
			}
		}
		return source;
	}

	/**
	 * <strong>冒泡排序</strong> <br>
	 * 方法：相邻两元素进行比较 性能：比较次数O(n^2),n^2/2；交换次数O(n^2),n^2/4
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_bubble(int[] source) {
		if (source == null || source.length < 2)
			return source;
		for (int i = 0; i < source.length - 1; i++) {
			for (int j = 0; j < source.length - 1 - i; j++) {
				if(source[j] > source[j+1]){
					swap(source, j, j + 1);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>冒泡排序</strong> <br>
	 * 方法：相邻两元素进行比较 性能：比较次数O(n^2),n^2/2；交换次数O(n^2),n^2/4
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_bubble_desc(int[] source) {
		if (source == null || source.length < 2)
			return source;
		for (int i = 0; i < source.length - 1; i++) {
			for (int j = 0; j < source.length - 1 - i; j++) {
				if(source[j] < source[j+1]){
					swap(source, j, j + 1);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>冒泡排序</strong> <br>
	 * 方法：相邻两元素进行比较 性能：比较次数O(n^2),n^2/2；交换次数O(n^2),n^2/4
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_bubble(double[] source) {
		if (source == null || source.length < 2)
			return source;
		for (int i = 0; i < source.length - 1; i++) {
			for (int j = 0; j < source.length - 1 - i; j++) {
				if(source[j] > source[j+1]){
					swap(source, j, j + 1);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>冒泡排序</strong> <br>
	 * 方法：相邻两元素进行比较 性能：比较次数O(n^2),n^2/2；交换次数O(n^2),n^2/4
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_bubble_desc(double[] source) {
		if (source == null || source.length < 2)
			return source;
		for (int i = 0; i < source.length - 1; i++) {
			for (int j = 0; j < source.length - 1 - i; j++) {
				if(source[j] < source[j+1]){
					swap(source, j, j + 1);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>快速排序</strong> 快速排序使用分治法（Divide and
	 * conquer）策略来把一个序列（list）分为两个子序列（sub-lists）。 <br>
	 * 步骤为： 1. 从数列中挑出一个元素，称为 "基准"（pivot）， 2.
	 * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面
	 * （相同的数可以到任一边）。在这个分割之后，该基准是它的最后位置。这个称为分割（partition）操作。 3.
	 * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
	 * 递回的最底部情形，是数列的大小是零或一，也就是永远都已经被排序好了
	 * 。虽然一直递回下去，但是这个算法总会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_quick(int[] source) {
		if (source == null || source.length < 2)
			return source;

		return qsort(source, 0, source.length - 1);
	}

	/**
	 * <strong>快速排序</strong> 快速排序使用分治法（Divide and
	 * conquer）策略来把一个序列（list）分为两个子序列（sub-lists）。 <br>
	 * 步骤为： 1. 从数列中挑出一个元素，称为 "基准"（pivot）， 2.
	 * 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面
	 * （相同的数可以到任一边）。在这个分割之后，该基准是它的最后位置。这个称为分割（partition）操作。 3.
	 * 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
	 * 递回的最底部情形，是数列的大小是零或一，也就是永远都已经被排序好了
	 * 。虽然一直递回下去，但是这个算法总会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_quick(double[] source) {
		if (source == null || source.length < 2)
			return source;

		return qsort(source, 0, source.length - 1);
	}

	/**
	 * 快速排序的具体实现，排正序
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @param low
	 *            开始低位
	 * @param high
	 *            结束高位
	 * @return 排序后的数组
	 */
	private static int[] qsort(int source[], int low, int high) {
		int i, j, x;
		if (low < high) {
			i = low;
			j = high;
			x = source[i];
			while (i < j) {
				while (i < j && source[j] > x) {
					j--;
				}
				if (i < j) {
					source[i] = source[j];
					i++;
				}
				while (i < j && source[i] < x) {
					i++;
				}
				if (i < j) {
					source[j] = source[i];
					j--;
				}
			}
			source[i] = x;
			qsort(source, low, i - 1);
			qsort(source, i + 1, high);
		}
		return source;
	}

	/**
	 * 快速排序的具体实现，排正序
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @param low
	 *            开始低位
	 * @param high
	 *            结束高位
	 * @return 排序后的数组
	 */
	private static double[] qsort(double source[], int low, int high) {
		int i, j;
		double x;
		if (low < high) {
			i = low;
			j = high;
			x = source[i];
			while (i < j) {
				while (i < j && source[j] > x) {
					j--;
				}
				if (i < j) {
					source[i] = source[j];
					i++;
				}
				while (i < j && source[i] < x) {
					i++;
				}
				if (i < j) {
					source[j] = source[i];
					j--;
				}
			}
			source[i] = x;
			qsort(source, low, i - 1);
			qsort(source, i + 1, high);
		}
		return source;
	}

	/**
	 * <strong>直接选择排序法</strong> <br>
	 * 方法：每一趟从待排序的数据元素中选出最小（或最大）的一个元素， 顺序放在已排好序的数列的最后，直到全部待排序的数据元素排完。
	 * 性能：比较次数O(n^2),n^2/2 交换次数O(n),n
	 * 交换次数比冒泡排序少多了，由于交换所需CPU时间比比较所需的CUP时间多，所以选择排序比冒泡排序快。
	 * 但是N比较大时，比较所需的CPU时间占主要地位，所以这时的性能和冒泡排序差不太多，但毫无疑问肯定要快些。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static int[] sort_select(int[] source) {
		if (source == null || source.length < 2)
			return source;

		for (int i = 0; i < source.length; i++) {
			for (int j = i + 1; j < source.length; j++) {
				if (source[i] > source[j]) {
					swap(source, i, j);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>直接选择排序法</strong> <br>
	 * 方法：每一趟从待排序的数据元素中选出最小（或最大）的一个元素， 顺序放在已排好序的数列的最后，直到全部待排序的数据元素排完。
	 * 性能：比较次数O(n^2),n^2/2 交换次数O(n),n
	 * 交换次数比冒泡排序少多了，由于交换所需CPU时间比比较所需的CUP时间多，所以选择排序比冒泡排序快。
	 * 但是N比较大时，比较所需的CPU时间占主要地位，所以这时的性能和冒泡排序差不太多，但毫无疑问肯定要快些。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行排序操作的数组
	 * @return 排序后的数组
	 */
	public static double[] sort_select(double[] source) {
		if (source == null || source.length < 2)
			return source;

		for (int i = 0; i < source.length; i++) {
			for (int j = i + 1; j < source.length; j++) {
				if (source[i] > source[j]) {
					swap(source, i, j);
				}
			}
		}
		return source;
	}

	/**
	 * <strong>堆排序</strong><br>
	 * 堆排序是一种树形选择排序，是对直接选择排序的有效改进。
	 * 堆的定义如下：具有n个元素的序列（h1,h2,...,hn),当且仅当满足（hi>=h2i,hi>=2i+1）或
	 * （hi<=h2i,hi<=2i+1）(i=1,2,...,n/2)时称之为堆。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:19:28
	 * @param source
	 *            数组
	 */
	public static int[] sort_heap(int[] source) {
		if (source == null || source.length < 2)
			return source;

		int arrayLength = source.length;

		// 循环建堆
		for (int i = 0; i < arrayLength - 1; i++) {

			// 建堆
			buildMaxHeap(source, arrayLength - 1 - i);

			// 交换堆顶和最后一个元素
			swap(source, 0, arrayLength - 1 - i);
		}
		return source;
	}

	/**
	 * <strong>堆排序</strong><br>
	 * 堆排序是一种树形选择排序，是对直接选择排序的有效改进。
	 * 堆的定义如下：具有n个元素的序列（h1,h2,...,hn),当且仅当满足（hi>=h2i,hi>=2i+1）或
	 * （hi<=h2i,hi<=2i+1）(i=1,2,...,n/2)时称之为堆。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:19:28
	 * @param source
	 *            数组
	 */
	public static double[] sort_heap(double[] source) {
		if (source == null || source.length < 2)
			return source;

		int arrayLength = source.length;

		// 循环建堆
		for (int i = 0; i < arrayLength - 1; i++) {

			// 建堆
			buildMaxHeap(source, arrayLength - 1 - i);

			// 交换堆顶和最后一个元素
			swap(source, 0, arrayLength - 1 - i);
		}
		return source;
	}

	// 对data 数组从0到lastIndex 建大顶堆
	private static int[] buildMaxHeap(int[] source, int lastIndex) {

		// 从lastIndex 处节点（最后一个节点）的父节点开始
		for (int i = (lastIndex - 1) / 2; i >= 0; i--) {

			// k 保存正在判断的节点
			int k = i;

			// 如果当前k节点的子节点存在
			while (k * 2 + 1 <= lastIndex) {

				// k 节点的左子节点的索引
				int biggerIndex = 2 * k + 1;

				// 如果biggerIndex 小于lastIndex，即biggerIndex+1 代表的k 节点的右子节点存在
				if (biggerIndex < lastIndex) {

					// 若果右子节点的值较大
					if (source[biggerIndex] < source[biggerIndex + 1]) {
						// biggerIndex 总是记录较大子节点的索引
						biggerIndex++;
					}
				}

				// 如果k节点的值小于其较大的子节点的值
				if (source[k] < source[biggerIndex]) {
					// 交换他们
					swap(source, k, biggerIndex);
					// 将biggerIndex 赋予k，开始while 循环的下一次循环，重新保证k节点的值大于其左右子节点的值
					k = biggerIndex;
				} else {
					break;
				}
			}
		}
		return source;
	}

	// 对data 数组从0到lastIndex 建大顶堆
	private static double[] buildMaxHeap(double[] source, int lastIndex) {

		// 从lastIndex 处节点（最后一个节点）的父节点开始
		for (int i = (lastIndex - 1) / 2; i >= 0; i--) {

			// k 保存正在判断的节点
			int k = i;

			// 如果当前k节点的子节点存在
			while (k * 2 + 1 <= lastIndex) {

				// k 节点的左子节点的索引
				int biggerIndex = 2 * k + 1;

				// 如果biggerIndex 小于lastIndex，即biggerIndex+1 代表的k 节点的右子节点存在
				if (biggerIndex < lastIndex) {

					// 若果右子节点的值较大
					if (source[biggerIndex] < source[biggerIndex + 1]) {
						// biggerIndex 总是记录较大子节点的索引
						biggerIndex++;
					}
				}

				// 如果k节点的值小于其较大的子节点的值
				if (source[k] < source[biggerIndex]) {
					// 交换他们
					swap(source, k, biggerIndex);
					// 将biggerIndex 赋予k，开始while 循环的下一次循环，重新保证k节点的值大于其左右子节点的值
					k = biggerIndex;
				} else {
					break;
				}
			}
		}
		return source;
	}

	/**
	 * <strong>归并排序</strong><br>
	 * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有
	 * 序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并 为整体有序序列。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:27:05
	 * @param source
	 *            数组
	 * @return
	 */
	public static int[] sort_merge(int[] source) {
		if (source == null || source.length < 2)
			return source;

		int left = 0;
		int right = source.length - 1;

		return sort_merge(source, left, right);
	}

	/**
	 * <strong>归并排序</strong><br>
	 * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有
	 * 序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并 为整体有序序列。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:27:05
	 * @param source
	 *            数组
	 * @return
	 */
	public static double[] sort_merge(double[] source) {
		if (source == null || source.length < 2)
			return source;

		int left = 0;
		int right = source.length - 1;

		return sort_merge(source, left, right);
	}

	private static int[] sort_merge(int[] source, int left, int right) {

		if (left < right) {

			// 找出中间索引
			int center = (left + right) / 2;

			// 对左边数组进行递归
			sort_merge(source, left, center);

			// 对右边数组进行递归
			sort_merge(source, center + 1, right);

			// 合并
			merge(source, left, center, right);
		}
		return source;
	}

	private static double[] sort_merge(double[] source, int left, int right) {

		if (left < right) {

			// 找出中间索引
			int center = (left + right) / 2;

			// 对左边数组进行递归
			sort_merge(source, left, center);

			// 对右边数组进行递归
			sort_merge(source, center + 1, right);

			// 合并
			merge(source, left, center, right);
		}
		return source;
	}

	private static int[] merge(int[] source, int left, int center, int right) {

		int[] tmpArr = new int[source.length];
		int mid = center + 1;
		// third 记录中间数组的索引
		int third = left;
		int tmp = left;

		while (left <= center && mid <= right) {

			// 从两个数组中取出最小的放入中间数组
			if (source[left] <= source[mid]) {
				tmpArr[third++] = source[left++];
			} else {
				tmpArr[third++] = source[mid++];
			}
		}

		// 剩余部分依次放入中间数组
		while (mid <= right) {
			tmpArr[third++] = source[mid++];
		}
		while (left <= center) {
			tmpArr[third++] = source[left++];
		}
		// 将中间数组中的内容复制回原数组
		while (tmp <= right) {
			source[tmp] = tmpArr[tmp++];
		}
		return source;
	}

	private static double[] merge(double[] source, int left, int center,
								  int right) {

		double[] tmpArr = new double[source.length];
		int mid = center + 1;
		// third 记录中间数组的索引
		int third = left;
		int tmp = left;

		while (left <= center && mid <= right) {

			// 从两个数组中取出最小的放入中间数组
			if (source[left] <= source[mid]) {
				tmpArr[third++] = source[left++];
			} else {
				tmpArr[third++] = source[mid++];
			}
		}

		// 剩余部分依次放入中间数组
		while (mid <= right) {
			tmpArr[third++] = source[mid++];
		}
		while (left <= center) {
			tmpArr[third++] = source[left++];
		}
		// 将中间数组中的内容复制回原数组
		while (tmp <= right) {
			source[tmp] = tmpArr[tmp++];
		}
		return source;
	}

	/**
	 * <strong>基数排序</strong><br>
	 * 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面 补零。然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成
	 * 以后,数列就变成一个有序序列。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:34:07
	 * @param source
	 */
	public static int[] sort_radix(int[] source) {
		if (source == null || source.length < 2)
			return source;

		// 首先确定排序的趟数;
		int max = source[0];

		for (int i = 1; i < source.length; i++) {

			if (source[i] > max) {
				max = source[i];
			}
		}

		int time = 0;

		// 判断位数;
		while (max > 0) {
			max /= 10;
			time++;
		}

		// 建立10个队列;
		List<ArrayList> queue = new ArrayList<ArrayList>();

		for (int i = 0; i < 10; i++) {
			ArrayList<Integer> queue1 = new ArrayList<Integer>();
			queue.add(queue1);
		}

		// 进行time 次分配和收集;
		for (int i = 0; i < time; i++) {
			// 分配数组元素;
			for (int j = 0; j < source.length; j++) {

				// 得到数字的第time+1 位数;
				int x = source[j] % (int) Math.pow(10, i + 1)
						/ (int) Math.pow(10, i);

				ArrayList<Integer> queue2 = queue.get(x);
				queue2.add(source[j]);
				queue.set(x, queue2);
			}

			int count = 0;// 元素计数器;

			// 收集队列元素;
			for (int k = 0; k < 10; k++) {

				while (queue.get(k).size() > 0) {
					ArrayList<Integer> queue3 = queue.get(k);
					source[count] = queue3.get(0);
					queue3.remove(0);
					count++;
				}
			}

		}
		return source;
	}

	/**
	 * <strong>基数排序</strong><br>
	 * 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面 补零。然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成
	 * 以后,数列就变成一个有序序列。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午3:34:07
	 * @param source
	 */
	public static double[] sort_radix(double[] source) {
		if (source == null || source.length < 2)
			return source;

		// 首先确定排序的趟数;
		double max = source[0];

		for (int i = 1; i < source.length; i++) {

			if (source[i] > max) {
				max = source[i];
			}
		}

		int time = 0;

		// 判断位数;
		while (max > 0) {
			max /= 10;
			time++;
		}

		// 建立10个队列;
		List<ArrayList> queue = new ArrayList<ArrayList>();

		for (int i = 0; i < 10; i++) {
			ArrayList<Double> queue1 = new ArrayList<Double>();
			queue.add(queue1);
		}

		// 进行time 次分配和收集;
		for (int i = 0; i < time; i++) {
			// 分配数组元素;
			for (int j = 0; j < source.length; j++) {

				// 得到数字的第time+1 位数;
				int x = (int) (source[j] % (int) Math.pow(10, i + 1) / (int) Math
						.pow(10, i));

				ArrayList<Double> queue2 = queue.get(x);
				queue2.add(source[j]);
				queue.set(x, queue2);
			}

			int count = 0;// 元素计数器;

			// 收集队列元素;
			for (int k = 0; k < 10; k++) {

				while (queue.get(k).size() > 0) {
					ArrayList<Double> queue3 = queue.get(k);
					source[count] = queue3.get(0);
					queue3.remove(0);
					count++;
				}
			}

		}
		return source;
	}

	// /////////////////////////////////////////////
	// 排序算法结束
	// ////////////////////////////////////////////

	/**
	 * 顺序查找 平均时间复杂度 O（n）
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午2:26:04
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_order(int[] source, int key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		for (int i = 0; i < source.length; i++) {
			if (source[i] == key) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 顺序查找 平均时间复杂度 O（n）
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午2:26:04
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_order(double[] source, int key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		for (int i = 0; i < source.length; i++) {
			if (source[i] == key) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * <strong>二分法查找 查找线性表必须是有序列表</strong> 二分查找又称折半查找，它是一种效率较高的查找方法。 <br>
	 * 【二分查找要求】：<br>
	 * 1.必须采用顺序存储结构 <br>
	 * 2.必须按关键字大小有序排列。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_binary(int[] source, int key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		int low = 0, high = source.length - 1, mid;
		while (low <= high) {
			mid = (low + high) >>> 1;
			if (key == source[mid]) {
				return mid;
			} else if (key < source[mid]) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}

	/**
	 * <strong>二分法查找 查找线性表必须是有序列表</strong> 二分查找又称折半查找，它是一种效率较高的查找方法。 <br>
	 * 【二分查找要求】：<br>
	 * 1.必须采用顺序存储结构 <br>
	 * 2.必须按关键字大小有序排列。
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_binary(double[] source, double key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		int low = 0, high = source.length - 1, mid;
		while (low <= high) {
			mid = (low + high) >>> 1;
			if (key == source[mid]) {
				return mid;
			} else if (key < source[mid]) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}

	/**
	 * <strong>斐波那契查找 查找线性表必须是有序列表</strong> <br>
	 * 斐波那契查找是根据斐波那契序列的特点对表进行分割。假设表中记录的个数比某个斐波那契数小1，即n=Fn−1，然后将给定值和表中第Fn−1
	 * 个记录的关键字进行比较。 <br>
	 * 1、若相等，则查找成功； <br>
	 * 2、若给定关键字<表中第Fn−1个记录的关键字，则继续在表中从第一个记录到第Fn−1−1个记录之间查找； <br>
	 * 3、若给定关键字>表中第Fn−1个记录的关键字，则继续在自Fn−1+1至Fn−1的子表中查找。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午2:26:04
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_fibonacci(int[] source, int key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		// 确定需要的斐波那契数
		int i = 0;
		while (getFibonacci(i) - 1 == source.length) {
			i++;
		}
		// 开始查找
		int low = 0;
		int height = source.length - 1;
		while (low <= height) {
			int mid = low + getFibonacci(i - 1);
			if (source[mid] == key) {
				return mid;
			} else if (source[mid] > key) {
				height = mid - 1;
				i--;
			} else if (source[mid] < key) {
				low = mid + 1;
				i -= 2;
			}
		}
		return -1;
	}

	/**
	 * <strong>斐波那契查找 查找线性表必须是有序列表</strong> <br>
	 * 斐波那契查找是根据斐波那契序列的特点对表进行分割。假设表中记录的个数比某个斐波那契数小1，即n=Fn−1，然后将给定值和表中第Fn−1
	 * 个记录的关键字进行比较。 <br>
	 * 1、若相等，则查找成功； <br>
	 * 2、若给定关键字<表中第Fn−1个记录的关键字，则继续在表中从第一个记录到第Fn−1−1个记录之间查找； <br>
	 * 3、若给定关键字>表中第Fn−1个记录的关键字，则继续在自Fn−1+1至Fn−1的子表中查找。
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午2:26:04
	 * @param source
	 *            需要进行查找操作的数组
	 * @param key
	 *            需要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_fibonacci(double[] source, double key) {
		if (source == null || source.length < 1)
			return -1;
		if (source.length == 1)
			return 0;

		// 确定需要的斐波那契数
		int i = 0;
		while (getFibonacci(i) - 1 == source.length) {
			i++;
		}
		// 开始查找
		int low = 0;
		int height = source.length - 1;
		while (low <= height) {
			int mid = low + getFibonacci(i - 1);
			if (source[mid] == key) {
				return mid;
			} else if (source[mid] > key) {
				height = mid - 1;
				i--;
			} else if (source[mid] < key) {
				low = mid + 1;
				i -= 2;
			}
		}
		return -1;
	}

	/**
	 * 得到第n个斐波那契数
	 *
	 * @return
	 */
	private static int getFibonacci(int n) {
		int res = 0;
		if (n == 0) {
			res = 0;
		} else if (n == 1) {
			res = 1;
		} else {
			int first = 0;
			int second = 1;
			for (int i = 2; i <= n; i++) {
				res = first + second;
				first = second;
				second = res;
			}
		}
		return res;
	}

	/**
	 * <strong>分块查找</strong> <br>
	 * <br>
	 * a. 首先将查找表分成若干块，在每一块中数据元素的存放是任意的，但块与块之间必须是有序的（假设这种排序是按关键字值递增的，
	 * 也就是说在第一块中任意一个数据元素的关键字都小于第二块中所有数据元素的关键字
	 * ，第二块中任意一个数据元素的关键字都小于第三块中所有数据元素的关键字，依次类推）； <br>
	 * b. 建立一个索引表，把每块中最大的关键字值按块的顺序存放在一个辅助数组中，这个索引表也按升序排列； <br>
	 * c. 查找时先用给定的关键字值在索引表中查找，确定满足条件的数据元素存放在哪个块中，查找方法既可以是折半方法，也可以是顺序查找。 <br>
	 * d. 再到相应的块中顺序查找，便可以得到查找的结果。
	 *
	 * @param index
	 *            索引表，其中放的是各块的最大值
	 * @param source
	 *            顺序表，
	 * @param key
	 *            要查找的值
	 * @param m
	 *            顺序表中各块的长度相等，为m
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_binary(int[] index, int[] source, int key, int m) {
		// 在序列st数组中，用分块查找方法查找关键字为key的记录
		// 1.在index[ ] 中折半查找，确定要查找的key属于哪个块中
		int i = search_binary(index, key);
		if (i >= 0) {
			int j = i > 0 ? i * m : i;
			int len = (i + 1) * m;
			// 在确定的块中用顺序查找方法查找key
			for (int k = j; k < len; k++) {
				if (key == source[k]) {
					return k;
				}
			}
		}
		return -1;
	}

	/**
	 * <strong>分块查找</strong> <br>
	 * <br>
	 * a. 首先将查找表分成若干块，在每一块中数据元素的存放是任意的，但块与块之间必须是有序的（假设这种排序是按关键字值递增的，
	 * 也就是说在第一块中任意一个数据元素的关键字都小于第二块中所有数据元素的关键字
	 * ，第二块中任意一个数据元素的关键字都小于第三块中所有数据元素的关键字，依次类推）； <br>
	 * b. 建立一个索引表，把每块中最大的关键字值按块的顺序存放在一个辅助数组中，这个索引表也按升序排列； <br>
	 * c. 查找时先用给定的关键字值在索引表中查找，确定满足条件的数据元素存放在哪个块中，查找方法既可以是折半方法，也可以是顺序查找。 <br>
	 * d. 再到相应的块中顺序查找，便可以得到查找的结果。
	 *
	 * @param index
	 *            索引表，其中放的是各块的最大值
	 * @param source
	 *            顺序表，
	 * @param key
	 *            要查找的值
	 * @param m
	 *            顺序表中各块的长度相等，为m
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_binary(double[] index, double[] source, double key, int m) {
		// 在序列st数组中，用分块查找方法查找关键字为key的记录
		// 1.在index[ ] 中折半查找，确定要查找的key属于哪个块中
		int i = search_binary(index, key);
		if (i >= 0) {
			int j = i > 0 ? i * m : i;
			int len = (i + 1) * m;
			// 在确定的块中用顺序查找方法查找key
			for (int k = j; k < len; k++) {
				if (key == source[k]) {
					return k;
				}
			}
		}
		return -1;
	}

	/**
	 * <strong>Hash查找 </strong> <br>
	 * 哈希表查找是通过对记录的关键字值进行运算，直接求出结点的地址，是关键字到地址的直接转换方法，不用反复比较。假设f包含n个结点，Ri为其中某个结点（
	 * 1≤i≤n），keyi是其关键字值，在keyi与Ri的地址之间建立某种函数关系，可以通过这个函数把关键字值转换成相应结点的地址，有：addr(Ri
	 * )=H(keyi)，addr(Ri)为哈希函数。 <br>
	 * 解决冲突的方法有以下两种：　　 <br>
	 * (1)开放地址法　　 <br>
	 * 如果两个数据元素的哈希值相同，则在哈希表中为后插入的数据元素另外选择一个表项。当程序查找哈希表时，
	 * 如果没有在第一个对应的哈希表项中找到符合查找要求的数据元素，程序就会继续往后查找，直到找到一个符合查找要求的数据元素，或者遇到一个空的表项。　　 <br>
	 * (2)链地址法 <br>
	 * 将哈希值相同的数据元素存放在一个链表中，在查找哈希表的过程中，当查找到这个链表时，必须采用线性查找方法。
	 *
	 * @param hash
	 *            需要进行查找操作的数组
	 * @param hashLength
	 *            哈希长度
	 * @param key
	 *            要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_hash(int[] hash, int hashLength, int key) {
		// 哈希函数
		int hashAddress = key % hashLength;

		// 指定hashAdrress对应值存在但不是关键值，则用开放寻址法解决
		while (hash[hashAddress] != 0 && hash[hashAddress] != key) {
			hashAddress = (++hashAddress) % hashLength;
		}

		// 查找到了开放单元，表示查找失败
		if (hash[hashAddress] == 0)
			return -1;
		return hashAddress;

	}

	/**
	 * <strong>Hash查找 </strong> <br>
	 * 哈希表查找是通过对记录的关键字值进行运算，直接求出结点的地址，是关键字到地址的直接转换方法，不用反复比较。假设f包含n个结点，Ri为其中某个结点（
	 * 1≤i≤n），keyi是其关键字值，在keyi与Ri的地址之间建立某种函数关系，可以通过这个函数把关键字值转换成相应结点的地址，有：addr(Ri
	 * )=H(keyi)，addr(Ri)为哈希函数。 <br>
	 * 解决冲突的方法有以下两种：　　 <br>
	 * (1)开放地址法　　 <br>
	 * 如果两个数据元素的哈希值相同，则在哈希表中为后插入的数据元素另外选择一个表项。当程序查找哈希表时，
	 * 如果没有在第一个对应的哈希表项中找到符合查找要求的数据元素，程序就会继续往后查找，直到找到一个符合查找要求的数据元素，或者遇到一个空的表项。　　 <br>
	 * (2)链地址法 <br>
	 * 将哈希值相同的数据元素存放在一个链表中，在查找哈希表的过程中，当查找到这个链表时，必须采用线性查找方法。
	 *
	 * @param hash
	 *            需要进行查找操作的数组
	 * @param hashLength
	 *            哈希长度
	 * @param key
	 *            要查找的值
	 * @return 需要查找的值在数组中的位置，若未查到则返回-1
	 */
	public static int search_hash(double[] hash, int hashLength, double key) {
		// 哈希函数
		int hashAddress = (int) (key % hashLength);

		// 指定hashAdrress对应值存在但不是关键值，则用开放寻址法解决
		while (hash[hashAddress] != 0 && hash[hashAddress] != key) {
			hashAddress = (++hashAddress) % hashLength;
		}

		// 查找到了开放单元，表示查找失败
		if (hash[hashAddress] == 0)
			return -1;
		return hashAddress;

	}

	/***
	 * 数据插入Hash表
	 *
	 * @param hash
	 *            哈希表
	 * @param hashLength
	 *            哈希长度
	 * @param data
	 *            数据
	 */
	public static void insertHash(int[] hash, int hashLength, int data) {
		// 哈希函数
		int hashAddress = data % hashLength;

		// 如果key存在，则说明已经被别人占用，此时必须解决冲突
		while (hash[hashAddress] != 0) {
			// 用开放寻址法找到
			hashAddress = (++hashAddress) % hashLength;
		}

		// 将data存入字典中
		hash[hashAddress] = data;
	}

	/***
	 * 数据插入Hash表
	 *
	 * @param hash
	 *            哈希表
	 * @param hashLength
	 *            哈希长度
	 * @param data
	 *            数据
	 */
	public static void insertHash(double[] hash, int hashLength, int data) {
		// 哈希函数
		int hashAddress = data % hashLength;

		// 如果key存在，则说明已经被别人占用，此时必须解决冲突
		while (hash[hashAddress] != 0) {
			// 用开放寻址法找到
			hashAddress = (++hashAddress) % hashLength;
		}

		// 将data存入字典中
		hash[hashAddress] = data;
	}

	// ////////////////////////////////////////////
	// 查找算法结束
	// ///////////////////////////////////////////

	/**
	 * 反转数组
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行反转操作的数组
	 * @return 反转后的数组
	 */
	public static int[] reverse(int[] source) {
		int length = source.length;
		int temp = 0;
		for (int i = 0; i < length >> 1; i++) {
			temp = source[i];
			source[i] = source[length - 1 - i];
			source[length - 1 - i] = temp;
		}
		return source;
	}

	/**
	 * 反转数组
	 *
	 * @since 1.1
	 * @param source
	 *            需要进行反转操作的数组
	 * @return 反转后的数组
	 */
	public static double[] reverse(double[] source) {
		int length = source.length;
		double temp = 0;
		for (int i = 0; i < length >> 1; i++) {
			temp = source[i];
			source[i] = source[length - 1 - i];
			source[length - 1 - i] = temp;
		}
		return source;
	}

	/**
	 * 在当前位置插入一个元素,数组中原有元素向后移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @param insertNumber
	 * @return
	 */
	public static int[] insert(int[] array, int index, int insertNumber) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index - 1 > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
		int[] dest = new int[array.length + 1];
		System.arraycopy(array, 0, dest, 0, index - 1);
		dest[index - 1] = insertNumber;
		System.arraycopy(array, index - 1, dest, index, dest.length - index);
		return dest;
	}

	/**
	 * 在当前位置插入一个元素,数组中原有元素向后移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @param insertNumber
	 * @return
	 */
	public static double[] insert(double[] array, int index, int insertNumber) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index - 1 > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
		double[] dest = new double[array.length + 1];
		System.arraycopy(array, 0, dest, 0, index - 1);
		dest[index - 1] = insertNumber;
		System.arraycopy(array, index - 1, dest, index, dest.length - index);
		return dest;
	}

	/**
	 * 在元素末尾添加元素，如果已存满则重新开辟50%空间
	 *
	 * @param array
	 * @param value
	 * @return
	 */
	public static int[] add(int[] array, int value) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		int[] dest = null;

		//满了
		if(isFull(array)){
			int len =  array.length + (array.length / 2);
			dest = new int[len];
			System.arraycopy(array, 0, dest, 0, array.length);
			dest[array.length] = value;
		}else{
			//没满 自己查出存放了多少元素
			dest = new int[array.length];

			int index = 0;
			for (int i = array.length-1; i >= 0 ; i--) {
				if(array[i] == 0){
					index = i;
				}else{
					break;
				}
			}

			System.arraycopy(array, 0, dest, 0, array.length - 1);
			dest[index] = value;
		}
		return dest;
	}

	/**
	 * 在元素末尾添加元素，如果已存满则重新开辟50%空间
	 *
	 * @param array
	 * @param value
	 * @return
	 */
	public static double[] add(double[] array, double value) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		double[] dest = null;

		//满了
		if(isFull(array)){
			int len =  array.length + (array.length / 2);
			dest = new double[len];
			System.arraycopy(array, 0, dest, 0, array.length);
			dest[array.length] = value;
		}else{
			//没满 自己查出存放了多少元素
			dest = new double[array.length];

			int index = 0;
			for (int i = array.length-1; i >= 0 ; i--) {
				if(array[i] == 0){
					index = i;
				}else{
					break;
				}
			}

			System.arraycopy(array, 0, dest, 0, array.length - 1);
			dest[index] = value;
		}
		return dest;
	}

	/**
	 * 整形数组中特定位置删除掉一个元素,数组中原有元素向前移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @return
	 */
	public static int[] remove(int[] array, int index) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
		int[] dest = new int[array.length - 1];
		System.arraycopy(array, 0, dest, 0, index - 1);
		System.arraycopy(array, index, dest, index - 1, array.length - index);
		return dest;
	}

	/**
	 * 整形数组中特定位置删除掉一个元素,数组中原有元素向前移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @return
	 */
	public static double[] remove(double[] array, int index) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
		double[] dest = new double[array.length - 1];
		System.arraycopy(array, 0, dest, 0, index - 1);
		System.arraycopy(array, index, dest, index - 1, array.length - index);
		return dest;
	}

	/**
	 * 2个数组合并，形成一个新的数组
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static int[] merge(int[] array1, int[] array2) {
		int[] dest = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, dest, 0, array1.length);
		System.arraycopy(array2, 0, dest, array1.length, array2.length);
		return dest;
	}

	/**
	 * 2个数组合并，形成一个新的数组
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static double[] merge(double[] array1, int[] array2) {
		double[] dest = new double[array1.length + array2.length];
		System.arraycopy(array1, 0, dest, 0, array1.length);
		System.arraycopy(array2, 0, dest, array1.length, array2.length);
		return dest;
	}

	/**
	 * 对两个有序集合进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            ：已排好序的数组a
	 * @param b
	 *            ：已排好序的数组b
	 * @return 合并后的排序数组
	 */
	public static List<Integer> mergeByList(int[] a, int[] b) {
		// 用于返回的新数组，长度可能不为a,b数组之和，因为可能有重复的数字需要去掉
		List<Integer> c = new ArrayList<Integer>();
		// a数组下标
		int aIndex = 0;
		// b数组下标
		int bIndex = 0;
		// 对a、b两数组的值进行比较，并将小的值加到c，并将该数组下标+1，
		// 如果相等，则将其任意一个加到c，两数组下标均+1
		// 如果下标超出该数组长度，则退出循环
		while (true) {
			if (aIndex > a.length - 1 || bIndex > b.length - 1) {
				break;
			}
			if (a[aIndex] < b[bIndex]) {
				c.add(a[aIndex]);
				aIndex++;
			} else if (a[aIndex] > b[bIndex]) {
				c.add(b[bIndex]);
				bIndex++;
			} else {
				c.add(a[aIndex]);
				aIndex++;
				bIndex++;
			}
		}
		// 将没有超出数组下标的数组其余全部加到数组c中
		// 如果a数组还有数字没有处理
		if (aIndex <= a.length - 1) {
			for (int i = aIndex; i <= a.length - 1; i++) {
				c.add(a[i]);
			}
			// 如果b数组中还有数字没有处理
		} else if (bIndex <= b.length - 1) {
			for (int i = bIndex; i <= b.length - 1; i++) {
				c.add(b[i]);
			}
		}
		return c;
	}

	/**
	 * 对两个有序集合进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            ：已排好序的数组a
	 * @param b
	 *            ：已排好序的数组b
	 * @return 合并后的排序数组
	 */
	public static List<Double> mergeByList(double[] a, double[] b) {
		// 用于返回的新数组，长度可能不为a,b数组之和，因为可能有重复的数字需要去掉
		List<Double> c = new ArrayList<Double>();
		// a数组下标
		int aIndex = 0;
		// b数组下标
		int bIndex = 0;
		// 对a、b两数组的值进行比较，并将小的值加到c，并将该数组下标+1，
		// 如果相等，则将其任意一个加到c，两数组下标均+1
		// 如果下标超出该数组长度，则退出循环
		while (true) {
			if (aIndex > a.length - 1 || bIndex > b.length - 1) {
				break;
			}
			if (a[aIndex] < b[bIndex]) {
				c.add(a[aIndex]);
				aIndex++;
			} else if (a[aIndex] > b[bIndex]) {
				c.add(b[bIndex]);
				bIndex++;
			} else {
				c.add(a[aIndex]);
				aIndex++;
				bIndex++;
			}
		}
		// 将没有超出数组下标的数组其余全部加到数组c中
		// 如果a数组还有数字没有处理
		if (aIndex <= a.length - 1) {
			for (int i = aIndex; i <= a.length - 1; i++) {
				c.add(a[i]);
			}
			// 如果b数组中还有数字没有处理
		} else if (bIndex <= b.length - 1) {
			for (int i = bIndex; i <= b.length - 1; i++) {
				c.add(b[i]);
			}
		}
		return c;
	}

	/**
	 * 对两个有序数组进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            :已排好序的数组a
	 * @param b
	 *            :已排好序的数组b
	 * @return合并后的排序数组,返回数组的长度=a.length + b.length,不足部分补0
	 */
	public static int[] mergeByArray(int[] a, int[] b) {
		int[] c = new int[a.length + b.length];

		int i = 0, j = 0, k = 0;

		while (i < a.length && j < b.length) {
			if (a[i] <= b[j]) {
				if (a[i] == b[j]) {
					j++;
				} else {
					c[k] = a[i];
					i++;
					k++;
				}
			} else {
				c[k] = b[j];
				j++;
				k++;
			}
		}
		while (i < a.length) {
			c[k] = a[i];
			k++;
			i++;
		}
		while (j < b.length) {
			c[k] = b[j];
			j++;
			k++;
		}
		return c;
	}

	/**
	 * 对两个有序数组进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            :已排好序的数组a
	 * @param b
	 *            :已排好序的数组b
	 * @return合并后的排序数组,返回数组的长度=a.length + b.length,不足部分补0
	 */
	public static double[] mergeByArray(double[] a, double[] b) {
		double[] c = new double[a.length + b.length];

		int i = 0, j = 0, k = 0;

		while (i < a.length && j < b.length) {
			if (a[i] <= b[j]) {
				if (a[i] == b[j]) {
					j++;
				} else {
					c[k] = a[i];
					i++;
					k++;
				}
			} else {
				c[k] = b[j];
				j++;
				k++;
			}
		}
		while (i < a.length) {
			c[k] = a[i];
			k++;
			i++;
		}
		while (j < b.length) {
			c[k] = b[j];
			j++;
			k++;
		}
		return c;
	}

	/**
	 * 对两个有序数组进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            ：可以是没有排序的数组
	 * @param b
	 *            ：可以是没有排序的数组
	 * @return合并后的排序数组 打印时可以这样： Map<Integer,Integer> map=sortByTreeMap(a,b);
	 *                 Iterator iterator = map.entrySet().iterator(); while
	 *                 (iterator.hasNext()) { Map.Entry mapentry =
	 *                 (Map.Entry)iterator.next();
	 *                 System.out.print(mapentry.getValue()+" "); }
	 */
	public static Map<Integer, Integer> mergeByTreeMap(int[] a, int[] b) {
		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], a[i]);
		}
		for (int i = 0; i < b.length; i++) {
			map.put(b[i], b[i]);
		}
		return map;
	}

	/**
	 * 对两个有序数组进行合并,并将重复的数字将其去掉
	 *
	 * @param a
	 *            ：可以是没有排序的数组
	 * @param b
	 *            ：可以是没有排序的数组
	 * @return合并后的排序数组 打印时可以这样： Map<Integer,Integer> map=sortByTreeMap(a,b);
	 *                 Iterator iterator = map.entrySet().iterator(); while
	 *                 (iterator.hasNext()) { Map.Entry mapentry =
	 *                 (Map.Entry)iterator.next();
	 *                 System.out.print(mapentry.getValue()+" "); }
	 */
	public static Map<Double, Double> mergeByTreeMap(double[] a, double[] b) {
		Map<Double, Double> map = new TreeMap<Double, Double>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], a[i]);
		}
		for (int i = 0; i < b.length; i++) {
			map.put(b[i], b[i]);
		}
		return map;
	}

	/**
	 * 数组中有n个数据，要将它们顺序循环向后移动k位， 即前面的元素向后移动k位，后面的元素则循环向前移k位，
	 * 例如，0、1、2、3、4循环移动3位后为2、3、4、0、1。
	 *
	 * @param array
	 * @param offset
	 * @return
	 */
	public static int[] offsetArray(int[] array, int offset) {
		int length = array.length;
		int moveLength = length - offset;
		int[] temp = Arrays.copyOfRange(array, moveLength, length);
		System.arraycopy(array, 0, array, offset, moveLength);
		System.arraycopy(temp, 0, array, 0, offset);
		return array;
	}

	/**
	 * 数组中有n个数据，要将它们顺序循环向后移动k位， 即前面的元素向后移动k位，后面的元素则循环向前移k位，
	 * 例如，0、1、2、3、4循环移动3位后为2、3、4、0、1。
	 *
	 * @param array
	 * @param offset
	 * @return
	 */
	public static double[] offsetArray(double[] array, int offset) {
		int length = array.length;
		int moveLength = (length - offset);
		double[] temp = Arrays.copyOfRange(array, moveLength, length);
		System.arraycopy(array, 0, array, offset, moveLength);
		System.arraycopy(temp, 0, array, 0, offset);
		return array;
	}

	/**
	 * 随机打乱一个数组
	 *
	 * @param array
	 * @return
	 */
	public int[] shuffle(int[] array) {
		Random random = new Random();
		for (int index = array.length - 1; index >= 0; index--) {
			// 从0到index处之间随机取一个值，跟index处的元素交换
			swap(array, random.nextInt(index + 1), index);
		}
		return array;
	}

	/**
	 * 随机打乱一个数组
	 *
	 * @param array
	 * @return
	 */
	public double[] shuffle(double[] array) {
		Random random = new Random();
		for (int index = array.length - 1; index >= 0; index--) {
			// 从0到index处之间随机取一个值，跟index处的元素交换

			swap(array, random.nextInt(index + 1), index);
		}
		return array;
	}

	/**
	 * 转Map
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 上午11:52:46
	 * @param array
	 *            String[][]、int[][]、double[][] ...
	 * @return
	 */
	public static Map toMap(Object[] array) {
		if (array == null) {
			return null;
		}
		Map map = new HashMap((int) (array.length * 1.5D));
		for (int i = 0; i < array.length; i++) {
			Object object = array[i];
			if ((object instanceof Map.Entry)) {
				Map.Entry entry = (Map.Entry) object;
				map.put(entry.getKey(), entry.getValue());
			} else if ((object instanceof Object[])) {
				Object[] entry = (Object[]) object;
				if (entry.length < 2) {
					throw new IllegalArgumentException("Array element " + i
							+ ", '" + object + "', has a length less than 2");
				}

				map.put(entry[0], entry[1]);
			} else {
				throw new IllegalArgumentException("Array element " + i + ", '"
						+ object
						+ "', is neither of type Map.Entry nor an Array");
			}
		}

		return map;
	}

	/**
	 * 数组转列表
	 *
	 * @param array
	 *            an array of T objects.
	 * @param <T>
	 *            a T object.
	 * @return a {@link List} object.
	 */
	public static final <T> List<T> array2List(T[] array) {
		if (isEmpty(array)) {
			return null;
		}
		return Arrays.asList(array);
	}

	/**
	 * 数组转SET
	 *
	 * @param array
	 *            an array of T objects.
	 * @param <T>
	 *            a T object.
	 * @return a {@link Set} object.
	 */
	public static final <T> Set<T> array2Set(T[] array) {
		if (isEmpty(array)) {
			return null;
		}
		return new LinkedHashSet<T>(Arrays.asList(array));
	}

	/**
	 * 改变数组的大小
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 上午11:53:14
	 * @param oldArray
	 * @param newSize
	 * @return
	 */
	public static Object resizeArray(Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType,
				newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		return newArray;
	}

	public static void main(String[] args) {
//		 int[] array1 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array2 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array3 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array4 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array5 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array6 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array7 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
//		 int[] array8 = { 21, 24, 13, 46, 35, 26, 14, 43, 11
//		 ,41,46,45,98,45,49,61,94,72};
		double[] array1 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array2 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array3 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array4 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array5 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array6 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array7 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array8 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165,
				46.41, 0.15, 514.5, 485, 7, 3, 5496 };

//		 int[] temp;
		double[] temp;

		temp = sort_bubble(array1);
		System.out.println(join(temp));

		temp = sort_heap(array2);
		System.out.println(join(temp));

		temp = sort_insert(array3);
		System.out.println(join(temp));

		temp = sort_merge(array4);
		System.out.println(join(temp));

		temp = sort_quick(array5);
		System.out.println(join(temp));

		temp = sort_radix(array6);
		System.out.println(join(temp));

		temp = sort_select(array7);
		System.out.println(join(temp));

		temp = sort_shell(array8);
		System.out.println(join(temp));


		int[] is = new int[6];
		is[0] = 1;
		is[1] = 2;
		is[2] = 3;

		double[] ds = {0.5,1.5,2.5,3.5};

		int[] add = add(is, 4);
		double[] add2 = add(ds,4.5);

		System.out.println(join(add));
		System.out.println(join(add2));
	}

}
