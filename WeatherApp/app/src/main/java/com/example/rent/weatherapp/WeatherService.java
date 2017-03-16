package com.example.rent.weatherapp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-15.
 */

public interface WeatherService {

    @GET("api.php")
    Observable<DataContainer> getWeather(@Query("city") String city);


}
