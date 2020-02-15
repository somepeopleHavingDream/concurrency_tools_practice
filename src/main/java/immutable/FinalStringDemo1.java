package immutable;

/**
 * @author yangxin
 * 2020/02/15 22:10
 */
public class FinalStringDemo1 {
    public static void main(String[] args) {
        String a = "wukong2";

        final String b = "wukong";
        String d = "wukong";

        String c = b + 2;
        String e = d + 2;

        System.out.println(a == c);
        System.out.println(a == e);
    }
}
