package com.example.reservezavlasnike;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank, container, false);

        RecyclerView recyclerView= view.findViewById(R.id.reciklirajBrate);
        TextView textView=view.findViewById(R.id.nadnevakFragment);
        textView.setText("Srbija do tokija");


        if(getArguments()!=null) {
            Bundle bundle = getArguments();
            String s = "go";
            if (bundle != null) {
                RelativeLayout relativeLayout=view.findViewById(R.id.sjebosisustav);
                relativeLayout.setVisibility(View.VISIBLE);
                s = bundle.getString("govno");
                textView.setText(lijepidatum(s));
                DatabaseReference dataRef;
                dataRef= FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("zaplatit").child(s).child("likovi");
                Query query;
                RecyclerView recyclerView2=view.findViewById(R.id.reciklirajBrate);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
                recyclerView2.setLayoutManager(linearLayoutManager);
                query=dataRef.orderByChild("termin");
                FirebaseRecyclerOptions<termin> options;
                FirebaseRecyclerAdapter<termin,MyViewHolder3> adapter;
                options=new FirebaseRecyclerOptions.Builder<termin>().setQuery(query, termin.class).build();
                adapter=new FirebaseRecyclerAdapter<termin, MyViewHolder3>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyViewHolder3 holder, int position, @NonNull termin model) {

                     String termin=model.getTermin();
                     String termin2=""+ termin.charAt(0)+termin.charAt(1)+":"+termin.charAt(2)+termin.charAt(3);
                     holder.lik.setText(termin2);
                     holder.termin.setText(model.getKorisnik());
                    }

                    @NonNull
                    @Override
                    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlbratresjakler, parent, false);
                        return new MyViewHolder3(v);
                    }
                };
                adapter.startListening();
                recyclerView2.setAdapter(adapter);
            }
        }
        return view;
    }
    void Loadajbre(String s){


    }
    String lijepidatum(String s){
        String povratni="";
        for(int i=0;i<8;i++){
            povratni+=s.charAt(i);
            if(i==3 || i==5)
                povratni+="/";
        }

        return povratni;

    }
}