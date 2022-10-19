package com.lv.distributed.wheel;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemTimerTest {
    //驱动时间轮向前的线程
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static  SystemTimer timer = new SystemTimer("test",1000L,5,System.currentTimeMillis());


    public static void runTask() throws Exception {
        for(int i = 0;i < 10000;i+= 1000) {
            // 添加任务，每个任务间隔1s
            timer.add(new TimerTask("") {
                @Override
                public void run() {
                    System.out.println("运行testTask的时间: " + LocalDateTime.now().toString());
                }
            });
        }
    }

    public static void main(String[] args) throws Exception {
        runTask();

        executorService.submit(() -> {
            while(true) {
                try {
                    // 驱动时间轮线程间隔0.2s驱动
                    timer.advanceClock(200L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Thread.sleep(1000000);
        timer.shutdown();
        executorService.shutdown();
    }
}
