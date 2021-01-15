package future;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.*;

/**
 * 演示get的超时方法，需要注意超时后处理，调用future.cancel()。
 * 演示cancel传入true和false的区别，代表是否中断正在执行的任务
 *
 * @author yangxin
 * 2020/02/21 21:56
 */
public class Timeout {

    private static final Ad DEFAULT_AD = new Ad("无网络时候的默认广告");
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    private void printAd() {
        Future<Ad> adFuture = EXECUTOR_SERVICE.submit(new FetchAdTask());
        Ad ad;
        try {
            ad = adFuture.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            ad = new Ad("被中断时候的默认广告");
        } catch (ExecutionException e) {
            ad = new Ad("执行异常时候的默认广告");
        } catch (TimeoutException e) {
            ad = new Ad("超时时候的默认广告");
            System.out.println("超时，未获取到广告");
            boolean cancel = adFuture.cancel(true);
            System.out.println("cancel的结果：" + cancel);
        }

        EXECUTOR_SERVICE.shutdown();
        System.out.println(ad);
    }

    public static void main(String[] args) {
        Timeout timeout = new Timeout();
        timeout.printAd();
    }

    /**
     * @author yangxin
     * 2020/02/21 10:01
     */
    @Data
    @AllArgsConstructor
    private static class Ad {

        private String name;
    }

    /**
     * @author yangxin
     * 2020/02/21 10:01
     */
    private static class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("sleep期间被中断了");
                return new Ad("被中断时候的默认广告");
            }

            return new Ad("旅游订票哪家强？找某程");
        }
    }
}
