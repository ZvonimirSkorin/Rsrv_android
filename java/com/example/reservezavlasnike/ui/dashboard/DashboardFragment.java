package com.example.reservezavlasnike.ui.dashboard;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;


import com.example.reservezavlasnike.ItemClickSupport;
import com.example.reservezavlasnike.MyViewHolder;
import com.example.reservezavlasnike.MyViewHolder4;
import com.example.reservezavlasnike.R;
import com.example.reservezavlasnike.SecondActivity;
import com.example.reservezavlasnike.terminiFirmi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class DashboardFragment extends Fragment {
    FirebaseRecyclerOptions<terminiFirmi> options;
    public FirebaseRecyclerAdapter<terminiFirmi,MyViewHolder4> adapter2;
    FirebaseRecyclerAdapter<terminiFirmi,MyViewHolder4> govno232;
    DatabaseReference Dataref;
    RecyclerView recyclerView;
private FirebaseRecyclerAdapter<terminiFirmi, RecyclerView.ViewHolder> govance;
    private DashboardViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        CalendarView calendarView=root.findViewById(R.id.kalendar);
        recyclerView = root.findViewById(R.id.resajkler);
        //        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DatabaseReference trenutniDatum=FirebaseDatabase.getInstance().getReference().child("Datum").child("Danas");
        trenutniDatum.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nadnevak=snapshot.getValue().toString();
                DatabaseReference stanje=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child("stvarano");
                stanje.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.hasChild(nadnevak))
                        {   stvorigaAkonema(nadnevak);}
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                String sttus=snapshot.child(nadnevak).getValue().toString();
                                if(  sttus.equals("0"))
                                {
                                    prviPutdanas(nadnevak);
                                }
                            }
                        }, 1000);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        TextView textView1=root.findViewById(R.id.nadnevak);
        String nadnevakneki2=String.valueOf(calendarView.getDateTextAppearance());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendarView.setVisibility(View.INVISIBLE);
        String selectedDate = sdf.format(new Date(calendarView.getDate()));
textView1.setText(selectedDate);
        final int[] k = {0};
textView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(k[0]!=0){
            calendarView.setVisibility(View.INVISIBLE);
            k[0]=0;
        }else{calendarView.setVisibility(View.VISIBLE);
            k[0]=10;}
    }
});


calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        calendarView.setVisibility(View.INVISIBLE);

        SimpleDateFormat sdfs = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate2 = sdfs.format(new Date(view.getDate()));
        k[0]=0;
        String godina=String.valueOf(year);
        int govno=month+1;
        String mjesec=String.valueOf(govno);
        String dan=String.valueOf(dayOfMonth);
        if(month<10)
        {mjesec="0"+mjesec;}
        if(dayOfMonth<10){dan="0"+dan;}
        textView1.setText(dan+"/"+mjesec+"/"+godina);
        DatabaseReference dejtRef=FirebaseDatabase.getInstance().getReference().child("Gloria").child("skok");
        final int[] n = new int[1];
        String finalMjesec = mjesec;
        String finalDan = dan;
        dejtRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n[0] =Integer.parseInt(snapshot.getValue().toString());
                ucitajAkonePostoji(godina+ finalMjesec + finalDan, n[0],true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
});

        Spinner spinCountry;
        spinCountry= (Spinner) root.findViewById(R.id.spiner);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.country_array));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(adapter);
