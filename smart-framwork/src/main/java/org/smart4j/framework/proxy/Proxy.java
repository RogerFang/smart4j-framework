package org.smart4j.framework.proxy;

/**
 * 代理
 * Created by Roger on 2016/11/24.
 */
public interface Proxy {

    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
