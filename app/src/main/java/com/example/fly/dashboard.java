package com.example.fly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class dashboard extends AppCompatActivity {

    TextView name;
    ImageView profile;

    Button add, contact, view, profile_button;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    FirebaseUser firebaseUser;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        name = (TextView) findViewById(R.id.name_view);
        profile = (ImageView) findViewById(R.id.profile_view);

        DatabaseReference reference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference().child("upload").child(firebaseAuth.getUid());

        toolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        this.setTitle("Dashboard");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String n = snapshot.child("name").getValue(String.class).toUpperCase();
                String img = snapshot.child("imageuri").getValue(String.class);
                name.setText(n);
                Picasso.get().load(img).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(dashboard.this, "Error, Try Again!", Toast.LENGTH_SHORT).show();

            }
        });

        add = (Button) findViewById(R.id.add_product);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, product_add.class);
                startActivity(intent);

            }
        });

        contact = (Button) findViewById(R.id.contact_us);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboard.this, contact_us.class);
                startActivity(intent);
            }
        });

        view = (Button) findViewById(R.id.view_product);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboard.this, product_view.class);
                startActivity(intent);

            }
        });


        profile_button = (Button) findViewById(R.id.profle_btn);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, profile_view.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        MenuItem menuItem = menu.findItem(R.menu.menu_dashboard);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
                builder.setTitle("Logout");
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Press Ok to Logout");
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(dashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent intent = new Intent(dashboard.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
                default: return super.onOptionsItemSelected(item);

        }
    }
}