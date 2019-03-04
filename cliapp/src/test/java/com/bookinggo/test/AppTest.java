package com.bookinggo.test;


import com.bookinggo.test.controller.Controller;
import com.bookinggo.test.controller.Utilities.CarTypes;
import com.bookinggo.test.controller.Utilities.LocationType;

import com.bookinggo.test.model.Car;
import com.bookinggo.test.model.Location;
import com.bookinggo.test.model.SearchRequestInfo;
import com.bookinggo.test.model.SearchResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    Controller controller;

    @Before
    public void before() {

        controller = new Controller();

    }

    @Test
    public void testNegativePassengers() {

        boolean flag = controller.storeNumPassengers(-9);

        assertEquals(flag, false);
    }

    @Test
    public void testLargeNumPassengers() {

        boolean flag = controller.storeNumPassengers(17);

        assertEquals(flag, false);
    }

    @Test
    public void testValidLocation() {

        boolean flag = controller.storeLocation(LocationType.PICKUP, 51.001, 25.002);

        assertEquals(flag, true);
    }

    @Test
    public void testInvalidLocation() {

        boolean flag = controller.storeLocation(LocationType.DROP_OFF, -91, -181) &&
                controller.storeLocation(LocationType.DROP_OFF, 91, 181);


        assertEquals(flag, false);
    }


    /*
     */
    @Test
    public void testResultsDescending() {

        SearchResult results = new SearchResult();

        Car car1 = new Car(CarTypes.STANDARD, 7000, "DAVE");
        Car car2 = new Car(CarTypes.MINIBUS, 18000, "ERIC");
        Car car3 = new Car(CarTypes.LUXURY_PEOPLE_CARRIER, 20000, "ERIC");

        results.getFilteredResults().add(car1);
        results.getFilteredResults().add(car2);
        results.getFilteredResults().add(car3);

        controller.setSearchResult(results);


        boolean flag = (results.getFilteredResults().get(0).getPrice() <= results.getFilteredResults().get(1).getPrice()) &&
                (results.getFilteredResults().get(1).getPrice() <= results.getFilteredResults().get(2).getPrice());


        Assert.assertTrue(flag);
    }

    @Test
    public void testRemoveRedundantResults() {

        SearchResult results = new SearchResult();
        SearchRequestInfo searchInfo = new SearchRequestInfo();

        controller.setSearchResult(results);
        controller.setSearchInfo(searchInfo);

        searchInfo.setPickup(new Location(51.470020, -0.454295));
        searchInfo.setDropoff(new Location(51.0, 1.0));
        searchInfo.setNumPassengers(6);

        Car car1 = new Car(CarTypes.STANDARD, 6000, "DAVE");
        Car car2 = new Car(CarTypes.EXECUTIVE, 6500, "JEFF");
        Car car3 = new Car(CarTypes.LUXURY, 7000, "JEFF");
        Car car4 = new Car(CarTypes.PEOPLE_CARRIER, 7500, "ERIC");
        Car car5 = new Car(CarTypes.LUXURY_PEOPLE_CARRIER, 20000, "ERIC");
        Car car6 = new Car(CarTypes.MINIBUS, 18000, "ERIC");

        results.addResult(car1);
        results.addResult(car2);
        results.addResult(car3);
        results.addResult(car4);
        results.addResult(car5);
        results.addResult(car6);

        List<Car> resultsList = controller.getResults();


        boolean flag = results.getFullResults().size() - 3  == resultsList.size();


        Assert.assertTrue(flag);
    }


    @Test
    public void testCheapestCar() {

        SearchResult results = new SearchResult();
        SearchRequestInfo searchInfo = new SearchRequestInfo();

        controller.setSearchResult(results);
        controller.setSearchInfo(searchInfo);

        searchInfo.setPickup(new Location(51.470020, -0.454295));
        searchInfo.setDropoff(new Location(51.0, 1.0));
        searchInfo.setNumPassengers(6);

        Car car1 = new Car(CarTypes.STANDARD, 6000, "DAVE");
        Car car2 = new Car(CarTypes.STANDARD, 6500, "ERIC");
        Car car3 = new Car(CarTypes.STANDARD, 7000, "JEFF");


        results.addResult(car1);
        results.addResult(car2);
        results.addResult(car3);



        assertTrue(results.getCheapestCar(CarTypes.STANDARD).getPrice() == 6000);


    }

}
