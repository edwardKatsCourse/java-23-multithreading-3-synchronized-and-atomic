package com.company.example_4_atomic_types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Counter> counters = new ArrayList<>();

        long start = new Date().getTime();
        for (int i = 0; i < 5; i++) {

            Counter t1 = new Counter();
            Counter t2 = new Counter();
            Counter t3 = new Counter();

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();

//            Thread.sleep(3000);

            System.out.println(Counter.getCounter1());
            Counter.renewCounter();
            counters.clear();
        }
        long end = new Date().getTime();
        System.out.println(end - start);
    }

    //increment 3 threads (by 1000 each)
    //synchronized  - 21 sec
    //atomic        - 7 secs

    //increment 3 threads, 2 integers (by 1000 each)
    //synchronized  - 28.7 sec
    //atomic        - 7 secs  - 14 secs
}
//counter ++
class Counter extends Thread {
    //Atomic Type - Атомарные типы
    //compare-and-swap (CAS)

    private static AtomicInteger counter1 = new AtomicInteger();
    private static AtomicInteger counter2 = new AtomicInteger();
//    private static int counter1 = 0;
//    private static int counter2 = 0;


    //counter = 0, version = 1
    //t1 counter = 0, version = 1 -> counter = 1, version = 2

    //t2 counter = 1, version = 2 -> counter = 2, version = 3
    //t3 counter = 2, version = 3 -> counter = 3, version = 4

    //300,000 - 140,000 (160,000 with atomic netto)

    private static final Object monitor1 = new Object();
    private static final Object monitor2 = new Object();

    @Override
    public void run() {
        //t1 counter 0 -> 1
        //t2 counter 1 -> 2
        //t3 counter 1 -> 2

//        synchronized (monitor1) {
            for (int i = 0; i < 1000; i++) {
//                counter1++;
                counter1.incrementAndGet();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//        }

//        synchronized (monitor2) {
            for (int i = 0; i < 1000; i++) {
                counter2.incrementAndGet();

//                counter2++;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//        }
    }

    public static int getCounter1() {
//        return counter1;
        return counter1.get();
    }

    public static int getCounter2() {
//        return counter2;
        return counter2.get();
    }

    public static void renewCounter() {
//        counter.set(0);
        counter1 = new AtomicInteger();
        counter2 = new AtomicInteger();
//        counter1 = 0;
//        counter2 = 0;
//
    }
}
