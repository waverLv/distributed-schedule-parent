package com.lv.distributed.support.chooser;

import com.lv.distributed.factory.ExtensionFactory;
import com.lv.distributed.support.tolerant.FaultTolerantStrategy;

public class FaultTolerantStrategyChooser extends AbstractChooser<FaultTolerantStrategy> {


    public FaultTolerantStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory,  FaultTolerantStrategy.class);
    }

}
