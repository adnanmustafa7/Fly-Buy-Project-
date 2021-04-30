package com.example.fly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class product_add extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    private static final int PICK_IMAGE = 1;

    EditText detail,description,modal,prices;
    Button chose, upload;

    ArrayList<Uri> imageList = new ArrayList<Uri>();

    ImageView imageView;
    Uri imageUri;
    String imageURI;

    Spinner spinner;

    private ProgressDialog progressDialog;
    private int upload_count = 0;

   private Uri individualImage;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String[] items = {"Mobile","Laptop","Tablet","Car","Motorbike","Land & Plots"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        spinner = (Spinner) findViewById(R.id.lists);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);


        detail = (EditText) findViewById(R.id.details);
        description = (EditText) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.img);
        upload = (Button) findViewById(R.id.add_data);
        modal = (EditText) findViewById(R.id.model);
        prices = (EditText) findViewById(R.id.price);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading.......");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String details = detail.getText().toString();
                String descriptions = description.getText().toString();
                String category = spinner.getSelectedItem().toString();
                String model = modal.getText().toString();
                String price = prices.getText().toString();

                if (details.isEmpty()){
                    detail.setError("Enter Details");
                    detail.requestFocus();
                    return;
                }

                if (descriptions.isEmpty()){

                    description.setError("Enter Description");
                    description.requestFocus();
                    return;
                }

                if (model.isEmpty()){
                    modal.setError("Enter Model name");
                    modal.requestFocus();
                    return;
                }

                if (price.isEmpty()){
                    prices.setError("Enter Price");
                    prices.requestFocus();
                    return;

                }


                progressDialog.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child("Info").child(firebaseAuth.getUid());
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Product_Images").child(firebaseAuth.getUid());

                if (imageUri != null) {
                 storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isComplete()) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageURI = uri.toString();
                                    product_add_helperclass product_add_helperclass = new product_add_helperclass(firebaseAuth.getUid(),details, descriptions,category ,model , price ,imageURI);
                                    reference.setValue(product_add_helperclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(product_add.this, "Done!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(product_add.this, view_user_product.class);
                                                startActivity(intent);
                                                detail.setText("");
                                                description.setText("");
                                                modal.setText("");
                                                prices.setText("");

                                            } else {
                                                Toast.makeText(product_add.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
            }
                else {

                    imageURI = "https://firebasestorage.googleapis.com/v0/b/flyy-269c6.appspot.com/o/profile.png?alt=media&token=650b22b0-5303-407f-9bef-e7fd73371dce";
                    product_add_helperclass product_add_helperclass = new product_add_helperclass(firebaseAuth.getUid(),details, descriptions,category,model,price,imageURI);
                    reference.setValue(product_add_helperclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                Toast.makeText(product_add.this, "Done!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(product_add.this, product_view.class);
                                startActivity(intent);
                                detail.setText("");
                                description.setText("");
                                modal.setText("");
                                prices.setText("");
                            } else {
                                Toast.makeText(product_add.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
        }

        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10){
            if (data != null){
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }

}