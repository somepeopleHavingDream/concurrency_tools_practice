package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个线程打印日期的任务，用线程池来执行，SimpleDateFormat对象唯一，加锁来解决问题
 *
 * @author yangxin
 * 2020/01/01 17:29
 */
@SuppressWarnings({"AlibabaThreadPoolCreation", "AlibabaUndefineMagicConstant"})
public class ThreadLocalNormalUsage04 {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(10);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970/01/01 00:00:00 gmt计时
        Date date = new Date(1000L * seconds);
        String format;
        synchronized (ThreadLocalNormalUsage04.class) {
            format = SIMPLE_DATE_FORMAT.format(date);
        }

        return format;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            THREAD_POOL.submit(() -> {
                String date = new ThreadLocalNormalUsage04().date(finalI);
                System.out.println(date);
            });
        }
        THREAD_POOL.shutdown();
    }
}
