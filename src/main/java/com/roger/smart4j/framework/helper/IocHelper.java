package com.roger.smart4j.framework.helper;

import com.roger.smart4j.framework.annotation.Inject;
import com.roger.smart4j.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现依赖注入功能
 * Created by Roger on 2016/11/23.
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)){
            for (Map.Entry<Class<?>, Object> beanEntry: beanMap.entrySet()){
                Class<?> beanCls = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                // 获取bean类定义的所有成员变量
                Field[] beanFields = beanCls.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){
                    for (Field field: beanFields){
                        if (field.isAnnotationPresent(Inject.class)){
                            // 在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldCls = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldCls);
                            if (beanFieldInstance != null){
                                // 通过反射初始化Field值
                                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
