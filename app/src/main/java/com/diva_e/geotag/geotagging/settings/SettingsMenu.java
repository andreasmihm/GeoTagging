package com.diva_e.geotag.geotagging.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.diva_e.geotag.geotagging.MapsActivity;
import com.diva_e.geotag.geotagging.R;

/**
 * Created by jjan on 29.01.2018.
 */

public class SettingsMenu extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        findViewById(R.id.button).setOnClickListener(getClickListener());
        findViewById(R.id.imageButton).setOnClickListener(getClickListener2());
    }

    public View.OnClickListener getClickListener() {
        return v -> {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        };
    }

    public View.OnClickListener getClickListener2() {
        return v -> {
            Intent intent = new Intent(this, SettingsMap.class);
            startActivity(intent);
        };
    }

}
