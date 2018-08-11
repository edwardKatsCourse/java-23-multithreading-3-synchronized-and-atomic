package com.company.example_2_monitor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();
        new Thread(new Counter()).start();

        Thread.sleep(1000);

        System.out.println(Counter.getCounter());

    }
}

//1 Counter = 1 thread
//10 Counters = 10 thread

//lock: 1 monitor = 100000000 threads
class Counter implements Runnable {
    private static int counter = 0;
    private static final Object monitor = new Object();

    @Override
    public void run() {
        //Counter.class = new Counter
        synchronized (monitor/*monitor -> monitoring*/) {
            for (int i = 0; i < 100000; i++) {
                counter = counter + 1;
            }
        }
    }


    //t1, t2, t3

    public /*synchronized - this*/ int getBlahBlahBlahCounter() { //Counter c = new Counter(); c.getBlahBlahBlahCounter()

        return counter;
    }

    public /*synchronized - Counter.class*/ static int getCounter() { //Counter.getCounter()
        //t3
        synchronized (monitor) { //locked
            System.out.println();
        }
        //t2
        //t1
        return counter;
    }

    public static void renewCounter() {
        synchronized (monitor) {
            //t145
            System.out.println();
        }
        //t15
        counter = 0;
    }
}