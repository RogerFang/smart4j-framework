package com.roger.demo.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by Roger on 2016/11/30.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void begin() {
        LOGGER.debug("------begin------");
    }

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        LOGGER.debug(String.format("class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        LOGGER.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
    }

    @Override
    public void end() {
        LOGGER.debug("------end-------");
    }
}
