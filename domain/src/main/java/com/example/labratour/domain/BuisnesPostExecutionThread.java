package com.example.labratour.domain;

import com.example.labratour.domain.executors.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BuisnesPostExecutionThread implements PostExecutionThread {
    private final Scheduler scheduler;

    public BuisnesPostExecutionThread() {
        this.scheduler = Schedulers.trampoline();
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler
                ;
    }
}
