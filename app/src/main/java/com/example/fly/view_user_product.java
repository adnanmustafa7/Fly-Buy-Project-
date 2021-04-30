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

public class view_user_product extends AppCompatActivity {


    TextView description, detail, model, price, category;
    ImageView image;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_product);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);



        description = (TextView) findViewById(R.id.description_view);
        detail   = (TextView) findViewById(R.id.detail_view);
        model = (TextView) findViewById(R.id.model_view);
        price = (TextView) findViewById(R.id.price_view);
        category = (TextView) findViewById(R.id.category_view);
        image = (ImageView) findViewById(R.id.image_vi);

        DatabaseReference reference = firebaseDatabase.getReference().child("Products").child("Info").child(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference().child("Product_Images").child(firebaseAuth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String des = snapshot.child("description").getValue(String.class);
                String det = snapshot.child("detail").getValue(String.class);
                String mod = snapshot.child("model").getValue(String.class);
                String pri = snapshot.child("price").getValue(String.class);
                String cate = snapshot.child("category").getValue(String.class);
                String imgg = snapshot.child("image").getValue(String.class);

                description.setText(des);
                detail.setText(det);
                model.setText(mod);
                price.setText(pri);
                category.setText(cate);
                Picasso.get().load(imgg).into(image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



                Toast.makeText(view_user_product.this, "Error, Try Again!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_del, menu);
        MenuItem menuItem = menu.findItem(R.menu.menu_del);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){

            case R.id.nav_del:

                AlertDialog.Builder builder = new AlertDialog.Builder(view_user_product.this);
                builder.setTitle("Delete");
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Press Ok to Delete current Prouct");
                builder.setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        firebaseDatabase.getReference().child("Products").child("Info").child(firebaseAuth.getUid()).removeValue();
                        Toast.makeText(view_user_product.this, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view_user_product.this, product_add.class);
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