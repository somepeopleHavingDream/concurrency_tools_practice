package cache;

import cache.computable.Computable;
import cache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缩小了synchronized额粒度，提高性能，但是依然并发不安全
 *
 * @author yangxin
 * 2020/02/22 11:36
 */
public class Cache6<T, R> implements Computable<T, R> {
    private final Map<T, R> cache = new ConcurrentHashMap<>();
    private final Computable<T, R> computable;

    private Cache6(Computable<T, R> computable) {
        this.computable = computable;
    }

    @Override
    public R compute(T arg) throws Exception {
        System.out.println("进入缓存机制");
        R result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }

    public static void main(String[] args) {
        Computable<String, Integer> expensiveComputer = new Cache6<>(new ExpensiveFunction());

        new Thread(() -> {
            Integer result;
            try {
                result = expensiveComputer.compute("666");
                System.out.println("第一次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


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
