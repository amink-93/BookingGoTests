package com.bookinggo.test.cliapp.controller;

/**
 * Utilities class holds common functions and information that is needed throughout the program
 */
public class Utilities {

    public enum CarTypes {
        STANDARD, EXECUTIVE, LUXURY, PEOPLE_CARRIER, LUXURY_PEOPLE_CARRIER, MINIBUS;
    }

    public enum LocationType {
        PICKUP, DROP_OFF;


    }

    public static String daveAPI = "https://techtest.rideways.com/dve";
    public static String ericAPI = "https://techtest.rideways.com/eric";
    public static String jeffAPI = "https://techtest.rideways.com/jeff";

    public static boolean validateLocation(Double latitude, Double longitude) {

        boolean isValid = true;

        if (latitude == null || longitude == null) {

            return false;
        }
        if (latitude < -90 || latitude > 90) {

            isValid = false;
        }

        if (longitude < -180 || longitude > 180) {

            isValid = false;
        }

        return isValid;

    }

    public static boolean validateNumPassengers(int x) {

        if (x > 16 || x < 1) {

            return false;
        }

        return true;
    }


}
