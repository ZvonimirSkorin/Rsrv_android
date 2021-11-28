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
                    "Authorization:key=AAAAuSQ1-BI:APA91bHeR468I6EA0HkicscKMLo4ywrgsaaJicCotJ5D67j8nGL83XOahhGqiW2LVxO3Vdccckc7nk8wVE5i8Ka9tMzFCDxP5OP63dUzJ_jmwpfw1PMi-BhFbQdPEd_LOIZ-JuBB4HWy"



            }
            )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}
