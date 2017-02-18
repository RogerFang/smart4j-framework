package org.smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.FileUtil;
import org.smart4j.framework.util.StreamUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手类
 * Created by Roger on 2017/2/17.
 */
public class UploadHelper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache Commons FileUpload 提供的 Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     * 需要社会ServletFileUpload对象的上传文件临时目录和上传文件的最大限制
     */
    public static void init(ServletContext servletContext){
        // 文件上传的临时目录 (应用服务器的临时目录)
        File repository = (File)servletContext.getAttribute("javax.servlet.context.tmpdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppFileUploadLimit();
        if (uploadLimit != 0){
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为 multipart 类型
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 封装请求参数
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();

        try {
            Map<String, List<FileItem>> fileItemMap = servletFileUpload.parseParameterMap(request);
            if (MapUtils.isNotEmpty(fileItemMap)){
                for (Map.Entry<String, List<FileItem>> entry: fileItemMap.entrySet()){
                    String fieldName = entry.getKey();
                    List<FileItem> fileItemList = entry.getValue();
                    if (CollectionUtils.isNotEmpty(fileItemList)){
                        for (FileItem fileItem: fileItemList){
                            if (fileItem.isFormField()){
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            }else {
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtils.isNotEmpty(fileName)){
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath, FileParam fileParam){
        if (fileParam != null){
            String filePath = basePath + fileParam.getFileName();
            FileUtil.createFile(filePath);
            InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
            try {
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            } catch (FileNotFoundException e) {
                LOGGER.error("upload file failure", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 批量上传
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList){
        if (CollectionUtils.isNotEmpty(fileParamList)){
            for (FileParam fileParam: fileParamList){
                uploadFile(basePath, fileParam);
            }
        }
    }
}
