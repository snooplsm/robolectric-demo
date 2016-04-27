package com.androidnyc.robot.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class GameConverter implements JsonDeserializer<Game> {

  private Gson gson;

  public GameConverter() {
    gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }

  @Override
  public Game deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Game game = gson.fromJson(json,Game.class);
    JsonObject obj = json.getAsJsonObject();
    JsonObject home = new JsonObject();
    JsonObject away = new JsonObject();
    for(Map.Entry<String,JsonElement> entry : obj.entrySet()) {
      if(entry.getKey().startsWith("home_")) {
        StringBuilder key = new StringBuilder(entry.getKey().replace("home_",""));
        key.setCharAt(0,Character.toLowerCase(key.charAt(0)));
        home.add(key.toString(),entry.getValue());
      } else if (entry.getKey().startsWith("away_")) {
        StringBuilder key = new StringBuilder(entry.getKey().replace("away_",""));
        key.setCharAt(0,Character.toLowerCase(key.charAt(0)));
        away.add(key.toString(),entry.getValue());
      }
    }
    game.setAwayTeam(gson.fromJson(home,Team.class));
    game.setHomeTeam(gson.fromJson(away,Team.class));
    return game;
  }

  public abstract Gson getGson();
}
