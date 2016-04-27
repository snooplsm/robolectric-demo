package com.androidnyc.robot.test.support;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class BuilderAnswer implements Answer<Object> {
  public Object answer(InvocationOnMock invocation) throws Throwable {
    Object mock = invocation.getMock();
    if (invocation.getMethod().getReturnType().isInstance(mock)) {
      return mock;
    } else {
      return Mockito.RETURNS_DEFAULTS.answer(invocation);
    }
  }
}
