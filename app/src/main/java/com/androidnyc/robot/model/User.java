package com.androidnyc.robot.model;

public class User {

  private String id;
  private String username;
  private String email;
  private String authenticationToken;

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getAuthenticationToken() {
    return authenticationToken;
  }

  public String getEmail() {
    return email;
  }
}
