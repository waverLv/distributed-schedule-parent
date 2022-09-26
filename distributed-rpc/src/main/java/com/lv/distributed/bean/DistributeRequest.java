package com.lv.distributed.bean;

public class DistributeRequest {
    private DistributeHeader header;
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


    @Override
    public String toString() {
        return "DistributeRequest{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
