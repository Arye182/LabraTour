package com.example.labratour.utils;

import com.example.labratour.domain.executors.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
public class UIThread implements PostExecutionThread{



//public UIThread() {}

@Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
        }
}
