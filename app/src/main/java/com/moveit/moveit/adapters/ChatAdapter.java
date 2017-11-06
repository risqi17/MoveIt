package com.moveit.moveit.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moveit.moveit.R;
import com.moveit.moveit.settergetters.ChatSetGet;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 10/18/2017.
 */

public class ChatAdapter extends BaseAdapter {

    Activity activity;
    ArrayList listItem;

    public ChatAdapter(Activity activity, ArrayList listItem) {
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_chat, null);

            holder.boxPesan = (RelativeLayout) view.findViewById(R.id.boxPesan);
            holder.cardPesan = (CardView) view.findViewById(R.id.cardPesan);
            holder.txtPesan = (TextView) view.findViewById(R.id.txtPesan);
            holder.txtWaktu = (TextView) view.findViewById(R.id.txtWaktu);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ChatSetGet data = (ChatSetGet) getItem(i);

        holder.txtPesan.setText(data.getPesan());
        holder.txtWaktu.setText(data.getJam() + ", " + data.getTgl());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String emailUser = user.getEmail();
        int etEmail = emailUser.length();
        String shortEmail = emailUser.substring(0, etEmail-4);

        if (data.getEmail().equals(shortEmail)){
            holder.boxPesan.setGravity(Gravity.RIGHT);
            //holder.cardPesan.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.blueMsg));
        }else {
            holder.boxPesan.setGravity(Gravity.LEFT);
            //holder.cardPesan.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
        }

        return view;

    }


    public class ViewHolder{
        public RelativeLayout boxPesan;
        public CardView cardPesan;
        public TextView txtPesan, txtWaktu;
    }
}
