package com.example.fly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class profile_view extends AppCompatActivity {

    EditText name, number, email, pass;
    Button upd;

    Toolbar toolbar;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        name = (EditText) findViewById(R.id.profile_name);
        number = (EditText) findViewById(R.id.profile_number);
        email = (EditText) findViewById(R.id.profile_email);
        pass = (EditText) findViewById(R.id.profile_pass);
        upd = (Button) findViewById(R.id.update);


        toolbar = (Toolbar) findViewById(R.id.toolbar_profileview);
        setSupportActionBar(toolbar);
        this.setTitle("Update");

        DatabaseReference reference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n = snapshot.child("name").getValue(String.class);
                String nu = snapshot.child("num").getValue(String.class);
                String e = snapshot.child("email").getValue(String.class);
                String p = snapshot.child("pass").getValue(String.class);

                name.setText(n);
                number.setText(nu);
                email.setText(e);
                pass.setText(p);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile_view.this, "Try Again!", Toast.LENGTH_SHORT).show();

            }
        });


        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(profile_view.this);
                builder.setTitle("Update");
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Press Ok to Update current Details");
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Map<String,Object> map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("num",number.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("pass",pass.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(profile_view.this, "Updated", Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                            }
                        });


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}