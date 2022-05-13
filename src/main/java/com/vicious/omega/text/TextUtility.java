package com.vicious.omega.text;

public class TextUtility {
    public static String toPlain(String s){
        StringBuilder ret = new StringBuilder();
        boolean skip = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == MCTextColor.COLOR_CHAR){
                skip = true;
            }
            else if(!skip){
                ret.append(c);
            }
            else skip = false;
        }
        return ret.toString();
    }
}
