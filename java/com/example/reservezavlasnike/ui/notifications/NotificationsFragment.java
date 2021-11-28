package com.example.reservezavlasnike.ui.notifications;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaDrm;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservezavlasnike.APIService;
import com.example.reservezavlasnike.MyRecyclerViewAdapter;
import com.example.reservezavlasnike.Notifications.Client;
import com.example.reservezavlasnike.Notifications.Data;
import com.example.reservezavlasnike.Notifications.MyResponse;
import com.example.reservezavlasnike.Notifications.NotificationSender;
import com.example.reservezavlasnike.Notifications.Sender;
import com.example.reservezavlasnike.Notifications.Token;
import com.example.reservezavlasnike.R;

import com.google.android.datatransport.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.AM;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        Spinner spinCountry;
        spinCountry= (Spinner) root.findViewById(R.id.spiner123);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.Rezano));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCountry.setAdapter(adapter);

  // Get a reference for the week view in the layout.
  /*      {     EditText UserTB=root.findViewById(R.id.userid);
        EditText Message=root.findViewById(R.id.poruka);
        EditText Title=root.findViewById(R.id.naslov);
        APIService apiService;
        UserTB.setText(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        Button send=root.findViewById(R.id.button);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(UserTB.getText().toString().toString()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String usertoken=snapshot.getValue().toString();
                        Toast.makeText(getContext(), usertoken, Toast.LENGTH_SHORT).show();
                        sendNotifications(usertoken, Title.getText().toString().trim(),Message.getText().toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        UpdateToken();}*/

        RecyclerView recyclerView=root.findViewById(R.id.posljedniresajkler);
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Roga");
        animalNames.add("Jakov");


        // set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyRecyclerViewAdapter adapter2;
         adapter2 = new MyRecyclerViewAdapter(getContext(), animalNames);
        recyclerView.setAdapter(adapter2);
        return root;
    }
    private void UpdateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);

    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        Sender sender1=new Sender(data,usertoken);
        APIService apiService=Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(getContext(), "Failed ", Toast.LENGTH_LONG);
                    }}
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }

}