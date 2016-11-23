package com.roger.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Roger on 2016/11/23.
 */
public final class CastUtil {

    public static String castString(Object obj){
        return castString(obj, "");
    }

    public static String castString(Object obj, String defaulValue){
        return obj != null ? String.valueOf(obj) : defaulValue;
    }

    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue){
        int value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)){
                value = Integer.parseInt(strValue);
            }
        }
        return value;
    }

    public static double castDouble(Object obj){
        return castDouble(obj, 0.0);
    }

    public static double castDouble(Object obj, double defaultValue){
        double value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)){
                value = Double.parseDouble(strValue);
            }
        }
        return value;
    }

    public static long castLong(Object obj){
        return castLong(obj, 0l);
    }

    public static long castLong(Object obj, long defaultValue){
        long value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)){
                value = Long.parseLong(strValue);
            }
        }
        return value;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue){
        boolean value = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if (StringUtils.isNotEmpty(strValue)){
                value = Boolean.parseBoolean(strValue);
            }
        }
        return value;
    }
}
