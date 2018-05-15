package com.goleee.luck.utility;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtility
{
    /**
     * 验证是否为电话号码
     * @param str
     * @return
     */
    public static boolean isPhone(final String str)
    {
        Matcher m = null;
        boolean b = false;
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p1 = Pattern.compile(regex);
        if(str.length() == 11)
        {
            m = p1.matcher(str);
            b = m.matches();
        }
        return b;
    }
}
