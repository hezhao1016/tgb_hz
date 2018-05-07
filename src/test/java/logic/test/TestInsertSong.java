package logic.test;

import java.util.Arrays;
import java.util.Scanner;

/** 插入新的歌曲，排序
 * Created by hezhao on 2018-04-04 10:17
 */
public class TestInsertSong {
    public static void main(String[] args) {
        String [] songs = new String[]{"Island","Ocean","Pretty","Sun"};

        System.out.print("插入前的数组为:");
        Arrays.stream(songs).forEach(song -> System.out.print(song + "  "));

        System.out.print("\n请输入歌曲名称：");
        Scanner input = new Scanner(System.in);
        String newSong = input.next();

        //新数组
        String[] newSongs = new String[songs.length + 1];
        //复制原数组
        System.arraycopy(songs, 0, newSongs, 0, songs.length);
        newSongs[songs.length] = newSong;
        newSongs = Arrays.stream(newSongs).sorted((n1,n2) -> n1.compareToIgnoreCase(n2)).toArray(String[]::new);

        System.out.print("插入后的数组为:");
        Arrays.stream(newSongs).forEach(song -> System.out.print(song + "  "));

    }
}
