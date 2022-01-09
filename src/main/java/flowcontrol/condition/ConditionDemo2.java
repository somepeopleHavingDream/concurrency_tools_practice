package flowcontrol.condition;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示用Condition实现生产者消费者模式
 *
 * @author yangxin
 * 2020/02/20 17:13
 */
@SuppressWarnings({"AlibabaRemoveCommentedCode", "AlibabaAvoidManuallyCreateThread"})
public class ConditionDemo2 {

    private final int queueSize = 10;
    private final PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    /**
     * @author yangxin
     * 2020/02/20 17:27
     */
    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("InfiniteLoopStatement")
        private void consume() throws InterruptedException {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        System.out.println("队列空，等待数据");
                        notEmpty.await();
                    }

                    queue.poll();
                    notFull.signalAll();
                    System.out.println("从队列里取走了一个数据，队列剩余" + queue.size() + "个元素");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * @author yangxin
     * 2020/02/20 20:05
     */
    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @SuppressWarnings("InfiniteLoopStatement")
        private void produce() throws InterruptedException {
            while (true) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        System.out.println("队列满，等待有空余");
                        notFull.await();
                    }

                    queue.offer(1);
                    notEmpty.signalAll();
                    System.out.println("往队列里插入了一个数据，队列剩余空间" + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ConditionDemo2 conditionDemo2 = new ConditionDemo2();
        Producer producer = conditionDemo2.new Producer();
        Consumer consumer = conditionDemo2.new Consumer();
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