//if you want to set any action you can do in this listener
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // Recycler
        Dataref = FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini");
        Spinner spinner=root.findViewById(R.id.spiner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  nadnevak();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
nadnevak();
        ImageView dugme=root.findViewById(R.id.crnoDugme);
        final boolean[] stanje = {false};
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stanje[0] ==false){
                RelativeLayout crnina=root.findViewById(R.id.relativniFragment);
                ImageView fejkdugme=root.findViewById(R.id.fejkGumb);
crnina.setVisibility(View.VISIBLE);
RelativeLayout relativKompletni=root.findViewById(R.id.relativKompletni);
                float cx = fejkdugme.getX()+fejkdugme.getHeight()/2;
                float cy = fejkdugme.getY()+fejkdugme.getHeight()/2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(crnina,(int) cx,(int) cy, 0f, finalRadius);
                anim.setDuration(400);
                if(crnina.getVisibility()==View.VISIBLE)
                anim.start();
                dugme.animate().rotation(-135).setDuration(400);
                stanje[0] =true;}
                else { RelativeLayout crnina=root.findViewById(R.id.relativniFragment);
                    ImageView fejkdugme=root.findViewById(R.id.fejkGumb);
                    crnina.setVisibility(View.VISIBLE);
                    RelativeLayout relativKompletni=root.findViewById(R.id.relativKompletni);
                    float cx = fejkdugme.getX()+fejkdugme.getHeight()/2;
                    float cy = fejkdugme.getY()+fejkdugme.getHeight()/2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(crnina,(int) cx,(int) cy, finalRadius, 0f);
                    anim.setDuration(400);
                    if(crnina.getVisibility()==View.VISIBLE)
                        anim.start();
                    dugme.animate().rotation(0).setDuration(400);
                    stanje[0] =false;
                    final Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            crnina.setVisibility(View.INVISIBLE);
                        }
                    }, 380);
                }
            }
        });
        return root;
    }
    void LoadData(String data){
        Query query;
        final Boolean[] istinaPrava = {true};
        final int[] n = {0};
if(data.equals("Svi"))
{ data="";}
        query=Dataref.child(data).orderByKey().startAt("000").endAt("2500");
        FirebaseRecyclerAdapter<terminiFirmi,MyViewHolder4> akcione;
 String nadnevakneki=data;
 int index=nadnevakneki.length();
        String polsat=""+nadnevakneki.charAt(index-2)+nadnevakneki.charAt(index-1);
        int stanje=Integer.parseInt(polsat);
        String startna;
        if(stanje>=30)
        {  if(nadnevakneki.charAt(0)!='0') startna=""+nadnevakneki.charAt(0)+nadnevakneki.charAt(1)+"30";
        else startna=""+nadnevakneki.charAt(1)+"30";}
        else   {  if(nadnevakneki.charAt(0)!='0') startna=""+nadnevakneki.charAt(0)+nadnevakneki.charAt(1)+"00";
        else startna=""+nadnevakneki.charAt(1)+"00";}



        options=new FirebaseRecyclerOptions.Builder<terminiFirmi>().setQuery(query, terminiFirmi.class).build();


       adapter2=new FirebaseRecyclerAdapter<terminiFirmi, MyViewHolder4>(options) {
           @Override
           protected void onBindViewHolder(@NonNull MyViewHolder4 holder, int position, @NonNull terminiFirmi model) {
           TextView vrijeme=holder.itemView.findViewById(R.id.cajttermina);
           String vrijemePom=model.getTermin();
           int nee=Integer.parseInt(vrijemePom);
           String vreme2=String.valueOf(nee/100)+":"+String.valueOf(nee%100)+"0";
           int index=vrijemePom.length();
           String vrijemepom2=vrijemePom;
           if(vrijemePom.charAt(vrijemePom.length()-2)=='0'){
           vrijeme.setText(vreme2);
           TextView ajdebre=holder.itemView.findViewById(R.id.ajdebremolimte);
           ajdebre.setVisibility(View.VISIBLE);
           }
           else{ vrijeme.setText("");
               TextView ajdebre=holder.itemView.findViewById(R.id.ajdebremolimte);
               ajdebre.setVisibility(View.INVISIBLE);}
           TextView statusni=holder.itemView.findViewById(R.id.kockica);
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @RequiresApi(api = Build.VERSION_CODES.M)
               @Override
               public void onClick(View v) {
                   Log.d(TAG, "From: " + "govnopasje");

                   Slider slider=getView().findViewById(R.id.slider);
   if(model.getStatus().equals("Slobodno")) {
       float s = slider.getValue();
       TextView edtxt=getView().findViewById(R.id.edTxtSlider);
       TextView editText2=getView().findViewById(R.id.edTxtSlider2);
       String ter=model.getTermin();
       int iner=Integer.parseInt(ter);
       if((iner/100)>9)
       edtxt.setText(String.valueOf(iner/100));
       else edtxt.setText("0"+String.valueOf(iner/100));
       editText2.setText(String.valueOf(iner%100));
       Button button = getView().findViewById(R.id.buttoncina);
       RelativeLayout relativeLayout = getView().findViewById(R.id.relativnidodatak);
       relativeLayout.setVisibility(View.VISIBLE);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String zaslanje=edtxt.getText().toString()+editText2.getText().toString();
               relativeLayout.setVisibility(View.GONE);
               EditText uslSign=getView().findViewById(R.id.uslugesign);
               String uslugesign=uslSign.getText().toString();
               EditText imeSign=getView().findViewById(R.id.imesign);
               String imessign=imeSign.getText().toString();
               if (model.getStatus().equals("Slobodno"))
               {
                   zauzmi(model.getDatum(), model.getTermin(), slider.getValue(), imessign,uslugesign);}
           }
       });

       Button ponistavajci = getView().findViewById(R.id.ponistavajuciButton);
       ponistavajci.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               relativeLayout.setVisibility(View.GONE);
           }
       });
       slider.setLabelBehavior(LabelFormatter.LABEL_GONE);
       slider.addOnChangeListener(new Slider.OnChangeListener() {
           @Override
           public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
               float sss = slider.getValue();
                int prvi=(int)sss/60;
                String ending;
                if(prvi!=0){
                    ending=String.valueOf(prvi)+":"+String.valueOf((int)sss%60);
                }
                else ending=String.valueOf((int)sss);
               TextView stanje = getView().findViewById(R.id.stanjeNaslideru);


               stanje.setText(ending);
           }
       });
   }
