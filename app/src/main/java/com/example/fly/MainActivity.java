package com.example.fly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;



public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    adapter_main adapter_main;
    adapter_main adapter;
    DatabaseReference database;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Main");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_main);
        database = FirebaseDatabase.getInstance().getReference().child("Products").child("Info");

        // set a LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<product_add_view_helperclass> options =
                new FirebaseRecyclerOptions.Builder<product_add_view_helperclass>()
                        .setQuery(database, product_add_view_helperclass.class)
                        .build();

        adapter = new adapter_main(options);
        recyclerView.setAdapter(adapter);


        searchView = (SearchView) findViewById(R.id.search_main);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                search(s);
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void search(String s) {


        FirebaseRecyclerOptions<product_add_view_helperclass> options =
                new FirebaseRecyclerOptions.Builder<product_add_view_helperclass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").child("Info")
                                        .orderByChild("category").startAt(s).endAt(s + "\uf8ff"),
                                product_add_view_helperclass.class)
                        .build();

        adapter = new adapter_main(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.menu.menu_main);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.nav_home:

                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent1);
                return true;

            case R.id.nav_signin:

                Intent intent2 = new Intent(MainActivity.this, signin.class);
                startActivity(intent2);
                return true;

            case R.id.nav_signup:

                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
                return true;
                default: return super.onOptionsItemSelected(item);
        }
    }
}