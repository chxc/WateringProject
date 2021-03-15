package com.xnsj.watering;

import android.util.Log;

import java.text.SimpleDateFormat;

public class YCStringTool {

    public static void logi(Class c, String str) {
        Log.i("TAG123456", c.getName() + "\n" + str);
    }

    /*格式化时间  为小时分钟加秒*/
    public static String formatS(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
        String formatStr =formatter.format(time);
        return formatStr;
    }
}
