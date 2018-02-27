package za.co.lutendomlab.loginfirebase.for_all_users;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import za.co.lutendomlab.loginfirebase.R;

public class SpashScreenActivity extends AppCompatActivity {

    //Splash screen timer
    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
            * Showing splash screen with a timer. This will be useful when you
            * want to show case your app logo / company
            */
            @Override
            public void run() {

                Intent intent = new Intent(SpashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}