package com.diva_e.geotag.geotagging.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.diva_e.geotag.geotagging.MapsActivity;
import com.diva_e.geotag.geotagging.R;
import com.diva_e.geotag.geotagging.marker.MapMarker;

/**
 * Created by jjan on 24.01.2018.
 */

public class MarkerCreateSnippet extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_create_snippet);
    }

    @Override
    public void onClick(View v) {
        EditText text = findViewById(R.id.editText);
//        if(text.getText().length() > 0){
            MarkerCreate.getMarkerCreate().setText(text.getText().toString().trim());

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

            MapMarker marker = MarkerCreate.getMarkerCreate().createMarker();


            if(marker != null){
                MapsActivity.getInstance().addMarker(marker);
//                Toast.makeText(this, "Finished", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Der Standort konnte nicht erstellt werden", Toast.LENGTH_SHORT).show();
            }
//        }
    }
}
