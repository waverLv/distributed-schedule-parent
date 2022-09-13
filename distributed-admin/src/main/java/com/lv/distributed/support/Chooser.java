package com.lv.distributed.support;

import com.lv.distributed.api.InvokeStrategy;

import java.util.Map;

public interface Chooser {

    public InvokeStrategy choose(String strategeyName);

    public Map<String,InvokeStrategy> load();
}
