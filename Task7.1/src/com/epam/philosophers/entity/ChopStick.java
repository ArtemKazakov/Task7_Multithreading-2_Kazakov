package com.epam.philosophers.entity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс палочки.
 */
public class ChopStick {
    Lock up = new ReentrantLock(); // Объект Lock , для каждой палочки свой
    private final int id; // Номер палочки

    public ChopStick(int id) {
        this.id = id;
    }

    // Метод взятия палочки. В него передается философ и вид палочки,
    // которую хочет взять этот философ. Никакие два философа не смогут взять её одновременно
    public boolean pickUp(Philosopher who, String where) throws InterruptedException {
        // Если в течении 10 милисикунд удастся взять палочку,
        // то метод выведет соответсвующее сообщение о том кто ее взял
        // и вернет true, сообщая что вызывающий данный метод поток
        // смог взять палочку и поставил на нее Lock
        if (up.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(who + " picked up " + where + " " + this);
            return true;
        }
        return false; // Если палочку взять не удалось возращаем false
    }

    // Метод "положить палочку". В него передается философ и вид палочки,
    // которую хочет положить этот философ
    public void putDown(Philosopher who, String name) {
        up.unlock(); // Отпукаем Lock с палочки
        // Выводим сообщение о том что передаваемый в метод
        // философ положил палочку
        System.out.println(who + " put down " + name + " " + this);
    }

    @Override
    public String toString() {
        return "Chopstick-" + id;
    }
}
