package com.example.travel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView tvName, tvDes,tvPrice;
    private ImageView imgImageView;
    private ImageView imgImageView_add;
    private ImageView imgImageView_addupdate;
    private Button btnBook;
    private ImageButton btn_suadetail, btn_xoadetail;
    private Uri imgUri;
    private StorageTask mUploadTask;
    private FirebaseStorage ref;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private DatabaseReference myReference;
    private FirebaseDatabase database;
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
        btn_suadetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogUpdate(finalTour);
            }
        });
    }
    private void onClickDeleteData(Tour tour){
        new AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage("Do you want to delete?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("tours");
                        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                        StorageReference imgref = firebaseStorage.getReferenceFromUrl(tour.getImageURL());
                        reference.child(tour.getTourId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                imgref.delete();
                                Toast.makeText(DetailActivity.this,"Delete data success!!",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailActivity.this,HomeActivity.class));
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            Picasso.get().load(imgUri).fit().centerCrop().into(imgImageView_addupdate);
        }
    }
    private void openDialogUpdate(Tour tour){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_update_diaglog);
        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        EditText edtupdatename = dialog.findViewById(R.id.name_update);
        EditText edtupdatedes = dialog.findViewById(R.id.des_update);
        EditText edtupdatelocal = dialog.findViewById(R.id.local_update);
        EditText edtupdateprice = dialog.findViewById(R.id.pr_update);
        Button btnupdate = dialog.findViewById(R.id.btn_update_update);
        Button btnupcancel = dialog.findViewById(R.id.btn_update_cancel);

        edtupdatename.setText(tour.getNamePlace());
        edtupdatedes.setText(tour.getDescriptive());
        edtupdatelocal.setText(tour.getLocate());
        edtupdateprice.setText(String.valueOf(tour.getPrice()));




        btnupcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("tours");
                String name = edtupdatename.getText().toString().trim();
                String des = edtupdatedes.getText().toString().trim();
                String local = edtupdatelocal.getText().toString().trim();
                String price = edtupdateprice.getText().toString().trim();
                tour.setNamePlace(name);
                tour.setDescriptive(des);
                tour.setLocate(local);
                tour.setPrice(Integer.parseInt(price));
                reference.child(tour.getTourId()).updateChildren(tour.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailActivity.this,"Update data succec!!!",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        tvName.setText(tour.getNamePlace()+", "+tour.getLocate());
                        tvDes.setText(tour.getDescriptive());
                        tvPrice.setText(String.valueOf(tour.getPrice()));
                    }
                });

            }
        });
        dialog.show();
    }

}