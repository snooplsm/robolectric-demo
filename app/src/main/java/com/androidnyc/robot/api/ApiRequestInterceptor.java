package com.androidnyc.robot.api;


import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import com.androidnyc.robot.BuildConfig;
import com.androidnyc.robot.support.SessionManager;


public class ApiRequestInterceptor implements Interceptor {

  static final String WEATHER_API_KEY = "appid";

  private final SessionManager sessionManager;
//  private final LocationProvider locationProvider;


  @Inject
  public ApiRequestInterceptor(SessionManager sessionManager /*, LocationProvider locationProvider*/) {
    this.sessionManager = sessionManager;
//    this.locationProvider = locationProvider;
  }

  @Override public Response intercept(Chain chain) throws IOException {

    HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
    urlBuilder.addQueryParameter(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY);
//    Location location = locationProvider.getLastFetchedLocation();
//    if (location != null) {
//      builder.addHeader(USER_LOCATION, String.format("%s,%s", location.getLatitude(), location.getLongitude()));
//    }

    Request.Builder requestBuilder = chain.request().newBuilder();
    requestBuilder.url(urlBuilder.build());
    return chain.proceed(requestBuilder.build());
  }
}