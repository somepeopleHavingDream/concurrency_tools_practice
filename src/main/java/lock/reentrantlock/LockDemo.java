package lock.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示ReentrantLock的基本用法，演示被打断
 *
 * @author yangxin
 * 2020/02/12 20:53
 */
@SuppressWarnings({"AlibabaAvoidManuallyCreateThread", "BusyWait"})
public class LockDemo {
    public static void main(String[] args) {
        new LockDemo().init();
    }

    private void init() {
        final Outputer outputer = new Outputer();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputer.output("悟空");
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputer.output("大师兄");
            }
        }).start();
    }

    /**
     * @author yangxin
     * 2020/02/12 20:54
     */
    static class Outputer {
        private final Lock lock = new ReentrantLock();

        /**
         * 字符串打印方法，一个一个字符地打印
         */
        void output(String name) {
            int length = name.length();
            lock.lock();
            try {
                for (int i = 0; i < length; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            } finally {
                lock.unlock();
            }
        }
    }
}
