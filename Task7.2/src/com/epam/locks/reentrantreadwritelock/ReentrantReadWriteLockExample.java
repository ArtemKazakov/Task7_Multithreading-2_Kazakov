package com.epam.locks.reentrantreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by ASUS on 17.10.2016.
 */
public class ReentrantReadWriteLockExample {

    // Создаем объект заглушки
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private static String message = "a";

    public static void main(String[] args) throws InterruptedException{
        // Создаём потоки
        Thread t1 = new Thread(new Read());
        Thread t2 = new Thread(new WriteA());
        Thread t3 = new Thread(new WriteB());
        // Запускаем потоки
        t1.start();
        t2.start();
        t3.start();
        // Ждём потоки
        t1.join();
        t2.join();
        t3.join();
    }

    // Класс читающий сообщение
    static class Read implements Runnable {

        public void run() {
            for(int i = 0; i<= 10; i ++) {
                if(lock.isWriteLocked()) { // Проверяет есть
                    System.out.println("I'll take the lock from Write");
                }
                lock.readLock().lock(); // Устанавливаем блокировку на чтение
                System.out.println("ReadThread " + Thread.currentThread().getId() + " ---> Message is " + message );
                lock.readLock().unlock(); // Снимаем блокировку на чтение
            }
        }
    }

    // Класс добавляющий символ 'а' к сообщению
    static class WriteA implements Runnable {

        public void run() {
            for(int i = 0; i<= 10; i ++) {
                try {
                    // Устанавливаем блокировку на запись и добавляем символ
                    lock.writeLock().lock();
                    message = message.concat("a");
                } finally {
                    lock.writeLock().unlock(); // Снимаем блокировку на запись
                }
            }
        }
    }

    // Класс добавляющий символ 'b' к сообщению
    static class WriteB implements Runnable {

        public void run() {
            for(int i = 0; i<= 10; i ++) {
                try {
                    // Устанавливаем блокировку на запись и добавляем символ
                    lock.writeLock().lock();
                    message = message.concat("b");
                } finally {
                    lock.writeLock().unlock(); // Снимаем блокировку на запись
                }
            }
        }
    }
}
