package b.in.locateafriend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void GoToHome(View view) {
        startActivity(new Intent(Welcome.this,HomeActivity.class));
        finish();
    }


    public void startApp(View view) {
        startActivity(new Intent(Welcome.this,HomeActivity.class));

    }
}
