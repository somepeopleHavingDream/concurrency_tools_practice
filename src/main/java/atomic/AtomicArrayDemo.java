package atomic;

import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 演示原子数组的使用方法
 *
 * @author yangxin
 * 2020/01/02 15:58
 */
public class AtomicArrayDemo {

    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1000);

        Increment increment = new Increment(atomicIntegerArray);
        Decrement decrement = new Decrement(atomicIntegerArray);

        Thread[] threadsIncrement = new Thread[100];
        Thread[] threadsDecrement = new Thread[100];

        for (int i = 0; i < 100; i++) {
            threadsDecrement[i] = new Thread(decrement);
            threadsIncrement[i] = new Thread(increment);

            threadsDecrement[i].start();
            threadsIncrement[i].start();
        }

        for (int i = 0; i < 100; i++) {
            try {
                threadsDecrement[i].join();
                threadsIncrement[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            if (atomicIntegerArray.get(i) != 0) {
                System.out.println("发现了错误" + i);
            }
        }
        System.out.println("运行结束");
    }
}

@AllArgsConstructor
class Decrement implements Runnable {

    private final AtomicIntegerArray array;

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndDecrement(i);
        }
    }
}

@AllArgsConstructor
class Increment implements Runnable {

    private final AtomicIntegerArray array;

    @Override
    public void run() {
        for (int i = 0; i < array.length(); i++) {
            array.getAndIncrement(i);
        }
    }
}