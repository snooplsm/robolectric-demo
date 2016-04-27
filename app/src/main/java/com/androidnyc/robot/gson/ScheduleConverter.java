package com.androidnyc.robot.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class ScheduleConverter implements JsonDeserializer<Scoreboard> {

  @Override
  public Scoreboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    JsonObject obj = json.getAsJsonObject();
    JsonObject gamesObject = obj.getAsJsonObject("games");
    JsonArray gamesArray = gamesObject.getAsJsonArray("game");
    List<Game> game = new ArrayList<>(gamesArray.size());
    for (int i = 0; i < gamesArray.size(); i++) {
      game.add(context.deserialize(gamesArray.get(i),Game.class));
    }
    return new Scoreboard(game);
  }


  protected abstract Gson getGson();

}
