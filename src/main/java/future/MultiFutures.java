package future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 演示批量提交任务时，用List来批量接收结果
 *
 * @author yangxin
 * 2020/02/21 21:17
 */
public class MultiFutures {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Future<Integer> future = executorService.submit(new CallableTask());
            futureList.add(future);
        }

        for (int i = 0; i < 20; i++) {
            Future<Integer> future = futureList.get(i);
            try {
                Integer result = future.get();
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
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
