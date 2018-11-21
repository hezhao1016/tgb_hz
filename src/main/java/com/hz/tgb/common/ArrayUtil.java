package com.hz.tgb.common;

import java.lang.reflect.Array;
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
    private ArrayUtil() {
        // 私有类构造方法
    }

	/*
	 * 排序算法的分类如下：
	 * 1.插入排序（直接插入排序、折半插入排序、希尔排序）；
	 * 2.交换排序（冒泡排序、快速排序）；
	 * 3.选择排序（直接选择排序、堆排序）；
	 * 4.归并排序；
	 * 5.分配排序（基数排序）。
	 *
	 * 关于排序方法的选择：
	 * (1)若n较小(如n≤50)，可采用直接插入或直接选择排序。
	 * (2)若文件初始状态基本有序(指正序)，则应选用直接插人、冒泡或随机的快速排序为宜；
	 * (3)若n较大，则应采用时间复杂度为O(nlgn)的排序方法：快速排序、堆排序或归并排序。
	 */

    /** 数组中元素未找到的下标，值为-1 */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
//			throw new NullPointerException("Object check for isArray is null");
            return false;
        }
        return obj.getClass().isArray();
    }

    /**
     * 数组是否为空
     *
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean isEmpty(T... array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空<br>
     * 此方法会匹配单一对象，如果此对象为{@code null}则返回true<br>
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
     * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(Object array) {
        if(null == array) {
            return true;
        }else if(isArray(array)) {
            return 0 == Array.getLength(array);
        }
        throw new RuntimeException("Object to provide is not a Array !");
    }

    /**
     * 数组是否为非空
     *
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean isNotEmpty(T... array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空<br>
     * 此方法会匹配单一对象，如果此对象为{@code null}则返回false<br>
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回true<br>
     * 如果此对象为数组对象，数组长度大于0情况下返回true，否则返回false
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Object array) {
        return false == isEmpty((Object) array);
    }

	/**
	 * 最后一个元素是不是存了元素
	 * @author hezhao
	 * @Time   2017年8月1日 下午8:32:02
	 * @param array
	 * @return
	 */
	public static <T> boolean isFull(T... array) {
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
    public static boolean isFull(boolean[] array) {
        if(isNotEmpty(array)){
            return array[array.length - 1 ] != false;
        }
        return false;
    }

    /**
     * 是否包含{@code null}元素
     *
     * @param <T> 数组元素类型
     * @param array 被检查的数组
     * @return 是否包含{@code null}元素
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> boolean hasNull(T... array) {
        if (isNotEmpty(array)) {
            for (T element : array) {
                if (null == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回数组中第一个非空元素
     *
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 非空元素，如果不存在非空元素或数组为空，返回{@code null}
     * @since 3.0.7
     */
    @SuppressWarnings("unchecked")
    public static <T> T firstNonNull(T... array) {
        if (isNotEmpty(array)) {
            for (final T val : array) {
                if (null != val) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * 比较两个对象是否相等。<br>
     * 相同的条件有两个，满足其一即可：<br>
     * <ol>
     * <li>obj1 == null &amp;&amp; obj2 == null</li>
     * <li>obj1.equals(obj2)</li>
     * </ol>
     * 1. obj1 == null &amp;&amp; obj2 == null 2. obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return 是否相等
     */
    private static boolean equalObject(Object obj1, Object obj2) {
        return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
    }

    /**
     * 比较两个字符串是否相等。
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @since 3.2.0
     */
    private static boolean equalsString(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (str1 == null) {
            return str2 == null;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.equals(str2);
        }
    }

    // ------------------------------------------------------------------- indexOf and lastIndexOf and contains
    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param <T> 数组类型
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static <T> int indexOf(T[] array, Object value) {
        for (int i = 0; i < array.length; i++) {
            if (equalObject(value, array[i])) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，忽略大小写，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.1.2
     */
    public static int indexOfIgnoreCase(CharSequence[] array, CharSequence value) {
        for (int i = 0; i < array.length; i++) {
            if (equalsString(array[i], value, true)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param <T> 数组类型
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static <T> int lastIndexOf(T[] array, Object value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (equalObject(value, array[i])) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param <T> 数组元素类型
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素，忽略大小写
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.1.2
     */
    public static boolean containsIgnoreCase(CharSequence[] array, CharSequence value) {
        return indexOfIgnoreCase(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(long[] array, long value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(long[] array, long value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(long[] array, long value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(int[] array, int value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(int[] array, int value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(short[] array, short value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(short[] array, short value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(short[] array, short value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(char[] array, char value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(char[] array, char value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(char[] array, char value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(byte[] array, byte value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(byte[] array, byte value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(byte[] array, byte value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(double[] array, double value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(double[] array, double value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(double[] array, double value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(float[] array, float value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(float[] array, float value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(float[] array, float value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int indexOf(boolean[] array, boolean value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
     * @since 3.0.7
     */
    public static int lastIndexOf(boolean[] array, boolean value) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     * @since 3.0.7
     */
    public static boolean contains(boolean[] array, boolean value) {
        return indexOf(array, value) > INDEX_NOT_FOUND;
    }

    // ------------------------------------------------------------------- Wrap and unwrap
    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Integer[] wrap(int... values) {
        final int length = values.length;
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = Integer.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static int[] unWrap(Integer... values) {
        final int length = values.length;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].intValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Long[] wrap(long... values) {
        final int length = values.length;
        Long[] array = new Long[length];
        for (int i = 0; i < length; i++) {
            array[i] = Long.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static long[] unWrap(Long... values) {
        final int length = values.length;
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].longValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Character[] wrap(char... values) {
        final int length = values.length;
        Character[] array = new Character[length];
        for (int i = 0; i < length; i++) {
            array[i] = Character.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static char[] unWrap(Character... values) {
        final int length = values.length;
        char[] array = new char[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].charValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Byte[] wrap(byte... values) {
        final int length = values.length;
        Byte[] array = new Byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = Byte.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static byte[] unWrap(Byte... values) {
        final int length = values.length;
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].byteValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Short[] wrap(short... values) {
        final int length = values.length;
        Short[] array = new Short[length];
        for (int i = 0; i < length; i++) {
            array[i] = Short.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static short[] unWrap(Short... values) {
        final int length = values.length;
        short[] array = new short[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].shortValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Float[] wrap(float... values) {
        final int length = values.length;
        Float[] array = new Float[length];
        for (int i = 0; i < length; i++) {
            array[i] = Float.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static float[] unWrap(Float... values) {
        final int length = values.length;
        float[] array = new float[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].floatValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Double[] wrap(double... values) {
        final int length = values.length;
        Double[] array = new Double[length];
        for (int i = 0; i < length; i++) {
            array[i] = Double.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static double[] unWrap(Double... values) {
        final int length = values.length;
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].doubleValue();
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Boolean[] wrap(boolean... values) {
        final int length = values.length;
        Boolean[] array = new Boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = Boolean.valueOf(values[i]);
        }
        return array;
    }

    /**
     * 包装类数组转为原始类型数组
     *
     * @param values 包装类型数组
     * @return 原始类型数组
     */
    public static boolean[] unWrap(Boolean... values) {
        final int length = values.length;
        boolean[] array = new boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i].booleanValue();
        }
        return array;
    }

    /**
     * 包装数组对象
     *
     * @param obj 对象，可以是对象数组或者基本类型数组
     * @return 包装类型数组或对象数组
     * @throws RuntimeException 对象为非数组
     */
    public static Object[] wrap(Object obj) {
        if (isArray(obj)) {
            try {
                return (Object[]) obj;
            } catch (Exception e) {
                final String className = obj.getClass().getComponentType().getName();
                switch (className) {
                    case "long":
                        return wrap((long[]) obj);
                    case "int":
                        return wrap((int[]) obj);
                    case "short":
                        return wrap((short[]) obj);
                    case "char":
                        return wrap((char[]) obj);
                    case "byte":
                        return wrap((byte[]) obj);
                    case "boolean":
                        return wrap((boolean[]) obj);
                    case "float":
                        return wrap((float[]) obj);
                    case "double":
                        return wrap((double[]) obj);
                    default:
                        throw new RuntimeException(e);
                }
            }
        }
        throw new RuntimeException(String.format("[{}] is not Array!", obj.getClass()));
    }

    // ---------------------------------------------------------------------- toXxxArray
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

	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
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

    // ---------------------------------------------------------------------- join
	/**
	 * 将数组转为字符串，逗号分隔，如 [1,2,3]
	 *
	 * @author hezhao
	 * @Time 2016年8月18日 下午4:54:04
	 * @param array
	 * @return
	 */
	public static <T> String join(T[] array) {
		if (array == null)
			return "null";
		int iMax = array.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; ; i++) {
			sb.append(array[i]);
			if (i == iMax)
				return sb.append(']').toString();
			sb.append(", ");
		}
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
	 * 将数组转为字符串,并以某个字符相连，如 1#2#3
	 *
	 * @author hezhao
	 * @Time 2017年8月1日 下午5:02:35
	 * @param array
	 * @param splitStr
	 * @return
	 */
	public static <T> String join(T[] array, String splitStr) {
		StringBuilder sb = new StringBuilder();
		if (isEmpty(array)) {
			return "";
		}
		if (array.length == 1)
			return String.valueOf(array[0]);
		int iMax = array.length - 1;
		for (int i = 0; i < array.length; i++) {
			if (i == iMax) {
				sb.append(array[i]);
			} else {
				sb.append(array[i] + splitStr);
			}
		}

		return sb.toString();
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
	 * 拼接Set集合，并以某个字符相连，如 1#2#3
	 * @param set
	 * @return
	 */
	public static <T> String joinSet(Set<T> set, String splitStr) {
		if (set == null || set.size() == 0)
			return "";
		if (set.size() == 1){
			for (T t : set) {
				return String.valueOf(t);
			}
		}

		StringBuilder sb = new StringBuilder();

		for (T t : set) {
			sb.append(String.valueOf(t) + splitStr);
		}
		return sb.toString().substring(0, sb.length() - 1);
	}

	/**
	 * 拼接List集合,并以某个字符相连，如 1#2#3
	 * @param list
	 * @return
	 */
	public static <T> String joinList(List<T> list, String splitStr) {
		if (list == null || list.size() == 0)
			return "";
		if (list.size() == 1){
			return String.valueOf(list.get(0));
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(String.valueOf(list.get(i)));
			} else {
				sb.append(String.valueOf(list.get(i)) + splitStr);
			}
		}
		return sb.toString();
	}


    // ---------------------------------------------------------------------- 排序算法开始
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
    private static <T> T[] swap(T[] ints, int x, int y) {
        T temp = ints[x];
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
	public static int[] sortByInsert(int[] source) {
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
	public static double[] sortByInsert(double[] source) {
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
	public static int[] sortByShell(int[] source) {
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
	public static double[] sortByShell(double[] source) {
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
	public static int[] sortByBubble(int[] source) {
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
	public static int[] sortByBubbleDesc(int[] source) {
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
	public static double[] sortByBubble(double[] source) {
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
	public static double[] sortByBubbleDesc(double[] source) {
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
	public static int[] sortByQuick(int[] source) {
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
	public static double[] sortByQuick(double[] source) {
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
	public static int[] sortBySelect(int[] source) {
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
	public static double[] sortBySelect(double[] source) {
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
	public static int[] sortByHeap(int[] source) {
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
	public static double[] sortByHeap(double[] source) {
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
	public static int[] sortByMerge(int[] source) {
		if (source == null || source.length < 2)
			return source;

		int left = 0;
		int right = source.length - 1;

		return sortByMerge(source, left, right);
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
	public static double[] sortByMerge(double[] source) {
		if (source == null || source.length < 2)
			return source;

		int left = 0;
		int right = source.length - 1;

		return sortByMerge(source, left, right);
	}

	private static int[] sortByMerge(int[] source, int left, int right) {

		if (left < right) {

			// 找出中间索引
			int center = (left + right) / 2;

			// 对左边数组进行递归
			sortByMerge(source, left, center);

			// 对右边数组进行递归
			sortByMerge(source, center + 1, right);

			// 合并
			merge(source, left, center, right);
		}
		return source;
	}

	private static double[] sortByMerge(double[] source, int left, int right) {

		if (left < right) {

			// 找出中间索引
			int center = (left + right) / 2;

			// 对左边数组进行递归
			sortByMerge(source, left, center);

			// 对右边数组进行递归
			sortByMerge(source, center + 1, right);

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

	private static double[] merge(double[] source, int left, int center, int right) {

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
	public static int[] sortByRadix(int[] source) {
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
	public static double[] sortByRadix(double[] source) {
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
    // ---------------------------------------------------------------------- 排序算法结束


    // ---------------------------------------------------------------------- 查找算法开始
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
	public static int searchByOrder(int[] source, int key) {
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
	public static int searchByOrder(double[] source, double key) {
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
	public static int searchByBinary(int[] source, int key) {
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
	public static int searchByBinary(double[] source, double key) {
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
	public static int searchByBinary(int[] index, int[] source, int key, int m) {
		// 在序列st数组中，用分块查找方法查找关键字为key的记录
		// 1.在index[ ] 中折半查找，确定要查找的key属于哪个块中
		int i = searchByBinary(index, key);
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
	public static int searchByBinary(double[] index, double[] source, double key, int m) {
		// 在序列st数组中，用分块查找方法查找关键字为key的记录
		// 1.在index[ ] 中折半查找，确定要查找的key属于哪个块中
		int i = searchByBinary(index, key);
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
	public static int searchByFibonacci(int[] source, int key) {
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
	public static int searchByFibonacci(double[] source, double key) {
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
	public static int searchByHash(int[] hash, int hashLength, int key) {
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
	public static int searchByHash(double[] hash, int hashLength, double key) {
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
    // ---------------------------------------------------------------------- 查找算法结束

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

    /**
     * 新建一个空数组
     *
     * @param <T> 数组元素类型
     * @param componentType 元素类型
     * @param newSize 大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 获取数组对象的元素类型
     *
     * @param array 数组对象
     * @return 元素类型
     * @since 3.2.2
     */
    public static Class<?> getComponentType(Object array){
        return null == array ? null : array.getClass().getComponentType();
    }

    /**
     * 获取数组对象的元素类型
     *
     * @param arrayClass 数组类
     * @return 元素类型
     * @since 3.2.2
     */
    public static Class<?> getComponentType(Class<?> arrayClass){
        return null == arrayClass ? null : arrayClass.getComponentType();
    }

    /**
     * 根据数组元素类型，获取数组的类型<br>
     * 方法是通过创建一个空数组从而获取其类型
     *
     * @param componentType 数组元素类型
     * @return 数组类型
     * @since 3.2.2
     */
    public static Class<?> getArrayType(Class<?> componentType) {
        return newArray(componentType, 0).getClass();
    }

    /**
     * 强转数组类型<br>
     * 强制转换的前提是数组元素类型可被强制转换<br>
     * 强制转换后会生成一个新数组
     *
     * @param type 数组类型或数组元素类型
     * @param arrayObj 原数组
     * @return 转换后的数组类型
     * @throws NullPointerException 提供参数为空
     * @throws IllegalArgumentException 参数arrayObj不是数组
     * @since 3.0.6
     */
    public static Object[] cast(Class<?> type, Object arrayObj) throws NullPointerException, IllegalArgumentException {
        if (null == arrayObj) {
            throw new NullPointerException("Argument [arrayObj] is null !");
        }
        if (false == arrayObj.getClass().isArray()) {
            throw new IllegalArgumentException("Argument [arrayObj] is not array !");
        }
        if (null == type) {
            return (Object[]) arrayObj;
        }

        final Class<?> componentType = type.isArray() ? type.getComponentType() : type;
        final Object[] array = (Object[]) arrayObj;
        final Object[] result = ArrayUtil.newArray(componentType, array.length);
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    /**
     * 反转数组，会变更原数组
     *
     * @param <T> 数组元素类型
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static <T> T[] reverse(final T[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        T tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     *
     * @param <T> 数组元素类型
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static <T> T[] reverse(final T[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     *
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static long[] reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        long tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static long[] reverse(final long[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static int[] reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        int tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static int[] reverse(final int[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static short[] reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        short tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static short[] reverse(final short[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static char[] reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static char[] reverse(final char[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static byte[] reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static byte[] reverse(final byte[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static double[] reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        double tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static double[] reverse(final double[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static float[] reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        float tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static float[] reverse(final float[] array) {
        return reverse(array, 0, array.length);
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @param startIndexInclusive 其实位置（包含）
     * @param endIndexExclusive 结束位置（不包含）
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static boolean[] reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (isEmpty(array)) {
            return array;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        boolean tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 反转数组，会变更原数组
     * @param array 数组，会变更
     * @return 变更后的原数组
     * @since 3.0.9
     */
    public static boolean[] reverse(final boolean[] array) {
        return reverse(array, 0, array.length);
    }

	/**
	 * 在当前位置插入一个元素,数组中原有元素向后移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @param insertObj
	 * @return
	 */
	public static <T> T[] insert(T[] array, int index, T insertObj) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index - 1 > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
		T[] dest = newArray(array.getClass().getComponentType(), array.length + 1);
		System.arraycopy(array, 0, dest, 0, index - 1);
		dest[index - 1] = insertObj;
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
	public static <T> T[] add(T[] array, T value) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		T[] dest = null;

		// 满了
		if(isFull(array)){
			int len =  array.length + (array.length / 2);
            dest = newArray(array.getClass().getComponentType(), len);
			System.arraycopy(array, 0, dest, 0, array.length);
			dest[array.length] = value;
		}else{
			// 没满 自己查出存放了多少元素
            dest = newArray(array.getClass().getComponentType(), array.length);

			int index = 0;
			for (int i = array.length-1; i >= 0 ; i--) {
				if(array[i] == null){
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
     * 将新元素添加到已有数组中<br>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param <T> 数组元素类型
     * @param buffer 已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(newElements)) {
            return buffer;

        }
        T[] t = resize(buffer, buffer.length + newElements.length);
        System.arraycopy(newElements, 0, t, buffer.length, newElements.length);
        return t;
    }

    /**
     * 生成一个新的重新设置大小的数组<br>
     * 调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
     *
     * @param <T> 数组元素类型
     * @param buffer 原数组
     * @param newSize 新的数组大小
     * @param componentType 数组元素类型
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
        T[] newArray = newArray(componentType, newSize);
        if (isNotEmpty(buffer)) {
            System.arraycopy(buffer, 0, newArray, 0, Math.min(buffer.length, newSize));
        }
        return newArray;
    }

    /**
     * 生成一个新的重新设置大小的数组<br>
     * 新数组的类型为原数组的类型，调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
     *
     * @param <T> 数组元素类型
     * @param buffer 原数组
     * @param newSize 新的数组大小
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize) {
        return resize(buffer, newSize, buffer.getClass().getComponentType());
    }

	/**
	 * 数组中特定位置删除掉一个元素,数组中原有元素向前移动; 如果插入位置超出原数组，则抛IllegalArgumentException异常
	 *
	 * @param array
	 * @param index
	 * @return
	 */
	public static <T> T[] remove(T[] array, int index) {
		if (isEmpty(array)) {
			throw new IllegalArgumentException();
		}
		if (index > array.length || index <= 0) {
			throw new IllegalArgumentException();
		}
        T[] dest = newArray(array.getClass().getComponentType(), array.length - 1);
		System.arraycopy(array, 0, dest, 0, index - 1);
		System.arraycopy(array, index, dest, index - 1, array.length - index);
		return dest;
	}

    /**
     * 去除{@code null} 元素
     * @param array 数组
     * @return 处理后的数组
     * @since 3.2.2
     */
    public static <T> T[] removeNull(T[] array) {
        ArrayList<T> list = new ArrayList<T>(array.length);
        for (T t : array) {
            if (t != null) {
                list.add(t);
            }
        }
        return list.toArray(Arrays.copyOf(array, list.size()));
    }

    /**
     * 去除{@code null}或者"" 元素
     * @param array 数组
     * @return 处理后的数组
     * @since 3.2.2
     */
    public static <T extends CharSequence> T[] removeEmpty(T[] array) {
        ArrayList<T> list = new ArrayList<T>(array.length);
        for (T t : array) {
            if (t != null && t.length() > 0) {
                list.add(t);
            }
        }
        return list.toArray(Arrays.copyOf(array, list.size()));
    }

    /**
     * 数组元素中的null转换为""
     *
     * @param array 数组
     * @return 新数组
     * @since 3.2.1
     */
    public static String[] nullToEmpty(String[] array){
        ArrayList<String> list = new ArrayList<String>(array.length);
        String modified;
        for (String t : array) {
            modified = null == t ? "" : t;
            if (null != modified) {
                list.add(modified);
            }
        }
        return list.toArray(Arrays.copyOf(array, list.size()));
    }

    /**
	 * 2个数组合并，形成一个新的数组
	 *
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> T[] merge(T[] array1, T[] array2) {
        T[] dest = newArray(array1.getClass().getComponentType(), array1.length + array2.length);
		System.arraycopy(array1, 0, dest, 0, array1.length);
		System.arraycopy(array2, 0, dest, array1.length, array2.length);
		return dest;
	}

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param <T> 数组元素类型
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        int length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            length += array.length;
        }
        T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);

        length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            System.arraycopy(array, 0, result, length, array.length);
            length += array.length;
        }
        return result;
    }

    /**
     * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
     * 数组复制
     *
     * @param src 源数组
     * @param srcPos 源数组开始位置
     * @param dest 目标数组
     * @param destPos 目标数组开始位置
     * @param length 拷贝数组长度
     * @return 目标数组
     * @since 3.0.6
     */
    public static Object copy(Object src, int srcPos, Object dest, int destPos, int length) {
        System.arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    /**
     * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
     * 数组复制，缘数组和目标数组都是从位置0开始复制
     *
     * @param src 源数组
     * @param dest 目标数组
     * @param length 拷贝数组长度
     * @return 目标数组
     * @since 3.0.6
     */
    public static Object copy(Object src, Object dest, int length) {
        System.arraycopy(src, 0, dest, 0, length);
        return dest;
    }

    /**
     * 克隆数组
     *
     * @param <T> 数组元素类型
     * @param array 被克隆的数组
     * @return 新数组
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 克隆数组，如果非数组返回<code>null</code>
     *
     * @param <T> 数组元素类型
     * @param obj 数组对象
     * @return 克隆后的数组对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(final T obj) {
        if (null == obj) {
            return null;
        }
        if (isArray(obj)) {
            final Object result;
            final Class<?> componentType = obj.getClass().getComponentType();
            if (componentType.isPrimitive()) {// 原始类型
                int length = Array.getLength(obj);
                result = Array.newInstance(componentType, length);
                while (length-- > 0) {
                    Array.set(result, length, Array.get(obj, length));
                }
            } else {
                result = ((Object[]) obj).clone();
            }
            return (T) result;
        }
        return null;
    }

    /**
     * 生成一个从0开始的数字列表<br>
     *
     * @param excludedEnd 结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd 结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd) {
        return range(includedStart, excludedEnd, 1);
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd 结束的数字（不包含）
     * @param step 步进
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd, int step) {
        if (includedStart > excludedEnd) {
            int tmp = includedStart;
            includedStart = excludedEnd;
            excludedEnd = tmp;
        }

        if (step <= 0) {
            step = 1;
        }

        int deviation = excludedEnd - includedStart;
        int length = deviation / step;
        if (deviation % step != 0) {
            length += 1;
        }
        int[] range = new int[length];
        for (int i = 0; i < length; i++) {
            range[i] = includedStart;
            includedStart += step;
        }
        return range;
    }

    /**
     * 拆分byte数组为几个等份（最后一份可能小于len）
     *
     * @param array 数组
     * @param len 每个小节的长度
     * @return 拆分后的数组
     */
    public static byte[][] split(byte[] array, int len) {
        int x = array.length / len;
        int y = array.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(array, i * len, arr, 0, y);
            } else {
                System.arraycopy(array, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param keys 键列表
     * @param values 值列表
     * @param isOrder 是否有序
     * @return Map
     * @since 3.0.4
     */
    public static <K, V> Map<K, V> zip(K[] keys, V[] values, boolean isOrder) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final int size = Math.min(keys.length, values.length);

        int initialCapacity = (int) (size / 0.75f);
        final Map<K, V> map = isOrder ? new LinkedHashMap<K, V>(initialCapacity) : new HashMap<K, V>(initialCapacity);

        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    /**
     * 映射键值（参考Python的zip()函数），返回Map无序<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @param keys 键列表
     * @param values 值列表
     * @return Map
     */
    public static <K, V> Map<K, V> zip(K[] keys, V[] values) {
        return zip(keys, values, false);
    }



	/**
	 * 对两个有序集合进行合并,并将重复的数字将其去掉
	 *
	 * @param a ：已排好序的数组a
	 * @param b ：已排好序的数组b
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
	 * @param a ：已排好序的数组a
	 * @param b ：已排好序的数组b
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
	 * @param a :已排好序的数组a
	 * @param b :已排好序的数组b
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
	 * @param a :已排好序的数组a
	 * @param b :已排好序的数组b
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
	 * @param a ：可以是没有排序的数组
	 * @param b ：可以是没有排序的数组
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
	 * @param a ：可以是没有排序的数组
	 * @param b ：可以是没有排序的数组
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
	public static <T> T[] offsetArray(T[] array, int offset) {
		int length = array.length;
		int moveLength = length - offset;
		T[] temp = Arrays.copyOfRange(array, moveLength, length);
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
	public <T> T[] shuffle(T[] array) {
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
     * 将集合转为数组
     *
     * @param <T> 数组元素类型
     * @param iterator {@link Iterator}
     * @param componentType 集合元素类型
     * @return 数组
     * @since 3.0.9
     */
    public static <T> T[] toArray(Iterator<T> iterator, Class<T> componentType) {
        return toArray(newArrayList(iterator), componentType);
    }

    /**
     * 将集合转为数组
     *
     * @param <T> 数组元素类型
     * @param iterable {@link Iterable}
     * @param componentType 集合元素类型
     * @return 数组
     * @since 3.0.9
     */
    public static <T> T[] toArray(Iterable<T> iterable, Class<T> componentType) {
        return toArray(toCollection(iterable), componentType);
    }

    /**
     * 将集合转为数组
     *
     * @param <T> 数组元素类型
     * @param collection 集合
     * @param componentType 集合元素类型
     * @return 数组
     * @since 3.0.9
     */
    public static <T> T[] toArray(Collection<T> collection, Class<T> componentType) {
        final T[] array = newArray(componentType, collection.size());
        return collection.toArray(array);
    }

    /**
     * {@link Iterable}转为{@link Collection}<br>
     * 首先尝试强转，强转失败则构建一个新的{@link ArrayList}
     *
     * @param <E> 集合元素类型
     * @param iterable {@link Iterable}
     * @return {@link Collection} 或者 {@link ArrayList}
     * @since 3.0.9
     */
    private static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return (iterable instanceof Collection) ? (Collection<E>) iterable : newArrayList(iterable.iterator());
    }

    /**
     * 新建一个ArrayList<br>
     * 提供的参数为null时返回空{@link ArrayList}
     *
     * @param <T> 集合元素类型
     * @param iter {@link Iterator}
     * @return ArrayList对象
     * @since 3.0.8
     */
    private static <T> ArrayList<T> newArrayList(Iterator<T> iter) {
        final ArrayList<T> list = new ArrayList<>();
        if (null == iter) {
            return list;
        }
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }

    /**
     * 数组或集合转String
     *
     * @param obj 集合或数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        if (ArrayUtil.isArray(obj)) {
            try {
                return Arrays.deepToString((Object[]) obj);
            } catch (Exception e) {
                final String className = obj.getClass().getComponentType().getName();
                switch (className) {
                    case "long":
                        return Arrays.toString((long[]) obj);
                    case "int":
                        return Arrays.toString((int[]) obj);
                    case "short":
                        return Arrays.toString((short[]) obj);
                    case "char":
                        return Arrays.toString((char[]) obj);
                    case "byte":
                        return Arrays.toString((byte[]) obj);
                    case "boolean":
                        return Arrays.toString((boolean[]) obj);
                    case "float":
                        return Arrays.toString((float[]) obj);
                    case "double":
                        return Arrays.toString((double[]) obj);
                    default:
                        throw new RuntimeException(e);
                }
            }
        }
        return obj.toString();
    }

    /**
     * 获取数组长度<br>
     * 如果参数为{@code null}，返回0
     *
     * <pre>
     * ArrayUtil.length(null)            = 0
     * ArrayUtil.length([])              = 0
     * ArrayUtil.length([null])          = 1
     * ArrayUtil.length([true, false])   = 2
     * ArrayUtil.length([1, 2, 3])       = 3
     * ArrayUtil.length(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array 数组对象
     * @return 数组长度
     * @throws IllegalArgumentException 如果参数不为数组，抛出此异常
     * @since 3.0.8
     * @see Array#getLength(Object)
     */
    public static int length(Object array) throws IllegalArgumentException {
        if (null == array) {
            return 0;
        }
        return Array.getLength(array);
    }

    //------------------------------------------------------------------------------------------------------------ min and max
    /**
     * 取最小值
     *
     * @param <T> 元素类型
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static <T extends Comparable<? super T>> T min(T[] numberArray) {
        T min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(compare(min, numberArray[i], false) > 0) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static long min(long[] numberArray) {
        long min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static int min(int[] numberArray) {
        int min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static short min(short[] numberArray) {
        short min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static char min(char[] numberArray) {
        char min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static byte min(byte[] numberArray) {
        byte min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static double min(double[] numberArray) {
        double min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * 取最小值
     * @param numberArray 数字数组
     * @return 最小值
     * @since 3.0.9
     */
    public static float min(float[] numberArray) {
        float min = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(min > numberArray[i]) {
                min = numberArray[i];
            }
        }
        return min;
    }

    /**
     * {@code null}安全的对象比较
     *
     * @param <T> 被比较对象类型
     * @param c1 对象1，可以为{@code null}
     * @param c2 对象2，可以为{@code null}
     * @param nullGreater 当被比较对象为null时是否排在前面
     * @return 比较结果，如果c1 &lt; c2，返回数小于0，c1==c2返回0，c1 &gt; c2 大于0
     * @since 3.0.7
     * @see java.util.Comparator#compare(Object, Object)
     */
    private static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
        if (c1 == c2) {
            return 0;
        } else if (c1 == null) {
            return nullGreater ? 1 : -1;
        } else if (c2 == null) {
            return nullGreater ? -1 : 1;
        }
        return c1.compareTo(c2);
    }

    /**
     * 取最大值
     *
     * @param <T> 元素类型
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static <T extends Comparable<? super T>> T max(T[] numberArray) {
        T max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(compare(max, numberArray[i], false) < 0) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static long max(long[] numberArray) {
        long max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static int max(int[] numberArray) {
        int max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static short max(short[] numberArray) {
        short max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static char max(char[] numberArray) {
        char max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static byte max(byte[] numberArray) {
        byte max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static double max(double[] numberArray) {
        double max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

    /**
     * 取最大值
     * @param numberArray 数字数组
     * @return 最大值
     * @since 3.0.9
     */
    public static float max(float[] numberArray) {
        float max = numberArray[0];
        for(int i = 0; i < numberArray.length; i++) {
            if(max < numberArray[i]) {
                max = numberArray[i];
            }
        }
        return max;
    }

	public static void main(String[] args) {
//		int[] array1 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array2 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array3 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array4 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array5 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array6 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array7 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};
//		int[] array8 = { 21, 24, 13, 46, 35, 26, 14, 43, 11,41,46,45,98,45,49,61,94,72};

		double[] array1 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array2 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array3 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array4 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array5 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array6 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array7 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };
		double[] array8 = { 11.21, 2.12, 45.33, 4.254, 55.5, 52266, 4641.165, 46.41, 0.15, 514.5, 485, 7, 3, 5496 };

//		 int[] temp;
		double[] temp;

		////// 排序
		temp = sortByBubble(array1);
		System.out.println(join(temp));

		temp = sortByHeap(array2);
		System.out.println(join(temp));

		temp = sortByInsert(array3);
		System.out.println(join(temp));

		temp = sortByMerge(array4);
		System.out.println(join(temp));

		temp = sortByQuick(array5);
		System.out.println(join(temp));

		temp = sortByRadix(array6);
		System.out.println(join(temp));

		temp = sortBySelect(array7);
		System.out.println(join(temp));

		temp = sortByShell(array8);
		System.out.println(join(temp));


		////// 查找
		int index;

		index = searchByOrder(array1, 46.41);
		System.out.println(index);

		index = searchByBinary(array1, 46.41);
		System.out.println(index);

		index = searchByFibonacci(array1, 46.41);
		System.out.println(index);

		index = searchByHash(array1,10, 46.41);
		System.out.println(index);


		int[] is = new int[6];
		is[0] = 1;
		is[1] = 2;
		is[2] = 3;

		double[] ds = {0.5, 1.5, 2.5, 3.5};

		int[] add = add(is, 4);
		double[] add2 = add(ds,4.5);

		System.out.println(join(add));
		System.out.println(join(add2));
	}

}
