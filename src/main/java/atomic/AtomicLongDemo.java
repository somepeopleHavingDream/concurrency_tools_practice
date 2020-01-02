package atomic;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演示高并发场景下，LongAdder比AtomicLong性能好
 *
 * @author yangxin
 * 2020/01/02 20:44
 */
public class AtomicLongDemo {
    public static void main(String[] args) {
        AtomicLong counter = new AtomicLong(0);

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(new Task(counter));
        }

//        TimeUnit.MILLISECONDS.sleep(10000);

        executorService.shutdown();
        while (!executorService.isTerminated()) {}
        long end = System.currentTimeMillis();

        System.out.println(counter.get());
        System.out.println("AtomicLong耗时：" + (end - start));
    }

    @AllArgsConstructor
    private static class Task implements Runnable {
        private AtomicLong counter;

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.incrementAndGet();
            }
        }
    }
}
