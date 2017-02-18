package org.smart4j.framework;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Roger on 2016/11/23.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 初始化Helper类
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();
        // 注册处理jsp的servlet
        // 传入一个"jsp"参数, 意味着从容器中获取JspServlet(这个Servlet由容器实现, The JSP page compiler and execution servlet)
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的默认servlet
        // 传入一个"default"参数, 意味着从容器中获取DefaultServlet(这个Servlet由容器实现, 它负责处理普通的静态资源响应)
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        
        // 初始化文件上传助手类
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rsp) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        // 过滤对 favicon.ico 的访问
        if (requestPath.equals("/favicon.ico")) {
            return;
        }

        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            // 获取Controller类及其实例
            Class<?> controllerCls = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerCls);

            Param param;
            if (UploadHelper.isMultipart(req)){
                param = UploadHelper.createParam(req);
            }else {
                param = RequestHelper.createParam(req);
            }
            
            // 调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            // 处理Action方法返回值
            if (result instanceof View) {
                handleViewResult((View) result, req, rsp);
            } else if (result instanceof Data) {
                // 返回json数据
                handleDataResult((Data) result, rsp);
            }
        }
    }
    
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                rsp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, rsp);
            }
        }
    }
    
    private void handleDataResult(Data data, HttpServletResponse rsp) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            rsp.setContentType("application/json");
            rsp.setCharacterEncoding("UTF-8");
            PrintWriter writer = rsp.getWriter();
            writer.write(JsonUtil.toJson(model));
            writer.flush();
            writer.close();
        }
    }
}
