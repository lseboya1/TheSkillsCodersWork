package za.co.lutendomlab.loginfirebase.student_activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import za.co.lutendomlab.loginfirebase.for_all_users.AboutUsActivity;
import za.co.lutendomlab.loginfirebase.adapter_and_object.CircleTransform;
import za.co.lutendomlab.loginfirebase.for_all_users.LoginActivity;
import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.Register;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;

public class HomeScreenUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView textViewUserEmail;
    private TextView textViewUserName;
    TextView student_email;
    String userName;
    String surname;
    private ImageView profile_Pic;
    ImageView imageView;
    ImageView mark_reg;
    TextView signed_in;
    TextView signed_out;
    TextView login_status;
    MenuItem markRegister;
    MenuItem apply_leave;
    MenuItem send_notification;
    String role;
    //image uploader

    int PICK_IMAGE_REQUEST = 111;
    String weekdays;
    String formattedDate;
    String time_in = "";
    String time_out;
    int week_number;
    String Sign;
    String userID;

    String dbKEy;

    private DatabaseReference databaseReferenceTimeSheet;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://the-skills-coders-work.appspot.com/");    //change the url according to your firebase app
    Uri filePath;
    Bitmap bitmap;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    View header;
    private FirebaseAuth firebaseAuth;

    private TimeTable timeTable;
    private String getSavedDate = "";

    Context context;
    String status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_user);
        context = getApplicationContext();

        this.timeTable = new TimeTable(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setSubtitle(R.string.unofficial);

        setSupportActionBar(toolbar);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        student_email = navigationView.getHeaderView(0).findViewById(R.id.student_email);
        signed_in = (TextView)findViewById(R.id.signed_in);
        signed_out = (TextView)findViewById(R.id.signed_out);

        getSavedDate = timeTable.getDate();

        login_status = (TextView)findViewById(R.id.login_status);

//        Sign = timeTable.getSigned();
//        signed_in.setText("Time in: "+getSavedDate);


        mark_reg = (ImageView)findViewById(R.id.mark_reg);
//        Picasso.with(context)
//                .load(R.drawable.in)
////                .placeholder(R.drawable.in)
//                .into(mark_reg);

        header = navigationView.findViewById(R.id.student_email);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        profile_Pic = (ImageView) findViewById(R.id.profile_picture);

        user = firebaseAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (user.getPhotoUrl() != null) {
                String url = user.getPhotoUrl().toString();
                Glide.with(context).load(url).into(profile_Pic);
                Glide.with(context).load(R.drawable.in).into(mark_reg);
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .transform(new CircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
            student_email.setText(user.getEmail());
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("User").child(userID);
        //db = database.getReference().child("User");
        databaseReferenceTimeSheet = database.getReference().child("TimeSheet").child(userID);

        textViewUserEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewUserName = (TextView) findViewById(R.id.textViewName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                role = user.getRole();

                userName = user.getName();
                surname = user.getLastName();
                textViewUserName.setText("Name: " + userName + ", " + surname);
                textViewUserEmail.setText("Email: " + user.getEmail());

                if(Objects.equals(user.getStatus(), "Disable")){
                    status = "Disable";
//                    Toast.makeText(HomeScreenUser.this,"status"+status, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {

        this.timeTable = new TimeTable(this);
        getSavedDate = timeTable.getDate();
        Sign = timeTable.getSigned();
//        signed_in.setText("Time in: "+getSavedDate);

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        markRegister = menu.findItem(R.id.markRegister);
        apply_leave = (MenuItem) findViewById(R.id.apply_leave);
        send_notification = (MenuItem)findViewById(R.id.send_notification);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(Objects.equals(user.getStatus(), "Disable")){
                    markRegister.setVisible(false);
//                    apply_leave.setVisible(false);
//                    send_notification.setVisible(false);
                     signed_in.setVisibility(View.GONE);
                     signed_out.setVisibility(View.GONE);
                    login_status.setText("Your account is not active! please contact your HR to activate your account.");

                }else {
                    markRegister.setVisible(true);
//                    apply_leave.setVisible(true);
//                    send_notification.setVisible(true);
//                    login_status.setText("Your account is not active! please contact your HR to activate your account.");

                }
//                Toast.makeText(HomeScreenUser.this,"status"+user., Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        int id = item.getItemId();

        switch (id) {

            case R.id.markRegister:
                signRegister();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.about:
                startActivity(new Intent(HomeScreenUser.this, AboutUsActivity.class));
                break;

            case R.id.update_profile:
                ViewProfileStudent();
                break;

            case R.id.send_notification:
                SendNotification();
                break;

            case R.id.apply_leave:
                ApplyForLeave();
                break;

            case R.id.logout:
                SignOut();
                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ViewProfileStudent(){

        Intent intent = new Intent(HomeScreenUser.this, ViewProfileStudent.class);
        startActivity(intent);
    }

    public void ApplyForLeave() {
//
        Intent intent = new Intent(HomeScreenUser.this, LeaveApply.class);
        intent.putExtra("name", userName);
        intent.putExtra("surname", surname);
        startActivity(intent);
    }

    public void SendNotification() {
        Intent intent = new Intent(this, SendMessage.class);
        startActivity(intent);
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

                if (!df.format(calendar.getTime()).equals(formattedDate)) {

                    time_in = date.format(currentLocalTime);
                    formattedDate = df.format(calendar.getTime());

                    timeTable.saveSigned("Sign in");
                    timeTable.saveNewDate(df.format(calendar.getTime()));

                    Register register = new Register(formattedDate, weekdays, month_year, time_in, time_out, weekNumber);
                    Map<String, Object> registerValues = register.toMap();

//                    Map<String, Object> childUpdate = new HashMap<>();
//                    childUpdate.put(userID, registerValues);
//                    databaseReferenceTimeSheet.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).updateChildren(registerValues)

                    dbKEy = databaseReferenceTimeSheet.push().getKey();
                    databaseReferenceTimeSheet.child(month_year).child(dbKEy).updateChildren(registerValues);

                    mark_reg = (ImageView)findViewById(R.id.mark_reg);
                    Glide.with(context).load(R.drawable.in).into(mark_reg);
                    login_status.setText("You sign in today's register");

                    signed_in.setText("Time in: "+time_in);

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

                Sign = "Sign_out";

                if (time_in.equals("")) {
                    Toast.makeText(HomeScreenUser.this, "you didnt sign in", Toast.LENGTH_LONG).show();
                } else {
                    weekdays = Day.format(calendar.getTime());
                    time_out = date.format(currentLocalTime);
                    Register register = new Register(formattedDate, weekdays, month_year, time_in, time_out, weekNumber);
                    Map<String, Object> registerValues = register.toMap();

//                    Map<String, Object> childUpdate = new HashMap<>();
//                    childUpdate.put(userID, registerValues);
//                    databaseReferenceTimeSheet.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).updateChildren(registerValues);

                    databaseReferenceTimeSheet.child(month_year).child(dbKEy).updateChildren(registerValues);

//                    databaseReferenceTimeSheet.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).child("timeOut").setValue(time_out);

//                    databaseReferenceTimeSheet.child(month_year).child(databaseReferenceTimeSheet.getKey()).setValue(time_out);

                    mark_reg = (ImageView)findViewById(R.id.mark_reg);
                    Glide.with(context).load(R.drawable.out).into(mark_reg);

                    login_status.setText("You signed today's register");

//                    Toast.makeText(HomeScreenUser.this, "Signed i", Toast.LENGTH_SHORT).show();
                    signed_out.setText("Time out: "+time_out);
                    timeTable.saveSigned("Sign_out");

                    markRegister.setVisible(false);

                }
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting image to ImageView
                profile_Pic.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);

                // uploading
                if (filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child(user.getUid() + ".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot task) {
                            Uri downloadUrl = task.getDownloadUrl();
                            assert downloadUrl != null;
                            String profile_url = downloadUrl.toString();


                            pd.dismiss();
                            Toast.makeText(HomeScreenUser.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(profile_url))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(HomeScreenUser.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(HomeScreenUser.this, "Select an image", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SignOut() {

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}