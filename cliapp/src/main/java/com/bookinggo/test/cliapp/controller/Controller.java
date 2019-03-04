package com.bookinggo.test.cliapp.controller;


import com.bookinggo.test.cliapp.controller.Utilities.CarTypes;
import com.bookinggo.test.cliapp.model.Car;
import com.bookinggo.test.cliapp.model.Location;
import com.bookinggo.test.cliapp.model.SearchRequestInfo;
import com.bookinggo.test.cliapp.model.SearchResult;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Single central controller that performs all the data manipulation and data retrieval
 */
public class Controller {


    private SearchRequestInfo searchInfo;
    private SearchResult searchResult;
    private RequestConfig requestConfig;
    private Logger logger;

    public Controller() {

        searchInfo = new SearchRequestInfo();
        searchResult = new SearchResult();

        logger = Logger.getAnonymousLogger();
        logger.setUseParentHandlers(false);

        requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .build();
    }


    public boolean storeLocation(Utilities.LocationType type, double latitude, double longitude) {

        if (Utilities.validateLocation(latitude, longitude)) {

            if (type == Utilities.LocationType.PICKUP) {

                searchInfo.setPickup(new Location(latitude, longitude));
            } else {

                searchInfo.setDropoff(new Location(latitude, longitude));
            }

            return true;
        }

        return false;
    }

    public boolean storeLocation(Utilities.LocationType type, Location location) {

        if (Utilities.validateLocation(location.getLatitude(), location.getLongitude())) {

            if (type == Utilities.LocationType.PICKUP) {

                searchInfo.setPickup(location);
            } else {

                searchInfo.setDropoff(location);
            }

            return true;
        }

        return false;
    }

    public boolean storeNumPassengers(int x) {

        if (Utilities.validateNumPassengers(x)) {

            searchInfo.setNumPassengers(x);

            return true;
        }

        return false;
    }

    public void search() {

        searchResult = new SearchResult();

        final Thread daveThread = new Thread("Dave API") {

            public void run() {
                super.run();
                searchResult.setDaveData(retrieveData(Utilities.daveAPI));
            }
        };

        final Thread ericThread = new Thread("Eric API") {

            public void run() {
                super.run();
                searchResult.setEricData(retrieveData(Utilities.ericAPI));
            }
        };

        final Thread jeffThread = new Thread("Jeff API") {

            public void run() {
                super.run();
                searchResult.setJeffData(retrieveData(Utilities.jeffAPI));
            }
        };

        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                daveThread.start();
                ericThread.start();
                jeffThread.start();

                try {
                    daveThread.join();
                    ericThread.join();
                    jeffThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {

            logger.log(Level.WARNING, "Thread interrupted");
        }


        System.out.println("SEARCH COMPLETED");
        System.out.println("Processing results.........");

    }

    private void removeRedundantResults() {


        for (CarTypes type : determineCarType(searchInfo.getNumPassengers())) {

            Car result = searchResult.getCheapestCar(type);

            if (result != null) {
                searchResult.addFilteredResult(result);
            }
        }
    }

    public List<Car> getResults() {

        removeRedundantResults();
        Collections.sort(searchResult.getFilteredResults());
        return searchResult.getFilteredResults();
    }

    private JsonObject retrieveData(String url) {

        boolean isSuccessful = true;
        JsonObject data = null;
        Location pickup = searchInfo.getPickup();
        Location dropoff = searchInfo.getDropoff();
        int numPassengers = searchInfo.getNumPassengers();

        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {

            HttpGet httpGet = new HttpGet(url + generateQuery(pickup, dropoff, numPassengers));

            String response = httpClient.execute(httpGet, new BasicResponseHandler());

            if (response != null) {

                JsonParser parser = new JsonParser();
                data = parser.parse(response).getAsJsonObject();

                cleanData(data);
            }

        } catch (HttpResponseException e) {

            logger.log(Level.INFO,"Problem accessing data for: " + Thread.currentThread().getName());
        } catch (ClientProtocolException e) {

            logger.log(Level.WARNING, "ClientProtocolException");

        } catch (SocketTimeoutException e) {

            logger.log(Level.INFO,"data request took too long");
        } catch (IOException e) {

        }

        return data;
    }

    private void cleanData(JsonObject data) {

        String supplier = data.get("supplier_id").getAsString();


        for (JsonElement option : data.getAsJsonArray("options")) {

            try {

                CarTypes type = CarTypes.valueOf(option.getAsJsonObject().get("car_type").getAsString());
                int price = Integer.parseInt(option.getAsJsonObject().get("price").getAsString());

                searchResult.addResult(new Car(type, price, supplier));


            } catch (NumberFormatException e) {

            } catch (IllegalArgumentException e) {

                logger.log(Level.INFO,"new car type found");
            }
        }


    }

    private CarTypes[] determineCarType(int numPassengers) {

        if (numPassengers <= 4 && numPassengers >= 0) {

            return CarTypes.values();
        } else if (numPassengers <= 6) {

            return new CarTypes[]{CarTypes.PEOPLE_CARRIER, CarTypes.LUXURY_PEOPLE_CARRIER, CarTypes.MINIBUS};
        } else if (numPassengers >= 6) {

            return new CarTypes[]{CarTypes.MINIBUS};
        }

        return new CarTypes[0];
    }

    private String generateQuery(Location pickup, Location dropoff, int numPassengers) {

        final String query = "?pickup=" + pickup.getLatitude() + "," + pickup.getLongitude()
                + "&dropoff=" + dropoff.getLatitude() + "," + dropoff.getLongitude();

        return query;
    }


    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public void setSearchInfo(SearchRequestInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

}
