package immutable;

/**
 * 演示栈封闭的两种情况，基本变量和对象
 * 先演示线程争抢带来错误结果，然后把变量放到方法内，情况就变了
 *
 * @author yangxin
 * 2020/02/15 22:02
 */
public class StackConfinement implements Runnable {
    private int index = 0;

    private void inThread() {
        int neverGoOut = 0;
        for (int i = 0; i < 10000; i++) {
            neverGoOut++;
        }
        System.out.println("栈内保护的数字是线程安全的：" + neverGoOut);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }

        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement stackConfinement = new StackConfinement();
        Thread thread1 = new Thread(stackConfinement);
        Thread thread2 = new Thread(stackConfinement);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(stackConfinement.index);
    }
}
