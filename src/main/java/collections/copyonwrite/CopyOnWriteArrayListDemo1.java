package collections.copyonwrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yangxin
 * 2020/02/19 20:11
 */
public class CopyOnWriteArrayListDemo1 {

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
        List<String> list = new CopyOnWriteArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        for (String s : list) {
            System.out.println("list is " + list);
            System.out.println(s);

            if (s.equals("2")) {
                list.remove("5");
            }
            if (s.equals("3")) {
                list.add("3 found");
            }
        }
    }
}
