package org.smart4j.framework.bean;

/**
 * 数据对象
 * Created by Roger on 2016/11/23.
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
