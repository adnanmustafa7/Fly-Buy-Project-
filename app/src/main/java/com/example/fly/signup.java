package com.example.fly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class signup extends AppCompatActivity {


    TextInputLayout name, num, email, pass, conf_pass;
    Button btnsignup;


    ImageView imageView;
    Uri imageUri;
    String imageURI;

    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        imageView = findViewById(R.id.img);
        name = findViewById(R.id.name);
        num = findViewById(R.id.number);
        email = findViewById(R.id.email);
        conf_pass = findViewById(R.id.conf_pass);
        pass = findViewById(R.id.pass);
        btnsignup = findViewById(R.id.signup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                String n = name.getEditText().getText().toString();
                String nu = num.getEditText().getText().toString();
                String e = email.getEditText().getText().toString();
                String p = pass.getEditText().getText().toString();
                String c = conf_pass.getEditText().getText().toString();


                if (n.isEmpty()) {
                    name.setError("Full Name can not be empty");
                    name.requestFocus();
                    return;
                }

                if (nu.isEmpty()) {
                    num.setError("Enter Number");
                    num.requestFocus();
                    return;
                }
                if (e.isEmpty()) {
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                    return;
                }

                if (p.length() < 6) {
                    pass.setError("Password Length Should be more than 6");
                    pass.requestFocus();
                    return;
                }

                if (p.isEmpty()) {
                    pass.setError("Enter Password");
                    pass.requestFocus();
                    return;
                }
                if (c.isEmpty()) {
                    conf_pass.setError("Re Enter Same Password");
                    conf_pass.requestFocus();
                    return;
                }
                if (!c.equals(p)) {
                    conf_pass.setError("Password Not Matched");
                    conf_pass.requestFocus();
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            DatabaseReference reference = database.getReference().child("Users").child(firebaseAuth.getUid());
                            StorageReference reference1 = storage.getReference().child("upload").child(firebaseAuth.getUid());

                            if (imageUri != null) {
                                reference1.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isComplete()) {
                                            reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    imageURI = uri.toString();
                                                    Users users = new Users(firebaseAuth.getUid(), nu, e, n, p, imageURI);
                                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                Intent i = new Intent(signup.this, signin.class);
                                                                startActivity(i);
                                                            }
                                                            else {
                                                                Toast.makeText(signup.this, "You are not Registered! Try again", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                });
                            } else {
                                progressDialog.dismiss();
                                imageURI = "https://firebasestorage.googleapis.com/v0/b/flyy-269c6.appspot.com/o/profile.png?alt=media&token=650b22b0-5303-407f-9bef-e7fd73371dce";
                                Users users = new Users(firebaseAuth.getUid(), nu, e, n, p, imageURI);
                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(signup.this, "Registered Successfully! ", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(signup.this, signin.class));

                                        } else {
                                            Toast.makeText(signup.this, "Not Registered! Try again", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }

                        } else {
                            Toast.makeText(signup.this, "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);

            }
        });
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