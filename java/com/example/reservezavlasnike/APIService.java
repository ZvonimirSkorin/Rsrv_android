package com.example.reservezavlasnike;

import com.example.reservezavlasnike.Notifications.MyResponse;
import com.example.reservezavlasnike.Notifications.NotificationSender;
import com.example.reservezavlasnike.Notifications.Sender;
import com.example.reservezavlasnike.Notifications.MyResponse;
import com.example.reservezavlasnike.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(


            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAuSQ1-BI:-BhFbQdPEd_LOIZ-JuBB4HWy"



            }
            )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
