public class Solution7 {
    public static void main(String[] args) {
        String capu = "Ballerina Cappuccina";
        String mi = "mi mi mi mi";

        System.out.println(capu);
        System.out.println(revStr(capu, capu.length()));

        System.out.println();

        System.out.println(mi);
        System.out.println(revStr(mi, mi.length()));
    }
    public static String revStr(String str, int len) {
        len = len - 1;
        String rev;
        if (len < 0) {
            return "";
        } else {
            return str.charAt(len) + revStr(str, len);
        }
    }
}