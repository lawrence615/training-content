package com.mobidev.spinerexample;

/**
 * Created by lawrence on 5/6/15.
 */
public class FilterSpinner {
    private String version_name;
    private String version;


    public FilterSpinner(String version_name, String version) {
        this.version_name = version_name;
        this.version = version;
    }

    public String getVersionName() {
        return version_name;
    }

    public void setVersionName(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
