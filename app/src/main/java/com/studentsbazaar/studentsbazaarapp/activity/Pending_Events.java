package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.PendingEventsAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.model.Project_details;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class Pending_Events extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView pendingeventsrecycler;
    SpotsDialog progressDialog;
    List<Project_details> drawerResponseList = null;
    PendingEventsAdapter mAdapter;
    TextView page_title;
    LinearLayout layout;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_pending);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipepending);
        pendingeventsrecycler = (RecyclerView) findViewById(R.id.pending_events_recycler);
        pendingeventsrecycler.setHasFixedSize(true);
        pendingeventsrecycler.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new SpotsDialog(this);
        layout = (LinearLayout) findViewById(R.id.empty1);
        page_title = findViewById(R.id.pending_events_title);
        progressDialog.show();
        Intent intent = getIntent();
         str = intent.getStringExtra("apitype");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpending);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryDark) , PorterDuff.Mode.SRC_ATOP);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    private void loadData() {

        progressDialog.show();
        Call<DownloadResponse> call=null;

        if (str==null){
            call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_PENDING_EVENTS);
            page_title.setText("Pending Event's");
        }else if (str.equals("uid")){
            call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.GET_USER_EVENTS+"?uid="+ Controller.getUID());
            page_title.setText("My Event's");
        }
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, retrofit2.Response<DownloadResponse> response) {
                Log.d("RESPONSE1", response.message().toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    drawerResponseList = response.body().getProject_details();
                    Log.d("RESPONSE2", drawerResponseList.toString());
                    progressDialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                    mAdapter = new PendingEventsAdapter(Pending_Events.this, drawerResponseList);
                    pendingeventsrecycler.setAdapter(mAdapter);
                    if (drawerResponseList.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        pendingeventsrecycler.setVisibility(View.INVISIBLE);
                    } else {
                        layout.setVisibility(View.INVISIBLE);
                        pendingeventsrecycler.setVisibility(View.VISIBLE);
                    }
                    // mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("RESPONSE3", "err" + t.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        /*if (str==null){
            new Move_Show(Pending_Events.this,HomeActivity.class);
        }
        else if (str.equals("uid")){
            new Move_Show(Pending_Events.this,ProfileActivity.class);
        }*/
    }
}
