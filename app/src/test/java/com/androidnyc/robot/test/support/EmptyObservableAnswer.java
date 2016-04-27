package com.androidnyc.robot.test.support;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import rx.Observable;
import rx.Single;

public class EmptyObservableAnswer implements Answer<Object> {
  public Object answer(InvocationOnMock invocation) throws Throwable {
    if (invocation.getMethod().getReturnType().getCanonicalName().equals(Observable.class.getCanonicalName())) {
      return Observable.empty();

    } else if (invocation.getMethod().getReturnType().getCanonicalName().equals(Single.class.getCanonicalName())) {
      return Observable.empty().toSingle();

    } else {
      return TestHelpers.RETURNS_BUILDER.answer(invocation);
    }
  }
}
