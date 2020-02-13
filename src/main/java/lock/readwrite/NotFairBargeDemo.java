package lock.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxin
 * 2020/02/13 20:50
 */
public class NotFairBargeDemo {
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(true);
}
