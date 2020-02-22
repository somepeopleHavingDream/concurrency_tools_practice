package cache.computable;

import java.io.IOException;

/**
 * 耗时计算的实现类，有概率计算失败
 *
 * @author yangxin
 * 2020/02/22 21:07
 */
public class MayFail implements Computable<String, Integer> {
    @Override
    public Integer compute(String arg) throws Exception {
        double random = Math.random();
        if (random > 0.95) {
            throw new IOException("读取文件出错");
        }

        Thread.sleep(3000);
        return Integer.valueOf(arg);
    }
}
