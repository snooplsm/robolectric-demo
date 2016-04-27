package com.androidnyc.robot.rx;

import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@SuppressWarnings("Convert2Lambda")
public class RobotSchedulers {

  private static Observable.Transformer DEFAULT = new Observable.Transformer() {
    @Override public Object call(Object observable) {
      return ((Observable) observable)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io());
    }
  };
  private static Observable.Transformer BACKGROUND = new Observable.Transformer() {
    @Override public Object call(Object observable) {
      return ((Observable) observable)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io());
    }
  };
  private static Single.Transformer SINGLE_DEFAULT = new Single.Transformer() {
    @Override public Object call(Object single) {
      return ((Single) single)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }
  };
  private static Single.Transformer SINGLE_BACKGROUND = new Single.Transformer() {
    @Override public Object call(Object single) {
      return ((Single) single)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io());
    }
  };

  @SuppressWarnings("unchecked")
  public static <T> Observable.Transformer<T, T> applyDefault() {
    return (Observable.Transformer<T, T>) DEFAULT;
  }

  @SuppressWarnings("unchecked")
  public static <T> Single.Transformer<T, T> applySingle() {
    return (Single.Transformer<T, T>) SINGLE_DEFAULT;
  }

  @SuppressWarnings("unchecked")
  public static <T> Observable.Transformer<T, T> applyBackground() {
    return (Observable.Transformer<T, T>) BACKGROUND;
  }

  @SuppressWarnings("unchecked")
  public static <T> Single.Transformer<T, T> applySingleBackground() {
    return (Single.Transformer<T, T>) SINGLE_BACKGROUND;
  }
}
