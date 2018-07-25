package com.hz.tgb.test.spiders;

import org.jsoup.Jsoup;

/**
 * 测试Jsoup爬取网页
 *
 * Created by hezhao on 2018-03-28 17:28
 */
public class TestJsoup {

    /**
     * 爬取网易云音乐歌单
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis(); // 开始时间
        System.out.println("开始下载歌曲...\n================================================");
        String play_url = "http://music.163.com/playlist?id=393565693"; // 歌单-民谣还在路上

        Jsoup.connect(play_url)
                .header("Referer", "http://music.163.com/")
                .header("Host", "music.163.com")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0 Iceweasel/38.3.0")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .get().select("ul.f-hide li a")
                .stream().map(w-> w.text() + "-->" + w.attr("href"))
                .forEach(System.out::println);
        long endTime = System.currentTimeMillis(); // 结束时间
        System.out.println("程序耗时"+ (endTime - startTime) / 1000.0 +"秒.");
    }
}