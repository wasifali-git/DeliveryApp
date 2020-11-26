package com.developer.deliveryapp.adapters;

/*

 Created by Wasif Ali for EMTEN

 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.deliveryapp.DetailsActivity;
import com.developer.deliveryapp.R;
import com.developer.deliveryapp.ScanActivity;
import com.developer.deliveryapp.helpers.Utils;
import com.developer.deliveryapp.modals.ParcelsModal;
import com.huawei.hms.ml.scan.HmsScan;

import java.util.ArrayList;
import java.util.Locale;

public class ParcelsAdapter extends RecyclerView.Adapter<ParcelsAdapter.ViewHolder> {

    ArrayList<ParcelsModal> dataAdapterList;
    Activity activity;
    public static final int SCAN_START_CODE = 001;
    private static final int SCAN_CODE = 002;
    private CallbackInterface mCallback;

    public interface CallbackInterface{

        /**
         * Callback invoked when clicked
         * @param text - the text to pass back
         */
        void onHandleSelection(String text);
    }

    public ParcelsAdapter(Activity activity, ArrayList<ParcelsModal> feedItemList) {
        this.dataAdapterList = feedItemList;
        this.activity = activity;
        mCallback = (CallbackInterface) activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_parcels, null);
        return new ParcelsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        final ParcelsModal feedItem = dataAdapterList.get(i);

        holder.serialNo.setText(String.valueOf(i+1));
        setTextOrHideView(holder.parcelId, Utils.makeSectionOfTextBold("Parcel ID : " + feedItem.parcel_id, "Parcel ID : "), feedItem.parcel_id);
        setTextOrHideView(holder.parcelName, Utils.makeSectionOfTextBold("Parcel Name : " + feedItem.parcel_dilvery_name, "Parcel Name : "), feedItem.parcel_dilvery_name);
        setTextOrHideView(holder.parcelContact, Utils.makeSectionOfTextBold("Parcel Contact : " + feedItem.parcel_dilvery_contact, "Parcel Contact : "), feedItem.parcel_dilvery_contact);
        setTextOrHideView(holder.parcelLocation, Utils.makeSectionOfTextBold("Parcel Address : " + feedItem.parcel_dilvery_location, "Parcel Address : "), feedItem.parcel_dilvery_location);
        setTextOrHideView(holder.parcelStatus, Utils.makeSectionOfTextBold("Parcel Status : " + feedItem.parcel_dilvery_status, "Parcel Status : "), feedItem.parcel_dilvery_status);

        if(feedItem.parcel_dilvery_status.equals("Delivered")){
            holder.cameraBtn.setVisibility(View.INVISIBLE);
            holder.parcelStatus.setTextColor(Color.parseColor("#08870D"));
        }else{
            holder.cameraBtn.setVisibility(View.VISIBLE);
            holder.parcelStatus.setTextColor(Color.parseColor("#A81123"));
        }

        holder.locationBtn.setOnClickListener(v -> {
            //Sending Data to Activity
            Bundle bundle = new Bundle();
            bundle.putString("serial", String.valueOf(i+1));
            bundle.putString("id", feedItem.parcel_id);
            bundle.putString("name", feedItem.parcel_dilvery_name);
            bundle.putString("contact", feedItem.parcel_dilvery_contact);
            bundle.putString("location", feedItem.parcel_dilvery_location);
            bundle.putString("status", feedItem.parcel_dilvery_status);

            Intent myIntent = new Intent(activity, DetailsActivity.class);
            myIntent.putExtras(bundle);
            activity.startActivity(myIntent);
        });

        holder.cameraBtn.setOnClickListener(v -> {
            //For Camera Activity
            if(mCallback != null){
                mCallback.onHandleSelection(feedItem.parcel_id);
            }
        });
        }

    public void setTextOrHideView(TextView textView, SpannableStringBuilder spanableText, String data) {
        if (data != null && data.length() > 0)
            textView.setText(spanableText);
        else
            textView.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return (null != dataAdapterList ? dataAdapterList.size() : 0);
    }

    public ArrayList<ParcelsModal> getDataAdapterList() {
        return dataAdapterList == null ? new ArrayList<>() : dataAdapterList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView serialNo, parcelId,parcelName,parcelContact,parcelLocation,parcelStatus;
        protected ImageView cameraBtn,locationBtn;

        public ViewHolder(View view) {
            super(view);
            serialNo = itemView.findViewById(R.id.serial_no);
            parcelId = itemView.findViewById(R.id.parcelid);
            parcelName = itemView.findViewById(R.id.parcelname);
            parcelContact = itemView.findViewById(R.id.parcelcontact);
            parcelLocation = itemView.findViewById(R.id.parcellocation);
            parcelStatus = itemView.findViewById(R.id.parcelstatus);
            cameraBtn = itemView.findViewById(R.id.cameraBtn);
            locationBtn = itemView.findViewById(R.id.locationBtn);

        }
}
}
