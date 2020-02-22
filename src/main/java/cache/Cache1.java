package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 最简单的缓存形式：HashMap
 *
 * @author yangxin
 * 2020/02/22 10:44
 */
public class Cache1 {
    private final Map<String, Integer> cache = new HashMap<>();

    private synchronized Integer compute(String userId) throws InterruptedException {
        // 先检查HashMap里面有没有保存过之前的计算结果
        Integer result = cache.get(userId);
        if (result == null) {
            // 如果缓存中找不到，那么需要现在计算一下结果且保存到HashMap中
            result = doCompute(userId);
            cache.put(userId, result);
        }

        return result;
    }

    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Integer(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        Cache1 cache1 = new Cache1();
        System.out.println("开始计算了");
        Integer result = cache1.compute("13");
        System.out.println("第一次计算结果：" + result);
        result = cache1.compute("13");
        System.out.println("第二次计算结果：" + result);
    }
}
