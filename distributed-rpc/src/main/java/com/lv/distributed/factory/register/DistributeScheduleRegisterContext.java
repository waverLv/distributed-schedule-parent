package com.lv.distributed.factory.register;

import com.lv.distributed.api.StoreService;
import com.lv.distributed.bean.*;
import io.netty.channel.ChannelHandlerContext;
import org.junit.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DistributeScheduleRegisterContext implements ScheduleRegisterContext {
    /**
     * key: groupName,生成规则：application#methodBean#methodName#cronTab
     * value: groupName下所有的任务
     */
    private Map<String,List<DistributeTask>> taskGroupMap = new ConcurrentHashMap<>();
    /**
     * key: applicationName
     * value: groupName
     */
    private Map<String,String>  applicationTaskGroup = new ConcurrentHashMap<>();
    private DistributeTaskFactory distributeTaskFactory;
    private StoreService storeService;

    public DistributeScheduleRegisterContext(DistributeTaskFactory distributeTaskFactory,StoreService storeService){
        Assert.assertNotNull("storeService不能为空",storeService);
        if(distributeTaskFactory == null){
            distributeTaskFactory = new DefaultDistributeTaskFactory();
        }
        this.storeService = storeService;
        this.distributeTaskFactory = distributeTaskFactory;
    }

    @Override
    public void register(ChannelHandlerContext ctx,Object msg) {
        DistributeRequest request = (DistributeRequest) msg;
        List<DistributeRequestBody> requestBodyList = (List<DistributeRequestBody>) request.getBody();
        requestBodyList.forEach(requestBody -> {
            DistributeTask distributeTask = distributeTaskFactory.newTask(requestBody, false,ctx);
            registerTaskGroup(distributeTask);
            registerApplicationGroup(distributeTask);
            //TODO 1、注册任务 2、注册成功后判定任务是否已经启动 3、若启动，添加到时间轮
            storeService.store(bo2Task(requestBody));
        });

    }

    @Override
    public void unRegister(ChannelHandlerContext ctx, Object msg) {

    }

    /**
     * 将任务注册进全量任务列表
     * @param distributeTask
     */
    private void registerApplicationGroup(DistributeTask distributeTask){
        AbstractDistributeTask abstractDistributeTask = (AbstractDistributeTask) distributeTask;
//        applicationTaskGroup.putIfAbsent(abstractDistributeTask.getRequestBody().getApplicationName(),abstractDistributeTask.getGroupName());
    }

    /**
     * 将任务注册到同一个applicationName下
     * @param distributeTask
     */
    private void registerTaskGroup(DistributeTask distributeTask){
        if(!registerExists(distributeTask)){
            doRegiserTaskGroup(distributeTask);
        }
    }

    /**
     * distribute task注册前检测
     * @param distributeTask
     * @return
     */
    private boolean registerExists(DistributeTask distributeTask){
        List<DistributeTask> distributeTasks = taskGroupMap.get(distributeTask.getGroupName());
        boolean exists = false;
        if(null != distributeTasks && distributeTasks.size() > 0){
            for(int i =0; i<distributeTasks.size(); i++){
                if(distributeTasks.get(i).getGroupName().equals(distributeTask.getGroupName())){
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

    /**
     * 任务注册入taskGroup核心
     * @param distributeTask
     */
    private void doRegiserTaskGroup(DistributeTask distributeTask){
        List<DistributeTask> distributeTasks = taskGroupMap.get(distributeTask.getGroupName());
        if(null == distributeTasks || distributeTasks.size() == 0) distributeTasks = new CopyOnWriteArrayList<>();
        distributeTasks.add(distributeTask);
        taskGroupMap.put(distributeTask.getGroupName(),distributeTasks);
    }

    @Override
    public List<DistributeTask> getTaskList(String groupName) {
           return taskGroupMap.get(groupName);
    }

    private DistributeTaskBO bo2Task(DistributeRequestBody requestBody){
        DistributeTaskBO distributeTaskBO = new DistributeTaskBO();
        distributeTaskBO.setMethodName(requestBody.getMethodName());
        distributeTaskBO.setMethodBean(requestBody.getMethodBean());
        distributeTaskBO.setApplicationName(requestBody.getApplicationName());
        return distributeTaskBO;
    }
}
