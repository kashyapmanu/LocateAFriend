package b.in.locateafriend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_location_on_black_24dpp)
                .setDescription("Locate a Friend\nWe are amateurs,but we try our best.")
                .addGroup("Connect with us")
                .addGroup("Manukashyap U V")
                .addEmail("manukashyap.u.v@gmail.com")
                .addFacebook("manu.kashyap.505")
                .addGitHub("kashyapmanu")
                .addInstagram("kashyap_manu_")
                .addGroup("Manjunath Patkar")
                .addEmail("manjunathapatkar150@gmail.com")
                .addFacebook("manjunathapatkar")
                .addGitHub("ManjunathaPatkar")
                .addInstagram("manjunathapatkar")
                .addGroup("Oliver B")
                .addEmail("manjunathapatkar150@gmail.com")
                .addFacebook("manjunathapatkar")
                .addGitHub("ManjunathaPatkar")
                .addInstagram("manjunathapatkar")
                .create();

        setContentView(aboutPage);
    }
}
