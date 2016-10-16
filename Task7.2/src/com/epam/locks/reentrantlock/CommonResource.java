package com.epam.locks.reentrantlock;

/**
 * Created by ASUS on 17.10.2016.
 */

//Разделяемый потоками ресурс
public class CommonResource{
    private int x=0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
