package aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxin
 * 2020/02/20 20:48
 */
public class AQSDemo {

    public static void main(String[] args) {
        new Semaphore(5);
        new CountDownLatch(3);
        new ReentrantLock();
    }
}
