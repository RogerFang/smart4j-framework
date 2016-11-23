package com.roger.smart4j.framework.bean;

import com.roger.smart4j.framework.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 * Created by Roger on 2016/11/23.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt(paramMap.get(name));
    }

    public double getDouble(String name){
        return CastUtil.castDouble(paramMap.get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.castBoolean(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}