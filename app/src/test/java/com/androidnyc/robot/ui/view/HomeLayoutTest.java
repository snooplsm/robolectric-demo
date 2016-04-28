package com.androidnyc.robot.ui.view;

import android.app.Activity;

import com.androidnyc.robot.BuildConfig;
import com.androidnyc.robot.R;
import com.androidnyc.robot.api.Api;
import com.androidnyc.robot.test.support.TestOnSubscribe;
import com.androidnyc.robot.ui.activity.BaseActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;

import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID)
public class HomeLayoutTest {

  @Inject Api api;

  private HomeLayout subject;

  private BaseActivity activity;

  private ActivityController<BaseActivity> controller;

  private TestOnSubscribe<Object> subscriber;

  @Before public void setUp() throws Exception {
    subscriber = new TestOnSubscribe<>();
    when(api.getWeather(anyFloat(),anyFloat())).thenReturn(Observable.create(subscriber));
    controller = Robolectric.buildActivity(BaseActivity.class);
    activity = controller.setup().get();
    subject = (HomeLayout) activity.findViewById(R.id.root_view);
  }

  @Test
  public void onHomeLayout_hasNoChildren() {
    assertThat(subject.getChildCount()).isEqualTo(0);
  }

  @Test
  public void onPause_setsPausedVariable() {
    assertThat(subject.isPaused).isFalse();
    controller.pause();
    assertThat(subject.isPaused).isTrue();
  }

  @Test
  public void onResme_subscribes() {
    verify(api).getWeather(anyFloat(),anyFloat());
  }
}