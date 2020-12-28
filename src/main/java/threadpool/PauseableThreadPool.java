package threadpool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示每个任务执行前后放钩子函数
 *
 * @author yangxin
 * 2020/01/01 21:30
 */
public class PauseableThreadPool extends ThreadPoolExecutor {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final Condition unPaused = reentrantLock.newCondition();
    private boolean isPaused;

    private PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public PauseableThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // 父类ThreadPoolExecutor的beforeExecute是个空实现
        super.beforeExecute(t, r);

        reentrantLock.lock();
        try {
            while (isPaused) {
                unPaused.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        reentrantLock.lock();
        try {
            isPaused = true;
        } finally {
            reentrantLock.unlock();
        }
    }

    /**
     * 恢复
     */
    private void resume() {
        reentrantLock.lock();

        try {
            isPaused = false;
            // 必须在锁内唤醒所有条件，因为await方法会释放锁，因此这边需要先获得锁在唤醒条件
            unPaused.signalAll();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PauseableThreadPool pauseableThreadPool = new PauseableThreadPool(10,
                20, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        Runnable runnable = () -> {
            System.out.println("我被执行");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 10000; i++) {
            pauseableThreadPool.execute(runnable);
        }
        TimeUnit.MILLISECONDS.sleep(1500);
        pauseableThreadPool.pause();
        System.out.println("线程池被暂停");
        TimeUnit.MILLISECONDS.sleep(1500);
        pauseableThreadPool.resume();
        System.out.println("线程池被恢复");
    }
}
