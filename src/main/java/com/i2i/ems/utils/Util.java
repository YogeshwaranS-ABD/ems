package com.i2i.ems.utils;

public class Util {

    private static int generateShortId() {
        int num = (int) (Math.random() * 1000);
        return num;
    }

    private static int generateLongId() {
        int num = (int) (Math.random() * 100000);
        return num;
    }

    public static String generateIdForAddress() {
        return "ADDR" + generateLongId();
    }

    public static String generateIdForCertification() {
        return "CERT" + generateLongId();
    }

    public static String generateIdForDepartment() {
        return "DEPT" + generateShortId();
    }

    public static String generateIdForEmployee() {
        return "I2I" + generateShortId();
    }

    public static String generateIdForProject() {
        return "PROJ" + generateShortId();
    }
}