package com.lv.distributed.invoke;

import com.lv.distributed.bean.DistributeCompletableFuture;
import com.lv.distributed.bean.DistributeHeader;
import com.lv.distributed.bean.DistributeRequest;
import com.lv.distributed.bean.DistributeRequestBody;
import com.lv.distributed.common.MessageType;
import com.lv.distributed.factory.invoke.Invoker;
import com.lv.distributed.factory.method.MethodDefinition;
import com.lv.distributed.factory.method.ScheduleMethodFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * @ProjectName: DefaultInvoker
 * @Author: lvminghui
 * @Description: 默认调用器
 * @Date: 2022/9/12 16:18
 * @Version: 1.0
 */
public class DefaultInvoker implements Invoker {

    private ScheduleMethodFactory methodFactory;

    public DefaultInvoker(ScheduleMethodFactory methodFactory){
        this.methodFactory = methodFactory;
    }


    @Override
    public void invoke(DistributeRequest request) {
        DistributeRequestBody body = (DistributeRequestBody) request.getBody();
        MethodDefinition method = methodFactory.method(methodFactory.generateMethodName(body));
        try{
            method.invoke(body.getMethodParam());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void callback(DistributeRequest request) {
        DistributeHeader header = request.getHeader();
        DistributeCompletableFuture future = DistributeCompletableFuture.getFuture(header.getSessionId());
        boolean exists = null != future;
        if(exists && header.getType() == MessageType.INVOKE_RESP.getCode()){
            DistributeCompletableFuture.invokeComplete(future);
        }else if(header.getType() == MessageType.INVOKE_FINISHED_RESP.getCode()){
            //TODO 更新数据库调用记录
        }
    }

}
