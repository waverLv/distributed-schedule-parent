package com.lv.distributed.support.chooser;

public interface Chooser<T> {

    public T choose(String strategeyName);

    public void load();

}
