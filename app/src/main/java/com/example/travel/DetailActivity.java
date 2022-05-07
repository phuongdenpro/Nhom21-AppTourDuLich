package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName, tvDes,tvPrice;
    private ImageView imgImageView;
    private Button btnBook;
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
    }
}