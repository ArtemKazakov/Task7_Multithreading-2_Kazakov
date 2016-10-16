package com.epam.locks.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 17.10.2016.
 */
public class CountThread implements Runnable{

    private CommonResource res; // Разделяемый ресурс
    private ReentrantLock locker; // Заглушка

    CountThread(CommonResource res, ReentrantLock lock){
        this.res=res;
        locker = lock;
    }

    public void run(){
        try{
            locker.lock(); // Устанавливаем блокировку
            // После этого только один поток имеет доступ к критической секции,
            // а остальные потоки ожидают снятия блокировки.
            res.setX(1);
            for (int i = 1; i < 5; i++){
                System.out.printf("%s %d \n", Thread.currentThread().getName(), res.getX());
                res.setX(res.getX()+1);
                Thread.sleep(100);
            }
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        finally{
            locker.unlock(); // Снимаем блокировку
        }
    }
}