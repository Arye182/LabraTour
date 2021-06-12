package com.example.labratour.ui;

import com.example.executors.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
public class UIThread implements PostExecutionThread{



public UIThread() {}

@Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
        }
}
