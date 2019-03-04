package com.bookinggo.test.cliapp.model;


import com.bookinggo.test.cliapp.controller.Utilities.CarTypes;

/**
 * Car class represents an option that is received when calling the taxi companies api.
 */
public class Car implements Comparable<Car> {

    private CarTypes type;
    private int price;
    private String supplier;

    public Car() {

        this.type = null;
        this.price = 0;
        this.supplier = null;
    }

    public Car(CarTypes type, int price, String supplier) {

        this.type = type;
        this.price = price;
        this.supplier = supplier;
    }

    public CarTypes getType() {
        return type;
    }

    public void setType(CarTypes type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public int compareTo(Car o) {

        if (this.getPrice() == o.getPrice()) {

            return 0;
        } else if (this.getPrice() > o.getPrice()) {

            return 1;
        }

        return -1;
    }
}
