package lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxin
 * 2020/02/13 19:41
 */
@SuppressWarnings("AlibabaLockShouldWithTryFinally")
public class GetHoldCount {
    private static final ReentrantLock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        System.out.println(LOCK.getHoldCount());
        LOCK.lock();
        System.out.println(LOCK.getHoldCount());
        LOCK.lock();
        System.out.println(LOCK.getHoldCount());
        LOCK.lock();
        System.out.println(LOCK.getHoldCount());
        LOCK.unlock();
        System.out.println(LOCK.getHoldCount());
        LOCK.unlock();
        System.out.println(LOCK.getHoldCount());
        LOCK.unlock();
    }
}
