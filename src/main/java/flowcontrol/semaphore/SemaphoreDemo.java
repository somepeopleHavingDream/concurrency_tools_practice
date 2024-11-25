package flowcontrol.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 演示Semaphore的用法
 *
 * @author yangxin
 * 2020/02/20 14:18
 */
@SuppressWarnings({"AlibabaThreadPoolCreation", "AlibabaUndefineMagicConstant"})
public class SemaphoreDemo {

    private static final Semaphore SEMAPHORE = new Semaphore(3, true);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Task());
        }
        executorService.shutdown();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                SEMAPHORE.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "拿到了许可证");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "释放了许可证");
            SEMAPHORE.release();
        }
    }
}
