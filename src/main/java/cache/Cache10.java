package cache;

import cache.computable.Computable;
import cache.computable.MayFail;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 出于安全性考虑，缓存需要设置有效期，到期自动失效，
 * 否则如果缓存一直不失效，那么带来缓存不一致等问题
 *
 * @author yangxin
 * 2020/02/22 20:11
 */
public class Cache10<T, R> implements Computable<T, R> {
    private final Map<T, Future<R>> cache = new ConcurrentHashMap<>();
    private final Computable<T, R> computable;
    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    private Cache10(Computable<T, R> computable) {
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

    private R compute(T arg, long expire) throws InterruptedException {
        if (expire > 0) {
            executor.schedule(() -> expire(arg), expire, TimeUnit.MILLISECONDS);
        }

        return compute(arg);
    }

    private R computeRandomExpire(T arg) throws InterruptedException {
//        long randomExpire = (long) (Math.random() * 10000);
        return compute(arg, (long) (Math.random() * 10000));
    }

    private synchronized void expire(T key) {
        Future<R> future = cache.get(key);
        if (future != null) {
            if (!future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }

            System.out.println("过期时间到，缓存被清除");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cache10<String, Integer> computer = new Cache10<>(new MayFail());

        new Thread(() -> {
            Integer result;
            try {
                result = computer.compute("666", 5000L);
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

        Thread.sleep(6000L);
        Integer result = computer.compute("666");
        System.out.println("第四次的计算结果：" + result);
    }
}
