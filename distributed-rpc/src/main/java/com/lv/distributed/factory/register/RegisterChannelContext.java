package com.lv.distributed.factory.register;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: RegisterChannelContext
 * @Author: lvminghui
 * @Description: 注册通讯通道容器
 * @Date: 2022/9/27 11:22
 * @Version: 1.0
 */
public class RegisterChannelContext {

    /**
     * 注册application和channel关联容器
     * key: applicationName
     * value: channelHandlerContext
     */
    private static Map<String, List<ChannelHandlerContext>> applicationContextGroup = new ConcurrentHashMap<>();

    /**
     * 缓存同一个ApplicationName下注册的Channel
     * @param key
     * @param ctx
     */
    public static  void put(String key,ChannelHandlerContext ctx){
        List<ChannelHandlerContext> ctxList = applicationContextGroup.get(key);
        if(null == ctxList || ctxList.size() == 0){
            ctxList = new ArrayList<>();
        }
        //TODO 重连场景，需考虑去重
        ctxList.add(ctx);
        applicationContextGroup.put(key,ctxList);
    }
    /**
     * 获取applicationName下Channel集合
     * @param key
     */
    public static List<ChannelHandlerContext> get(String key){
        return applicationContextGroup.get(key);
    }

}
