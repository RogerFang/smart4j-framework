package org.smart4j.framework;


import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.ClassUtil;

/**
 * 初始化框架
 * 入口程序：加载Helper
 * Created by Roger on 2016/11/23.
 */
public class HelperLoader {

    public static void init(){
        Class<?>[] clsList = {
                ClassHelper.class,
                BeanHelper.class,
                // AopHelper在IocHelper之前加载
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls: clsList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
