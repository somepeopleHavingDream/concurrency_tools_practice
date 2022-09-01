package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/14 17:23
 */
@SuppressWarnings({"DuplicatedCode", "AlibabaAvoidManuallyCreateThread"})
public class CinemaReadWriteQueue {

    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock(false);
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();

    private static void read() {
        READ_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            READ_LOCK.unlock();
        }
    }

    private static void write() {
        WRITE_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            WRITE_LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(CinemaReadWriteQueue::write, "Thread1").start();
        new Thread(CinemaReadWriteQueue::read, "Thread2").start();
        new Thread(CinemaReadWriteQueue::read, "Thread3").start();
        new Thread(CinemaReadWriteQueue::write, "Thread4").start();
        new Thread(CinemaReadWriteQueue::read, "Thread5").start();
    }
}
