package collections.concurrenthashmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 组合操作并不保证线程安全
 *
 * @author yangxin
 * 2020/02/19 15:38
 */
public class OptionsNotSafe implements Runnable {
    private static ConcurrentHashMap<String, Integer> scoreMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        scoreMap.put("小明", 0);

        Thread t1 = new Thread(new OptionsNotSafe());
        Thread t2 = new Thread(new OptionsNotSafe());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(scoreMap);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            while (true) {
                Integer score = scoreMap.get("小明");
                Integer newScore = score + 1;
                boolean result = scoreMap.replace("小明", score, newScore);
                if (result) {
                    break;
                }
            }

        }
    }
}
