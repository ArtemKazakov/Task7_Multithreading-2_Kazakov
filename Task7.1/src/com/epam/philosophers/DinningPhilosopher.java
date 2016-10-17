package com.epam.philosophers;

import com.epam.philosophers.entity.ChopStick;
import com.epam.philosophers.entity.Philosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DinningPhilosopher {

    private static final int NO_OF_PHILOSOPHER = 3; // Количество философов
    private static final int SIMULATION_MILLIS = 1000 * 5; // Время работы философов

    public static void main(String args[]) throws InterruptedException {
        ExecutorService executorService = null;

        Philosopher[] philosophers = null;
        try {
            // Массив философов
            philosophers = new Philosopher[NO_OF_PHILOSOPHER];

            // Массив палочек с размером равным кол-ву философов
            ChopStick[] chopSticks = new ChopStick[NO_OF_PHILOSOPHER];
            for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
                chopSticks[i] = new ChopStick(i);
            }

            // Создание пула потоков фиксированного размера равного кол-ву философов
            executorService = Executors.newFixedThreadPool(NO_OF_PHILOSOPHER);

            for (int i = 0; i < NO_OF_PHILOSOPHER; i++) {
                // Выдаем каждому философу левую и правую палочку
                philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i + 1) % NO_OF_PHILOSOPHER]);
                // Запускаем их работу
                executorService.execute(philosophers[i]);
            }
            // Главный поток ожидает время работы философов
            Thread.sleep(SIMULATION_MILLIS);
            // Останавливаем всех философов
            for (Philosopher philosopher : philosophers) {
                philosopher.setTummyFull(true);
            }

        } finally {
            // Всё закрываем
            executorService.shutdown();

            // Ждём пока все закончат работу
            while (!executorService.isTerminated()) {
                Thread.sleep(1000);
            }

            // Проверяем сколько поел каждый философ
            for (Philosopher philosopher : philosophers) {
                System.out.println(philosopher + " => No of Turns to Eat ="
                        + philosopher.getNoOfTurnsToEat());
            }
        }
    }
}
