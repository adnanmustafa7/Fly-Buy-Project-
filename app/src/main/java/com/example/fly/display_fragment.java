package com.example.fly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;



public class display_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String image, description ,details, email, num, uid;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    public display_fragment() {

    }


    public display_fragment(String uid, String image, String description, String details) {

        this.image = image;
        this.description = description;
        this.details = details;
        this.uid = uid;

    }



    public static display_fragment newInstance(String param1, String param2) {
        display_fragment fragment = new display_fragment();
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

        View  view = inflater.inflate(R.layout.fragment_display_fragment, container, false);



        ImageView imageView = view.findViewById(R.id.image_frag);
        TextView des = view.findViewById(R.id.description_frag);
        TextView det = view.findViewById(R.id.detail_frag);
        TextView email = view.findViewById(R.id.user_email);
        TextView num = view.findViewById(R.id.user_num);
        TextView nam = view.findViewById(R.id.user_name);
        ImageView img = view.findViewById(R.id.user_img);


        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference().child("Users").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String e = snapshot.child("email").getValue(String.class);
                String n = snapshot.child("num").getValue(String.class);
                String na = snapshot.child("name").getValue(String.class).toUpperCase();
                String i = snapshot.child("imageuri").getValue(String.class);

                email.setText(e);
                num.setText(n);
                nam.setText(na);
                Picasso.get().load(i).into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        des.setText(description);
        det.setText(details);
        Picasso.get().load(image).into(imageView);

        return view;
    }
}