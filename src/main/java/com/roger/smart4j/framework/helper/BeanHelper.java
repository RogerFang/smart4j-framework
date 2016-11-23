package com.roger.smart4j.framework.helper;

import com.roger.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 相当于Bean容器
 * Created by Roger on 2016/11/23.
 */
public final class BeanHelper {

    /**
     * 定义Bean映射(存放Bean类与Bean实例的映射)
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet =  ClassHelper.getBeanClassSet();
        for (Class<?> beanCls: beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanCls);
            BEAN_MAP.put(beanCls, obj);
        }
    }

    /**
     * 获取bean映射
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
