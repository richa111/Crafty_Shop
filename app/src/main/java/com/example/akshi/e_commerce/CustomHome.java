package com.example.akshi.e_commerce;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CustomHome extends RecyclerView.Adapter<CustomHome.MyViewHolder> {

    private List<Lidata> lists;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, price;

        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.profileimg);
            name = (TextView) view.findViewById(R.id.textname);
            price = (TextView) view.findViewById(R.id.txtemail);

           /* title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);*/
        }
    }


    public CustomHome(Context applicationContext, List<Lidata> lists) {
        this.lists = lists;
        this.context = applicationContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.datarow, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String imageurl = lists.get(position).getImage();

        Glide.with(context).load(imageurl)
                .thumbnail(0.5f)
                .into(holder.image);
        holder.name.setText(lists.get(position).getName());
        holder.price.setText(lists.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

