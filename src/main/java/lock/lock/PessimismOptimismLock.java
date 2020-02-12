package lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangxin
 * 2020/02/12 20:30
 */
public class PessimismOptimismLock {
    private int a;

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    public synchronized void testMethod() {
        a++;
    }
}
