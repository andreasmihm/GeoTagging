package com.diva_e.geotag.geotagging;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diva_e.geotag.geotagging.create.MarkerCreate;
import com.diva_e.geotag.geotagging.create.MarkerCreateTitle;
import com.diva_e.geotag.geotagging.edit.EditMarker;
import com.diva_e.geotag.geotagging.edit.EditMarkerMenu;
import com.diva_e.geotag.geotagging.info.MarkerInformation;
import com.diva_e.geotag.geotagging.marker.MapMarker;
import com.diva_e.geotag.geotagging.marker.MarkerList;
import com.diva_e.geotag.geotagging.rest.RestMarkerUtils;
import com.diva_e.geotag.geotagging.settings.MapSettings;
import com.diva_e.geotag.geotagging.settings.SettingsMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private static MapsActivity instance;

    public static MapsActivity getInstance() {
        return instance;
    }

    public static String userId;

    private GoogleMap mMap;
    private MarkerList markerList;

    private Marker selcted;

    private MapMarker delete;
    private boolean undo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(getClickListener());

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(getClickListener2());

        ImageButton imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(getClickListener3());

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PackageManager.PERMISSION_GRANTED);

        userId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        setSettings(mMap);
        markerList = registerMarker();
        markerList.addToMap(mMap);
        LocalMarker.markerList.addToMap(mMap);

        mMap.setMapType(MapSettings.getSettings().getType());

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
//                RelativeLayout layout = new RelativeLayout(getApplicationContext());

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context mContext = getApplicationContext();

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);

                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        mMap.setOnInfoWindowClickListener(marker ->
                MarkerInformation.openMarkerInformation(this, MapMarker.fromMarker(marker))
        );

        mMap.setOnMapClickListener(getMapClickListener(mMap, (Button) findViewById(R.id.button)));
//        mMap.setOnCameraMoveListener(getCameraMoveListener());
        mMap.setOnMarkerClickListener(getMarkerClickListener(mMap));


    }

    @Override
    public void onBackPressed() {
        if (undo && delete != null) {
            addMarker(delete);
            undo = false;
            delete = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences preferences = getSharedPreferences("Marker", 0);
//        MarkerList markerList = new MarkerList();
//        for (String string : preferences.getStringSet
//                ("marker", new HashSet<String>())) {
//            markerList.add(MapMarker.fromString(string));
//        }
//        String s = "";
//        for (MapMarker mapMarker : markerList) {
//            s += mapMarker + "\n";
//        }
        if (mMap != null) {
            mMap.clear();
        }
        LocalMarker.deleted.clear();
        RestMarkerUtils.getMarkersByThisUser(markers -> {
            LocalMarker.addAll(MarkerList.fromRestMarkers(markers));
            if (mMap != null) {
                LocalMarker.markerList.addToMap(mMap);
                registerMarker().addToMap(mMap);
            }
        }, throwable -> Toast.makeText(this, "Es konnte keine Verbindung zum Server hergestellt werden. Bitte überprüfe deine Internetverbindung.", Toast.LENGTH_LONG).show());

    }

    @Override
    protected void onStop() {
        super.onStop();
//        SharedPreferences preferences = getSharedPreferences("Marker", 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        Set<String> marker = LocalMarker.markerList.getStrings();
//        editor.putStringSet("marker", marker);
//        String s = "";
//        for (String mapMarker : marker) {
//            s += mapMarker + "\n";
//        }
//        editor.commit();
        findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
        RestMarkerUtils.setMarkersForThisUser(LocalMarker.markerList.getRestMapMarkers(userId), markers -> {
        }, throwable -> Toast.makeText(this, "Es konnte keine Verbindung zum Server hergestellt werden. Bitte überprüfe deine Internetverbindung.", Toast.LENGTH_LONG).show());
        LocalMarker.markerList.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length == 1) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "GeoTagging wurde ohne Standortberechtigungen gestartet!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void setSettings(GoogleMap googleMap) {
        googleMap.getUiSettings().setCompassEnabled(false);
    }

    public MarkerList registerMarker() {
//        MapMarker m1 = new MapMarker(new LatLng(50.926034, 11.594063), "Jena", "Das ist Jena", "Jena ist eine Stadt").setLink("google.de");
//        MapMarker m2 = new MapMarker(new LatLng(52.496482, 13.485131), "Berlin", "Das ist Berlin", "Berlin ist auch eine Stadt");

        MarkerList markerList = new MarkerList(
//                m1, m2
        );
        return markerList;
    }


    private boolean addLocation = false;

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update((Button) view);
            }
        };
    }

    public View.OnClickListener getClickListener2() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selcted != null) {
                    MapMarker mapMarker = MapMarker.fromMarker(selcted);
                    if (LocalMarker.markerList.contains(mapMarker)) {
//                        selcted.remove();
//                        LocalMarker.markerList.remove(mapMarker);
//                        Toast.makeText(MapsActivity.this, "Der Marker wurde gelöscht", Toast.LENGTH_SHORT).show();
//                        selcted = null;
//                        findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
//                        delete = mapMarker;
//                        undo = true;
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                if (delete == mapMarker) {
//                                    delete = null;
//                                    undo = false;
//                                }
//                            }
//                        }, 5000l);
                        EditMarker.newEditMarker().setMapMarker(mapMarker);
                        findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
                        Intent intent = new Intent(MapsActivity.this, EditMarkerMenu.class);
                        startActivity(intent);
                    } else {
//                        Toast.makeText(MapsActivity.this, "Der Marker konnte nicht gelöscht werden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    public View.OnClickListener getClickListener3() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
                Intent intent = new Intent(MapsActivity.this, SettingsMenu.class);
                startActivity(intent);
            }
        };
    }

    public GoogleMap.OnMapClickListener getMapClickListener(final GoogleMap googleMap, final Button button) {
        return new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selcted = null;
                findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
                if (addLocation) {
                    update(button);
                    MarkerCreate.getMarkerCreate().setPosition(latLng);
                    Intent intent = new Intent(MapsActivity.this, MarkerCreateTitle.class);
                    startActivity(intent);
                }
            }
        };
    }

    public GoogleMap.OnCameraMoveListener getCameraMoveListener() {
        return new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                selcted = null;
                findViewById(R.id.button2).setVisibility(Button.INVISIBLE);
            }
        };
    }

    public GoogleMap.OnMarkerClickListener getMarkerClickListener(GoogleMap googleMap) {
        return new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                selcted = marker;
                MapMarker mapMarker = MapMarker.fromMarker(marker);
                if (LocalMarker.markerList.contains(mapMarker)) {
                    findViewById(R.id.button2).setVisibility(Button.VISIBLE);
                }
                return false;
            }

        };
    }

    public void update(Button button) {
        addLocation = !addLocation;

        String text = "Standort hinzufügen";

        if (addLocation) {
            text = "Abbrechen";
        }
        button.setText(text);
    }


    public void addMarker(MapMarker marker) {
        marker.addToMap(mMap);
        LocalMarker.markerList.add(marker);
        RestMarkerUtils.createMarker(marker);
    }

    public void removeMarker(MapMarker marker) {
        LocalMarker.markerList.remove(marker);
        LocalMarker.deleted.add(marker.getPosition());
        RestMarkerUtils.removeMarker(marker);
    }

    private boolean isConnectingToInternet(){
        return ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

}
