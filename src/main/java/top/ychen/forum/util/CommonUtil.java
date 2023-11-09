package top.ychen.forum.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author moqi
 */
public class CommonUtil {

    public static String MD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(MD5("123456"));
    }
}
