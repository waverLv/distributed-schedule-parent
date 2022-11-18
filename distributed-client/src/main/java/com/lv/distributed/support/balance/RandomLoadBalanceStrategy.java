package com.lv.distributed.support.balance;

import com.lv.distributed.bean.DistributeTaskBO;
import com.lv.distributed.bean.DistributeTaskBOWrapper;
import com.lv.distributed.bean.DistributeTaskRequestWrapper;
import com.lv.distributed.factory.register.RegisterChannelContext;
import com.lv.distributed.support.tolerant.FailoverFaultTolerantStrategy;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @ProjectName: RandomLoadBalanceStrategy
 * @Author: lvminghui
 * @Description: 随机负载策略
 * @Date: 2022/10/12 11:19
 * @Version: 1.0
 */
public class RandomLoadBalanceStrategy extends AbstractLoadBalanceStrategy {
    private static  final Logger LOGGER  = LoggerFactory.getLogger(RandomLoadBalanceStrategy.class);


    @Override
    public void choose(DistributeTaskBOWrapper wrapper) {
        try{
            randomize(wrapper);
        }catch (UnknownHostException ex){
            ex.printStackTrace();
        }
    }

    /**
     * 根据调用服务器内网IP随机选择定时任务服务器
     */
    private static void randomize(DistributeTaskBOWrapper wrapper) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        List<ChannelHandlerContext> addressList = new ArrayList<>(RegisterChannelContext.get(wrapper.getApplicationName()));
        Random random = new Random(hostAddress.hashCode());
        int size = addressList.size();
        for (int i = 0; i < size-1; i++) {
            int pos = random.nextInt(size - i);
            if (pos != i) {
                Collections.swap(addressList, i, pos);
            }
        }
        wrapper.setCtx(addressList.get(random.nextInt(size-1)));
    }
}
