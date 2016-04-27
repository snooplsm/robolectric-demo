package com.androidnyc.robot.test.support;

import com.androidnyc.robot.TestRobotApplication;
import com.androidnyc.robot.api.Api;
import com.androidnyc.robot.dagger.ApiModule;
import com.androidnyc.robot.ui.view.HomeLayout;
import com.androidnyc.robot.ui.view.HomeLayoutTest;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module(
  injects = {
    HomeLayoutTest.class,
    TestRobotApplication.class
  },
  library = true,
  overrides = true,
  includes = ApiModule.class
)
public class ApiTestModule {

  @Provides @Singleton Api provideApi() {
    return mock(Api.class, Mockito.withSettings().name("Mock Api").defaultAnswer(TestHelpers.RETURNS_EMPTY_OBSERVABLE));
  }

}
