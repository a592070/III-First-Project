package utils;

import java.util.Objects;

public class StringUtil {
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim()) || Objects.equals(str, "null")) return true;
        return false;
    }
    public static String capitalize(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
}
