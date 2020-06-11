package threadpool;

/**
 * 每个任务一个线程
 *
 * @author yangxin
 * 2020/01/01 20:05
 */
public class EveryTaskOneThread {

    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.start();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("执行了任务");
        }
    }
}
