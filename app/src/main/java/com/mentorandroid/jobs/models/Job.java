package com.mentorandroid.jobs.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by brunodelhferreira on 20/01/18.
 */

@SuppressWarnings("serial")
public class Job implements Serializable{
    private String id;
    private String company_id;
    private String title;
    private String location;
    @SerializedName("job_type")
    private String jobType;
    private String skills;
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    private Company company;


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
