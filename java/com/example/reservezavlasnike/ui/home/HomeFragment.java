package com.example.reservezavlasnike.ui.home;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.reservezavlasnike.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    BarChart barChart;
    PieChart pieChart;
    String pravaIstina=FirebaseAuth.getInstance().getUid();
    ImageView imageView;
    EditText editText;
    ImageView crnina;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
imageView=root.findViewById(R.id.otvararacune);
imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(),com.example.reservezavlasnike.listaplacenosti.class));
    }
});
        Spinner spinCountry;
        spinCountry= (Spinner) root.findViewById(R.id.chartSpinner);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.Rezervacije));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        barChart=root.findViewById(R.id.graf);
        pieChart=root.findViewById(R.id.pieChart);
        spinCountry.setAdapter(adapter);
        spinCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
if(position==0)
    GetData();
else if(position==1){Loaddejta2();}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinCountry2;
        spinCountry2= (Spinner) root.findViewById(R.id.pieCharSpinner);//fetch the spinner from layout file
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.Grafovi));//setting the country_array to spinner
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinCountry2.setAdapter(adapter2);
        spinCountry2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 if(position==0)
                 {
                     barChart.setVisibility(View.INVISIBLE);
                     pieChart.setVisibility(View.VISIBLE);
                 }
              else {
                     barChart.setVisibility(View.VISIBLE);
                     pieChart.setVisibility(View.INVISIBLE);
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData();

crnina=root.findViewById(R.id.crnina);
editText=root.findViewById(R.id.spasiSifrom);
editText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
   if(editText.getText().toString().equals("1234"))
   {
   hideKeyboardFrom(getContext(),getView());

       Handler handler=new Handler();
       handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               editText.setVisibility(View.GONE);

               RelativeLayout relativeLayout=root.findViewById(R.id.relativicaja);
               int cx = crnina.getWidth() / 2;
               int cy = crnina.getHeight() / 2;

               // get the final radius for the clipping circle
               float finalRadius = (float) Math.hypot(cx, cy);

               // create the animator for this view (the start radius is zero)
               Animator anim = ViewAnimationUtils.createCircularReveal(crnina, cx, cy, finalRadius, 0f);
               anim.setDuration(500);
               anim.start();
               final Handler handler2 = new Handler();
               handler2.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       // Do something after 5s = 5000ms
                       crnina.setVisibility(View.GONE);
                   }
               }, 480);

           }
       }, 100);
   }


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});

        ArrayList<PieEntry> entries = new ArrayList<>();
        int count=3;
        int range=30;

        for (int i = 0; i < count ; i++) {
            String govno;
            if(i==0)
             govno="Marija";
            else if(i==1)
                govno="Jasna";
                        else govno="Alfonso";
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                  govno,
                    getResources().getDrawable(R.drawable.ic__kurvica)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        ArrayList<Integer> colors = new ArrayList<>();



        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);


        return root;
    }
    void LoadIng(ArrayList<String> nekaj, ArrayList<String> datumi){

barChart.clear();
        ArrayList<BarEntry> pare=new ArrayList<>();

       for(int i=0;i<7;i++){

            int prvi=Integer.parseInt(nekaj.get(i));
            int drugi=Integer.parseInt(datumi.get(i))%100;
            pare.add(new BarEntry(i+1,prvi));
        }
        int indeks=datumi.size()-1;
        String prvi=""+datumi.get(0).charAt(6)+datumi.get(0).charAt(7)+"."+datumi.get(0).charAt(4)+datumi.get(0).charAt(5)+"."+datumi.get(0).charAt(0)+datumi.get(0).charAt(1)+datumi.get(0).charAt(2)+datumi.get(0).charAt(3);
        String drugi=""+datumi.get(indeks).charAt(6)+datumi.get(indeks).charAt(7)+"."+datumi.get(indeks).charAt(4)+datumi.get(indeks).charAt(5)+"."+datumi.get(indeks).charAt(0)+datumi.get(indeks).charAt(1)+datumi.get(indeks).charAt(2)+datumi.get(indeks).charAt(3);

        String stringovich="Broj rezervacija "+prvi+"-"+drugi;
        BarDataSet barDataSet=new BarDataSet(pare,stringovich);



        barDataSet.setColor(Color.BLACK);
        barDataSet.setValueTextColor(Color.CYAN);
        barDataSet.setValueTextSize(15);
        BarData bardata=new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(bardata);
        barChart.getDescription().setText("Prvi graf");

    }
    void GetData(){

        DatabaseReference nadnevak;
        final String[] trenutniDatum = new String[1];
        nadnevak=FirebaseDatabase.getInstance().getReference().child("Datum").child("Danas");
nadnevak.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        trenutniDatum[0] =snapshot.getValue().toString();
        noviPodatci(trenutniDatum[0],1);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
    }
    void noviPodatci(String datum, int dani){
        DatabaseReference dataref;

        dataref=FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("Racuni");
        int prvi;
        int a=dajdatum(datum);
        int pocetna=Integer.parseInt(datum);
        int pocetna2=pocetna/10000;
        if(pocetna%10000/100==1)
        {pocetna2=pocetna2-1;

            pocetna2=pocetna2*100+12;}
        else { pocetna2=pocetna2*100+pocetna/10000%100-1;}

        pocetna2=pocetna2*100+a;
        ArrayList<String> djecica = new ArrayList<>();
        ArrayList<String> djecica2 = new ArrayList<>();
dataref.orderByKey().startAt(String.valueOf(pocetna2)).endAt(datum).addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        djecica.add(snapshot.getValue().toString());
        djecica2.add(snapshot.getKey().toString());
        if(djecica.size()==7)
        {  LoadIng(djecica,djecica2);}
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
        Toast.makeText(getContext(), "Nedovoljno podataka za analizu", Toast.LENGTH_SHORT).show();

    }
});

    }
    int dajdatum(String datum){
        int naks=  Integer.parseInt(datum);
        int dan=naks%10;
        if(dan>=7){return (naks-7);}
        else {
            if((naks%10000/100)%2==0){
                int a=naks%10;
                a=7-a;
                return(31-a+1);
            }
            else if((naks%10000/100)==3){ int a=naks%10;
                a=7-a;
                return(28-a+1);}
           else {  int a=naks%10;
                a=7-a;
                return(30-a+1);

            }
        }
    }
    void Loaddejta2(){
        ArrayList<String> nekakviareji = new ArrayList<>();
        ArrayList<String> datumi=new ArrayList<>();

        final int[] k = {0};
        final int[] z = {1};
        ArrayList<Integer> dodadada=new ArrayList<>();

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("Racuni");
        databaseReference.orderByKey().limitToLast(49).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                dodadada.add(Integer.parseInt(snapshot.getValue().toString()));
                if(k[0] ==6){int zvone=0;
                    for(int i=0;i<7;i++){zvone+=dodadada.get(i* z[0]);
                    }
                    datumi.add(String.valueOf(20201212));
                    nekakviareji.add(String.valueOf(zvone));
                    k[0] =-1;
                    z[0]++;
                }
                if(datumi.size()==7)
                {LoadIng(nekakviareji,datumi);

               k[0]=100;

                }
                k[0]++;

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
    
    void noviPoddatci(){
        DatabaseReference dejta;
        dejta=FirebaseDatabase.getInstance().getReference().child(pravaIstina).child("Racuni");
        Map<String ,String> zasNe = new HashMap<>();
        for(int i=0;i<30;i++){
            Toast.makeText(getContext(), "Loada se", Toast.LENGTH_SHORT).show();
            int vre=20201101+i;
            zasNe.put(String.valueOf(vre),String.valueOf( i%10));
        }
        for(int i=0;i<20;i++){
            int vre=20210101+i;
            zasNe.put(String.valueOf(vre),String.valueOf( i%10));
        }

        dejta.setValue(zasNe);
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}