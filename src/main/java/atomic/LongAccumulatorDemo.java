package atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

/**
 * 演示LongAccumulator的用法
 *
 * @author yangxin
 * 2020/01/02 21:09
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) {
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
//        longAccumulator.accumulate(1);
//        longAccumulator.accumulate(2);

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        IntStream.range(1, 10).forEach(i -> executorService.submit(() -> longAccumulator.accumulate(i)));
        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println(longAccumulator.getThenReset());
    }
}
