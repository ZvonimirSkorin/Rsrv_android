package com.example.reservezavlasnike;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



class MyViewHolder5 extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    RelativeLayout relativeLayout;
    ImageView desna;
    ImageView ljeva;
    private View.OnClickListener onItemClickListener;
    View v;

    public MyViewHolder5(@NonNull View itemView) {

        super(itemView);
        imageView=itemView.findViewById(R.id.slikausluge);
        textView=itemView.findViewById(R.id.vrstaUsluge23);
        itemView.setTag(this);
        itemView.setOnClickListener(onItemClickListener);
        desna=itemView.findViewById(R.id.desnaSlika);
        ljeva=itemView.findViewById(R.id.ljevaslika);
        v=itemView;

    }
    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }
}
