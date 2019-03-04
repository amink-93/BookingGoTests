package com.bookinggo.test.api;

//import com.bookinggo.test.controller.Controller;
import com.bookinggo.test.controller.Utilities.CarTypes;
import com.bookinggo.test.controller.Utilities.LocationType;
import com.bookinggo.test.model.Car;
import com.bookinggo.test.model.Location;
import com.bookinggo.test.controller.Controller;
import com.google.gson.Gson;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestController
public class APIController {


    private Controller controller = new Controller();


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "pickup") Location pickup,
                         @RequestParam(value = "dropoff") Location dropoff,
                         @RequestParam(required = false, defaultValue = "1", value = "numPassengers") int numPassengers) {


        if (controller.storeNumPassengers(numPassengers)) {

            if (controller.storeLocation(LocationType.PICKUP, pickup) && controller.storeLocation(LocationType.DROP_OFF, dropoff)) {

                controller.search();
            } else {

                return "{response: ERROR Invalid parameters given for pickup or dropoff}";
            }

        } else {

            return "{response: ERROR Invalid value given for number of passengers}";
        }

        return generateResponse();
    }


    @RequestMapping("/")
    public String error() {

        return "{response: ERROR Invalid URL, please refer to the api for reference}";
    }

    @ExceptionHandler({ConversionFailedException.class, MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public String handleException(Exception e) {

        if (e.getClass() == MethodArgumentTypeMismatchException.class) {

            return "{response: Error with parameter format, please see the api documentation for reference}";
        }

        if (e.getClass() == MissingServletRequestParameterException.class) {

            return "{response: " + e.getMessage() + "}";
        }

        return "{response: some error has occurred please try again}";
    }

    private String generateResponse() {

        Gson json = new Gson();

        List<Car> tmp = controller.getResults();

        System.err.println("is tmp empty: " + tmp.isEmpty());

        if (!tmp.isEmpty()) {

            return json.toJson(tmp);
        } else {

            return "{response: Sorry we could not find any cars for you, please try again later}";
        }
    }
}
