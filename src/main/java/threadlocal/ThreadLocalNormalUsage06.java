package threadlocal;

import lombok.AllArgsConstructor;

/**
 * 演示ThreadLocal用法2：避免传递参数的麻烦
 *
 * @author yangxin
 * 2020/01/01 18:08
 */
public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Service1().process();
    }
}

class Service1 {
    void process() {
        User user = new User("tom");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {
    void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到用户名： " + user.name);
        new Service3().process();
    }
}

class Service3 {
    void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名： " + user.name);
        UserContextHolder.holder.remove();
    }
}

@AllArgsConstructor
class User {
    String name;
}

class UserContextHolder {
    static ThreadLocal<User> holder = new ThreadLocal<>();
}