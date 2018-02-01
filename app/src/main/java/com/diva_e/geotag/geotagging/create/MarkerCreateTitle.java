package com.diva_e.geotag.geotagging.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.diva_e.geotag.geotagging.R;

/**
 * Created by jjan on 24.01.2018.
 */

public class MarkerCreateTitle extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_create_title);
    }

    @Override
    public void onClick(View v) {
        EditText text = findViewById(R.id.editText);
        if(text.getText().length() > 0){
            MarkerCreate.getMarkerCreate().setTitle(text.getText().toString().trim());
            Intent intent = new Intent(this, MarkerCreateSnippet.class);
            startActivity(intent);
        }
    }


}
