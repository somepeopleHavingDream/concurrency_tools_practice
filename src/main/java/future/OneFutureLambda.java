package future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示一个Future的使用方法，lambda形式
 *
 * @author yangxin
 * 2020/02/021 20:55
 */
public class OneFutureLambda {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<Integer> callable =  () -> {
            Thread.sleep(3000);
            return new Random().nextInt();
        };

        Future<Integer> future = executorService.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
