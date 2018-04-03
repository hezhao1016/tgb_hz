package com.hz.tgb.tool;

import java.io.IOException;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker 模板邮件
 * @author hezhao
 * @Time   2017年3月13日 下午12:01:05
 * @Description 无
 * @Version V 1.0
 */
public class FreemarkerUtil {
	
	private Configuration config;
	
	/**
	 * 得到模板内容
	 * @author hezhao
	 * @Time   2017年3月13日 下午1:01:08
	 * @param fileName
	 * @param map
	 * @return
	 */
	public String getMailText(String fileName,Map<String,Object> map){
		String htmlText = null;
		try {
			Template template = config.getTemplate(fileName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return htmlText;
	}
	

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}
}
