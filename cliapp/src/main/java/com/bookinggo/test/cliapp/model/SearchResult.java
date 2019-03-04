package com.bookinggo.test.cliapp.model;

import com.bookinggo.test.cliapp.controller.Utilities.CarTypes;
import com.bookinggo.test.cliapp.controller.Utilities.LocationType;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchResults class stores all the results gathered from the search based on the SearchInfo
 */
public class SearchResult {


    private List<Car> fullResults;
    private List<Car> filteredResults;
    private JsonObject daveData;
    private JsonObject jeffData;
    private JsonObject ericData;

    private SearchRequestInfo searchInfo;

    public SearchResult() {

        this.fullResults = new ArrayList<>();
        this.filteredResults = new ArrayList<>();
        this.searchInfo = new SearchRequestInfo();
    }


    public void addResult(Car car) {


        fullResults.add(car);
    }

    public void addFilteredResult(Car car) {

        filteredResults.add(car);
    }


    public Car getCheapestCar(CarTypes type) {

        Car cheapestCar = null;

        for (Car car : fullResults) {

            if (cheapestCar == null && car.getType().equals(type)) {

                cheapestCar = car;
            } else if (car.getType().equals(type) && car.getPrice() < cheapestCar.getPrice()) {

                cheapestCar = car;
            }
        }

        return cheapestCar;
    }

    public void setFilteredResults(List<Car> results) {

        this.filteredResults = results;
    }

    public List<Car> getFilteredResults() {
        return filteredResults;
    }

    public List getFullResults() {
        return fullResults;
    }

    public JsonObject getDaveData() {
        return daveData;
    }

    public void setDaveData(JsonObject daveData) {
        this.daveData = daveData;
    }

    public JsonObject getJeffData() {
        return jeffData;
    }

    public void setJeffData(JsonObject jeffData) {
        this.jeffData = jeffData;
    }

    public JsonObject getEricData() {
        return ericData;
    }

    public void setEricData(JsonObject ericData) {
        this.ericData = ericData;
    }


}
