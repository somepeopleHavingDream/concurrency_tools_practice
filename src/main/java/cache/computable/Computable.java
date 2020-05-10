package cache.computable;

/**
 * 有一个计算函数compute，用来代表耗时计算，每个计算器都要实现这个接口，
 * 这样就可以无侵入实现缓存功能
 *
 * @author yangxin
 * 2020/02/22 11:00
 */
public interface Computable<T, R> {

    R compute(T arg) throws Exception;
}
