package com.androidnyc.robot.dagger;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.androidnyc.robot.BuildConfig;
import com.androidnyc.robot.ui.view.HomeLayout;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

import com.androidnyc.robot.RobotApplication;
import com.androidnyc.robot.api.Api;
import com.androidnyc.robot.api.ApiRequestInterceptor;
import com.androidnyc.robot.ui.activity.BaseActivity;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module(
  injects = {
    BaseActivity.class,
    HomeLayout.class,
    RobotApplication.class,
  },
  library=true
)
public class ApiModule {

  private RobotApplication application;

  public ApiModule(RobotApplication application) {
    this.application = application;
  }

  @Provides Api provideApi(Retrofit retrofit) {
    return retrofit.create(Api.class);
  }

  @Provides Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
    return new Retrofit.Builder()
      .client(client)
      .baseUrl(BuildConfig.API_ENDPOINT)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .build();
  }

  @Provides HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    return interceptor;
  }

  private Gson gson;

  @Provides SharedPreferences provideSharedPreferences() {
    return PreferenceManager.getDefaultSharedPreferences(application);
  }

  @Provides public Gson provideGson() {

    return gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create();
  }

  @Singleton @Provides
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, ApiRequestInterceptor apiRequestInterceptor) {
    return new OkHttpClient.Builder()
      .writeTimeout(10, SECONDS)
      .readTimeout(10, SECONDS)
      .connectTimeout(15, SECONDS)
      .addInterceptor(loggingInterceptor)
      .addInterceptor(apiRequestInterceptor)
      .build();
  }

}
