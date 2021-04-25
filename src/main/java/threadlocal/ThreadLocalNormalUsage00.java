package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 两个线程打印日期
 *
 * @author yangxin
 * 2020/01/01 16:54
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class ThreadLocalNormalUsage00 {

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970/01/01 00:00:00 gmt计时
        Date date = new Date(1000L * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            String date = new ThreadLocalNormalUsage00().date(10);
            System.out.println(date);
        }).start();

        new Thread(() -> {
            String date = new ThreadLocalNormalUsage00().date(1007);
            System.out.println(date);
        }).start();
    }
}
