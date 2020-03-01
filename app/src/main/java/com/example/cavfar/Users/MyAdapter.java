package com.example.cavfar.Users;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cavfar.FuenteModelos;
import com.example.cavfar.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<FuenteModelos> mDataset;
    Context contexto;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tituloModelo;
        ImageView favorite;
        public MyViewHolder(View itemview) {
            super(itemview);
            tituloModelo = itemview.findViewById(R.id.tituloModelo);
            favorite = itemview.findViewById(R.id.fav);
        }
    }

    public MyAdapter(List<FuenteModelos> myDataset, Context context) {
        this.contexto = context;
        this.mDataset = myDataset;
    }

    public void setmDataset(List<FuenteModelos> data) {
        this.mDataset = data;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tituloModelo.setText(mDataset.get(position).getTitle());

        Log.i("hola",mDataset.get(position).getTitle());

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Función añadir a favoritos
                holder.favorite.setImageDrawable(contexto.getDrawable(R.drawable.heart2));
            }
            //Condición para rellenar o no el corazón (BBDD)
        });
    }
    @Override
    public int getItemCount() {
            return mDataset.size();
    }
}
