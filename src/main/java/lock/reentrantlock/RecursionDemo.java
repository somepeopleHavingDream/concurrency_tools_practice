package lock.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxin
 * 2020/02/13 19:49
 */
@SuppressWarnings({"SingleStatementInBlock", "AlibabaRemoveCommentedCode", "AlibabaUndefineMagicConstant"})
public class RecursionDemo {
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static void accessResource() {
        LOCK.lock();
        try {
            System.out.println("已经对资源进行了处理");
            if (LOCK.getHoldCount() < 5) {
//                System.out.println(lock.getHoldCount());
                accessResource();
//                System.out.println(lock.getHoldCount());
            }
        } finally {
            LOCK.unlock();
        }
    }

    public static void main(String[] args) {
        accessResource();
    }
}
