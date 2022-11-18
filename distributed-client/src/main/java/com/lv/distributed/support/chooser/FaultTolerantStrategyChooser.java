package com.lv.distributed.support.chooser;

import com.lv.distributed.api.FaultTolerantStrategy;
import com.lv.distributed.api.SupportStrategy;
import com.lv.distributed.factory.ExtensionFactory;

public class FaultTolerantStrategyChooser extends AbstractChooser<FaultTolerantStrategy> {


    public FaultTolerantStrategyChooser(ExtensionFactory extensionFactory) {
        super(extensionFactory,  FaultTolerantStrategy.class);
    }

}
