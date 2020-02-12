package lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock不会像synchronized一样，异常的时候自动释放锁，所以最佳实践是finally中释放锁，以便保证发生异常的时候锁一定被释放
 *
 * @author yangxin
 * 2020/02/13 19:45
 */
public class MustUnlock {
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行任务");
        } finally {
            lock.unlock();
        }
    }
}
