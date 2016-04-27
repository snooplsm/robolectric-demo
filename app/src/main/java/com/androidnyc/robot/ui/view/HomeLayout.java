package com.androidnyc.robot.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.androidnyc.robot.R;
import com.androidnyc.robot.api.Api;
import com.androidnyc.robot.ui.lifecycle.Lifecycle;

import javax.inject.Inject;

public class HomeLayout extends RelativeLayout implements Lifecycle {

  @Inject Api api;

  protected boolean isPaused;

  public HomeLayout(Context context) {
    super(context);
    inflate(context, R.layout.layout_home, this);
  }

  @Override public void onCreate(Bundle savedInstanceState) {

  }

  @Override public void onResume() {
    isPaused = false;
  }

  @Override public void onPause() {
    isPaused = true;
  }

  @Override public void onDestroy() {

  }

  @Override public void onsaveInstanceState(Bundle outState) {

  }

  @Override public void onLowMemory() {

  }
}
