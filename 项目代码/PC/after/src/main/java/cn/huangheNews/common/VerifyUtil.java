package cn.huangheNews.common;

public class VerifyUtil {
    public static String getVerifyCode(){
        String verifyCode = "";
        for (int i=0;i<6;i++) {
            verifyCode = verifyCode.concat(String.valueOf((int)(Math.random()*10)));
        }
        return verifyCode;
    }
}
