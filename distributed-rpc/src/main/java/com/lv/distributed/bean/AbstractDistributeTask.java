package com.lv.distributed.bean;

import com.lv.distributed.common.MessageType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractDistributeTask implements DistributeTask{

    private DistributeRequestBody requestBody;
    private boolean start;
    private ChannelHandlerContext ctx;
    private List<Channel> channels = new CopyOnWriteArrayList<>();

    public AbstractDistributeTask(DistributeRequestBody body,boolean start,ChannelHandlerContext ctx){
        this.requestBody = body;
        this.start = start;
        this.ctx = ctx;

    }
    @Override
    public DistributeRequestBody requestBody() {
        return requestBody;
    }

    public boolean isStart() {
        return start;
    }


    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void run() {
        DistributeHeader header = new DistributeHeader();
        header.setType(MessageType.INVOKE_REQ.getCode());
        header.setSessionId(requestBody.getRequestId());
        DistributeRequest request = new DistributeRequest();
        request.setHeader(header);
        request.setBody(requestBody);
        ctx.writeAndFlush(request);
    }

    @Override
    public boolean equals(Object obj) {
        return this.getUniqueTaskName().equals(((DistributeTask)obj).getUniqueTaskName());
    }

    @Override
    public String getUniqueTaskName( ){
        StringBuffer sb = new StringBuffer();
        sb.append(requestBody.getApplicationName());
        sb.append("#");
        sb.append(requestBody.getMethodBean());
        sb.append("#");
        sb.append(requestBody.getMethodName());
       /* sb.append("#");
        sb.append(requestBody.getMethodParamTypes().toString());*/
        sb.append("#");
        sb.append(ctx.channel().remoteAddress().toString());
        return sb.toString();
    }

    @Override
    public String getGroupName() {
        return getUniqueTaskName().substring(0,getUniqueTaskName().lastIndexOf("#"));
    }


}
