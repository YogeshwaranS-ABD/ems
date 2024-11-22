package com.i2i.ems.utils;

public class Util {

    public static int generateShortId() {
        int num = (int) (Math.random() * 1000);
        return num;
    }

    public static int generateLongId() {
        int num = (int) (Math.random() * 100000);
        return num;
    }
}