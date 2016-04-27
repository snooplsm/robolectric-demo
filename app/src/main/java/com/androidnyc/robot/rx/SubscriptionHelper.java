package com.androidnyc.robot.rx;

import android.support.annotation.Nullable;

import rx.Subscription;

public class SubscriptionHelper {
  public static boolean isSubscribed(@Nullable Subscription subscription) {
    return subscription != null && !subscription.isUnsubscribed();
  }

  public static void unsubscribe(@Nullable Subscription... subscriptions) {
    if (subscriptions != null) {
      for (int i = 0; i < subscriptions.length; i++) {
        Subscription subscription = subscriptions[i];
        if (subscription != null && !subscription.isUnsubscribed()) {
          subscription.unsubscribe();
        }
      }
    }
  }
}