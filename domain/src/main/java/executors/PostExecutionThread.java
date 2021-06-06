package executors;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    public Scheduler getScheduler();
}
