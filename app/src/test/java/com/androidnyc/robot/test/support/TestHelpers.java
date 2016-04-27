package com.androidnyc.robot.test.support;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.androidnyc.robot.R;
import com.androidnyc.robot.dagger.ApiModule;
import com.androidnyc.robot.dagger.Injector;
import com.google.gson.Gson;

import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowActivity;

import dagger.ObjectGraph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

public class TestHelpers {
  public static final Answer RETURNS_BUILDER = new BuilderAnswer();
  public static final Answer RETURNS_EMPTY_OBSERVABLE = new EmptyObservableAnswer();
  private static ApiModule robotModule = new ApiModule(null);

  public static String getString(int resId, Object... objects) {
    return RuntimeEnvironment.application.getString(resId, objects);
  }

  public static Gson getGson() {
    return robotModule.provideGson();
  }

  public static AppCompatActivity startVisibleBackStackedFragment(Fragment fragment, @IdRes int containerId, String tag) {
    AppCompatActivity activity = startActivityWithId(containerId);

    activity
      .getSupportFragmentManager()
      .beginTransaction()
      .add(containerId, fragment, tag)
      .addToBackStack(tag)
      .commit();

    return activity;
  }

  public static AppCompatActivity startVisibleFragment(AppCompatActivity activity, Fragment fragment, @IdRes int containerId, String tag) {
    activity
      .getSupportFragmentManager()
      .beginTransaction()
      .add(containerId, fragment, tag)
      .commit();

    return activity;
  }

  public static AppCompatActivity startVisibleFragment(Fragment fragment, String tag) {
    return startVisibleFragment(fragment, R.id.fragment_container, tag);
  }

  private static AppCompatActivity startVisibleFragment(Fragment fragment, @IdRes int containerId, String tag) {
    AppCompatActivity activity = startActivityWithId(containerId);
    startVisibleFragment(activity, fragment, containerId, tag);
    return activity;
  }

  public static void checkPendingTransitionAnimation(Activity subject, int enter, int exit) {
    subject.finish();
    ShadowActivity shadowActivity = shadowOf(subject);
    assertThat(shadowActivity.getPendingTransitionEnterAnimationResourceId()).isEqualTo(enter);
    assertThat(shadowActivity.getPendingTransitionExitAnimationResourceId()).isEqualTo(exit);
  }

  public static AppCompatActivity startActivityWithId(int containerId) {
    AppCompatActivity activity = Robolectric.setupActivity(TestActivity.class);
    activity.setTheme(R.style.AppTheme);
    FrameLayout view = new FrameLayout(activity);
    view.setId(containerId);
    activity.setContentView(view);
    return activity;
  }

  public static class TestActivity extends AppCompatActivity {
    private boolean onBackPressed = false;

    @Override public void onBackPressed() {
      super.onBackPressed();
      onBackPressed = true;
    }

    @Override public Object getSystemService(String name) {
      if(Injector.matches(name)) {
        ObjectGraph objectGraph = (ObjectGraph) super.getSystemService(name);
        if(objectGraph==null) {
          objectGraph = Injector.obtain(RuntimeEnvironment.application);
        }
        return objectGraph;
      }
      return super.getSystemService(name);
    }

    public boolean wasOnBackPressed() {
      return onBackPressed;
    }
  }
}
