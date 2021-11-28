package com.example.reservezavlasnike;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder2 extends RecyclerView.ViewHolder {
   TextView mjesec;
   TextView status;
   TextView sumarno;
   LinearLayout zaklik;
    View v;

    public MyViewHolder2(@NonNull View itemView) {

        super(itemView);
        mjesec=itemView.findViewById(R.id.mjesec);
        status=itemView.findViewById(R.id.statusMjeseca);
        sumarno=itemView.findViewById(R.id.sumarno);
        zaklik=itemView.findViewById(R.id.zaklik);
        v=itemView;

    }
}
