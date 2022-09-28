package com.lv.distributed.support;

public interface Chooser<T> {

    public <T> T choose(String strategeyName);

    public void load();


}
