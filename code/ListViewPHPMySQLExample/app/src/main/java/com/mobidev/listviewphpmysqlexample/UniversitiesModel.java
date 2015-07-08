package com.mobidev.listviewphpmysqlexample;

/**
 * Created by lawrence on 4/20/15.
 */
public class UniversitiesModel {
    private int id;
    private String university_name;
    private String description;
    private String phone_no;
    private String website;
    private String address;
    private String latitude;
    private String longitude;


    public UniversitiesModel(int id, String university_name, String description, String phone_no, String website, String address, String latitude, String longitude) {
        this.id = id;
        this.university_name = university_name;
        this.description = description;
        this.phone_no = phone_no;
        this.website = website;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniversityName() {
        return university_name;
    }

    public void setUniversityName(String university_name) {
        this.university_name = university_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNo() {
        return phone_no;
    }

    public void setPhoneNo(String phoneNo) {
        this.phone_no = phone_no;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
