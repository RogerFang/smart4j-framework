package com.roger.smart4j.framework.helper;

import com.roger.smart4j.framework.annotation.Controller;
import com.roger.smart4j.framework.annotation.Service;
import com.roger.smart4j.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Roger on 2016/11/23.
 */
public final class ClassHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 定义类集合(用于存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包下的所有service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        return getAnnotationClassSet(Service.class);
    }

    /**
     * 获取应用包下的所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        return getAnnotationClassSet(Controller.class);
    }

    /**
     * 获取应用包下的所有Bean
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

    private static Set<Class<?>> getAnnotationClassSet(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls: CLASS_SET){
            if (cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
