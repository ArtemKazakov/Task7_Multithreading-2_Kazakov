package com.epam.locks.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 17.10.2016.
 */
public class ReentrantLockExample {

    public static void main(String[] args) {

        CommonResource commonResource= new CommonResource();  //Создание разделяемого ресурса
        ReentrantLock locker = new ReentrantLock(); //Создание объекта заглушки
        for (int i = 1; i < 6; i++){
            //Создание потоков и их запуск
            Thread t = new Thread(new CountThread(commonResource, locker));
            t.setName("Поток "+ i);
            t.start();
        }
    }
}




