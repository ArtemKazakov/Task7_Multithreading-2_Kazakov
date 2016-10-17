package com.epam.synchronizers.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 17.10.2016.
 */
public class BookCrossing {
    private static final Random rand = new Random();

    // Класс "Человек"
    public static class Man implements Runnable {
        private String name;
        private String book; // Книга - объект, которым будут меняться
        private Exchanger<String> exchanger; // Обменник, который будет обмениваться типом String

        public Man(String name, String ring, Exchanger<String> exchanger){
            this.name = name;
            this.book = ring;
            this.exchanger = exchanger;
        }

        // Обмен книгами
        @Override
        public void run(){
            int time = rand.nextInt(10); // Время похода до места

            try{
                System.out.println("У " + name + " есть книга " + book);

                TimeUnit.SECONDS.sleep(time); // Идём к месту

                System.out.println(name + " пришёл к обмену " + time + " сек");

                // Пришедший раньше поток блокируется и ждёт вызова этого метода на этом же
                // объекте из другого потока. В метод передается тот объект, который будет
                // отдан другому потоку, а возвращает объект переданный другим потоком
                this.book = exchanger.exchange(this.book);

                System.out.println("Теперь " + name + " с книгой " + book);
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args){
        final Exchanger<String> exchanger = new Exchanger<>(); // Создание обменника

        ExecutorService exec = Executors.newCachedThreadPool(); // Пул потоков

        // Создаем людей с книгами и передаём им обменник
        exec.execute(new Man("Ann", "Invisible Man", exchanger));
        exec.execute(new Man("Alex", "Winter's Bone", exchanger));
        exec.execute(new Man("Jack", "Legends of the Fall", exchanger));
        exec.execute(new Man("Kirill", "For Whom the Bell Tolls", exchanger));

        exec.shutdown(); // Всё заканчиваем
    }
}
