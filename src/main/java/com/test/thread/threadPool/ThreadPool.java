package com.test.thread.threadPool;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);

    void shutdown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();
}
