package com.lv.distributed.factory.executor;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,"bz-thread-pool");
    }
}
