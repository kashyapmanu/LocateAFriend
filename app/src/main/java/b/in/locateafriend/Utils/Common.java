package b.in.locateafriend.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import b.in.locateafriend.Model.User;
import b.in.locateafriend.Remote.IFCMService;
import b.in.locateafriend.Service.RetrofitClient;

public class Common {
    public static final String USER_INFORMATION = "UserInformation";
    public static final String USER_UID_SAVE_KEY = "SaveUid";
    public static final String TOKENS = "Tokens";
    public static final String FROM_NAME = "FromName";
    public static final String ACCEPT_LIST = "acceptList";
    public static final String FROM_UID = "FromUid";
    public static final String TO_UID = "ToUid";
    public static final String TO_NAME = "ToName";
    public static final String FRIEND_REQUEST = "FriendRequests";
    public static final String PUBLIC_LOCATION = "PublicLocation";
    public static User loggedUser;
    public static User trakingUser;

    public static IFCMService getFCMService() {
        return RetrofitClient.getClient("https://fcm.googleapis.com/")
                .create(IFCMService.class);
    }

    public static Date convertTimeStampToDate(long time) {
        return new Date(new Timestamp(time).getTime());
    }

    public static String getDateFormatted(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date).toString();
    }
}
