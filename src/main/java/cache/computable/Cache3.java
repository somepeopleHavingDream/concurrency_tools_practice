package cache.computable;

import java.util.HashMap;
import java.util.Map;

/**
 * 用装饰者模式，给计算器自动添加缓存功能
 *
 * @author yangxin
 * 2020/02/22 11:28
 */
public class Cache3<T, R> implements Computable<T, R> {
    private final Map<T, R> cache = new HashMap<>();
    private final Computable<T, R> computable;

    private Cache3(Computable<T, R> computable) {
        this.computable = computable;
    }

    @Override
    public synchronized R compute(T arg) throws Exception {
        System.out.println("进入缓存机制");
        R result = cache.get(arg);
        if (result == null) {
            result = computable.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        Computable<String, Integer> expensiveComputer = new Cache3<>(new ExpensiveFunction());

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
                result = expensiveComputer.compute("667");
                System.out.println("第二次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(6000);

        new Thread(() -> {
            Integer result;
            try {
                result = expensiveComputer.compute("666");
                System.out.println("第三次计算结果：" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
