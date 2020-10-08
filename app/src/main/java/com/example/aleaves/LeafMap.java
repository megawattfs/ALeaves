package com.example.aleaves;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;

import java.util.ArrayList;

public class LeafMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LeafCapture> leafCaptureList;
    private RemoteFindIterable<LeafCapture> documentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.d("mapload","Are we getting to this point?");//Yes

        leafCaptureList = new ArrayList<>();;
        documentList = MainActivity.all_leaves.find();//Returns all leaves in the database
        documentList.forEach( (element) -> {
            leafCaptureList.add( element );
            Log.d("arraylist","element added");});
        /*while(leafCaptureList.size() == 0) {
        }*/
        Log.d("viewleaves", Integer.valueOf(leafCaptureList.size()).toString());

        String googleError = null;
        switch (MapsInitializer.initialize(getApplicationContext())) { // or GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx)
            case ConnectionResult.SERVICE_MISSING: googleError = "Failed to connect to google mapping service: Service Missing"; break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: googleError = "Failed to connect to google mapping service: Google Play services out of date. Service Version Update Required"; break;
            case ConnectionResult.SERVICE_DISABLED: googleError = "Failed to connect to google mapping service: Service Disabled. Possibly app is missing API key or is not a signed app permitted to use API key."; break;
            case ConnectionResult.SERVICE_INVALID: googleError = "Failed to connect to google mapping service: Service Invalid. Possibly app is missing API key or is not a signed app permitted to use API key."; break;
            //case ConnectionResult.DATE_INVALID: googleError = "Failed to connect to google mapping service: Date Invalid"; break;
        }
        if (googleError != null)
            Log.d("MyApp", googleError);

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
        Log.d("leafmap","map ready");
        // Add a marker in Athens and move the camera
        LatLng athens = new LatLng(33.9511697, -83.367307);

        for(LeafCapture leafCapture : leafCaptureList) {
            String leafCaptureLocation = leafCapture.getLocation();
            LatLng leafCaptureLatLng = new LatLng(Double.parseDouble(leafCaptureLocation.substring(0,7)), Double.parseDouble(leafCaptureLocation.substring(7)));
        }
        mMap.addMarker(new MarkerOptions().position(athens).title("Marker in Athens"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(athens, 12));
    }
}
