package future;

import java.util.concurrent.*;

/**
 * 演示FutureTask的用法
 *
 * @author yangxin
 * 2020/02/21 22:33
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
public class FutureTaskDemo {

    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
//        new Thread(futureTask).start();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);

        try {
            // get方法会阻塞当前线程
            System.out.println("task运行结果：" + futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}

/**
 * @author yangxin
 * 2020/02/21 22:33
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
class Task implements Callable<Integer> {

    @Override
    public Integer call() throws InterruptedException {
        System.out.println("子线程正在计算");
        Thread.sleep(3000);

        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}
