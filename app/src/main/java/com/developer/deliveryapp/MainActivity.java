package com.developer.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.developer.deliveryapp.adapters.ParcelsAdapter;
import com.developer.deliveryapp.modals.ParcelsModal;
import com.huawei.hms.ml.scan.HmsScan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ParcelsAdapter.CallbackInterface {

    List<ParcelsModal> parcelsModalList;
    RecyclerView mRecyclerView;
    ParcelsAdapter parcelsAdapter;
    public static final int SCAN_START_CODE = 001;
    private static final int SCAN_CODE = 002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parcelsModalList = new ArrayList<ParcelsModal>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_parcels);
        mRecyclerView.setLayoutManager(layoutManager);

        // Add Data to list
        parcelsModalList.add(new ParcelsModal("00001", "John Atkinson", "123456789", "40.716124, -74.001884","Delivered"));
        parcelsModalList.add(new ParcelsModal("00002", "Mark Bennett", "123456789", "40.712188, -73.997378","Delivered"));
        parcelsModalList.add(new ParcelsModal("00003", "Jack Brooks", "123456789", "40.717588, -74.010403","Not Delivered"));
        parcelsModalList.add(new ParcelsModal("00004", "Alice John", "123456789", "40.715188, -73.995078","Not Delivered"));
        parcelsModalList.add(new ParcelsModal("00005", "Julie Berk", "123456789", "40.713588, -74.013303","Not Delivered"));

        parcelsAdapter = new ParcelsAdapter(MainActivity.this, (ArrayList<ParcelsModal>) parcelsModalList);
        mRecyclerView.setAdapter(parcelsAdapter);
        parcelsAdapter.notifyDataSetChanged();
   }

   //To Handle Callback
    @Override
    public void onHandleSelection(String text) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                SCAN_START_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == SCAN_START_CODE) {
            //start your activity for scanning barcode
            Intent newIntent = new Intent(this, ScanActivity.class);
            this.startActivityForResult(newIntent, SCAN_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        //Get Code from QR
        if (requestCode == SCAN_CODE) {
            HmsScan hmsScan = data.getParcelableExtra(ScanActivity.SCAN_RESULT);
            if (hmsScan != null && !TextUtils.isEmpty(hmsScan.getOriginalValue())) {
                processData(hmsScan.getOriginalValue());
            }
        }
    }

    //To Handle Adapter callback data in Activity
    private void processData(String value){
        for (ParcelsModal modal : parcelsAdapter.getDataAdapterList()) {
            //Update date at your Server Here
            if(modal.getParcel_id().equals(value)){
                modal.setParcel_dilvery_status("Delivered");
                Toast.makeText(this, "Parcel Delivered", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        parcelsAdapter.notifyDataSetChanged();
    }
}