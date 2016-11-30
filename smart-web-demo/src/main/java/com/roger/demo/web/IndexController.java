package com.roger.demo.web;

import com.roger.demo.service.UserService;
import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roger on 2016/11/24.
 */
@Controller
public class IndexController {

    @Inject
    private UserService userService;

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

    @Action("GET:/transaction")
    public Data transaction(){
        userService.create(new HashMap<>());
        return new Data("hello");
    }
}
