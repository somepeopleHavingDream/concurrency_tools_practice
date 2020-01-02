package atomic;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示高并发场景下，LongAdder比AtomicLong性能好
 *
 * @author yangxin
 * 2020/01/02 20:59
 */
public class LongAdderDemo {
    public static void main(String[] args) {
        LongAdder counter = new LongAdder();

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(new Task(counter));
        }

//        TimeUnit.MILLISECONDS.sleep(10000);

        executorService.shutdown();
        while (!executorService.isTerminated()) {}
        long end = System.currentTimeMillis();

        System.out.println(counter.sum());
        System.out.println("LongAdder耗时：" + (end - start));
    }

    @AllArgsConstructor
    private static class Task implements Runnable {
        private LongAdder counter;

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        }
    }
}
