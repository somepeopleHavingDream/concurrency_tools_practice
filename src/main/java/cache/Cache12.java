package cache;

import cache.computable.Computable;
import cache.computable.ExpensiveFunction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangxin
 * 2020/02/22 21:45
 */
public class Cache12 {
    private static Computable<String, Integer> computable = new Cache10<>(new ExpensiveFunction());

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3000);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 3000; i++) {
            executorService.submit(() -> {
                Integer result = null;
                try {
                    result = computable.compute("666");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
