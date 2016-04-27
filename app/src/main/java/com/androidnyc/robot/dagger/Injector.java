package com.androidnyc.robot.dagger;

import android.annotation.SuppressLint;
import android.content.Context;

import dagger.ObjectGraph;

public class Injector {
  private static final String SERVICE_NAME = "INJECT_SERVICE";

  public static boolean matches(String name) {
    return SERVICE_NAME.equals(name);
  }

  @SuppressWarnings("ResourceType") @SuppressLint("ServiceCast")
  public static ObjectGraph obtain(Context context) {
    return (ObjectGraph) context.getSystemService(SERVICE_NAME);
  }

  private Injector() {}
}
