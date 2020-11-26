package com.developer.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.developer.deliveryapp.helpers.Utils;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.ml.scan.HmsScan;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static LatLng riderLocation = new LatLng(40.706124, -74.00454);
    TextView parcelID,parcelName,parcelContact,parcelLocation,parcelStatus;
    String serial,id,name,contact,location,status;
    Intent intent;
    Button deliver;
    private static final String TAG = "MapActivity";
    private HuaweiMap hMap;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        parcelID =findViewById(R.id.parcelid);
        parcelName =findViewById(R.id.parcelname);
        parcelContact =findViewById(R.id.parcelcontact);
        parcelLocation =findViewById(R.id.parcellocation);
        parcelStatus =findViewById(R.id.parcelstatus);
        intent = getIntent();
        mMapView = findViewById(R.id.mapView);

        setValues();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(this);
        MapsInitializer.setApiKey(config.getString("client/api_key"));
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

    private void setValues() {

        //Get Data from Bundle
        id = intent.getStringExtra("id");
        serial = intent.getStringExtra("serial");
        name = intent.getStringExtra("name");
        contact = intent.getStringExtra("contact");
        location = intent.getStringExtra("location");
        status = intent.getStringExtra("status");

        System.out.println("Status :" +status);
        //Set date from Bundle into Views
        setTextOrHideView(parcelID, Utils.makeSectionOfTextBold("Parcel ID : " + id, "Parcel ID : "), id);
        setTextOrHideView(parcelName, Utils.makeSectionOfTextBold("Parcel Name : " + name, "Parcel Name : "), name);
        setTextOrHideView(parcelContact, Utils.makeSectionOfTextBold("Parcel Contact : " + contact, "Parcel Contact : "), contact);
        setTextOrHideView(parcelLocation, Utils.makeSectionOfTextBold("Parcel Address : " + location, "Parcel Address : "), location);
        setTextOrHideView(parcelStatus, Utils.makeSectionOfTextBold("Parcel Status : " + status, "Parcel Status : "), status);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        //get map instance in a callback method
        Log.d(TAG, "onMapReady: ");
        hMap = map;
        addMarkersOnMap();

    }

    private void addMarkersOnMap() {
        //Strings convert to LatLong for sample
        String[] latLng = location.split(",");
        double latitude = Double.parseDouble(latLng[0]);
        double longitude = Double.parseDouble(latLng[1]);
        LatLng parcelLocation = new LatLng(latitude, longitude);
        //Add Rider Marker
        hMap.addMarker(new MarkerOptions().position(riderLocation).title("Rider").snippet(contact).clusterable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //Add Parcel Delivery Marker
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 14));
        hMap.addMarker(new MarkerOptions().position(parcelLocation).title(name).snippet(contact).clusterable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    public void setTextOrHideView(TextView textView, SpannableStringBuilder spanableText, String data) {
        if (data != null && data.length() > 0)
            textView.setText(spanableText);
        else
            textView.setVisibility(View.GONE);
    }
}