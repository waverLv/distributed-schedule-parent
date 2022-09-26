package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.factory.SpiExtensionFactory;
import com.lv.distributed.support.Chooser;

/**
 * @ProjectName: Demo
 * @Author: lvminghui
 * @Description: SpiDemo
 * @Date: 2022/9/16 17:39
 * @Version: 1.0
 */
public class Demo {

    public static void main(String[] args) {
        FaultTolerantStrategyChooser<FaultTolerantStrategy> chooser = new FaultTolerantStrategyChooser<FaultTolerantStrategy>(new SpiExtensionFactory());
        chooser.choose("default");
    }
}
