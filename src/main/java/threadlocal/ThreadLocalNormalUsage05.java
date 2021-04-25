package threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 利用ThreadLocal，给每个线程分配自己的SimpleDateFormat对象，保证了线程安全，高效地利用内存
 *
 * @author yangxin
 * 2020/01/01 17:56
 */
@SuppressWarnings({"AlibabaThreadPoolCreation", "AlibabaUndefineMagicConstant"})
public class ThreadLocalNormalUsage05 {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(10);

    private String date(int seconds) {
        // 参数的单位是毫秒，从1970/01/01 00:00:00 gmt计时
        Date date = new Date(1000L * seconds);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.simpleDateFormatThreadLocal.get();
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            THREAD_POOL.submit(() -> {
                String date = new ThreadLocalNormalUsage05().date(finalI);
                System.out.println(date);
            });
        }
        THREAD_POOL.shutdown();
    }
}

/**
 * @author yangxin
 * 2020/02/22 22:04
 */
class ThreadSafeFormatter {

    static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal
            = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
}
