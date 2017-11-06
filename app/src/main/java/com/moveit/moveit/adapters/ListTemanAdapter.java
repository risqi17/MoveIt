package com.moveit.moveit.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moveit.moveit.Chat;
import com.moveit.moveit.LocationUser;
import com.moveit.moveit.R;
import com.moveit.moveit.settergetters.ListTemanSetGet;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 10/18/2017.
 */

public class ListTemanAdapter extends RecyclerView.Adapter<ListTemanAdapter.MyViewHolder>{

    public Context ctx;
    public ArrayList arrayList;

    public ListTemanAdapter(Context ctx, ArrayList arrayList) {
        this.ctx = ctx;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teman_aktif, parent, false);
        return new MyViewHolder(view, ctx, arrayList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ListTemanSetGet data = (ListTemanSetGet) arrayList.get(position);

        holder.txtNama.setText(data.getNama());
        holder.txtWaktu.setText(data.getJam());
        holder.txtLokasi.setText(data.getLokasi());

        holder.imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();

                Intent iMap = new Intent(ctx, LocationUser.class);
                /*b.putDouble("lat", Double.parseDouble(data.getLat()));
                yourIntent.putExtras(b);*/
                iMap.putExtra("lat", data.getLat());
                iMap.putExtra("longi", data.getLongi());
                ctx.startActivity(iMap);
                //Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Context ctx;
        public ArrayList arrayList;
        public TextView txtNama, txtWaktu, txtLokasi;
        public ImageView imgMap;

        public MyViewHolder(View itemView, final Context ctx, ArrayList arrayList) {
            super(itemView);
            this.ctx = ctx;
            this.arrayList = arrayList;
            itemView.setOnClickListener(this);

            txtNama = (TextView) itemView.findViewById(R.id.txtNama);
            txtWaktu = (TextView) itemView.findViewById(R.id.txtWaktu);
            txtLokasi = (TextView) itemView.findViewById(R.id.txtLokasi);
            imgMap = (ImageView) itemView.findViewById(R.id.imgMap);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ListTemanSetGet data = (ListTemanSetGet) this.arrayList.get(position);

            Intent iChat = new Intent(ctx, Chat.class);
            iChat.putExtra("lokasi", data.getLokasi());

            if (!data.getLokasi().equals("")) {
                ctx.startActivity(iChat);
            }
        }
    }
}
