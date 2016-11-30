package com.roger.demo.service;

import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;

import java.util.Map;

/**
 * Created by Roger on 2016/11/30.
 */
@Service
public class UserService {

    /**
     * 创建用户
     * @param fieldMap
     * @return
     */
    @Transaction
    public boolean create(Map<String, Object> fieldMap){
        return true;
    }

    /**
     * 获取用户
     * @param id
     * @return
     */
    public Object get(long id){
        return null;
    }
}
