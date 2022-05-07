package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Tour> tourList;
    private TourAdapter tourAdapter;
    private ImageView imgPlus;
    private FirebaseStorage ref;
    private StorageReference storageReference;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycler_view);
        tourList = new ArrayList<>();
        tourAdapter = new TourAdapter(this,tourList);
        recyclerView.setAdapter(tourAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseStorage.getInstance();
        storageReference = ref.getReference();



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