package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/13 20:50
 */
@SuppressWarnings({"AlibabaCommentsMustBeJavadocFormat", "DuplicatedCode", "AlibabaAvoidManuallyCreateThread", "AlibabaUndefineMagicConstant"})
public class UnfairBargeDemo {

    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock(false);

    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取读锁");
        READ_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到读锁，正在读取");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放读锁");
            READ_LOCK.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始尝试获取写锁");
        WRITE_LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到写锁，正在写入");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放写锁");
            WRITE_LOCK.unlock();
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
