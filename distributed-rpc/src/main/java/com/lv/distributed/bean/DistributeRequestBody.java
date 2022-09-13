package com.lv.distributed.bean;

import java.io.Serializable;

public class DistributeRequestBody implements Serializable {
    private String methodBean;
    private String methodName;
    private Object[] methodParamTypes;
    private String applicationName;
    private String methodParam;

    public String getMethodBean() {
        return methodBean;
    }

    public void setMethodBean(String methodBean) {
        this.methodBean = methodBean;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getMethodParamTypes() {
        return methodParamTypes;
    }

    public void setMethodParamTypes(Object[] methodParamTypes) {
        this.methodParamTypes = methodParamTypes;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(String methodParam) {
        this.methodParam = methodParam;
    }
}
