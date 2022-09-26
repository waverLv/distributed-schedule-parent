package com.lv.distributed.executor;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,"bz-thread-pool");
    }
}
