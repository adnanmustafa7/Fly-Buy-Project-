package com.example.fly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class signin extends AppCompatActivity {

    TextInputLayout email, pass;
    Button signin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        firebaseAuth = FirebaseAuth.getInstance();
        email = (TextInputLayout) findViewById(R.id.email_signin);
        pass = (TextInputLayout) findViewById(R.id.pass_signin);
        signin = (Button) findViewById(R.id.signin);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e =  email.getEditText().getText().toString();
                String p = pass.getEditText().getText().toString();

               if (e.isEmpty()){
                   email.setError("Enter Email");
                   pass.requestFocus();
                   return;
               }

               if (p.isEmpty()){
                   pass.setError("Enter Password");
                   pass.requestFocus();
                   return;
               }

               if (p.length() < 6){
                   pass.setError("Length Should be more than 6");
                   pass.requestFocus();
                   return;
               }
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if (task.isSuccessful()){
                           progressDialog.dismiss();
                           Toast.makeText(signin.this, "Welcome", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(signin.this, dashboard.class);
                           startActivity(i);
                       }
                       else {
                           progressDialog.dismiss();
                           Toast.makeText(signin.this, "Invalid Email or Password, Try Again!", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

            }
        });
    }
}