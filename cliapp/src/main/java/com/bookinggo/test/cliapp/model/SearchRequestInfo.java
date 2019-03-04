package com.bookinggo.test.cliapp.model;

/**
 * Stores information about the search request
 */
public class SearchRequestInfo {

    private Location pickup;
    private Location dropoff;
    private int numPassengers;

    public SearchRequestInfo() {

    }


    public Location getPickup() {
        return pickup;
    }

    public void setPickup(Location pickup) {
        this.pickup = pickup;
    }

    public Location getDropoff() {
        return dropoff;
    }

    public void setDropoff(Location dropoff) {
        this.dropoff = dropoff;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }


}
