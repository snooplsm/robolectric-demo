package com.androidnyc.robot;

import android.annotation.SuppressLint;
import android.app.Application;

import com.androidnyc.robot.dagger.Injector;
import com.androidnyc.robot.test.support.ApiTestModule;
import com.androidnyc.robot.test.support.SkipTestInjection;
import com.androidnyc.robot.test.support.UseModule;


import org.robolectric.TestLifecycleApplication;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

@SuppressWarnings("unused")
public class TestRobotApplication extends RobotApplication implements TestLifecycleApplication {
  static {
    RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
      @Override public Scheduler getMainThreadScheduler() {
        return Schedulers.immediate();
      }
    });
    RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
      @Override public Scheduler getIOScheduler() {
        return Schedulers.immediate();
      }

    });
  }
  private List<Object> testModule = new ArrayList<>();

  @SuppressLint("MissingSuperCall") @Override public void onCreate() {
    // Noop don't inject until annotations processed
  }

  @Override protected Collection<? extends Object> getModules() {
    ArrayList<Object> modules = new ArrayList<>();

    Collection<?> realModules = super.getModules();
    modules.addAll(realModules);
    modules.addAll(getTestModules(realModules));

    if (testModule != null) {
      modules.addAll(testModule);
    }

    return modules;
  }

  private Collection<? extends Object> getTestModules(Collection<?> realModules) {
    return Arrays.asList(new ApiTestModule());
  }

  @Override public void prepareTest(Object test) {
    boolean injectTest = true;
    try {
      for (Annotation annotation : test.getClass().getAnnotations()) {
        if (annotation instanceof UseModule) {
          Class[] module = ((UseModule) annotation).value();
          for(Class clazz : module) {
            addTestModule(clazz.newInstance());
          }
        } else if (annotation instanceof SkipTestInjection) {
          injectTest = false;
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    buildInjector();

    if (injectTest) {
      Injector.obtain(this).inject(test);
    }
  }

  private void addTestModule(Object testModule) {
    this.testModule.add(testModule);
  }

  @Override public void beforeTest(Method method) { }

  @Override public void afterTest(Method method) {
    testModule.clear();
  }
}
