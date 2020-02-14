package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/13 20:50
 */
public class UnfairBargeDemo {
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
//    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(false);

    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到写锁，正在写入");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(UnfairBargeDemo::write, "Thread1").start();
        new Thread(UnfairBargeDemo::read, "Thread2").start();
        new Thread(UnfairBargeDemo::read, "Thread3").start();
        new Thread(UnfairBargeDemo::write, "Thread4").start();
        new Thread(UnfairBargeDemo::read, "Thread5").start();

        new Thread(() -> {
            Thread[] threads = new Thread[1000];
            for (int i = 0; i < 1000; i++) {
                threads[i] = new Thread(UnfairBargeDemo::read, "子线程创建的Thread" + i);
            }
            for (int i = 0; i < 1000; i++) {
                threads[i].start();
            }
        }).start();
    }
}
