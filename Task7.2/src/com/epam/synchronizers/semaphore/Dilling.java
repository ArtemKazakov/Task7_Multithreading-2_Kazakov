package com.epam.synchronizers.semaphore;

import java.util.concurrent.Semaphore;

public class Dilling {

    public static void main(String[] args) {

        Semaphore sem = new Semaphore(2); // Семафор на 2 одновременно работающих потока
        // Создание и запуск потоков
        for(int i=1;i<6;i++)
            new Philosopher(sem,i).start();
    }
}
