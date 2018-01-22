package com.mentorandroid.jobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mentorandroid.jobs.models.Job;

public class JobDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Intent i = getIntent();
        Job job = new Job();
        job = ((Job)i.getSerializableExtra("job"));

        TextView textViewTitle = (TextView) findViewById(R.id.job_title);
        textViewTitle.setText(job.getTitle());
        TextView textViewDescription = (TextView) findViewById(R.id.job_description);
        textViewDescription.setText((job.getDescription()));

        TextView textViewLocation = (TextView) findViewById(R.id.job_location);
        textViewLocation.setText((job.getLocation()));

        TextView textViewJobType = (TextView) findViewById(R.id.job_type);
        textViewJobType.setText((job.getJobType()));

        TextView textViewJobSkills = (TextView) findViewById(R.id.job_skills);
        textViewJobSkills.setText((job.getSkills()));

        TextView textViewCompanyName = (TextView) findViewById(R.id.company_name);
        textViewCompanyName.setText((job.getCompany().getCompanyName()));

        TextView textViewCompanyDescription = (TextView) findViewById(R.id.company_description);
        textViewCompanyDescription.setText((job.getCompany().getCompanyDescription()));

    }
}
