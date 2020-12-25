package collections.copyonwrite;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 对比两个迭代器
 *
 * @author yangxin
 * 2020/02/19 20:57
 */
public class CopyOnWriteArrayListDemo2 {

    public static void main(String[] args) {
        List<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        System.out.println(list);

        Iterator<Integer> iterator1 = list.iterator();
        list.add(4);
        System.out.println(list);

        Iterator<Integer> iterator2 = list.iterator();

        iterator1.forEachRemaining(System.out::println);
        System.out.println();
        iterator2.forEachRemaining(System.out::println);
    }
}
