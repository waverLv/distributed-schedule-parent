package com.lv.distributed.task;

import com.lv.distributed.annotation.DistributeComponent;
import com.lv.distributed.annotation.DistributeSchedule;
import com.lv.distributed.annotation.Inject;
import com.lv.distributed.service.ServiceB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ProjectName: TaskA
 * @Author: lvminghui
 * @Description: task a
 * @Date: 2022/9/7 15:06
 * @Version: 1.0
 */

@DistributeComponent
public class TaskA {
    @Inject(value = "serviceB")
    private ServiceB b;

    @DistributeSchedule(name = "A任务",cron = "0 */1 * * * ?")
    public void a(){
        System.out.println("task a任务执行开始");
        b.b();
        System.out.println("task a任务执行结束");
    }
}
