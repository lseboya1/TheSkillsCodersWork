package za.co.lutendomlab.loginfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HomeScreenUser extends AppCompatActivity {


    private TextView textViewUserEmail;
    private TextView textViewUserName;
    private TextView staff_number;
    String userName;
    String weekdays;
    String formattedDate;
    String time_in = "";
    String time_out;
    int week_number;
    String Sign;
    int count = 1;
    private DatabaseReference db;
    FirebaseUser user;
    String userID;
    String role;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_user);

        getSupportActionBar().setTitle("Home Page");

        firebaseAuth = FirebaseAuth.getInstance();
        User users = new User();

        user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this,LoginActivity.class));
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        db = database.getReference().child("User");

        userID = user.getUid();


        Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();
        staff_number = (TextView) findViewById(R.id.staff_number);

        textViewUserName = (TextView) findViewById(R.id.textViewName);


        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                // db.child()

                if (userID.equals(dataSnapshot.child("userId").getValue().toString())) {

                    Register reg = dataSnapshot.getValue(Register.class);

                    User user = dataSnapshot.getValue(User.class);

                    role = user.getRole();


                    userName = user.getName();
                    textViewUserName.setText("Name: " + userName);

//                textViewUserName.setText("Name: "+user.getName());
                    //  Toast.makeText(HomeScreenUser.this, user.getName(), Toast.LENGTH_SHORT).show();
                    staff_number.setText(String.valueOf("Staff No: " + user.getStaffNO()));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if ("Admin".equals(role)) {
            finish();
            Intent intent1 = new Intent(HomeScreenUser.this, AdminActivity.class);
            startActivity(intent1);
        }

        textViewUserEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewUserEmail.setText("Email :" + user.getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Sign_uot:

                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;
            case R.id.Register:
                signRegister();
                break;

            case R.id.delete_account:

                //get current user
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(HomeScreenUser.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(HomeScreenUser.this, SignupActivity.class));
                                        finish();

                                    } else {
                                        Toast.makeText(HomeScreenUser.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                break;

            case R.id.change_password:

                Intent intent2 = new Intent(HomeScreenUser.this, ChangePassword.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ApplyForLeave(View view) {

        Intent intent = new Intent(this, LeaveApply.class);
        startActivity(intent);
    }

    public void SendNotification(View view) {
        Intent intent = new Intent(this, SendMessage.class);
        startActivity(intent);
    }

    public void UpdateProfile(View view) {
        Toast.makeText(HomeScreenUser.this, "Update profile", Toast.LENGTH_SHORT).show();
    }

    public void signRegister() {

        //current date
        final Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("dd, MMMM , yyyy");

        //time
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        final Date currentLocalTime = cal.getTime();
        final DateFormat date = new SimpleDateFormat("KK:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));

        //month
        final SimpleDateFormat month = new SimpleDateFormat("MMMM");
        final String month_year = month.format(calendar.getTime());

        //week
        week_number = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        final String weekNumber = Integer.toString(week_number);

        //Day
        final SimpleDateFormat Day = new SimpleDateFormat("EEEE");
        weekdays = Day.format(calendar.getTime());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreenUser.this);
        // Setting Dialog Title
        alertDialog.setTitle("     Mark Register");

        // Setting Dialog Message
        alertDialog.setMessage("You want to sign IN ot OUT.?");

        alertDialog.setPositiveButton("Sign IN \t", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
//                Intent intent1 = new Intent(HomeScreenUser.this, MapsActivity.class);
//                startActivity(intent1);

                //Getting content for email
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String email = "lseboya101@gmail.com";
//                String subject = "Att Register ";
////            String message = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " is now " + "Present \n " + currentDateTimeString;
//                String message = userName + " is now " + "Absent (NOT IN) \n " + currentDateTimeString;
//
//                //Toast.makeText(HomeScreenUser.this," " +message,Toast.LENGTH_LONG).show();
//                //Creating SendMail object
//                SendMail sm = new SendMail(HomeScreenUser.this, email, subject, message);
//
//                //Executing sendmail to send email
//                sm.execute();

                if (!df.format(calendar.getTime()).equals(formattedDate)) {

                    time_in = date.format(currentLocalTime);
                    formattedDate = df.format(calendar.getTime());
                    Sign = "Sign_in";


                    // db= database.getReference().child(month_year);

//Register regi = new Register(formattedDate,weekdays,month_year,time_in,time_out,weekNumber);

                    db.child(user.getUid()).child("Register");
                    db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("month").setValue(month_year);
                    db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("weekNumbr").setValue(weekNumber);
                    db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("day").setValue(weekdays);
                    db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("date").setValue(formattedDate);
                    db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("timeIn").setValue(time_in);


                  /* db.child(user.getUid()).child("Register");
                    db.child(user.getUid()).child("Register").child("Month").setValue(month_year);
                    db.child(user.getUid()).child("Register").child("Week").setValue(weekNumber);
                    db.child(user.getUid()).child("Register").child("Days").setValue(weekdays);
                            db.child(user.getUid()).child("Register").child("month").setValue(month_year);*/
                    //  db.child(user.getUid()).child("Month").child(month_year).child("Week").child(weekNumber).child("Days").child(weekdays).child("weekNumbr").setValue(weekNumber);
                    // db.child(user.getUid()).child("Month").child(month_year).child("Week").child(weekNumber).child("Days").child(weekdays).child("day").setValue(weekdays);

                    /**
                     * save to fireBase
                     * Month
                     * Week number
                     * Day of the week
                     * Date
                     * Time in
                     */
                    Toast.makeText(HomeScreenUser.this, "Signed in", Toast.LENGTH_SHORT).show();
                } else if (!Day.format(calendar.getTime()).equals(weekdays)) {
                    Sign = "Sign_in";


                    /**
                     * save to fireBase
                     * Date
                     * Time in
                     */
                } else {
                    Toast.makeText(HomeScreenUser.this, "you already signed in for today", Toast.LENGTH_LONG).show();

                }
            }
        });
        alertDialog.setNegativeButton("Sign Out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                db.child(user.getUid()).child("Register").child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("timeOut").setValue(time_in);

                //Toast.makeText(HomeScreenUser.this,"Registered",Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(HomeScreenUser.this, MapsActivity.class);
//                startActivity(intent1);

                //Getting content for email
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String email = "lseboya101@gmail.com";
//                String subject = "Att Register ";
////            String message = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " is now " + "Present \n " + currentDateTimeString;
//                String message = userName + " is now " + "Signed out \n " + currentDateTimeString;
//
//               // Toast.makeText(HomeScreenUser.this," " +message,Toast.LENGTH_LONG).show();
//                //Creating SendMail object
//                SendMail sm = new SendMail(HomeScreenUser.this, email, subject, message);
//
//                //Executing sendmail to send email
//                sm.execute();
                Sign = "Sign_out";

                if (time_in.equals("")) {
                    Toast.makeText(HomeScreenUser.this, "you didnt sign in", Toast.LENGTH_LONG).show();
                } else {
                    weekdays = Day.format(calendar.getTime());
                    time_out = date.format(currentLocalTime);

                    /**
                     * save to firebbse
                     * Time out
                     */
                    Toast.makeText(HomeScreenUser.this, "Signed in", Toast.LENGTH_SHORT).show();
                    time_in = "";
                }
            }
        });

        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}

