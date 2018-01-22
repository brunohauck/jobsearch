package com.mentorandroid.jobs.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunodelhferreira on 20/01/18.
 */

public class Jobs {
    @SerializedName("jobs")
    private List<Job> jobsList = new ArrayList<Job>();

    public List<Job> getJobsList() {
        return jobsList;
    }

    public void setJobsList(List<Job> postsList) {
        this.jobsList = postsList;
    }
}
