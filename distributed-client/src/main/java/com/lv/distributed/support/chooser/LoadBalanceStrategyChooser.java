package com.lv.distributed.support.chooser;

import com.lv.distributed.api.LoadBalanceStrategy;
import com.lv.distributed.factory.ExtensionFactory;

public  class LoadBalanceStrategyChooser<T extends LoadBalanceStrategy> extends AbstractChooser<T> {


    public LoadBalanceStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory, (Class<T>) LoadBalanceStrategy.class);
    }

}
