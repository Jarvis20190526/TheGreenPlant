package com.example.a95795.thegreenplant.custom;

/**
 * Created by 95795 on 2019-04-28.
 */

public class Phone {
     private String number;

    public Phone(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                '}';
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