else {/*  if(model.getRedni().equals("0") && statusni.getBackground().equals(Color.WHITE)) {
      makni(model.getTermin(), model.getDatum(), model.getKorisnik());
   }*/
   }
               }
           });

           if(model.getStatus().equals("Zauzeto") )
           {statusni.setElevation(1);
           String datumcic=model.getDatum().toString();
           if(datumcic.equals("reserve"))
               statusni.setBackground(getResources().getDrawable(R.drawable.customborderzaracun));
           else if(datumcic.equals("reserve1"))
               statusni.setBackgroundColor(Color.BLUE);
           else                statusni.setBackgroundColor(Color.WHITE);

               if(model.getRedni().equals("0"))
               {   TextView pokazivac=holder.itemView.findViewById(R.id.kockica2);
pokazivac.setElevation(2);
                   statusni.setText(model.getKorisnik()+"-"+model.getUsluge());}
           else {statusni.setText("");
                   TextView pokazivac=holder.itemView.findViewById(R.id.kockica2);
                   pokazivac.setElevation(0);
           }}
             else {statusni.setBackgroundColor(Color.WHITE);
               statusni.setElevation(0);
               TextView pokazivac=holder.itemView.findViewById(R.id.kockica2);
               pokazivac.setElevation(0);
             }
           }

           @NonNull
           @Override
           public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rasporedniitem, parent, false);
               return new MyViewHolder4(v);

           }
       };
        adapter2.startListening();
        recyclerView.setAdapter(adapter2);




    }
    void nadnevak(){
        DatabaseReference dejtic=FirebaseDatabase.getInstance().getReference().child("Datum").child("Danas");
        final String[] povratak = {new String()};
        dejtic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
      LoadData(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    void NoviPodatci(String data){
        DatabaseReference dejtref=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini");
        dejtref.child(data).push();
  for(int i=0;i<24;i++){
      String datum="202101"+String.valueOf(i);
      String termin=String.valueOf(i)+"00";
      dejtref.child(termin).push();
      String danas="01"+String.valueOf(i);
      dejtref.child(termin).child("datum").setValue("20210111");
      dejtref.child(termin).child("korisnik").setValue("Marko");
      String status="Zauzeti";
      if(i%2==0) status="Slobodno";
      dejtref.child(termin).child("status").setValue("Marko");
      dejtref.child(termin).child("termin").setValue(termin);
      dejtref.child(termin).child("usluge").setValue("nista");

       datum="202101"+String.valueOf(i);

       termin=String.valueOf(i)+"30";
      dejtref.child(termin).push();
       danas="01"+String.valueOf(i);
      dejtref.child(termin).child("datum").setValue("20210111");
      dejtref.child(termin).child("korisnik").setValue("Marko");
       status="Zauzeti";
      if(i%2==0) status="Slobodno";
      dejtref.child(termin).child("status").setValue("Marko");
      dejtref.child(termin).child("termin").setValue(termin);
      dejtref.child(termin).child("usluge").setValue("nista");



  }
    }

    void ucitajAkonePostoji(String s, int skok,boolean nekajeje) {
        String stvori = s;
        final int[] pocetak = new int[1];
        final int[] kraj = new int[1];
        DatabaseReference datano=FirebaseDatabase.getInstance().getReference().child("Gloria").child("radnoVrijeme");
        datano.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pocetak[0] =Integer.parseInt(snapshot.child("pocetak").getValue().toString());
                kraj[0] =Integer.parseInt(snapshot.child("kraj").getValue().toString());


                DatabaseReference dejtic = FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini");
                dejtic.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if( ! snapshot.hasChild(s)){
                            dejtic.child(stvori).push();
                            for (int i = pocetak[0]; i < kraj[0]; i++) {
                                String unos = "";

                                unos = String.valueOf((i))+"00";
                                dejtic.child(stvori).child(unos).push();
                                dejtic.child(stvori).child(unos).child("redni").setValue("0");
                                dejtic.child(stvori).child(unos).child("datum").setValue(stvori);
                                dejtic.child(stvori).child(unos).child("korisnik").setValue("Marko");
                                dejtic.child(stvori).child(unos).child("status").setValue("Slobodno");
                                dejtic.child(stvori).child(unos).child("termin").setValue(unos);
                                dejtic.child(stvori).child(unos).child("usluge").setValue("nista");

                                for(int j=0;j<60;j+=skok){
                                    String brojVeliki=new String();
                                    if(j<10)
                                    { brojVeliki="0"+String.valueOf(j);}
                                    else { brojVeliki=String.valueOf(j);}
                                    unos = String.valueOf(i) + brojVeliki;
                                    dejtic.child(stvori).child(unos).push();
                                    dejtic.child(stvori).child(unos).child("redni").setValue("0");
                                    dejtic.child(stvori).child(unos).child("datum").setValue(stvori);
                                    dejtic.child(stvori).child(unos).child("korisnik").setValue("Marko");
                                    dejtic.child(stvori).child(unos).child("status").setValue("Slobodno");
                                    dejtic.child(stvori).child(unos).child("termin").setValue(unos);
                                    dejtic.child(stvori).child(unos).child("usluge").setValue("nista");}

                            }
                            if(nekajeje==true)
                            LoadData(stvori);
                        }
                        else {if(nekajeje==true) LoadData(stvori);}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    void prviPutdanas(String nadnevak){
String nadnevak3=nadnevak;
           for(int i=0;i<5;i++){
              String nasice=""+ nadnevak.charAt(6)+nadnevak.charAt(7);
               int nasice2=Integer.parseInt(nasice);
               String mesec=""+nadnevak.charAt(4)+nadnevak.charAt(5);
               int mesec2=Integer.parseInt(mesec);
               if(mesec.equals("2") && nasice2==28){
                   nasice2=1;

                   mesec2++;
               }
               else if((mesec2==1 || mesec2==3 || mesec2==5 ||mesec2==7 || mesec2==8 || mesec2==10 || mesec2==12)   && nasice2==31){
                   mesec2++;
                   if(mesec2==13)
                       mesec2=1;
                   nasice2=1;
               }
               else if((mesec2==4 || mesec2==6 || mesec2==9 ||mesec2==11 )&& nasice2==30){
                   mesec2++;
                   nasice2=1;
               }
               else {if(i!=0)nasice2++;}

               if(nasice2<10)
                   nasice="0"+String.valueOf(nasice2);
               else nasice=String.valueOf(nasice2);
               if(mesec2<10)
                   mesec="0"+String.valueOf(mesec2);
               else mesec=String.valueOf(mesec2);
               String nadnevakona=""+nadnevak.charAt(0)+nadnevak.charAt(1)+nadnevak.charAt(2)+nadnevak.charAt(3)
                       +mesec+nasice;
               nadnevak=nadnevakona;
               DatabaseReference dejtic=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini");
               String finalNadnevak = nadnevak;
               dejtic.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(!snapshot.hasChild(finalNadnevak)){
                           {
                               DatabaseReference dejtaja=FirebaseDatabase.getInstance().getReference().child("Gloria").child("skok");
                               dejtaja.addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       ucitajAkonePostoji(finalNadnevak,Integer.parseInt(snapshot.getValue().toString()),false);
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });
                           }
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
             FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child("stvarano").child(nadnevak).setValue("1");

               LoadData(nadnevak3);

           }
    }
    void stvorigaAkonema(String nadnevak){
        DatabaseReference dejeje=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child("stvarano");
        dejeje.child(nadnevak).setValue("0");
    }

    void zauzmi(String datum,String termin, float trajanje, String ime, String usluge){
        FirebaseDatabase.getInstance().getReference().child("Gloria").child("skok").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float kraj=Integer.parseInt(termin)+trajanje;
                int skok=Integer.parseInt(snapshot.getValue().toString());
                String kraj2=String.valueOf(kraj);
                final int[] k = {0};
                int n=0;
                final float[] brojac = {trajanje };
                DatabaseReference nekaera=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child(datum);
                nekaera.orderByKey().startAt(termin).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        if(snapshot.child("status").getValue().toString().equals("Slobodno") && brojac[0] >0)
                        { snapshot.child("status").getRef().setValue("Zauzeto");
                        if(k[0] !=0)
                        { snapshot.child("redni").getRef().setValue("1");
                        snapshot.child("datum").getRef().setValue("Mi");
                        }
                        else {snapshot.child("redni").getRef().setValue("0");
                        snapshot.child("korisnik").getRef().setValue(ime);
                        snapshot.child("datum").getRef().setValue("Mi");
                        snapshot.child("usluge").getRef().setValue(usluge);
                        }
                        brojac[0] -=skok;
                        k[0]++;
                        }
                        else  brojac[0]=-1;
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    void makni(String termin, String datum, String korisnik ){
        final int[] n = {0};
        String prva;
        final String[] zadnja = new String[1];
        DatabaseReference dejeje=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child(datum);
        dejeje.startAt(termin).orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if(snapshot.child("korisnik").getValue().equals(korisnik) &&n[0]!=2){
                    String redni= snapshot.child("redni").getValue().toString();
                    if(redni.equals("0")&&n[0]==1)
                        n[0]=2;
                    if(n[0]!=2){
                  if(n[0]==0 || redni.equals("1"))
                  { snapshot.child("korisnik").getRef().setValue("");
                  snapshot.child("redni").getRef().setValue("0");
                        snapshot.child("status").getRef().setValue("Slobodno");}}
             if(n[0]!=2)
                  n[0]=1;
                }
                else{
                     n[0]=2;}
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


    }
