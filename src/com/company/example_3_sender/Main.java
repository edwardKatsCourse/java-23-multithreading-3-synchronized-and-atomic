package com.company.example_3_sender;

public class Main {

    public static void main(String[] args) {
        Sender sender = new Sender();

        ThreadedSender привет = new ThreadedSender("привет!", sender);
        ThreadedSender пока = new ThreadedSender("пока!", sender);

        привет.start();
        пока.start();

        try {
            привет.join();
            пока.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class ThreadedSender extends Thread {

    private String message;
    private final Sender sender;

    public ThreadedSender(String message, Sender sender) {
        this.message = message;
        this.sender = sender;
    }

    @Override
    public void run() {

        synchronized (sender) {
            sender.sendMessage(message);
        }

    }
}

class Sender {
    public void sendMessage(String message) {
        System.out.println("Sending message:\t " + message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Message " + message + " sent!");
    }
}