package com.example.reservezavlasnike;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentContainer;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.random;

public class ZaracunajbrateActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dataref;
    String pravaIstina=FirebaseAuth.getInstance().getUid();
    FirebaseRecyclerOptions<ocemepokrast> options;
    public FirebaseRecyclerAdapter<ocemepokrast,MyViewHolder> adapter2;
    DatabaseReference dataref2;
    Fragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.zaracunajbrate);
        recyclerView=findViewById(R.id.racunskiresajkler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataref= FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("zaplatit");

        getNadnevak("");
        ImageView rasporednazad;
        rasporednazad=findViewById(R.id.nazadzaracunajbrate);
        rasporednazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ZaracunajbrateActivity.this,com.example.reservezavlasnike.listaplacenosti.class));
            }
        });
        Intent intent=getIntent();
String asta=intent.getStringExtra("godinica");
String kaebre=intent.getStringExtra("mjesecje");
        Toast.makeText(ZaracunajbrateActivity.this, "a brate"+kaebre, Toast.LENGTH_SHORT).show();
        String pravaInfo=asta+kaebre;


        loadj("202101");
    }
    void noviPoddatci(){
        DatabaseReference dejta;
        dejta=FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("zaplatit");
        Map<String ,String> zasNe = new HashMap<>();
        for(int i=0;i<30;i++){
            Toast.makeText(ZaracunajbrateActivity.this, "Loada se", Toast.LENGTH_SHORT).show();
            int vre=20210101+i;
            zasNe.put(String.valueOf(vre),String.valueOf( i%10));
            dejta.child(String.valueOf(vre)).push();
            dejta.child(String.valueOf(vre)).child("broj").push();
            dejta.child(String.valueOf(vre)).child("stanje").push();
            dejta.child(String.valueOf(vre)).child("broj").setValue(String.valueOf(vre));
            Random random=new Random();
            int j=random.nextInt(10);
            dejta.child(String.valueOf(vre)).child("stanje").setValue(String.valueOf(j));

        }


    }
    void getNadnevak(String  kaj){
        final String[] trenutniDatum = new String[1];
        DatabaseReference nadnevak;
        nadnevak=FirebaseDatabase.getInstance().getReference().child("Datum").child("Danas");
        nadnevak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trenutniDatum[0] =snapshot.getValue().toString();
                Toast.makeText(ZaracunajbrateActivity.this, trenutniDatum[0], Toast.LENGTH_SHORT).show();
                loadj(trenutniDatum[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }


void loadj(String nadnevak){
        nadnevak="202101";
String volja =nadnevak;
Toast.makeText(ZaracunajbrateActivity.this, volja, Toast.LENGTH_SHORT).show();
    Query query;
    TextView textView=findViewById(R.id.kolokejebre);

    query=dataref.orderByChild("broj").startAt(nadnevak).endAt(nadnevak+ "\uf8ff");;
    FirebaseRecyclerAdapter<ocemepokrast,MyViewHolder> akcione;
    final int[] sve = {0};

    options=new FirebaseRecyclerOptions.Builder<ocemepokrast>().setQuery(query, ocemepokrast.class).build();
    adapter2=new FirebaseRecyclerAdapter<ocemepokrast, MyViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ocemepokrast model) {
holder.datum.setText(model.getBroj());
sve[0] +=Integer.parseInt(model.getStanje());
holder.imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("govno", model.getBroj().toString());
        BlankFragment myFragment=new BlankFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
// set Fragmentclass Arguments
        myFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment, myFragment).commit();
        Fragment fragment=new Fragment();
        fragment.setArguments(bundle);
        View nasstari=findViewById(R.id.fragment);
        nasstari.setVisibility(View.VISIBLE);
    }
});

holder.dole.setOnClickListener(v -> {
    holder.sakriveni.setVisibility(View.VISIBLE);
    holder.brojRezervacija.setText(model.getStanje());
    int neskaj=Integer.parseInt(model.getStanje())*3;
    holder.ukupno.setText(String.valueOf(neskaj));
});

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.racunskiajtem, parent, false);
            return new MyViewHolder(v);
        }
    };

    adapter2.startListening();
    recyclerView.setAdapter(adapter2);
govance(nadnevak);
}
void dajSve(int sve){


}

void getpolje(String jedan){
        dataref=FirebaseDatabase.getInstance().getReference().child(pravaIstina);
        Query query;
        int jeden=Integer.parseInt(jedan);



        query=dataref.child("zaplatit").orderByKey().startAt(jedan).endAt(jedan+ "\uf8ff");
    final int[] i = {1};
    final int[] k = {0};
    query.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            k[0] +=Integer.parseInt(snapshot.child("stanje").getValue().toString());


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

}

void govance(String nadnevak){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("zaplatit").child("zbroj");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textView23=findViewById(R.id.kolokejebre);
                textView23.setText(snapshot.getValue().toString()+" kn");
                TextView textView234=findViewById(R.id.kolokejebrePara);
                int nesss=Integer.parseInt(snapshot.getValue().toString())/3;
                textView234.setText(String.valueOf(nesss));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
