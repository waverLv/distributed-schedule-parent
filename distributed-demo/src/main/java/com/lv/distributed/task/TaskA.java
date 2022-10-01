package com.lv.distributed.task;

import com.lv.distributed.annotation.DistributeComponent;
import com.lv.distributed.annotation.DistributeSchedule;
import com.lv.distributed.annotation.Inject;
import com.lv.distributed.service.ServiceB;

/**
 * @ProjectName: TaskA
 * @Author: lvminghui
 * @Description: task a
 * @Date: 2022/9/7 15:06
 * @Version: 1.0
 */

@DistributeComponent
public class TaskA {
    @Inject(name = "serviceB")
    private ServiceB b;

    @DistributeSchedule(name = "A任务")
    public void a(){
        System.out.println("task a任务执行开始");
        b.b();
        System.out.println("task a任务执行结束");
    }

    @DistributeSchedule(name = "B任务")
    public void b() {
        System.out.println("task b任务执行开始");
        b.b();
        System.out.println("task b任务执行结束");
    }

}
