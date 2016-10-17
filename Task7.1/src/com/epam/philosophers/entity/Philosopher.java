package com.epam.philosophers.entity;

import java.util.Random;

/**
 * Класс философа. У каждого философа есть свой номер,
 * а также левая и правая палочки. Философы могут думать и есть
 * при условии что они смогли взять 2 палочки - левую и правую.
 * Ситуация при которой каждый философ возьмет левую палочку и произойдет дэдлок
 * исключена засчет того, что при такой ситуации, когда невозможно взять правые палочки
 * философы будут лодить обратно левые и начинать размышлять и пытатьс взять палочки заного.
 */
public class Philosopher extends Thread{
    private final int id; // Номер философа
    private final ChopStick leftChopStick; // Левая палочка философа
    private final ChopStick rightChopStick; // Правая палочка философа

    private volatile boolean isTummyFull = false; // Наелся ли философ
    private Random randomGenerator = new Random(); // Для рандомизации времени приема пищи/размышлений
    private int noOfTurnsToEat = 0; // Количесвто приемов пищи

    public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick) {
        this.id = id;
        this.leftChopStick = leftChopStick;
        this.rightChopStick = rightChopStick;
    }

    @Override
    public void run() {

        try {
            while (!isTummyFull) { // Выполняется пока философ голоден
                think(); // Размышления философа
                if (leftChopStick.pickUp(this, "left")) {  // Попытаться взять левую палочку
                    if (rightChopStick.pickUp(this, "right")) { // Попытаться взять правую палочку
                        // Если удалось взять обе палочки - покушать
                        eat();
                        // Положить обратно правую палочку
                        rightChopStick.putDown(this, "right");
                    }
                    // Положить обратно левую палочку
                    leftChopStick.putDown(this, "left");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод размышления философа
    private void think() throws InterruptedException {
        System.out.println(this + " is thinking"); // Вывод сообщения о том, кто думает
        Thread.sleep(randomGenerator.nextInt(1000)); // Процес "размышления" рандомное время
    }

    // Метод приема пищи философа
    private void eat() throws InterruptedException {
        System.out.println(this + " is eating"); // Вывод сообщения о том, кто ест
        noOfTurnsToEat++; // Инкрементирование кол-ва приёмов пищи философа
        Thread.sleep(randomGenerator.nextInt(1000)); // Процес "приёма пищи" рандомное время
    }

    public int getNoOfTurnsToEat() {
        return noOfTurnsToEat;
    }

    public void setTummyFull(boolean tummyFull) {
        isTummyFull = tummyFull;
    }

    @Override
    public String toString() {
        return "Philosopher-" + id;
    }
}
