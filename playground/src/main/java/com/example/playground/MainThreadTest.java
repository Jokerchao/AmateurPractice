package com.example.playground;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MainThreadTest {
    public static void main(String[] args) {
//        threadTest();
//        runnableTest();
//        threadFactoryTest();
//        executor();
//        callable();
        runSynchronized();
//        runSynchronized2();
    }

    /**
     * 使用Thread类来定义工作
     */
    static void threadTest() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 这里是子线程的代码
                System.out.println("Thread is running.");
            }
        });
        thread.start();
    }

    /**
     * 使用Runnable接口来定义工作
     */
    static void runnableTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 这里是子线程的代码
                System.out.println("Runnable is running.");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    static void threadFactoryTest() {
        ThreadFactory threadFactory = new ThreadFactory() {
            final AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + threadNumber.incrementAndGet());
            }
        };
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 这里是子线程的代码
                System.out.println(Thread.currentThread().getName() + " is running.");
            }
        };
        Thread thread = threadFactory.newThread(runnable);
        thread.start();
        Thread thread2 = threadFactory.newThread(runnable);
        thread2.start();
    }

    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 这里是子线程的代码
                System.out.println("Thread with Executor is running.");
            }
        };

        // 创建一个线程池
        Executor executor = Executors.newCachedThreadPool();
        // 提交任务
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

    }

    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 这里是子线程的代码
                Thread.sleep(2000);
                return "Callable is running.";
            }
        };

        // 创建一个线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 提交任务
        Future<String> future = executor.submit(callable);
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void runSynchronized() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2() {
        new Synchronized2Demo().runTest();
    }
}