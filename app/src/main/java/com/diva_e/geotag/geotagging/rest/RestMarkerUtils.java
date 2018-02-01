package com.diva_e.geotag.geotagging.rest;

import android.widget.Toast;

import com.diva_e.geotag.geotagging.LocalMarker;
import com.diva_e.geotag.geotagging.MapsActivity;
import com.diva_e.geotag.geotagging.marker.MapMarker;

import java.util.Set;
import java.util.function.Consumer;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jjan on 26.01.2018.
 */

public class RestMarkerUtils {

    public static final String API_BASE_URL = "https://julianapi.localtunnel.me/";
    public static String key = "VeF4sndWI2H9npCGLjwexmwil5L1ak";

    public static MapMarkerClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();

        MapMarkerClient client = retrofit.create(MapMarkerClient.class);

        return client;
    }

    public static void saveMarkers() {
        setMarkersForThisUser(LocalMarker.markerList.getRestMapMarkers(MapsActivity.userId), markers -> {
        }, throwable ->
                Toast.makeText(MapsActivity.getInstance(), "Es konnte keine Verbindung zum Server hergestellt werden. Bitte überprüfe deine Internetverbindung.", Toast.LENGTH_LONG).show());
    }

    public static void createMarker(MapMarker marker) {
        createMarker(marker.getRestMapMarker(MapsActivity.userId), aBoolean -> {
            if (!aBoolean)
                Toast.makeText(MapsActivity.getInstance(), "Der Marker konnte nicht erstellz werden.", Toast.LENGTH_LONG).show();
        }, throwable -> Toast.makeText(MapsActivity.getInstance(), "Es konnte keine Verbindung zum Server hergestellt werden. Bitte überprüfe deine Internetverbindung.", Toast.LENGTH_LONG).show());
    }

    public static void removeMarker(MapMarker marker) {
        removeMarker(marker.getRestMapMarker(MapsActivity.userId), aBoolean -> {
            if (!aBoolean)
                Toast.makeText(MapsActivity.getInstance(), "Der Marker konnte nicht gelöscht werden.", Toast.LENGTH_LONG).show();
        }, throwable -> Toast.makeText(MapsActivity.getInstance(), "Es konnte keine Verbindung zum Server hergestellt werden. Bitte überprüfe deine Internetverbindung.", Toast.LENGTH_LONG).show());
    }

    public static void getMarkers(Consumer<Set<RestMapMarker>> response, Consumer<Throwable> failure) {
        getClient().getMarkers(key).enqueue(new Callback<Set<RestMapMarker>>() {
            @Override
            public void onResponse(Call<Set<RestMapMarker>> call, Response<Set<RestMapMarker>> r) {
                response.accept(r.body());
            }

            @Override
            public void onFailure(Call<Set<RestMapMarker>> call, Throwable t) {
                failure.accept(t);
            }
        });
    }

    public static void getMarkersByUser(String id, Consumer<Set<RestMapMarker>> response, Consumer<Throwable> failure) {
        getClient().getMarkersByUser(key, id).enqueue(new Callback<Set<RestMapMarker>>() {
            @Override
            public void onResponse(Call<Set<RestMapMarker>> call, Response<Set<RestMapMarker>> r) {
                response.accept(r.body());
            }

            @Override
            public void onFailure(Call<Set<RestMapMarker>> call, Throwable t) {
                failure.accept(t);
            }
        });
    }

    public static void getMarkersByThisUser(Consumer<Set<RestMapMarker>> response, Consumer<Throwable> failure) {
        getMarkersByUser(MapsActivity.userId, response, failure);
    }

    public static void createMarker(RestMapMarker marker, Consumer<Boolean> response, Consumer<Throwable> failure) {
        getClient().createMarker(key, marker).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> r) {
                response.accept(r.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                failure.accept(t);
            }
        });
    }

    public static void removeMarker(RestMapMarker marker, Consumer<Boolean> response, Consumer<Throwable> failure) {
        getClient().removeMarker(key, marker).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> r) {
                response.accept(r.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                failure.accept(t);
            }
        });
    }

    public static void setMarkersForUser(String id, Set<RestMapMarker> markers, Consumer<Set<RestMapMarker>> response, Consumer<Throwable> failure) {
        getClient().setMarkersForUser(key, id, markers).enqueue(new Callback<Set<RestMapMarker>>() {
            @Override
            public void onResponse(Call<Set<RestMapMarker>> call, Response<Set<RestMapMarker>> r) {
                response.accept(r.body());
            }

            @Override
            public void onFailure(Call<Set<RestMapMarker>> call, Throwable t) {
                failure.accept(t);
            }
        });
    }

    public static void setMarkersForThisUser(Set<RestMapMarker> markers, Consumer<Set<RestMapMarker>> response, Consumer<Throwable> failure) {
        setMarkersForUser(MapsActivity.userId, markers, response, failure);
    }

}
