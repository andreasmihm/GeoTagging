package com.diva_e.geotag.geotagging.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.diva_e.geotag.geotagging.MapsActivity;
import com.diva_e.geotag.geotagging.R;
import com.diva_e.geotag.geotagging.marker.MapMarker;

/**
 * Created by jjan on 25.01.2018.
 */

public class EditMarkerMenu extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_marker);
        findViewById(R.id.button).setOnClickListener(getClickListener());
        findViewById(R.id.button2).setOnClickListener(getClickListener2());
        findViewById(R.id.button3).setOnClickListener(getClickListener3());
        findViewById(R.id.button4).setOnClickListener(getClickListener4());
        findViewById(R.id.button5).setOnClickListener(getClickListener5());
        findViewById(R.id.button6).setOnClickListener(getClickListener6());
    }

    public View.OnClickListener getClickListener() {
        return v -> {
            Intent intent = new Intent(this, EditMarkerTitle.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener getClickListener2() {
        return v -> {
            Intent intent = new Intent(this, EditMarkerSnippet.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener getClickListener3() {
        return v -> {
            MapMarker mapMarker = EditMarker.getEditMarker().edit();
            MapsActivity.getInstance().addMarker(mapMarker);
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener getClickListener4() {
        return v -> {
            if (EditMarker.getEditMarker().getMarker() != null)
                MapsActivity.getInstance().removeMarker(EditMarker.getEditMarker().getMarker());
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Der Marker wurde erfolgreich gelöscht", Toast.LENGTH_SHORT).show();
        };
    }

    public View.OnClickListener getClickListener5() {
        return v -> {
            Intent intent = new Intent(this, EditMarkerLink.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener getClickListener6() {
        return v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        };
    }

}
