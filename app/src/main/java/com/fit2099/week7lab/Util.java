package com.fit2099.week7lab;

public class Util {
    public static int parseInt(String s) {
        int result;
        try{
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }

    public static float parseFloat(String s) {
        float result;
        try{
            result = Float.parseFloat(s);
        } catch (NumberFormatException e) {
            result = 0;
        }
        return result;
    }
}
