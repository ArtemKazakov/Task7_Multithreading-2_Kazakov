package com.epam.locks.condition;

/**
 * Created by ASUS on 17.10.2016.
 */
// класс Производитель
public class Producer implements Runnable{

    private Store store;

    Producer(Store store){
        this.store=store;
    }

    public void run(){
        for (int i = 1; i < 6; i++) {
            store.put();
        }
    }
}
