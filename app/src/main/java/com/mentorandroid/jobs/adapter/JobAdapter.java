package com.mentorandroid.jobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mentorandroid.jobs.JobDetailActivity;
import com.mentorandroid.jobs.R;
import com.mentorandroid.jobs.models.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunodelhferreira on 28/12/17.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.Holderview>{

    private List<Job> jobList;
    private Context context;

    public JobAdapter(List<Job> productlist, Context context) {
        this.jobList = productlist;
        this.context = context;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.customitem,parent,false);

        return new Holderview(layout);
    }

    @Override
    public void onBindViewHolder(Holderview holder, final int position) {

        holder.title.setText(jobList.get(position).getTitle());
        holder.description.setText(jobList.get(position).getDescription());
        holder.company.setText(jobList.get(position).getCompany().getCompanyName());
        holder.jobLocation.setText(jobList.get(position).getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "click on " + jobList.get(position).getTitle(),
                //        Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, JobDetailActivity.class);
                i.putExtra("job", jobList.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public void setfilter(List<Job> filtertopic)
    {
        jobList =new ArrayList<>();
        jobList.addAll(filtertopic);
        notifyDataSetChanged();
    }
    class Holderview extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView description;
        TextView jobLocation;
        TextView company;

        Holderview(View itemview)
        {
            super(itemview);
            title = (TextView) itemView.findViewById(R.id.job_title);
            description= (TextView) itemview.findViewById(R.id.job_description);
            jobLocation = (TextView) itemView.findViewById(R.id.job_location);
            company = (TextView) itemView.findViewById(R.id.company_name);


        }
    }
}

