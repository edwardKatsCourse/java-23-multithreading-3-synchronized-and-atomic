package com.company.example_1_synchronized_block;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	    //1. thread synchronization
        //2. atomic type
        //3. synchronized collection

        for (int i = 0; i < 10; i++) {

            new Counter().start();
            new Counter().start();
            new Counter().start();

            Thread.sleep(1000);

            System.out.println(Counter.getCounter()); //300 000
            Counter.renewCounter();
        }

    }
}

class Counter extends Thread {

    private static int counter = 0;

    @Override
    public void run() {
        /**
         *          async: одновременно
         * synchronized(lockObject) {
         *          sync: друг за другом
         * }
         */
        synchronized (Counter.class) {
            for (int i = 0; i < 1000; i++) {
                //start
                //t1, t2, t3

                //finish
                //1.
                //t3, t1, t2

                //2.
                //t3, t2
                //t1

                //thread 1 - counter = 0 -> 1
                //thread 2 - counter = 1 -> 2
                //thread 3 - counter = 1 -> 2

                //3 запуска = увеличили на 2

                counter = counter /*1*/ + 1; //2
            }
        }
    }

    public static void renewCounter() {
        counter = 0;
    }

    public static int getCounter() {
        return counter;
    }
}