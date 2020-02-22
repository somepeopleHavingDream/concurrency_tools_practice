package cache;

import cache.computable.Computable;
import cache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 缩小了synchronized额粒度，提高性能，但是依然并发不安全
 *
 * @author yangxin
 * 2020/02/22 20:11
 */
public class Cache9<T, R> implements Computable<T, R> {
    private final Map<T, Future<R>> cache = new ConcurrentHashMap<>();
    private final Computable<T, R> computable;

    private Cache9(Computable<T, R> computable) {
        this.computable = computable;
    }

    @Override
    public R compute(T arg) throws InterruptedException {
        while (true) {
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

            try {
                return future.get();
            } catch (CancellationException e) {
                System.out.println("被取消了");
                cache.remove(arg);
                throw e;
            } catch (InterruptedException e) {
                cache.remove(arg);
                throw e;
            } catch (ExecutionException e) {
                System.out.println("计算错误，需要重试");
                cache.remove(arg);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cache9<String, Integer> computer = new Cache9<>(new MayFail());

        new Thread(() -> {
            Integer result;
            try {
                result = computer.compute("666");
                System.out.println("第一次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            Integer result;
            try {
                result = computer.compute("666");
                System.out.println("第三次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            Integer result;
            try {
                result = computer.compute("667");
                System.out.println("第二次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        Future<Integer> future = computer.cache.get("666");
//        future.cancel(true);
    }
}
