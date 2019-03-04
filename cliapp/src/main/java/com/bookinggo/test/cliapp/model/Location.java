package com.bookinggo.test.cliapp.model;

/**
 * Location class represents a geographical location in its absoulte location using latitude and longitude.
 */
public class Location {


    private Double latitude;
    private Double longitude;

    public Location(Double latitude, Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }


}
