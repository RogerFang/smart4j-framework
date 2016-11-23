package com.roger.smart4j.framework;

import com.roger.smart4j.framework.helper.BeanHelper;
import com.roger.smart4j.framework.helper.ClassHelper;
import com.roger.smart4j.framework.helper.ControllerHelper;
import com.roger.smart4j.framework.helper.IocHelper;
import com.roger.smart4j.framework.util.ClassUtil;

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
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls: clsList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
