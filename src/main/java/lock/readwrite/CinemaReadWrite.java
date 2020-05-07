package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/13 20:32
 */
public class CinemaReadWrite {

    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了读锁，正在读取");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到了写锁，正在写入");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
//        new Thread(CinemaReadWrite::read, "Thread1").start();
//        new Thread(CinemaReadWrite::read, "Thread2").start();
//        new Thread(CinemaReadWrite::write, "Thread3").start();
//        new Thread(CinemaReadWrite::write, "Thread4").start();

        new Thread(CinemaReadWrite::write, "Thread1").start();
        new Thread(CinemaReadWrite::read, "Thread2").start();
        new Thread(CinemaReadWrite::read, "Thread3").start();
        new Thread(CinemaReadWrite::write, "Thread4").start();
        new Thread(CinemaReadWrite::read, "Thread5").start();
    }
}
