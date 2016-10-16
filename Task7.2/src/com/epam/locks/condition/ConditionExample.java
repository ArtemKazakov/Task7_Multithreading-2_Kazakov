package com.epam.locks.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ASUS on 17.10.2016.
 */
public class ConditionExample {
    public static void main(String[] args) {
        Store store = new Store(); // Объект с которым будут раотать потоки "производитель" и "покупатель"
        // Создание произваодителя и покупателя
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);
        // Запуск их потоков
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}







