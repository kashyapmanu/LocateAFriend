package b.in.locateafriend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import b.in.locateafriend.Utils.Common;
import mehdi.sakout.aboutpage.AboutPage;

public class FeedBackActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_location_on_black_24dpp)
                .setDescription("Locate a Friend\nWe are amateurs,but we try our best.")
                .addGroup("If you have any feedback,Please mail us")
                .addGroup("Manukashyap U V")
                .addEmail("manukashyap.u.v@gmail.com")
                .addGroup("Manjunath Patkar")
                .addEmail("manjunathapatkar150@gmail.com")
                .create();

        setContentView(aboutPage);


    }


}
