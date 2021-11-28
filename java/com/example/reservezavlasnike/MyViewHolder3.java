package com.example.reservezavlasnike;


import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder3 extends RecyclerView.ViewHolder {
    TextView lik;
    TextView termin;
    View v;

    public MyViewHolder3(@NonNull View itemView) {

        super(itemView);
        lik=itemView.findViewById(R.id.fragmentime);
        termin=itemView.findViewById(R.id.fragmentcajt);

        v=itemView;

    }
}
