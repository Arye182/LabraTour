package com.example.labratour.domain.executors;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    public Scheduler getScheduler();
}
