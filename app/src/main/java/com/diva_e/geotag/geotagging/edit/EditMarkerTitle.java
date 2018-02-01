package com.diva_e.geotag.geotagging.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.diva_e.geotag.geotagging.R;

/**
 * Created by jjan on 25.01.2018.
 */

public class EditMarkerTitle extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_marker_title);
        EditText editText = findViewById(R.id.editText);
        if(EditMarker.getEditMarker().getTitle() != null && EditMarker.getEditMarker().getTitle().length() > 0){
            editText.setText(EditMarker.getEditMarker().getTitle());
        }
        findViewById(R.id.button).setOnClickListener(getClickListener());
        findViewById(R.id.button2).setOnClickListener(getClickListener2());
    }

    public View.OnClickListener getClickListener(){
        return v -> {
            EditText text = findViewById(R.id.editText);
            if(text.getText().length() > 0){
                EditMarker.getEditMarker().setTitle(text.getText().toString().trim());
                Intent intent = new Intent(this, EditMarkerMenu.class);
                startActivity(intent);
            }
        };
    }

    public View.OnClickListener getClickListener2(){
        return  v -> {
            Intent intent = new Intent(this, EditMarkerMenu.class);
            startActivity(intent);
        };
    }

}
