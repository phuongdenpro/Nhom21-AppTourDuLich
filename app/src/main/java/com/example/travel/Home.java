package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Tour> tourList;
    private TourAdapter tourAdapter;
    private ImageView imgPlus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycler_view);
        tourList = new ArrayList<>();
        tourList.add(new Tour("Ho Chi minh","fdffdf","VietNam",45555,R.drawable.rectangle_5));
        tourList.add(new Tour("Ho Chi minh","fdffdf","VietNam",45555,R.drawable.rectangle_5));
        tourList.add(new Tour("Ho Chi minh","fdffdf","VietNam",45555,R.drawable.rectangle_5));
        tourAdapter = new TourAdapter(this,tourList);
        recyclerView.setAdapter(tourAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imgPlus = findViewById(R.id.imageViewAdd);
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, AddPlaceActivity.class);
                startActivity(i);
            }
        });
    }
}