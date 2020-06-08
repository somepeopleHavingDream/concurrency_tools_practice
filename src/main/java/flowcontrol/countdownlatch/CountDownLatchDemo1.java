package flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工厂中，质检，5个工人检查，所有人都认为通过才通过
 *
 * @author yangxin
 * 2020/02/20 11:21
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = () -> {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("No." + no + "完成了检查。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            };

            executorService.submit(runnable);
        }
        System.out.println("等待5个人检查完……");
        countDownLatch.await();
        System.out.println("所有人都完成了工作，进入下一个环节。");
        executorService.shutdown();
    }
}
