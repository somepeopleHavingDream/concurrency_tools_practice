package flowcontrol.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示Condition的基本用法
 *
 * @author yangxin
 * 2020/02/20 16:45
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class ConditionDemo1 {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private void method1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println("条件不满足，开始await");
            condition.await();
            System.out.println("条件满足了，开始执行后续的任务");
        } finally {
            lock.unlock();
        }
    }

    private void method2() {
        lock.lock();
        try {
            System.out.println("准备工作完成，唤醒其他的线程");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo1 conditionDemo1 = new ConditionDemo1();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                conditionDemo1.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        conditionDemo1.method1();
    }
}
