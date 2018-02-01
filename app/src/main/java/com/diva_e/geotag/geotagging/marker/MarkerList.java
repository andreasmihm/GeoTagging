package com.diva_e.geotag.geotagging.marker;

import com.diva_e.geotag.geotagging.rest.RestMapMarker;
import com.google.android.gms.maps.GoogleMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jjan on 22.01.2018.
 */

public class MarkerList extends HashSet<MapMarker> {

    public static MarkerList fromRestMarkers(Collection<? extends RestMapMarker> markers) {
        MarkerList markerList = new MarkerList();
        if (markers != null) {
            markers.forEach(marker -> {
                if (marker != null) markerList.add(marker.toMapMarker());
            });
        }
        return markerList;
    }

    public MarkerList(MapMarker... marker) {
        addAll(marker);
    }

    public void addAll(MapMarker... marker) {
        addAll(Arrays.asList(marker));
    }

    public void addToMap(final GoogleMap googleMap) {
        this.stream().filter(marker -> marker != null).forEach(marker -> marker.addToMap(googleMap));
    }

//    @Override
//    public boolean add(MapMarker marker) {
//        if(stream().filter(mapMarker -> mapMarker.getPosition().equals(marker.getPosition())).collect(Collectors.toList()).size() > 0)
//            return false;
//        return super.add(marker);
//    }

    public Set<String> getStrings() {
        return this.stream().filter(marker -> marker != null).map(marker -> marker.toString()).collect(Collectors.toSet());
    }

    public Set<RestMapMarker> getRestMapMarkers(String userid) {
        return this.stream().filter(marker -> marker != null).map(marker -> marker.getRestMapMarker(userid)).collect(Collectors.toSet());
    }
}
