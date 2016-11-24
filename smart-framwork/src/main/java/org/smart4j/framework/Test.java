package org.smart4j.framework;

/**
 * Created by Roger on 2016/11/23.
 */
public class Test {

    public static void main(String[] args) {
        // ClassUtil.getClassSet("com.roger.smart4j");
        String str = "GET:/";
        boolean t = str.matches("\\w+:/\\w*");
        boolean r = str.matches("\\w+:/\\w*");
    }
}
