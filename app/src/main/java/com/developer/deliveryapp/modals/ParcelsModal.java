package com.developer.deliveryapp.modals;

public class ParcelsModal {

    public String parcel_id;
    public String parcel_dilvery_name;
    public String parcel_dilvery_contact;
    public String parcel_dilvery_location;
    public String parcel_dilvery_status;

    public ParcelsModal(String parcel_id, String parcel_dilvery_name, String parcel_dilvery_contact, String parcel_dilvery_location,String parcel_dilvery_status){

        this.parcel_id = parcel_id;
        this.parcel_dilvery_name = parcel_dilvery_name;
        this.parcel_dilvery_contact = parcel_dilvery_contact;
        this.parcel_dilvery_location = parcel_dilvery_location;
        this.parcel_dilvery_status = parcel_dilvery_status;

    }

    public String getParcel_id() {
        return parcel_id;
    }

    public void setParcel_id(String parcel_id) {
        this.parcel_id = parcel_id;
    }

    public String getParcel_dilvery_name() {
        return parcel_dilvery_name;
    }

    public void setParcel_dilvery_name(String parcel_dilvery_name) {
        this.parcel_dilvery_name = parcel_dilvery_name;
    }

    public String getParcel_dilvery_contact() {
        return parcel_dilvery_contact;
    }

    public void setParcel_dilvery_contact(String parcel_dilvery_contact) {
        this.parcel_dilvery_contact = parcel_dilvery_contact;
    }

    public String getParcel_dilvery_location() {
        return parcel_dilvery_location;
    }

    public void setParcel_dilvery_location(String parcel_dilvery_location) {
        this.parcel_dilvery_location = parcel_dilvery_location;
    }
    public String getParcel_dilvery_status() {
        return parcel_dilvery_status;
    }

    public void setParcel_dilvery_status(String parcel_dilvery_status) {
        this.parcel_dilvery_status = parcel_dilvery_status;
    }
}
