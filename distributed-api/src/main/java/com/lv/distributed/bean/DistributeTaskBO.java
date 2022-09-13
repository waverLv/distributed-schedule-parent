package com.lv.distributed.bean;

import java.util.List;

public class DistributeTaskBO {
    private String methodBean;
    private String methodName;
    private String methodParam;
    private Object[] methodParamTypes;
    private String applicationName;
    private List<SocketAddress> addressList;

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

    public List<SocketAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<SocketAddress> addressList) {
        this.addressList = addressList;
    }

    public String getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(String methodParam) {
        this.methodParam = methodParam;
    }

    public String generateUniqueName(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getApplicationName());
        sb.append("#");
        sb.append(this.getMethodBean());
        sb.append("#");
        sb.append(this.getMethodName());
        sb.append("#");
        sb.append(this.getMethodParamTypes().toString());
        return sb.toString();
    }

    /**
     * 定时任务服务器地址信息
     */
    public static class SocketAddress{
        private String host;
        private Integer port;

       public String getHost() {
           return host;
       }

       public void setHost(String host) {
           this.host = host;
       }

       public Integer getPort() {
           return port;
       }

       public void setPort(Integer port) {
           this.port = port;
       }
   }
}
