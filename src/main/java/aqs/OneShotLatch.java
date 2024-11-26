package aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自己用 AQS 实现一个简单的线程协作器
 *
 * @author yangxin
 * 2020/02/21 10:19
 */
public class OneShotLatch {

    private final Sync sync = new Sync();

    private void signal() {
        sync.releaseShared(0);
    }

    /**
     * 想让线程等待的话，得让 acquireShared 方法返回负数，让线程排队去才行
     */
    private void await() {
        sync.acquireShared(0);
    }

    /**
     * @author yangxin
     * 2020/02/21 10:27
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -3229443816708463423L;

        @Override
        protected int tryAcquireShared(int arg) {
            return (getState() == 1) ? 1 : -1;
//            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "尝试打开latch，获取失败那就等待");
                oneShotLatch.await();
                System.out.println("开闸放行" + Thread.currentThread().getName() + "继续运行");
            }).start();
        }

        Thread.sleep(5000);
        oneShotLatch.signal();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "尝试打开latch，获取失败那就等待");
            oneShotLatch.await();
            System.out.println("开闸放行" + Thread.currentThread().getName() + "继续运行");
        }).start();
    }
}
