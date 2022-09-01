package lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 *
 * @author yangxin
 * 2020/02/15 20:19
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class SpinLock {
    private final AtomicReference<Thread> SIGN = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!SIGN.compareAndSet(null, current)) {
            System.out.println("自旋获取失败，再次尝试");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        SIGN.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "开始尝试获取自旋锁");
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了自旋锁");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放了自旋锁");
                spinLock.unlock();
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
