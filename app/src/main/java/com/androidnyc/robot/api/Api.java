package com.androidnyc.robot.api;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

  //f28484e6e35893c0002dfe36d437da1e
  @Headers("Content-Type: application/json")
  @GET("weather")
  Observable<Object> getWeather(@Query("lat") Float lat, @Query("lng") Float lng);

}
