package com.lv.distributed.wheel;

public interface Timer {
    void add(TimerTask timerTask);
    boolean advanceClock(Long timeoutMs) throws Exception;
    int size();
    void shutdown();
}
