package com.diva_e.geotag.geotagging.rest;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by jjan on 26.01.2018.
 */

public interface MapMarkerClient {

    @GET("/markers")
    Call<Set<RestMapMarker>> getMarkers(@Header("secret-key") String key);

    @GET("/markers/users/{userId}")
    Call<Set<RestMapMarker>> getMarkersByUser(@Header("secret-key") String key, @Path("userId") String id);

    @POST("/markers")
    Call<Boolean> createMarker(@Header("secret-key") String key, @Body RestMapMarker marker);

    @HTTP(method = "DELETE", path = "/markers", hasBody = true)
    Call<Boolean> removeMarker(@Header("secret-key") String key, @Body RestMapMarker marker);

    @PUT("markers/users/{userId}")
    Call<Set<RestMapMarker>> setMarkersForUser(@Header("secret-key") String key, @Path("userId") String id, @Body Set<RestMapMarker> markers);

}
