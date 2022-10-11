package com.lv.distributed.wheel;

import com.lv.distributed.util.CronUtil;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class TimerTask implements Runnable {

//    private long delayMs; //表示当前任务延迟多久后执行(单位ms)，比如说延迟3s，则此值为3000
    private String cron;
    // 指向TimerTaskEntry对象，一个TimerTaskEntry包含一个TimerTask，TimerTaskEntry是可复用的
    private TimerTaskEntry timerTaskEntry = null;

    public TimerTask(String cron) {
        this.cron =  cron;
    }


    // 取消当前任务，就是从TimerTaskEntry移出TimerTask，并且把当前的timerTaskEntry置空
    public synchronized void cancel() {
        if(timerTaskEntry != null) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = null;
    }

    //设置当前任务绑定的TimerTaskEntry
    public synchronized void setTimerTaskEntry(TimerTaskEntry entry) {
        if(timerTaskEntry != null && timerTaskEntry != entry) {
            timerTaskEntry.remove();
        }
        timerTaskEntry = entry;
    }

    public TimerTaskEntry getTimerTaskEntry() {
        return timerTaskEntry;
    }

    public long getDelayMs() {
        return getDelayTime(cron);
    }

    /**
     *获取延迟时间
     * @param cronTab
     *
     * @return
     */
    private Long getDelayTime(String cronTab){
        LocalDateTime nextExecuteTime = CronUtil.getNextExecuteTime(cronTab);
        Duration between = Duration.between(LocalDateTime.now(), nextExecuteTime);
        return between.toMillis();
    }
}
