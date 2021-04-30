package com.example.fly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;



public class adapter_view extends FirebaseRecyclerAdapter<product_add_view_helperclass,adapter_view.myviewholder> {

    public adapter_view(@NonNull FirebaseRecyclerOptions<product_add_view_helperclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull product_add_view_helperclass product_add_view_helperclass) {



        product_add_view_helperclass.getUid();
        Picasso.get().load(product_add_view_helperclass.getImage()).into(myviewholder.imageView);
        myviewholder.category.setText(product_add_view_helperclass.getCategory());
        myviewholder.model.setText(product_add_view_helperclass.getModel());
        myviewholder.price.setText(product_add_view_helperclass.getPrice());

        myviewholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new display_fragment(product_add_view_helperclass.getUid(),product_add_view_helperclass.getImage(),
                        product_add_view_helperclass.getDescription(), product_add_view_helperclass.getDetail() )).addToBackStack(null).commit();

            }
        });
    }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_product,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView model,price,category;
        ImageView imageView;
        CardView cardView;

        public myviewholder(@NonNull View itemView) {

            super(itemView);

            category = (TextView) itemView.findViewById(R.id.category_single);
            model = (TextView) itemView.findViewById(R.id.model_single);
            price = (TextView) itemView.findViewById(R.id.price_single);
            imageView = (ImageView) itemView.findViewById(R.id.signle_image);
            cardView = (CardView) itemView.findViewById(R.id.card);

        }
    }
}

