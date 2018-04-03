package com.hz.tgb.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 关键词过滤，读取文件中配置的关键词
 * @author hezhao
 *
 */
public class KeyWordsFilter {
	
	public static final String FILEPATH = "keyWord.txt";
	
	private static KeyWordsFilter keyWordsFilter = null;
	
	private static List<String> keyWord = null;
	
	public static KeyWordsFilter getInstance() {
	    if(keyWordsFilter == null) {
	    	keyWordsFilter = new KeyWordsFilter();
	    }
	    return keyWordsFilter;
	} 
	
	private KeyWordsFilter(){
		try {
			keyWord = readKeyWord();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readKeyWord() throws IOException{
		List<String> keyWord = new ArrayList<String>();
		String line = "";
		BufferedReader bufferin = null;
		bufferin = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH),"UTF-8"));
		if (bufferin != null){
			while ((line = bufferin.readLine()) != null){
				if(null != line && line.trim().length() > 0){
					keyWord.add(line);
				}
			}
		}
		return keyWord;
	}
	
	public List<String> getKeyWordList(){
		return keyWord;
	}
	
	public String filterKeyWord(String src){
		List<String> keyWord = KeyWordsFilter.getInstance().getKeyWordList();
		
		if(src != null)
		{
			for(String str : keyWord){
				if(src.indexOf(str) > -1){
					src = src.replaceAll(str, "**");
				}
			}
		}
		
		return src;
	}
	
	public static void main(String[] args) {
		System.out.println(KeyWordsFilter.getInstance().getKeyWordList().size());
		List<String> test = KeyWordsFilter.getInstance().getKeyWordList();
		String s = "测试关键词替换是否成功替换av和白粉and法轮功习x近x平能不能替换呢？";
		System.out.println(KeyWordsFilter.getInstance().filterKeyWord(s));
		for(String str : test){
			if(s.indexOf(str) > -1){
				s = s.replaceAll(str, "**");
			}
		}
		System.out.println("替换后的s:"+s);
	}
}
