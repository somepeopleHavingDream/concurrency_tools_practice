package lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示多线程预定电影院座位
 *
 * @author yangxin
 * 2020/02/12 20:42
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class CinemaBookSeat {

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static void bookSeat() {
        LOCK.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预定座位");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "完成预定座位");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(CinemaBookSeat::bookSeat).start();
        new Thread(CinemaBookSeat::bookSeat).start();
        new Thread(CinemaBookSeat::bookSeat).start();
        new Thread(CinemaBookSeat::bookSeat).start();
    }
}
