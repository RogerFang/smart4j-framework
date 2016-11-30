package com.roger.demo.aspect;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.proxy.AspectProxy;

/**
 * Created by Roger on 2016/11/30.
 */
@Aspect(Service.class)
public class TransactionAspect extends AspectProxy {
}
