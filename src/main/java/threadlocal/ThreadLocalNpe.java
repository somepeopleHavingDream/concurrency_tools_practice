package threadlocal;

/**
 * ThreaLocal空指针异常
 *
 * @author yangxin
 * 2020/01/01 18:58
 */
@SuppressWarnings("AlibabaAvoidManuallyCreateThread")
public class ThreadLocalNpe {

    private final ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    private void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

    private Long get() {
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNpe threadLocalNpe = new ThreadLocalNpe();
        System.out.println(threadLocalNpe.get());
        new Thread(() -> {
            threadLocalNpe.set();
            System.out.println(threadLocalNpe.get());
        }).start();
    }
}
