package com.lv.distributed.bean;

import java.io.Serializable;

public class DistributeRequestBody implements Serializable {
    private String methodBean;
    private String methodName;
    private Object[] methodParamTypes;
    private String applicationName;
    private String methodParam;
    private String loadBalance;
    private String faultTolerant;
    private Boolean start;
    private String description;
    private String cron;

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

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getFaultTolerant() {
        return faultTolerant;
    }

    public void setFaultTolerant(String faultTolerant) {
        this.faultTolerant = faultTolerant;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
