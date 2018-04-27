package com.hz.tgb.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.*;
import com.hz.tgb.entity.User;

import java.util.*;
import java.util.Map.Entry;

/**
 * Json工具类
 * 
 * @author hezhao
 * 
 */

public class FastJsonUtil {

	private static SerializeConfig mapping = new SerializeConfig();

	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer(
				"yyyy-MM-dd HH:mm:ss"));
	}

	private FastJsonUtil() {
		// 私有类构造方法
	}

	/**
	 * json转List
	 * 
	 * @param str
	 * @param cls
	 * @return
	 */
	public static final <T> List<T> getList(String str, Class<T> cls) {
		List<T> list = JSON.parseArray(str, cls);
		return list;
	}

	/**
	 * 将Json文本数据信息转换为JsonObject对象,获取Value
	 * 
	 * @param key
	 *            "name"
	 * @param json
	 *            {"name":"中车信息"}
	 * @return "中车信息"
	 */
	public static String getValue(String key, String json) {
		JSONObject object = JSON.parseObject(json);
		Object value = object.get(key);
		return value == null ? null : value.toString();
	}

	/**
	 * 通过json格式，返回JavaBean对象
	 * 
	 * @param <T>
	 * @param json
	 *            eg:{"name":"中车信息"}
	 * @param cls
	 *            User.class
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> cls) {
		return JSON.parseObject(json, cls);
	}

	// 以下方法是将对象转成Json格式字符串
	/**
	 * 通过对象转换成json字符串,返回有指定key值的json
	 * 
	 * @param <T>
	 * @return eg:{"name":"中车信息"}
	 */
	public static <T> String toJson(String key, T o) {
		JSONObject json = new JSONObject();
		json.put(key, o);
		return json.toString();
	}

	/**
	 * 传入对象直接返回Json
	 * 
	 * @param object
	 * @return
	 */
	public static <T> String serialize(T object) {
		return JSON.toJSONString(object,
				SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 传入对象直接返回Json
	 * @author hezhao
	 * @Time   2017年7月31日 下午7:43:03
	 * @param object
	 * @param feature
	 * @return
	 */
	public static <T> String serialize(T object,SerializerFeature feature) {
		return JSON.toJSONString(object,feature);
	}
	
	/**
	 * 传入对象直接返回Json，格式化
	 * @author hezhao
	 * @Time   2017年7月31日 下午7:43:03
	 * @param object
	 * @return
	 */
	public static <T> String serializeFormat(T object) {
		return JSON.toJSONString(object,true);//格式化数据，方便阅读  
	}
	
	/**
	 * 传入对象直接返回Json
	 * @author hezhao
	 * @Time   2017年7月31日 下午7:43:03
	 * @param object
	 * @return
	 */
	public static <T> String serializeMapping(T object) {
		return JSON.toJSONString(object,mapping);
	}

	
	/**
	 * 将对象转换成Json格式字符串，并过滤多余的字段。
	 * 
	 * @param object
	 * @param filter
	 *            过滤器
	 * @return
	 */
	public static <T> String serialize(T object, PropertyFilter filter) {
		return JSON.toJSONString(object, filter,
				SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 将对象转换成Json格式字符串，并过滤多余的字段。
	 * @author hezhao
	 * @Time   2017年7月31日 下午7:43:40
	 * @param object
	 * @param filter 过滤器
	 * @param feature 
	 * @return
	 */
	public static <T> String serialize(T object, PropertyFilter filter,SerializerFeature feature) {
		return JSON.toJSONString(object, filter,feature);
	}
	
	


	/**
	 * json string convert to map
	 */
	public static <T> Map<String, Object> jsonToMap(String jsonStr) {
		return jsonToObject(jsonStr, Map.class);
	}

	/**
	 * json string convert to map with javaBean
	 */
	public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clazz) {
		Map<String, T> map = JSON.parseObject(jsonStr,
				new TypeReference<Map<String, T>>() {
				});
		for (Entry<String, T> entry : map.entrySet()) {
			JSONObject obj = (JSONObject) entry.getValue();
			map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));
		}
		return map;
	}

	
	public static void main(String[] args) {
		/****************** 示例1 *************/
		User user = new User();
		user.setName("张三");
		user.setAge(20);
		System.out.println(toJson("user", user));
		User user2 = new User();
		user2.setName("李四");
		user2.setAge(25);
		System.out.println(toJson("user2", user2));
		/****************** 示例2 *************/
		List<User> list = new ArrayList<User>();
		list.add(user);
		list.add(user2);
		System.out.println(toJson("list", list));
		System.out.println(serialize(list));
		/****************** 示例3 *************/
		String s = "{\"name\":\"王五\",\"age\":30}";
		User user3 = (User) jsonToObject(s, User.class);
		System.out.println("返回类型对象:" + user3.getName() + ":" + user3.getAge());

		/****************** 示例4 *************/
		String str = "{\"name\":\"中车信息\"}";
		System.out.println(getValue("name", str));
		/****************** 示例5 *************/
		Map<String, User> map = new HashMap<String, User>();
		map.put("user", user);

		FastJsonFilter cfilter = new FastJsonFilter();
		cfilter.addFiled(User.class, "age");// 去掉User里面的age字段
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		serializer.getPropertyFilters().add(cfilter);
		serializer.write(map);

		System.out.println(sw.toString());

		String str1 = "[{\"name\":\"aaaaa\",\"age\":2},{\"name\":\"bbbb\",\"age\":3}]";
		List<User> list1 = FastJsonUtil.getList(str1, User.class);
		for (User u : list1) {
			System.out.println(u.getName());
			System.out.println(u.getAge());
		}
	}
}
