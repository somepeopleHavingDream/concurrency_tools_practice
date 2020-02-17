package immutable;

/**
 * @author yangxin
 * 2020/02/15 22:14
 */
public class FinalStringDemo2 {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = getDaShiXiong();
        String c = b + 2;
        System.out.println(a == c);
    }

    private static String getDaShiXiong() {
        return "wukong";
    }
}
