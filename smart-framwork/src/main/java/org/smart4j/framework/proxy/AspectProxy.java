package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 * 提供一些"钩子方法", 这些方法可以在子类中有选择性的实现
 * Created by Roger on 2016/11/24.
 */
public abstract class AspectProxy implements Proxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        try {
            if (intercept(cls, method, params)){
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin(){
    }

    public boolean intercept(Class<?> cls, Method method, Object[] params){
        return true;
    }

    /**
     * 前置增强
     * @param cls
     * @param method
     * @param params
     */
    public void before(Class<?> cls, Method method, Object[] params){

    }

    /**
     * 后置增强
     * @param cls
     * @param method
     * @param params
     * @param result
     */
    public void after(Class<?> cls, Method method, Object[] params, Object result){

    }

    /**
     * 抛出增强
     * @param cls
     * @param method
     * @param params
     * @param throwable
     */
    public void error(Class<?> cls, Method method, Object[] params, Throwable throwable){

    }

    public void end(){

    }
}
