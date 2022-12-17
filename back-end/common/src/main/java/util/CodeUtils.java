package util;

import java.util.Random;

/**
 * 生成6位随机验证码
 */
public class CodeUtils {
    private static final String baseChar="0123456789";

    public static String generateText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String ch = baseChar.charAt(new Random().nextInt(baseChar.length())) + "";
            sb.append(ch);
        }
        return sb.toString();
    }
}
