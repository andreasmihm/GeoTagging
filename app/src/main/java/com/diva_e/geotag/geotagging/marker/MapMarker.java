package com.diva_e.geotag.geotagging.marker;

import com.diva_e.geotag.geotagging.Spacer;
import com.diva_e.geotag.geotagging.rest.RestMapMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jjan on 22.01.2018.
 */

public class MapMarker {

    private static final String HTTPS = "https://";
    private static final String HTTP = "http://";
    private static final String WWW = "www.";

    private static Map<Marker, MapMarker> marker = new HashMap<>();

    /**
     * @param marker
     * @return
     */

    public static MapMarker fromMarker(Marker marker) {
        return MapMarker.marker.containsKey(marker) ? MapMarker.marker.get(marker) : new MapMarker(marker.getPosition(), marker.getTitle(), marker.getSnippet());
    }

    public static List<MapMarker> getMarker(LatLng latLng) {
        return new ArrayList<>(marker.values().stream().filter(mapMarker -> mapMarker.getPosition().equals(latLng)).collect(Collectors.toList()));
    }

    public static MapMarker fromString(String string) {
        String[] split = string.split(MapMarker.split);
        if (split.length >= 3) {
            String s_position = split[0];
            String title = split[1];
            String text = split[2].substring(1);

            String[] split_position = s_position.split(";");
            if (split_position.length == 2) {
                try {
                    double latitude = Double.parseDouble(split_position[0]);
                    double longitude = Double.parseDouble(split_position[1]);
                    LatLng position = new LatLng(latitude, longitude);
                    MapMarker mapMarker = new MapMarker(position, title, text.split(Spacer.NEW_LINE.toString()));
                    if (split.length >= 4) {
                        mapMarker.setLink(split[3]);
                    }
                    return mapMarker;
                } catch (NumberFormatException e) {
                }
            }
        }
        return null;
    }


    private static String split = Spacer.SPLIT.toString();

    LatLng position;
    String title;
    String text;

    String link;

    public MapMarker(LatLng position, String title, String... text) {
        this.position = position;
        this.title = title;
        this.text = "";
        for (String s : text) {
            if (this.text.length() > 0) {
                this.text += "\n" + s;
            } else {
                this.text += s;
            }
        }
    }

    public void addToMap(GoogleMap googleMap) {
//        if (marker.containsValue(this)) {
//            return false;
//        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title(title);
        markerOptions.snippet(text);
        marker.put(googleMap.addMarker(markerOptions), this);
    }

    public String toString() {
        String s_position = position.latitude + ";" + position.longitude;
        String s_title = title;
        String s_text = " " + text.replace("\n", Spacer.NEW_LINE.toString());
        String string = s_position + split + s_title + split + s_text;
        if (link != null && link.length() > 0) {
            string += split + link;
        }
        return string;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public MapMarker setLink(String link) {
        if(link != null) {
            if (!link.startsWith(HTTP) && !link.startsWith(HTTPS)) {
                if (!link.toLowerCase().startsWith(WWW.toLowerCase())) {
                    link = WWW + link;
                }
                link = HTTP + link;
            }
            if (!link.endsWith("/")) {
                link += "/";
            }
            this.link = link;
        }
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MapMarker)) {
            return false;
        }
        MapMarker marker = (MapMarker) obj;
        return position.equals(marker.getPosition());
    }

    public RestMapMarker getRestMapMarker(String userid) {
        return new RestMapMarker(title, text, link, position.latitude, position.longitude, userid);
    }

}
