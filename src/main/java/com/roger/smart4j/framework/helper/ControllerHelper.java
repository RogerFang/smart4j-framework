package com.roger.smart4j.framework.helper;

import com.roger.smart4j.framework.annotation.Action;
import com.roger.smart4j.framework.bean.Handler;
import com.roger.smart4j.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 封装请求对象(Request)与处理对象(Handler)的映射关系
 * Created by Roger on 2016/11/23.
 */
public final class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)){
            for (Class<?> controllerCls: controllerClassSet){
                // 获取Controller类中定义的方法
                Method[] methods = controllerCls.getDeclaredMethods();

                for (Method method: methods){
                    if (method.isAnnotationPresent(Action.class)){
                        // 从Action中获取URL映射规则
                        Action action = method.getAnnotation(Action.class);
                        String mapping = action.value();
                        if (mapping.matches("\\w+:/\\w+")){
                            String[] array = mapping.split(":");
                            if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                                String requestMethod = array[0];
                                String requestPath = array[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerCls, method);
                                ACTION_MAP.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
