package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 10个线程打印日期
 *
 * @author yangxin
 * 2020/01/01 17:14
 */
public class ThreadLocalNormalUsage01 {
    private String date(int seconds) {
        // 参数的单位是毫秒，从1970/01/01 00:00:00 gmt计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                String date = new ThreadLocalNormalUsage01().date(finalI);
                System.out.println(date);
            }).start();

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
