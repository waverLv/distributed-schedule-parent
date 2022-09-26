package com.lv.distributed.support;

import com.lv.distributed.api.SupportStrategy;

public interface Chooser<T extends SupportStrategy> {

    public <T> T choose(String strategeyName);

    public void load();


}
