package future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示一个Future的使用方法
 *
 * @author yangxin
 * 2020/02/021 20:55
 */
public class OneFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> future = executorService.submit(new CallableTask());
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    /**
     * @author yangxin
     * 2020/02/21 20:57
     */
    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws InterruptedException {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}
