package org.smart4j.framework.bean;

import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 * 表单中所有的参数包括：表单参数FormParam和文件参数FileParam
 * Created by Roger on 2016/11/23.
 */
public class Param {
    
    private List<FormParam> formParamList;
    
    private List<FileParam> fileParamList;

    private Map<String, Object> fieldMap;
    private Map<String, List<FileParam>> fileMap;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
        
        createFieldMap(formParamList);
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
        
        createFieldMap(formParamList);
        createFileMap(fileParamList);
    }
    
    private void createFieldMap(List<FormParam> formParamList){
        fieldMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(formParamList)){
            for (FormParam formParam: formParamList){
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fileMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPERATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
    }
    
    private void createFileMap(List<FileParam> fileParamList){
        fileMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fileParamList)){
            for (FileParam fileParam: fileParamList){
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParams;
                if (fileMap.containsKey(fieldName)){
                    fileParams = fileMap.get(fieldName);
                }else {
                    fileParams = new ArrayList<>();
                }
                fileParams.add(fileParam);
                fileMap.put(fieldName, fileParams);
            }
        }
    }

    public long getLong(String name){
        return CastUtil.castLong(fieldMap.get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt(fieldMap.get(name));
    }

    public double getDouble(String name){
        return CastUtil.castDouble(fieldMap.get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.castBoolean(fieldMap.get(name));
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public Map<String, List<FileParam>> getFileMap() {
        return fileMap;
    }

    /**
     * 获取fieldName对应的所有上传文件
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName){
        return fileMap.get(fieldName);
    }

    /**
     * 获取fieldName对应的唯一上传文件
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList = fileMap.get(fieldName);
        if (CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数是否为空
     * @return
     */
    public boolean isEmpty(){
        return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
    }
}