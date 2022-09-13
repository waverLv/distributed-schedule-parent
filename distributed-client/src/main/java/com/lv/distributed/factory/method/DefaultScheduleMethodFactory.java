package com.lv.distributed.factory.method;

import com.lv.distributed.bean.DistributeRequestBody;

public class DefaultScheduleMethodFactory extends AbstractScheduleMethodFactory{




    @Override
    public String generateMethodName(MethodDefinition methodDefinition) {
        StringBuffer sb = new StringBuffer();
        sb.append(methodDefinition.getMethod().getName());
        sb.append("#");
        sb.append(methodDefinition.getBeanDefinition().getBeanName());
        return  sb.toString();
    }
    @Override
    public String generateMethodName(DistributeRequestBody requestBody) {
        StringBuffer sb = new StringBuffer();
        sb.append(requestBody.getMethodName());
        sb.append("#");
        sb.append(requestBody.getMethodBean());
        return  sb.toString();
    }


}
