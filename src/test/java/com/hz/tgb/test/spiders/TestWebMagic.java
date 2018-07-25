package com.hz.tgb.test.spiders;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * WebMagic是一个简单灵活的Java爬虫框架。基于WebMagic，你可以快速开发出一个高效、易维护的爬虫。
 * http://webmagic.io/
 *
 * Created by hezhao on 2018-04-03 16:15
 */
public class TestWebMagic {
    public static void main(String[] args) {
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(2);
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";
        ResultItems resultItems = spider.get(String.format(urlTemplate, "水力发电"));
        System.out.println(resultItems);

        List<String> list = new ArrayList();
        list.add(String.format(urlTemplate, "风力发电"));
        list.add(String.format(urlTemplate, "太阳能"));
        list.add(String.format(urlTemplate, "地热发电"));
        list.add(String.format(urlTemplate, "地热发电"));
        List<ResultItems> resultItemsList = spider.getAll(list);
        Iterator var6 = resultItemsList.iterator();

        while(var6.hasNext()) {
            ResultItems items = (ResultItems)var6.next();
            System.out.println(items.getAll());
        }

        spider.close();
    }
}
