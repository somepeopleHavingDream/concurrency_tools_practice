package collections.predecessor;

import java.util.Vector;

/**
 * 演示Vector，主要是看Vector源码
 *
 * @author yangxin
 * 2020/02/17 19:39
 */
public class VectorDemo {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("test");
        System.out.println(vector.get(0));
    }
}
