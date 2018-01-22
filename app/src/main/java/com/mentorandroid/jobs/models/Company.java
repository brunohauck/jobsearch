package com.mentorandroid.jobs.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by brunodelhferreira on 21/01/18.
 */

@SuppressWarnings("serial")
public class Company implements Serializable {
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("company_location")
    private String companyLocation;
    @SerializedName("company_description")
    private String companyDescription;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
