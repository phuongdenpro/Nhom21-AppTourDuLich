package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Tour> tourList;
    private TourAdapter tourAdapter;
    private ImageView imgPlus;
    private ImageView imgLogout;
    private DatabaseReference databaseReference;
    private FirebaseStorage ref;
    private StorageReference storageReference;
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tourList = new ArrayList<>();

        tourAdapter = new TourAdapter(HomeActivity.this,tourList);
        recyclerView.setAdapter(tourAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("tours");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tourList.clear();
                for(DataSnapshot tourshSnapshot : snapshot.getChildren()){
                    Tour tour = tourshSnapshot.getValue(Tour.class);
                    tourList.add(tour);
                }
                tourAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        imgPlus = findViewById(R.id.imageViewAdd);
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AddPlaceActivity.class);
                startActivity(i);
            }
        });
        imgLogout = findViewById(R.id.imgLogout);
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

    }

    private void logout() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Do you want to logout?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(HomeActivity.this,"Logout successfull!!!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                            }
                        }

                )
                .setNegativeButton("Cancel",null)
                .show();
    }

}