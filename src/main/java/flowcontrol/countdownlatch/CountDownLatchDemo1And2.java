package flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步，当所有人都到终点后，比赛结束。
 *
 * @author yangxin
 * 2020/02/20 13:46
 */
@SuppressWarnings({"AlibabaThreadPoolCreation", "AlibabaAvoidMissUseOfMathRandom", "AlibabaUndefineMagicConstant", "CallToPrintStackTrace"})
public class CountDownLatchDemo1And2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Runnable runnable = getRunnable(i, begin, end);
            executorService.submit(runnable);
        }

        // 裁判员检查发令枪...
        Thread.sleep(5000);
        System.out.println("发令枪响，比赛开始！");
        begin.countDown();

        end.await();
        System.out.println("所有人到达终点，比赛结束");
        executorService.shutdown();
    }

    private static Runnable getRunnable(int i, CountDownLatch begin, CountDownLatch end) {
        final int no = i + 1;
        return () -> {
            System.out.println("No." + no + "准备完毕，等待发令枪");
            try {
                begin.await();
                System.out.println("No." + no + "开始跑步了");

                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("No." + no + "跑到了终点");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                end.countDown();
            }
        };
    }
}
