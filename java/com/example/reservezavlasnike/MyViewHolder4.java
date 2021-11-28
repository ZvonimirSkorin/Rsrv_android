package com.example.reservezavlasnike;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class MyViewHolder4 extends RecyclerView.ViewHolder {
 private  TextView korisnik;
   TextView datum;
   TextView cajttermina;
   TextView vrijeme;
   TextView usluge;
   TextView kockica;
   TextView textView;
   RelativeLayout trenutnaCrta;
    private View.OnClickListener onItemClickListener;
    RelativeLayout relativeLayout;
    RelativeLayout stae;
    View v;

    public MyViewHolder4(@NonNull View itemView) {

        super(itemView);
        relativeLayout=itemView.findViewById(R.id.relativica);
        korisnik=itemView.findViewById(R.id.bezvezninaslov);
        datum=itemView.findViewById(R.id.ustaskiDatum);
        kockica=itemView.findViewById(R.id.kockica);
        stae=itemView.findViewById(R.id.cijelirelaitiv);
        cajttermina=itemView.findViewById(R.id.cajttermina);
        trenutnaCrta=itemView.findViewById(R.id.trenutnaCrta);
        textView=itemView.findViewById(R.id.bezveznaadresa);
        vrijeme=itemView.findViewById(R.id.crticarasproedna);
        usluge=itemView.findViewById(R.id.bezveznaadresa);

            itemView.setTag(this);
itemView.setOnClickListener(onItemClickListener);


        v=itemView;

    }
    public void setItemClickListener(View.OnClickListener clickListener) {
        onItemClickListener = clickListener;
    }
}
