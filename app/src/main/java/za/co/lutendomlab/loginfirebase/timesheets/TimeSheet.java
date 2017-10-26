package za.co.lutendomlab.loginfirebase.timesheets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.User;

public class TimeSheet extends AppCompatActivity {

    String formattedDate;
    String timein;
    String timeout;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);


        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");

        final Calendar calendar = Calendar.getInstance();

        //month
        final SimpleDateFormat month = new SimpleDateFormat("MMMM");
        final String month_year = month.format(calendar.getTime());

        TextView name = (TextView)findViewById(R.id.name);
        TextView location = (TextView)findViewById(R.id.location);
        TextView month2 = (TextView)findViewById(R.id.month);


        month2.setText(String.format("Month: %s", month_year));
        name.setText("Name: " + user.getName() + " " + user.getLastName());
        location.setText(String.format("Location: %s", user.getFacility()));
    }
}
