package com.example.fly;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class refragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    SearchView searchView;
    RecyclerView recyclerView;
    adapter_view adapter;

    FirebaseAuth firebaseAuth;
    DatabaseReference database;
    FirebaseUser firebaseUser;


    public refragment() {

    }


    public static refragment newInstance(String param1, String param2) {
        refragment fragment = new refragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance().getReference().child("Products").child("Info");

        // set a LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<product_add_view_helperclass> options =
                new FirebaseRecyclerOptions.Builder<product_add_view_helperclass>()
                        .setQuery(database, product_add_view_helperclass.class)
                        .build();

        adapter = new adapter_view(options);
        recyclerView.setAdapter(adapter);


        searchView = (SearchView) view.findViewById(R.id.search);
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

        return view;

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

        adapter = new adapter_view(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);

   }

}