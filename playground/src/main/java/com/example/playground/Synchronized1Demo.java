package com.example.playground;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author kraos
 * @date 2025/4/14
 */
public class Synchronized1Demo implements TestDemo {
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void stop() {
        running.set(false);
    }


    @Override
    public void runTest() {
        new Thread(() -> {
            while (running.get()) {
                // 执行任务
//                System.out.println("Thread is running.");
            }
        }).start();

        // 模拟主线程的其他工作
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 停止线程
        stop();

    }
}
