package collections.predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 演示Map的基本用法
 *
 * @author yangxin
 * 2020/02/17 21:52
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        System.out.println(map.isEmpty());
        map.put("东哥", 38);
        map.put("西哥", 28);
        System.out.println(map.keySet());
        System.out.println(map.get("西哥"));
        System.out.println(map.size());
        System.out.println(map.containsKey("东哥"));
    }
}
