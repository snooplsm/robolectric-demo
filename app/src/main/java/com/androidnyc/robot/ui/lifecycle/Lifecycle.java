package com.androidnyc.robot.ui.lifecycle;

import android.os.Bundle;

public interface Lifecycle {

  void onCreate(Bundle savedInstanceState);
  void onResume();
  void onPause();
  void onDestroy();
  void onsaveInstanceState(Bundle outState);
  void onLowMemory();


}
