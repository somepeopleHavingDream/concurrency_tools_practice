import java.util.HashMap;
import java.util.Map;

/**
 * 演示HashMap在多线程情况下造成死循环问题
 *
 * @author yangxin
 * 2020/02/17 22:00
 */
public class HashMapEndlessLoop {
    private static Map<Integer, String> map = new HashMap<>(2, 1.5f);

    public static void main(String[] args) {
        map.put(5, "C");
        map.put(7, "B");
        map.put(3, "A");
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(15, "D");
                System.out.println(map);
            }
        }, "Thread 1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                map.put(1, "E");
                System.out.println(map);
            }
        }, "Thread 2").start();
    }
}
