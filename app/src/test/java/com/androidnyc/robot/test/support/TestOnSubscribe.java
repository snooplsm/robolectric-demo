package com.androidnyc.robot.test.support;

import rx.Observable;
import rx.Subscriber;

public class TestOnSubscribe<T> implements Observable.OnSubscribe<T> {
  private Subscriber<? super T> subscriber;

  @Override public void call(Subscriber<? super T> subscriber) {
    this.subscriber = subscriber;
  }

  public void onNext(T data) {
    if (subscriber == null) {
      throw new RuntimeException("Subscriber was never set");
    }

    subscriber.onNext(data);
  }

  public boolean isUnsubscribed() {
    return subscriber == null || subscriber.isUnsubscribed();
  }

  public void onCompleted() {
    if (subscriber == null) {
      throw new RuntimeException("Subscriber was never set");
    }

    subscriber.onCompleted();
  }

  public void onError(Throwable throwable) {
    if (subscriber == null) {
      throw new RuntimeException("Subscriber was never set");
    }

    subscriber.onError(throwable);
  }

  public void unsubscribe() {
    subscriber.unsubscribe();
  }
}
