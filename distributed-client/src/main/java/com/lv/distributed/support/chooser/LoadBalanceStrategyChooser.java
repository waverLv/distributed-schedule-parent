package com.lv.distributed.support.chooser;

import com.lv.distributed.factory.ExtensionFactory;
import com.lv.distributed.support.balance.LoadBalanceStrategy;

public  class LoadBalanceStrategyChooser extends AbstractChooser<LoadBalanceStrategy> {


    public LoadBalanceStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory, LoadBalanceStrategy.class);
    }

}
