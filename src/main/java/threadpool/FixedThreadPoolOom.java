package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 演示newFixedThreadPool出错的情况
 *
 * @author yangxin
 * 2020/01/01 20:37
 */
@SuppressWarnings("AlibabaThreadPoolCreation")
public class FixedThreadPoolOom {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * -Xmx8m -Xms8m
     */
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
