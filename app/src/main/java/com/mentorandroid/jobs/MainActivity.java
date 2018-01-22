package com.mentorandroid.jobs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mentorandroid.jobs.adapter.JobAdapter;
import com.mentorandroid.jobs.models.Job;
import com.mentorandroid.jobs.models.Jobs;
import com.mentorandroid.jobs.service.GenericRequest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Job> listJob = new ArrayList<>();
    RecyclerView mRecyclerView ;
    JobAdapter adapter;
    private ProgressDialog pDialog;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView  = (RecyclerView) findViewById(R.id.listshow);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Jobs");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        GenericRequest<Jobs> myReq = new GenericRequest<Jobs>( "http://forum.idsgeo.com/index.php/job", Jobs.class, createMyReqSuccessListener(), createMyReqErrorListener());
        queue.add(myReq);

    }

    private Response.Listener<Jobs> createMyReqSuccessListener() {
        return new Response.Listener<Jobs>() {
            @Override
            public void onResponse(Jobs response) {
                listJob= response.getJobsList();
                pDialog.dismiss();
                adapter = new JobAdapter(listJob, MainActivity.this);
                mRecyclerView.setAdapter(adapter);
            }
        };
    }
    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                pDialog.dismiss();
                Log.d("DEBUG", error.getMessage().toString());
                Toast.makeText(MainActivity.this, "ERRO", Toast.LENGTH_LONG).show();
            }
        };
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Job> filtermodelist = filter(listJob, newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });

        return true;
    }
    private List<Job> filter(List<Job> pl, String query) {
        query = query.toLowerCase();
        final List<Job> filteredModeList = new ArrayList<>();
        for (Job model : pl) {
            final String text = model.getTitle().toLowerCase();
            if (text.startsWith(query)) {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private List<Job> filter(List<Job> pl, String query, String type) {
        query = query.toLowerCase();
        type = type.toLowerCase();
        final List<Job> filteredModeList = new ArrayList<>();
        if(query.equals("") && type.equals("")){

        }else
        if(query.equals("")) {
            for (Job model : pl) {
                final String text = model.getJobType().toLowerCase();
                if (text.startsWith(type)) {
                    filteredModeList.add(model);
                }
            }
        } else
        if(type.equals("")) {
            for (Job model : pl) {
                final String text = model.getLocation().toLowerCase();
                if (text.startsWith(query)) {
                    filteredModeList.add(model);
                }
            }

        } else{
            for (Job model : pl) {
                final String text = model.getLocation().toLowerCase();
                final String text2 = model.getJobType().toLowerCase();
                if (text.startsWith(query) && text2.startsWith(type)) {
                    filteredModeList.add(model);
                }
            }
        }
        return filteredModeList;
    }

    //for changing the text color of searchview
    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_jobs) {
            // Handle the camera action
        } else if (id == R.id.nav_about) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_filter) {
            showChangeLangDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.location);
        final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.radioGroup1);

        dialogBuilder.setTitle("Job Filter");
        dialogBuilder.setMessage("Enter Job Location");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String query;
                String type;
                Integer genid= rg.getCheckedRadioButtonId();
                if(!genid.equals(-1)) {
                    RadioButton radioButton = (RadioButton) dialogView.findViewById(genid);
                    type = radioButton.getText().toString();
                }else{
                    type = "";
                }
                if(TextUtils.isEmpty(edt.getText().toString())){
                    query = "";
                    final List<Job> filtermodelist = filter(listJob, query, type);
                    adapter.setfilter(filtermodelist);

                }else{
                    query = edt.getText().toString();
                    final List<Job> filtermodelist = filter(listJob, query, type);
                    adapter.setfilter(filtermodelist);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
