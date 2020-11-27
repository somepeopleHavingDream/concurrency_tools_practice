package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 演示AtomicIntegerFieldUpdater的用法
 *
 * @author yangxin
 * 2020/01/02 16:44
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable {

    private static Candidate tom;
    private static Candidate peter;

    private static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater
            .newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            peter.score++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    private static class Candidate {
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        tom = new Candidate();
        peter = new Candidate();

        AtomicIntegerFieldUpdaterDemo atomicIntegerFieldUpdaterDemo = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(atomicIntegerFieldUpdaterDemo);
        Thread t2 = new Thread(atomicIntegerFieldUpdaterDemo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("普通变量：" + peter.score);
        System.out.println("升级后的结果：" + tom.score);
    }
}
