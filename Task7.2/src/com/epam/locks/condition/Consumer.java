package com.epam.locks.condition;

/**
 * Created by ASUS on 17.10.2016.
 */
// Класс Потребитель
public class Consumer implements Runnable{

    private Store store;

    Consumer(Store store){
        this.store=store;
    }

    public void run(){
        for (int i = 1; i < 6; i++) {
            store.get(); // Этот поток пытается получить элемент
        }
    }
}
