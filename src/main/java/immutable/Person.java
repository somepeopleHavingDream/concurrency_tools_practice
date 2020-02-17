package immutable;

/**
 * 不可变的对象，演示其他类无法修改这个对象，public也不行
 */
class Person {
    final int age = 18;
    final String name = "Alice";
}
