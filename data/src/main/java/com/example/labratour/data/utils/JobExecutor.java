package com.example.labratour.data.utils;

import android.util.Log;

import com.example.labratour.domain.executors.ExecutionThread;
import com.google.firebase.database.annotations.NotNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExecutor implements ExecutionThread {
    private final ThreadPoolExecutor threadPoolExecutor;


    public JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(3, 5, 10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override public void execute(@NotNull Runnable runnable) {

        Log.i("thrad",
                "new thread num " );
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NotNull Runnable runnable) {
            Log.i("thrad",
                    "new thread num " + counter);
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
