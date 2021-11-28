package com.example.reservezavlasnike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class listaplacenosti extends AppCompatActivity {
RecyclerView recyclerView;
FirebaseRecyclerAdapter<sumarnimjesec,MyViewHolder2> adapter;
FirebaseRecyclerOptions<sumarnimjesec> options;
DatabaseReference dataref;
ImageView rasporednazad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listaplacenosti);
        recyclerView=findViewById(R.id.resajklermjeseca);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataref= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("placenost");
        LoadData("");
        Spinner spinCountry;
        spinCountry= (Spinner) findViewById(R.id.spinercina);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(listaplacenosti.this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.Dane));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(adapter);
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadData(spinCountry.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rasporednazad=findViewById(R.id.rasporedNazadKorisnika);
        rasporednazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(listaplacenosti.this,com.example.reservezavlasnike.SecondActivity.class));
            }
        });

    }
    void LoadData(String data){
        Query query;

        query=dataref.orderByChild("status").equalTo(data);
        if(data.equals("Svi računi"))
        {        query=dataref.orderByChild("dodatno");
        }
        options=new FirebaseRecyclerOptions.Builder<sumarnimjesec>().setQuery(query, sumarnimjesec.class).build();
        adapter=new FirebaseRecyclerAdapter<sumarnimjesec, MyViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder2 holder, int position, @NonNull sumarnimjesec model) {
       holder.mjesec.setText(model.getMjesec());
       holder.sumarno.setText(model.getSumarno());
       holder.status.setText(model.getStatus());
       if(holder.status.getText().toString().equals("Plaćeno")){
           holder.status.setTextColor(Color.GREEN);
       }
       else holder.status.setTextColor(Color.RED);

       holder.zaklik.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(listaplacenosti.this,com.example.reservezavlasnike.ZaracunajbrateActivity.class);
               String stas=holder.mjesec.getText().toString();
               String mjesecje="";
               if(stas.charAt(1)=='.'){
                mjesecje="0"+stas.charAt(0);}
               else { mjesecje=""+stas.charAt(0)+stas.charAt(1);}
               String godinica="";
               for(int i=stas.length()-5;i<stas.length();i++){
                   godinica+=stas.charAt(i);
               }

               intent.putExtra("mjesecje",mjesecje);
               intent.putExtra("godinica",godinica);
               startActivity(intent);
           }
       });
            }

            @NonNull
            @Override
            public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.mjesecniivjeszaj,parent,false);
                return new MyViewHolder2(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
    @Override
    public void onBackPressed() {
        if (shouldAllowBack()) {
            super.onBackPressed();
        } else {

        }
    }

    private boolean shouldAllowBack() {
        return false;
    }
}