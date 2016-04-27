package com.androidnyc.robot.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;

import com.androidnyc.robot.R;
import com.androidnyc.robot.dagger.Injector;
import com.androidnyc.robot.support.SessionManager;
import com.androidnyc.robot.ui.view.HomeLayout;

public class BaseActivity extends AppCompatActivity {

  @Inject SessionManager sessionManager;
  private ViewGroup root;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Injector.obtain(getApplicationContext()).inject(this);
    root = new HomeLayout(this);
    root.setId(R.id.root_view);
    setContentView(root);
    traverse(root, lifecycle -> {
      lifecycle.onCreate(savedInstanceState);
    });
  }

  private void traverse(ViewGroup root, Lifecycle lifecycle) {
    if(root instanceof com.androidnyc.robot.ui.lifecycle.Lifecycle) {
      lifecycle.doLifecycle((com.androidnyc.robot.ui.lifecycle.Lifecycle) root);
    }
    traverseChildren(root, lifecycle);
  }

  private void traverseChildren(ViewGroup root, Lifecycle lifecycle) {
    for (int i = 0; i < root.getChildCount(); i++) {
      View child = root.getChildAt(i);
      if (child instanceof com.androidnyc.robot.ui.lifecycle.Lifecycle) {
        lifecycle.doLifecycle((com.androidnyc.robot.ui.lifecycle.Lifecycle) child);
      }
      if (child instanceof ViewGroup) {
        traverseChildren((ViewGroup) child, lifecycle);
      }
    }
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
  }

  @Override public Object getSystemService(@NonNull String name) {
    if (Injector.matches(name)) {
      return Injector.obtain(getApplicationContext());
    }

    return super.getSystemService(name);
  }

  @Override protected void onPause() {
    super.onPause();
    traverse(root, lifecycle -> {
      lifecycle.onPause();
    });
  }

  @Override protected void onResume() {
    super.onResume();
    traverse(root, lifecycle -> {
      lifecycle.onResume();
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    traverse(root, lifecycle -> {
      lifecycle.onDestroy();
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    traverse(root, lifecycle -> lifecycle.onsaveInstanceState(outState));
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    traverse(root, lifecycle -> lifecycle.onLowMemory());
  }

  private interface Lifecycle {
    void doLifecycle(com.androidnyc.robot.ui.lifecycle.Lifecycle lifecycle);
  }

}
