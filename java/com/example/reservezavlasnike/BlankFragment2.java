package com.example.reservezavlasnike;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.datatransport.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.StringValue;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView;
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank2, container, false);
       TextView textView1=root.findViewById(R.id.nadnevak2);
        CalendarView calendarView=root.findViewById(R.id.kalendar2);
        calendarView.setVisibility(View.INVISIBLE);
        final int[] k = {0};
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(k[0] !=0){
                    calendarView.setVisibility(View.INVISIBLE);
                    k[0] =0;
                }else{calendarView.setVisibility(View.VISIBLE);
                    k[0]=10;}
            }
        });
        DatabaseReference databaseRef= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("usluge");
        ArrayList<String>popisUsluga=new ArrayList<>();
        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    String event=data.getKey().toString();
                    popisUsluga.add(event);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Boolean istina;
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

                String datum=""+godina+mjesec+dan;
                RecyclerView recyclerView;
                recyclerView = root.findViewById(R.id.recyclerView3);
                //        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);                recyclerView.setLayoutManager(linearLayoutManager);
                FirebaseRecyclerAdapter<cijenik,MyViewHolder5> adapter;
                FirebaseRecyclerOptions<cijenik> options;
                ArrayList<Integer> stanje2=new ArrayList<>();
                DatabaseReference dataRef=FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("usluge");

                Query query=dataRef.orderByChild("trajanje").limitToLast(100);
                ArrayList<Boolean> stanje=new ArrayList<>();
                final int[] brojac = {0};
                options=new FirebaseRecyclerOptions.Builder<cijenik>().setQuery(query, cijenik.class).build();
                final boolean[] kae = {true};
                adapter=new FirebaseRecyclerAdapter<cijenik, MyViewHolder5>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyViewHolder5 holder, int position, @NonNull cijenik model) {
                        holder.textView.setText(model.getVrstaUsluge());
                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean istina=true;
                                for(int i=0;i<stanje2.size();i++)
                                {  if(stanje2.get(i)==holder.getAdapterPosition())
                                {  istina=false;
                                    stanje2.remove(i);
                                }

                                }
                                if(istina==true) {
                                    holder.itemView.setBackground(getResources().getDrawable(R.drawable.colorica));
                                    brojac[0] += model.getTrajanje();
                                    stanje2.add(holder.getAdapterPosition());



                                }else{ brojac[0]-=model.getTrajanje();
                                    holder.itemView.setBackground(getResources().getDrawable(R.drawable.cutomacha));
                                    if(brojac[0]==0){
                                        kae[0] =false;
                                    }
                                }
                                if(brojac[0]!=0)
Loadaj(datum,brojac[0]);
                   if(kae[0]==false){
                       kae[0]=true;
                       Loadaj(datum,brojac[0]);
                   }
                            }

                        });
                        //ucitavanje slike
                        String modelinjo=model.getSpol();
                        if(modelinjo.equals("M")){
                            holder.imageView.setBackground(getResources().getDrawable(R.drawable.ic_smile));
                        }
                        else if(modelinjo.equals("Ž")){
                            holder.imageView.setBackground(getResources().getDrawable(R.drawable.ic__kurvica));
                        }
                        else {
                            holder.imageView.setBackground(null);
                            holder.ljeva.setBackground(getResources().getDrawable(R.drawable.ic_smile));
                            holder.desna.setBackground(getResources().getDrawable(R.drawable.ic__kurvica));

                        }


                    }

                    @NonNull
                    @Override
                    public MyViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.usluge, parent, false);
                        return new MyViewHolder5(v);
                    }

                };
                adapter.startListening();
                recyclerView.setAdapter(adapter);

            }
        });
        //za spinner




        return root;

    }

    void Loadaj(String datum,int brojac){

        ArrayList<String> termini=new ArrayList<>();
        int n=0;
        Boolean budala=false;
        DatabaseReference dataRef=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child(datum);
        dataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 String nest=snapshot.child("status").getValue().toString();
String cajt=snapshot.child("termin").getValue().toString();
                  if(nest.equals("Slobodno")){
                      termini.add(cajt);
                  }
                  if(cajt.equals("1930")){
                      citajNanovo(termini,brojac,datum);
                  }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               Loadaj(datum,brojac);
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
    void citajNanovo(ArrayList<String> termini, int potrebno, String godijano){
        ArrayList<String> termini2=new ArrayList<>();

        DatabaseReference databaseRef=FirebaseDatabase.getInstance().getReference().child("Gloria").child("skok");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              float n=Float.parseFloat(  snapshot.getValue().toString())*5/3;
                Toast.makeText(getContext(), "Vrijednost n je"+String.valueOf(n), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "Tu smo"+String.valueOf(n), Toast.LENGTH_SHORT).show();
 int kal=0;
                  DecimalFormat df2 = new DecimalFormat("#.##");
                for(int i=0;i<termini.size();i++){
                    int brojac=0;
                    {for(int j=i+1;j<termini.size()-1;j++){
                        double x1=Float.parseFloat(termini.get(j));


                        double x2=Float.parseFloat(termini.get(j-1));

                       double x11=((x1%100))*5/3 +(x1-x1%100);
                       double x22=((x2%100))*5/3+(x2-x2%100);
                       x1=x11;
                       x2=x22;

                       boolean izbacivanje=false;
                       // Toast.makeText(getContext(), "Ovo us brojke= "+String.valueOf(x1)+"  "+ String.valueOf(x2), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(),String.valueOf(x1-x2), Toast.LENGTH_SHORT).show();

                        if((int)(x1-x2)<=n)
                        {
                            brojac+=n;

                        }
                        else {brojac+=n;
                          izbacivanje=true;
                        }
                        if(brojac>=(float)potrebno*5/3){
                            String zavrsni="";
                            int duzina=termini.get(i).length();
                            for(int k=0;k<duzina;k++){
                                if(k==duzina-2)
                                    zavrsni=zavrsni+":";

                                zavrsni=zavrsni+termini.get(i).charAt(k);
                            }
                            termini2.add(zavrsni);


                            break;
                        }
                        if(izbacivanje==true){
                            break;
                        }
                    }
                    }
                }

                ucitaj(termini2,godijano,String.valueOf(potrebno));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void ucitaj(ArrayList<String> termini, String godijano, String potrebno){

       RecyclerView recyclerView2 = (RecyclerView) getView().findViewById(R.id.recyclerView2);
       // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView2.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager);
       ArrayList<String> input = new ArrayList<>();

       RecyclerView.Adapter mAdapter = new MyAdapter(termini);
     recyclerView2.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
         @Override
         public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
             return false;
         }

         @Override
         public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
     Toast.makeText(getContext(),"bok",Toast.LENGTH_LONG);
         }

         @Override
         public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

         }
     });
        recyclerView2.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ItemClickSupport.addTo(recyclerView2).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String  majka=termini.get(position);
                String govance="";
                for(int i=0;i<majka.length();i++){
                    if(i!=majka.length()-3){
                        govance=govance+majka.charAt(i);
                    }

                }

