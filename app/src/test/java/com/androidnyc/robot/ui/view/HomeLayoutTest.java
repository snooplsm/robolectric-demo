package com.androidnyc.robot.ui.view;

import com.androidnyc.robot.BuildConfig;
import com.androidnyc.robot.api.Api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 18, constants = BuildConfig.class, packageName = BuildConfig.APPLICATION_ID)
public class HomeLayoutTest {

  @Inject Api api;

  private HomeLayout subject;

  @Before public void setUp() throws Exception {
    subject = new HomeLayout(ShadowApplication.getInstance().getApplicationContext());
  }


  @Test
  public void onHomeLayout_hasNoChildren() {
    assertThat(subject.getChildCount()).isEqualTo(0);
  }

}