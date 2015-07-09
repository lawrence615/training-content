package com.mobidev.httprequest.model;

/**
 * Created by lawrence on 7/9/15.
 */
public class University {

    private int id;
    private String university_name;
    private String desc;


    public University(int id, String university_name, String desc){
        this.id = id;
        this.university_name = university_name;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
