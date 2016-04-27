package com.androidnyc.robot.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;

import com.androidnyc.robot.R;
import com.androidnyc.robot.api.Api;

import javax.inject.Inject;

public class HomeLayout extends RelativeLayout {

  @Inject Api api;

  public HomeLayout(Context context) {
    super(context);
    inflate(context, R.layout.layout_home, this);

  }

}
