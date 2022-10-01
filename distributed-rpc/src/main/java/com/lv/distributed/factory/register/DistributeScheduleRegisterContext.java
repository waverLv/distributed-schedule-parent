package com.lv.distributed.factory.register;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.lv.distributed.bean.DefaultDistributeTaskFactory;
import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.bean.DistributeTask;
import com.lv.distributed.bean.DistributeTaskFactory;
import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DistributeScheduleRegisterContext implements ScheduleRegisterContext {

    /**
     * row: groupName
     * column: remoteAddress
     * value: task
     */
    private Table<String, String, DistributeTask> taskTable = HashBasedTable.create();

    private DistributeTaskFactory distributeTaskFactory;

    public DistributeScheduleRegisterContext(){
        if(distributeTaskFactory == null){
            distributeTaskFactory = new DefaultDistributeTaskFactory();
        }
    }

    @Override
    public void register(ChannelHandlerContext ctx,Object msg) {
        final String REMOTE_ADDRESS = ctx.channel().remoteAddress().toString();
        final Set<Object> SURVIVAL_GROUPNAME_SET = Sets.newHashSet();
        DistributeRequest request = (DistributeRequest) msg;
        List<DistributeRequestBody> requestBodyList = (List<DistributeRequestBody>) request.getBody();
        requestBodyList.forEach(requestBody -> {
            DistributeTask distributeTask = distributeTaskFactory.newTask(requestBody, false, ctx);
            taskTable.put(distributeTask.getGroupName(), REMOTE_ADDRESS, distributeTask);
            SURVIVAL_GROUPNAME_SET.add(distributeTask.getGroupName());
        });
        Map<String, DistributeTask> groupMap = taskTable.column(REMOTE_ADDRESS);
        Sets.SetView<String> setView = Sets.difference(groupMap.keySet(), SURVIVAL_GROUPNAME_SET);
        Iterator<String> iterator = setView.stream().iterator();
        while (iterator.hasNext()) {
            taskTable.remove(iterator.next(), REMOTE_ADDRESS);
        }
    }

    @Override
    public void unRegister(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public Collection<DistributeTask> getTaskList(String groupName) {
        return taskTable.row(groupName).values();
    }

}
