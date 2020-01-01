package threadlocal;

/**
 * ThreaLocal空指针异常
 *
 * @author yangxin
 * 2020/01/01 18:58
 */
public class ThreadLocalNPE {
    private ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    private void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

    private Long get() {
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        System.out.println(threadLocalNPE.get());
        new Thread(() -> {
            threadLocalNPE.set();
            System.out.println(threadLocalNPE.get());
        }).start();
    }
}
