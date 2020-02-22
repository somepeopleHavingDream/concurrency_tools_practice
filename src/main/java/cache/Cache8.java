package cache;

import cache.computable.Computable;
import cache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 缩小了synchronized额粒度，提高性能，但是依然并发不安全
 *
 * @author yangxin
 * 2020/02/22 20:11
 */
public class Cache8<T, R> implements Computable<T, R> {
    private final Map<T, Future<R>> cache = new ConcurrentHashMap<>();
    private final Computable<T, R> computable;

    private Cache8(Computable<T, R> computable) {
        this.computable = computable;
    }

    @Override
    public R compute(T arg) throws Exception {
        Future<R> future = cache.get(arg);
        if (future == null) {
            FutureTask<R> futureTask = new FutureTask<>(() -> computable.compute(arg));
            future = cache.putIfAbsent(arg, futureTask);
            if (future == null) {
                future = futureTask;
                System.out.println("从FutureTask调用了计算函数");
                futureTask.run();
            }
        }

        return future.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Computable<String, Integer> expensiveComputer = new Cache8<>(new ExpensiveFunction());

        new Thread(() -> {
            Integer result;
            try {
                result = expensiveComputer.compute("666");
                System.out.println("第一次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            Integer result;
            try {
                result = expensiveComputer.compute("666");
                System.out.println("第三次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Integer result;
            try {
                result = expensiveComputer.compute("667");
                System.out.println("第二次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
