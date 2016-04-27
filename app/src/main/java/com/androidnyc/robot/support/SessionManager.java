package com.androidnyc.robot.support;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.androidnyc.robot.model.User;

@Singleton
public class SessionManager {

  private User user;
  private final Gson gson;
  private final SharedPreferences sharedPreferences;

  @Inject public SessionManager(Gson gson,SharedPreferences sharedPreferences) {
    this.gson = gson;
    this.sharedPreferences = sharedPreferences;
  }

  public User getUser() {
    if(user==null) {
      String user = sharedPreferences.getString("user",null);
      if(user!=null) {
        this.user = gson.fromJson(user,User.class);
      }
    }
    return this.user;
  };

  public void saveUser(User user) {
    this.user = user;
    sharedPreferences.edit().putString("user",gson.toJson(user)).apply();
  }

  public void logout() {
    user = null;
    sharedPreferences.edit().remove("user").apply();
  }
}
