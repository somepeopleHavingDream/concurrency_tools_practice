package collections.predecessor;

import java.util.Hashtable;

/**
 * @author yangxin
 * 2020/02/17 19:42
 */
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("学完以后跳槽涨薪幅度", "80%");
        System.out.println(hashtable.get("学完以后跳槽涨薪幅度"));
    }
}
