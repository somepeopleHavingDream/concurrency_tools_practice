package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/14 17:28
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class Upgrading {

    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();

    private static void readUpgrading() {
        READ_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);

            System.out.println("升级会带来阻塞");
            WRITE_LOCK.lock();
            System.out.println(Thread.currentThread().getName() + "获取到了写锁，升级成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            READ_LOCK.unlock();
        }
    }

    private static void writeDowngrading() {
        WRITE_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);

            READ_LOCK.lock();
            System.out.println("在不释放写锁的情况下，直接获取读锁，成功降级");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            READ_LOCK.unlock();
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            WRITE_LOCK.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("先演示降级是可以的");
        Thread thread1 = new Thread(Upgrading::writeDowngrading, "Thread1");
        thread1.start();
        thread1.join();

        System.out.println("--------------------");
        System.out.println("演示升级是不行的");
        new Thread(Upgrading::readUpgrading, "Thread2").start();
    }
}
