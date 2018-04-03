package com.hz.tgb.http;

import java.util.HashMap;
import java.util.Map;

import com.hz.tgb.json.FastJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hz.tgb.encryption.aes.BackAES;

/**
 * 接口调用工具类
 * @author hezhao
 * @Time   2016年8月17日 下午5:45:27
 * @Description 无
 * @Version V 1.0
 */
public class InvokeUtil {
	private static Logger logger = LoggerFactory.getLogger(InvokeUtil.class);
	private static final String INTERFACE_NAME = "InvokeUtil";
	private static final String AES_KEY = "dAA%D#V*2a9r4I!V";
	private static final String SERVER_URL = "http://127.0.0.7/interfacexx/";
	
	/**
	 * 调用接口，加密
	 * @author hezhao
	 * @Time   2016年8月17日 下午5:22:40
	 * @param interfaceName 接口名称
	 * @param params 参数
	 * @return
	 */
	public static String invoke(String interfaceName,Map<String,String> params){
		logger.info("=============go into "+INTERFACE_NAME+"::invoke");
		
		Map<String, String> reqParams = new HashMap<String, String>();
		String result = null;
		
		String in = FastJsonUtil.serialize(params);
		
		logger.debug("输入：interfaceName："+interfaceName+",params："+in);

		try {
			
			
			//如果接口名称为空，返回null
			if(interfaceName == null){
				return result;
			}
			
			//如果有参数
			if(params != null && params.size() > 0){
				
				//迭代参数集合，加密
				for (String paramKey : params.keySet()) {
					
					if (params.get(paramKey) != null 
							&& !"".equals(params.get(paramKey).toString())
					) {
						
						//加密
						byte[] encrypt = BackAES.encrypt(params.get(paramKey), AES_KEY, 0);
						String encryptStr = new String(encrypt);
						
						//添加参数
						reqParams.put(paramKey, encryptStr);
					}
				}
			}
			
			//接口路径
			String url = SERVER_URL + interfaceName;
			
			//调用真有货接口，返回信息
			String responseMsg = HttpClientUtils.request(reqParams, url); 
			
			//解密返回结果
			result = BackAES.decrypt(responseMsg, AES_KEY, 0);
			
		} catch (Exception e) {
			logger.debug("调用接口出现异常");
			e.printStackTrace();
		}
		
		logger.debug("输出："+result);
		logger.info("=============go out "+INTERFACE_NAME+"::invoke");
		
		return result;
	}
	
	
	
	/**
	 * 调用接口，不加密
	 * @author hezhao
	 * @Time   2016年8月17日 下午5:22:40
	 * @param interfaceName 接口名称
	 * @param params 参数
	 * @return
	 */
	public static String invokeimple(String interfaceName,Map<String,String> params){
		logger.info("=============go into "+INTERFACE_NAME+"::invokeSimple");
		
		String result = null;
		
		String in = FastJsonUtil.serialize(params);
		
		logger.debug("输入：interfaceName："+interfaceName+",params："+in);

		try {
			
			//如果接口名称为空，返回null
			if(interfaceName == null){
				return result;
			}
			
			//接口路径
			String url = SERVER_URL + interfaceName;
			
			//调用真有货接口，返回信息
			result = HttpClientUtils.request(params, url); 
			
		} catch (Exception e) {
			logger.debug("调用接口出现异常");
			e.printStackTrace();
		}
		
		logger.debug("输出："+result);
		logger.info("=============go out "+INTERFACE_NAME+"::invokeSimple");
		
		return result;
	}
}
