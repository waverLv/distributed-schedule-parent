package com.lv.distributed.support.balance;

import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.factory.ExtensionFactory;
import com.lv.distributed.support.AbstractChooser;

public  class LoadBalanceStrategyChooser<T extends LoadBalanceStrategy> extends AbstractChooser<T> {


    public LoadBalanceStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory, (Class<T>) LoadBalanceStrategy.class);
    }

}
