package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;

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
        return getClassSetByAnnotation(Service.class);
    }

    /**
     * 获取应用包下的所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        return getClassSetByAnnotation(Controller.class);
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

    /**
     * 获取应用包下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls: CLASS_SET){
            if (cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下某父类(或接口)的所有子类(或实现类)
     * @param superCls
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superCls){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls: CLASS_SET){
            if (superCls.isAssignableFrom(cls) && !superCls.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
