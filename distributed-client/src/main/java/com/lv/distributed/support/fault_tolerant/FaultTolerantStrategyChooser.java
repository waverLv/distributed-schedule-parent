package com.lv.distributed.support.fault_tolerant;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.factory.ExtensionFactory;
import com.lv.distributed.support.AbstractChooser;

public class FaultTolerantStrategyChooser<T extends SupportStrategy> extends AbstractChooser<T> {


    public FaultTolerantStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory, (Class<T>) FaultTolerantStrategy.class);
    }



}
