package com.bookinggo.test.cliapp;

import com.bookinggo.test.cliapp.controller.Controller;
import com.bookinggo.test.cliapp.controller.Utilities.LocationType;
import com.bookinggo.test.cliapp.model.Car;

import java.util.List;

public class Part1 {

    Controller controller;


    public static void main(String[] args) {

        Part1 part1 = new Part1(args[0], args[1], args[2], args[3], args[4]);

    }


    public Part1(String lat1, String long1, String lat2, String long2, String numPassengers) {

        controller = new Controller();
        init(lat1, long1, lat2, long2, numPassengers);
    }


    private void init(String lat1, String long1, String lat2, String long2, String numPassengers) {


        boolean flag = getLocation(LocationType.PICKUP, lat1, long1) &&
                getLocation(LocationType.DROP_OFF, lat2, long2) &&
                getNumPassengers(numPassengers);

        if (flag) {

            controller.search();

            List<Car> tmp = controller.getResults();

            if (!tmp.isEmpty()) {

                for (Car car : tmp) {

                    System.out.println(car.getType() + " - " + car.getSupplier() + " - " + car.getPrice());

                }
            } else {

                System.err.println("There are no cars available that meet your criteria");
            }

        }
    }


    private boolean getLocation(LocationType type, String lat1, String long1) {

        double latitude = 0.0;
        double longitude = 0.0;


        try {
            latitude = Double.parseDouble(lat1);

        } catch (NumberFormatException e) {

            System.out.println("Incorrect format of latitude given, please try again");
            return false;
        }

        System.out.println();


        try {
            longitude = Double.parseDouble(long1);

        } catch (NumberFormatException e) {

            System.out.println("Incorrect format of longitude given, please try again");
            return false;
        }

        if (!controller.storeLocation(type, latitude, longitude)) {

            System.out.println();
            System.out.println("Invalid latitude or longitude given");
            System.out.println("Latitude should be between -90 to 90");
            System.out.println("Longitude should be between -180 to 180");

            return false;
        }

        return true;
    }

    private boolean getNumPassengers(String passengers) {

        int numPassengers = 0;

        try {

            numPassengers = Integer.parseInt(passengers);

        } catch (NumberFormatException e) {

            System.out.println("You entered an invalid value, please enter a number between 1 - 16");
            return false;
        }

        if (!controller.storeNumPassengers(numPassengers)) {

            System.out.println();
            System.out.println("Invalid number of passengers entered, please enter a number between 1 - 16 inclusive");
            return false;

        }

        return true;


    }


}
