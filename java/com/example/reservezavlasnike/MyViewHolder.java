package com.example.reservezavlasnike;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView datum;
    ImageView dole;
    LinearLayout sakriveni;
    ImageView imageView;

    TextView brojRezervacija;
    TextView ukupno;
    View v;

    public MyViewHolder(@NonNull View itemView) {

        super(itemView);
        datum=itemView.findViewById(R.id.racunskidatem);
        dole=itemView.findViewById(R.id.dajpokazistalazes);
        sakriveni=itemView.findViewById(R.id.sakriveni);

        imageView=itemView.findViewById(R.id.illuminati);
        brojRezervacija=itemView.findViewById(R.id.brojRezevacion);
        ukupno=itemView.findViewById(R.id.ukupnoRezervacion);
        v=itemView;

    }
}
