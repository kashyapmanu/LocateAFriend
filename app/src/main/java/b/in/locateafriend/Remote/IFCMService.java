package b.in.locateafriend.Remote;

import java.util.Observable;

import b.in.locateafriend.Model.MyResponse;
import b.in.locateafriend.Model.Request;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface IFCMService {
    @Headers({
        "Content-Type:application/json",
            "Authorization:key=AAAAlB8w_1w:APA91bG8IaZDc9nivRD2qInBaBzFId13ULxSSHa7Bh-owzxSNqS5TWcOgnKIysdDQ7nmR-MuiYl67hyVFDLge5Is-A8gD5qI_D8g_m1Ca_zXTXS7CkCM8jE2oi3KzT_a7hZIxQsCDZ44"

    })

    @POST("fcm/send")
    //Observable<MyResponse> sendFriendRequestToUser(@Body Request body);
    io.reactivex.Observable<MyResponse> sendFriendRequestToUser(@Body Request body);

}
