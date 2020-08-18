package com.example.akshi.e_commerce;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
/**
 * Created by akshi on 02-08-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Lidata> lists;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        public MyViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.profileimg);
        }
    }


    public void RecyclerAdater(Context applicationContext, List<Lidata> moviesList) {
        this.lists = moviesList;
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
        Lidata movie = lists.get(position);
        //holder.tvname.setText(lists.get(position).getDoc_name());
        //holder.tvemail.setText(lists.get(position).getEmail());
        String imageurl = lists.get(position).getImage();

        Glide.with(context).load(imageurl)
                .thumbnail(0.5f)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}