promjeni(govance,godijano,potrebno);
            }
        });
    }

    void promjeni(String terminT,String godijano,String potrebno){
        CalendarView calendarView=getView().findViewById(R.id.kalendar2);

        SimpleDateFormat sdfs = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate2 = sdfs.format(calendarView.getDate());
        String s=""+selectedDate2.charAt(6)+selectedDate2.charAt(7)+selectedDate2.charAt(8)+
                selectedDate2.charAt(9)+selectedDate2.charAt(3)+selectedDate2.charAt(4)+
        selectedDate2.charAt(0)+selectedDate2.charAt(1);


        Toast.makeText(getContext(), godijano, Toast.LENGTH_SHORT).show();
        DatabaseReference dejtano=FirebaseDatabase.getInstance().getReference().child("Gloria").child("Termini").child(godijano);
     float x2=Integer.parseInt(potrebno);
float x1=Integer.parseInt(terminT);

float x7=x1%100 + x2;
float x6=((int)x1/100)*100 + ((int)x7/60)*100 +(x7%60);

     String kako2=String.valueOf((int)x6);
        Toast.makeText(getContext(), potrebno+"==="+kako2, Toast.LENGTH_SHORT).show();
      Query query=dejtano.orderByKey().startAt(terminT).endAt(kako2);

      query.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              if(snapshot.getKey().equals(terminT))
                  snapshot.child("status").getRef().setValue("Zauzeto");
                          else snapshot.getRef().removeValue();



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
        /*dejtano.setValue("Zauzeto").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Rezervacija uspješno zaprimljena", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Nešto je pošlo po zlu. Molim vas ponovite rezervaciju.", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(getContext(),com.example.reservezavlasnike.MainActivity.class));
            }
        });*/
    }


}