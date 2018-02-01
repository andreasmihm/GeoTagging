package com.diva_e.geotag.geotagging.info;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.diva_e.geotag.geotagging.R;
import com.diva_e.geotag.geotagging.marker.MapMarker;

/**
 * Created by jjan on 25.01.2018.
 */

public class MarkerInformation extends Activity {

    public static void openMarkerInformation(Context context, MapMarker marker){
        if(marker != null && marker.getLink() != null) {
            context.startActivity(new Intent(context, MarkerInformation.class));
            mapMarker = marker;
        }
    }

    private static MapMarker mapMarker;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker_info);
        webView = findViewById(R.id.webView);
        if(mapMarker != null) {
            webView.setWebViewClient(new WebViewClient());
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setDatabaseEnabled(true);
            if(mapMarker.getLink() != null){
                webView.loadUrl(mapMarker.getLink());
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
