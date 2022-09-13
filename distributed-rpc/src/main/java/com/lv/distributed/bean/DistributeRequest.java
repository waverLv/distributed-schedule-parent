package com.lv.distributed.bean;

import java.util.List;

public class DistributeRequest {
    private DistributeHeader header;
    private List<DistributeRequestBody> requestBody;
    private Object body;

    public DistributeHeader getHeader() {
        return header;
    }

    public void setHeader(DistributeHeader header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public List<DistributeRequestBody> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(List<DistributeRequestBody> requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "DistributeRequest{" +
                "header=" + header +
                ", requestBody=" + requestBody +
                ", body=" + body +
                '}';
    }
}
