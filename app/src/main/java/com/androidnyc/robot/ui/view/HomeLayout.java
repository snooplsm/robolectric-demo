package com.androidnyc.robot.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.androidnyc.robot.R;
import com.androidnyc.robot.api.Api;
import com.androidnyc.robot.dagger.Injector;
import com.androidnyc.robot.rx.RobotSchedulers;
import com.androidnyc.robot.rx.SubscriptionHelper;
import com.androidnyc.robot.ui.lifecycle.Lifecycle;

import javax.inject.Inject;

import rx.Subscription;
import timber.log.Timber;

public class HomeLayout extends RelativeLayout implements Lifecycle {

  @Inject Api api;

  protected boolean isPaused;

  protected Object result;

  private Subscription subscription;

  public HomeLayout(Context context) {
    super(context);
    inflate(context, R.layout.layout_home, this);
    Injector.obtain(context).inject(this);
  }

  @Override public void onCreate(Bundle savedInstanceState) {

  }

  @Override public void onResume() {
    isPaused = false;
    subscription = api.getWeather(40.712f,74.005f)
      .compose(RobotSchedulers.applyDefault())
      .subscribe(res-> {
        Timber.i(res.toString());
        this.result = res;
      },error-> {
        Timber.e(error,error.getMessage());
      });
    ;
  }

  @Override public void onPause() {
    isPaused = true;
    SubscriptionHelper.unsubscribe(subscription);
    subscription = null;
  }

  @Override public void onDestroy() {

  }

  @Override public void onsaveInstanceState(Bundle outState) {

  }

  @Override public void onLowMemory() {

  }
}
