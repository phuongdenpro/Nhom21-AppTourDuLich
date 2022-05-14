package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName, tvDes,tvPrice;
    private ImageView imgImageView;
    private ImageView imgImageView_add;
    private Button btnBook;
    private ImageButton btn_suadetail, btn_xoadetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle data =intent.getExtras();
        Tour tour = null;
        if(data!=null){
            tour = (Tour) data.getSerializable("tour");
        }else{
            Toast.makeText(DetailActivity.this, "loi", Toast.LENGTH_SHORT).show();
        }

        tvName =findViewById(R.id.located_detail);
        tvDes = findViewById(R.id.descriptivePlace_detail);
        tvPrice = findViewById(R.id.price_detail);
        imgImageView = findViewById(R.id.image_detail);
        imgImageView_add = findViewById(R.id.imageView_addtail);
        btn_suadetail = findViewById(R.id.btn_suadetail);
        btn_xoadetail = findViewById(R.id.btn_xoadetail);

        tvName.setText(tour.getNamePlace()+", "+tour.getLocate());
        tvDes.setText(tour.getDescriptive());
        tvPrice.setText(String.valueOf(tour.getPrice()));
        Picasso.get().load(tour.getImageURL()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imgImageView);

        btnBook = findViewById(R.id.btn_Booknow);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this,"Book successfully!!! ",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailActivity.this, HomeActivity.class));
            }
        });
        imgImageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, AddPlaceActivity.class));
            }
        });
        Tour finalTour = tour;
        btn_xoadetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeleteData(finalTour);
            }
        });
    }
    private void onClickDeleteData(Tour tour){
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Ban co muon xoa?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("tours");
                        reference.child(tour.getTourId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailActivity.this,"Delete data success!!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailActivity.this,HomeActivity.class));
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
}