package com.bookinggo.test.api;

import com.bookinggo.test.model.Location;
import org.springframework.core.convert.converter.Converter;

public class StringToLocationConverter implements Converter<String, Location> {
    @Override
    public Location convert(String s) {


        String[] data = s.split(",");

        double latitude, longitude;

        try {

            latitude = Double.parseDouble(data[0]);
            longitude = Double.parseDouble(data[1]);

            return new Location(latitude, longitude);

        } catch (NumberFormatException e) {

            return new Location(null, null);
        }


    }
}
