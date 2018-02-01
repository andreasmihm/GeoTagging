package com.diva_e.geotag.geotagging.edit;

import com.diva_e.geotag.geotagging.marker.MapMarker;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jjan on 25.01.2018.
 */

public class EditMarker {

    private static EditMarker editMarker;

    public static EditMarker getEditMarker() {
        return editMarker != null ? editMarker : newEditMarker();
    }

    public static EditMarker newEditMarker() {
        editMarker = new EditMarker();
        return editMarker;
    }

    MapMarker mapMarker;

    LatLng position;
    String title;
    String text;
    String link;

    private EditMarker() {

    }

    public void setMapMarker(MapMarker mapMarker) {
        this.mapMarker = mapMarker;
        if (position == null) position = mapMarker.getPosition();
        if (title == null) title = mapMarker.getTitle();
        if (text == null) text = mapMarker.getText();
        if (link == null) link = mapMarker.getLink();
    }

    public MapMarker getMarker() {
        return mapMarker;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String... text) {
        this.text = "";
        for (String s : text) {
            if (this.text.length() > 0) {
                this.text += "\n" + s;
            } else {
                this.text += s;
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public MapMarker createMarker() {
        return position != null && title != null && text != null ? new MapMarker(position, title, text) : null;
    }

    public MapMarker edit() {
        if (mapMarker != null) {
            if (position != null) mapMarker.setPosition(position);
            if (title != null) mapMarker.setTitle(title);
            if (text != null) mapMarker.setText(text);
            if(link != null) mapMarker.setLink(link);
            return mapMarker;
        }
        return createMarker();
    }

}
