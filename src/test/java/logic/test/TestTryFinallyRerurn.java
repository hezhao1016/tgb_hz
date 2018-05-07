package logic.test;

/** 测试 try-catch-finally return 的最终结果
 * Created by hezhao on 2018-04-26 17:36
 */
public class TestTryFinallyRerurn {
    public static void main(String[] args) {
        System.out.println(testTry());
    }

    public static int testTry(){
        int i = 0;
        try {
            i += 1;

//            int temp = 0;
//            int a = 1 / temp;

            return i;
        } catch (Exception $e) {
            i += 2;
            return i;
        } finally {
            i += 3;
//            return i; // 当finally有return的时候 返回这个，当注销后，返回try 或者是 catch的内容。
        }
    }

}
