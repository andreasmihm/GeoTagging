package com.diva_e.geotag.geotagging;

import com.diva_e.geotag.geotagging.marker.MapMarker;
import com.diva_e.geotag.geotagging.marker.MarkerList;
import com.google.android.gms.maps.model.LatLng;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jjan on 24.01.2018.
 */

public class LocalMarker {

    public static MarkerList markerList = new MarkerList();

    public static Set<LatLng> deleted = new HashSet<>();


    public static boolean addAll(Collection<? extends MapMarker> c){
        return markerList.addAll(c.stream().filter(mapMarker -> !deleted.contains(mapMarker.getPosition())).collect(Collectors.toList()));
    }
}
