package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示AtomicInteger的基本用法，对比非原子类的线程安全问题，使用了原子类之后，不需要加锁，也可以保证线程安全
 *
 * @author yangxin
 * 2020/01/02 11:48
 */
public class AtomicIntegerDemo1 implements Runnable {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    private void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    private static volatile int basicCount = 0;

    private synchronized void incrementBasic() {
        basicCount++;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerDemo1 atomicIntegerDemo1 = new AtomicIntegerDemo1();

        Thread t1 = new Thread(atomicIntegerDemo1);
        Thread t2 = new Thread(atomicIntegerDemo1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("原子类的结果：" + atomicInteger.get());
        System.out.println("普通变量的结果：" + basicCount);
    }
}
