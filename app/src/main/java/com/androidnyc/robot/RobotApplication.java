package com.androidnyc.robot;

import android.app.Application;

import java.util.Arrays;
import java.util.Collection;

import dagger.ObjectGraph;
import timber.log.Timber;
import com.androidnyc.robot.dagger.ApiModule;
import com.androidnyc.robot.dagger.Injector;

public class RobotApplication extends Application {

  private ObjectGraph objectGraph;

  @Override public void onCreate() {
    super.onCreate();
    buildInjector();
  }

  protected void buildInjector() {
    objectGraph = ObjectGraph.create(getModules().toArray());
    objectGraph.inject(this);

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      //Timber.plant(new Timber.R);
    }
  }

  protected Collection<? extends Object> getModules() {
    return Arrays.asList(new ApiModule(this));
  }

  @Override public Object getSystemService(String name) {
    if (Injector.matches(name)) {
      return objectGraph;
    }

    return super.getSystemService(name);
  }
}
