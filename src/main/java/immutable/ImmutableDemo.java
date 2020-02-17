package immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * 一个属性是对象，但是整体不可变，其他类无法修改set里面的数据
 *
 * @author yangxin
 * 2020/02/15 21:56
 */
public class ImmutableDemo {
    private final Set<String> studentSet = new HashSet<>();

    public ImmutableDemo() {
        studentSet.add("李小梅");
        studentSet.add("王壮");
        studentSet.add("徐福记");
    }

    public boolean isStudent(String name) {
        return studentSet.contains(name);
    }
}
