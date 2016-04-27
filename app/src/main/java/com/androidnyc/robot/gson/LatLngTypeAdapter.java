package com.androidnyc.robot.gson;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class LatLngTypeAdapter extends TypeAdapter<LatLng> {

  @Override public void write(JsonWriter out, LatLng value) throws IOException {
    out.beginArray();
    out.value(value.longitude);
    out.value(value.latitude);
    out.endArray();
  }

  @Override public LatLng read(JsonReader in) throws IOException {
    in.beginArray();
    double lng = in.nextDouble();
    double lat = in.nextDouble();
    in.endArray();
    return new LatLng(lat,lng);
  }
}
