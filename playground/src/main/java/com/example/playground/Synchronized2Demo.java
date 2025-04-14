package com.example.playground;

/**
 * @author kraos
 * @date 2025/4/14
 */
public class Synchronized2Demo implements TestDemo {
    private int x = 0;

    public synchronized void count() {
        x++;
    }


    @Override
    public void runTest() {
        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                count();
            }
            System.out.println("Thread 1 finished : " + x);
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                count();
            }
            System.out.println("Thread 2 finished : " + x);
        }).start();
    }
}
