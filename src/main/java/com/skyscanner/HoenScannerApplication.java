package com.skyscanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import javax.naming.directory.SearchResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {

    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) {
        ObjectMapper mapper = new ObjectMapper();

        List<SearchResult> searchResults = new ArrayList<>();

        try {

            SearchResult[] cars = mapper.readValue(
                    new File("src/main/resources/rental_cars.json"),
                    SearchResult[].class
            );

            SearchResult[] hotels = mapper.readValue(
                    new File("src/main/resources/hotels.json"),
                    SearchResult[].class
            );

            searchResults.addAll(Arrays.asList(cars));
            searchResults.addAll(Arrays.asList(hotels));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register API resource
        environment.jersey().register(new SearchResource(searchResults));
    }

}
