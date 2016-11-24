package com.roger.demo.web;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roger on 2016/11/24.
 */
@Controller
public class IndexController {

    @Action("GET:/")
    public View index(){
        View view = new View("index.jsp");
        view.addAttr("name", "roger");
        return view;
    }

    @Action("GET:/list")
    public Data list(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "roger");
        map.put("age", "23");
        Data data = new Data(map);
        return data;
    }
}
