package cache;

import cache.computable.Computable;
import cache.computable.ExpensiveFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangxin
 * 2020/02/22 21:45
 */
public class Cache12 {
    private static Computable<String, Integer> computable = new Cache10<>(new ExpensiveFunction());
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                Integer result = null;
                try {
                    System.out.println(Thread.currentThread().getName() + "开始等待");
                    countDownLatch.await();

                    SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
                    String time = simpleDateFormat.format(new Date());
                    System.out.println(Thread.currentThread().getName() + "    " + time +  "被放行");

                    result = computable.compute("666");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            });
        }

        Thread.sleep(5000);
        countDownLatch.countDown();
        executorService.shutdown();
    }
}

/**
 * @author yangxin
 * 2020/02/22 22:04
 */
class ThreadSafeFormatter {
    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal
            = new ThreadLocal<SimpleDateFormat>() {
        /**
         * 每个线程会调用本方法一次，用于初始化
         */
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }

        /**
         * 首次调用本方法，会调用initialValue();后面的调用会返回第一次创建的值
         */
        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };
}
