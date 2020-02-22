package cache;

import cache.computable.Computable;
import cache.computable.ExpensiveFunction;

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
                    System.out.println(Thread.currentThread().getName() + "被放行");
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

//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//
//        }
//        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
