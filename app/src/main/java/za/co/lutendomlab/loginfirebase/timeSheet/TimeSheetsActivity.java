package za.co.lutendomlab.loginfirebase.timeSheet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.Register;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;

public class TimeSheetsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

//    TableLayout tl;
//    TableRow tr;
//    TextView companyTV;
//    TextView valueTV;
//
//    ArrayList<String> time_out = new ArrayList<>();
//    ArrayList<String> formattedDate = new ArrayList<>();
//    ArrayList<String> time_in = new ArrayList<>();
//    ArrayList<String> weekdays = new ArrayList<>();
//    ArrayList<String> week_number = new ArrayList<>();

//    String [] weekdays;
//    String [] formattedDate;
//    String [] time_out;
//    String [] time_in;
//    ArrayList <String> week_number;


    TextView monday_date_week_1, monday_in_week_1,monday_out_week_1;
    TextView tuesday_date_week_1 ,tuesday_in_week_1,tuesday_out_week_1;
    TextView wednesday_date_week_1,wednseday_in_week_1,wednesday_out_week_1;
    TextView thursday_date_week_1,thursday_in_week_1,thursday_out_week_1;
    TextView friday_date_week_1, friday_in_week_1, friday_out_week_1;

    TextView monday_date_week_2, monday_in_week_2,monday_out_week_2;
    TextView tuesday_date_week_2 ,tuesday_in_week_2,tuesday_out_week_2;
    TextView wednesday_date_week_2,wednseday_in_week_2,wednesday_out_week_2;
    TextView thursday_date_week_2,thursday_in_week_2,thursday_out_week_2;
    TextView friday_date_week_2, friday_in_week_2, friday_out_week_2;

    TextView monday_date_week_3, monday_in_week_3,monday_out_week_3;
    TextView tuesday_date_week_3 ,tuesday_in_week_3,tuesday_out_week_3;
    TextView wednesday_date_week_3,wednseday_in_week_3,wednesday_out_week_3;
    TextView thursday_date_week_3,thursday_in_week_3,thursday_out_week_3;
    TextView friday_date_week_3, friday_in_week_3, friday_out_week_3;

    TextView monday_date_week_4, monday_in_week_4,monday_out_week_4;
    TextView tuesday_date_week_4 ,tuesday_in_week_4,tuesday_out_week_4;
    TextView wednesday_date_week_4,wednseday_in_week_4,wednesday_out_week_4;
    TextView thursday_date_week_4,thursday_in_week_4,thursday_out_week_4;
    TextView friday_date_week_4, friday_in_week_4, friday_out_week_4;

    User user;
    Register register;
    String userId;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;

    TextView month;
    TextView location;
    TextView name;

    Spinner month_list;
    String monthSelected;
    String [] monthList = {"January","February","March","April", "May","June", "July"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        month_list = (Spinner)findViewById(R.id.month_list);
        month_list.setOnItemSelectedListener(this);

        ArrayAdapter monthSpinner = new ArrayAdapter(this,android.R.layout.simple_spinner_item,monthList);
        monthSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_list.setAdapter(monthSpinner);

        //week  one
        monday_date_week_1 = (TextView)findViewById(R.id.monday_date_week_1);
        monday_in_week_1 = (TextView)findViewById(R.id.monday_in_week_1);
        monday_out_week_1 = (TextView)findViewById(R.id.monday_out_week_1);

        tuesday_date_week_1 = (TextView)findViewById(R.id.tuesday_date_week_1);
        tuesday_in_week_1 = (TextView)findViewById(R.id.tuesday_in_week_1);
        tuesday_out_week_1 = (TextView)findViewById(R.id.tuesday_out_week_1);

        wednesday_date_week_1 = (TextView)findViewById(R.id.wednesday_date_week_1);
        wednseday_in_week_1 = (TextView)findViewById(R.id.wednseday_in_week_1);
        wednesday_out_week_1 = (TextView)findViewById(R.id.wednesday_out_week_1);

        thursday_date_week_1 = (TextView)findViewById(R.id.thursday_date_week_1);
        thursday_in_week_1 = (TextView)findViewById(R.id.thursday_in_week_1);
        thursday_out_week_1 = (TextView)findViewById(R.id.thursday_out_week_1);

        friday_date_week_1 = (TextView)findViewById(R.id.friday_date_week_1);
        friday_in_week_1 = (TextView)findViewById(R.id.friday_in_week_1);
        friday_out_week_1 = (TextView)findViewById(R.id.friday_out_week_1);

        //week two
        monday_date_week_2 = (TextView)findViewById(R.id.monday_date_week_2);
        monday_in_week_2 = (TextView)findViewById(R.id.monday_in_week_2);
        monday_out_week_2 = (TextView)findViewById(R.id.monday_out_week_2);

        tuesday_date_week_2 = (TextView)findViewById(R.id.tuesday_date_week_2);
        tuesday_in_week_2 = (TextView)findViewById(R.id.tuesday_in_week_2);
        tuesday_out_week_2 = (TextView)findViewById(R.id.tuesday_out_week_2);

        wednesday_date_week_2 = (TextView)findViewById(R.id.wednesday_date_week_2);
        wednseday_in_week_2 = (TextView)findViewById(R.id.wednseday_in_week_2);
        wednesday_out_week_2 = (TextView)findViewById(R.id.wednesday_out_week_2);

        thursday_date_week_2 = (TextView)findViewById(R.id.thursday_date_week_2);
        thursday_in_week_2 = (TextView)findViewById(R.id.thursday_in_week_2);
        thursday_out_week_2 = (TextView)findViewById(R.id.thursday_out_week_2);

        friday_date_week_2 = (TextView)findViewById(R.id.friday_date_week_2);
        friday_in_week_2 = (TextView)findViewById(R.id.friday_in_week_2);
        friday_out_week_2 = (TextView)findViewById(R.id.friday_out_week_2);

        //week three
        monday_date_week_3 = (TextView)findViewById(R.id.monday_date_week_3);
        monday_in_week_3 = (TextView)findViewById(R.id.monday_in_week_3);
        monday_out_week_3 = (TextView)findViewById(R.id.monday_out_week_3);

        tuesday_date_week_3 = (TextView)findViewById(R.id.tuesday_date_week_3);
        tuesday_in_week_3 = (TextView)findViewById(R.id.tuesday_in_week_3);
        tuesday_out_week_3 = (TextView)findViewById(R.id.tuesday_out_week_3);

        wednesday_date_week_3 = (TextView)findViewById(R.id.wednesday_date_week_3);
        wednseday_in_week_3 = (TextView)findViewById(R.id.wednseday_in_week_3);
        wednesday_out_week_3 = (TextView)findViewById(R.id.wednesday_out_week_3);

        thursday_date_week_3 = (TextView)findViewById(R.id.thursday_date_week_3);
        thursday_in_week_3 = (TextView)findViewById(R.id.thursday_in_week_3);
        thursday_out_week_3 = (TextView)findViewById(R.id.thursday_out_week_3);

        friday_date_week_3 = (TextView)findViewById(R.id.friday_date_week_3);
        friday_in_week_3 = (TextView)findViewById(R.id.friday_in_week_3);
        friday_out_week_3 = (TextView)findViewById(R.id.friday_out_week_3);

        //week four
        monday_date_week_4 = (TextView)findViewById(R.id.monday_date_week_4);
        monday_in_week_4 = (TextView)findViewById(R.id.monday_in_week_4);
        monday_out_week_4 = (TextView)findViewById(R.id.monday_out_week_4);

        tuesday_date_week_4 = (TextView)findViewById(R.id.tuesday_date_week_4);
        tuesday_in_week_4 = (TextView)findViewById(R.id.tuesday_in_week_4);
        tuesday_out_week_4 = (TextView)findViewById(R.id.tuesday_out_week_4);

        wednesday_date_week_4 = (TextView)findViewById(R.id.wednesday_date_week_4);
        wednseday_in_week_4 = (TextView)findViewById(R.id.wednseday_in_week_4);
        wednesday_out_week_4 = (TextView)findViewById(R.id.wednesday_out_week_4);

        thursday_date_week_4 = (TextView)findViewById(R.id.thursday_date_week_4);
        thursday_in_week_4 = (TextView)findViewById(R.id.thursday_in_week_4);
        thursday_out_week_4 = (TextView)findViewById(R.id.thursday_out_week_4);

        friday_date_week_4 = (TextView)findViewById(R.id.friday_date_week_4);
        friday_in_week_4 = (TextView)findViewById(R.id.friday_in_week_4);
        friday_out_week_4 = (TextView)findViewById(R.id.friday_out_week_4);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");

        userId = user.getUserId();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("TimeSheet");
//        databaseReference = firebaseDatabase.getReference().child("TimeSheet");
//        childEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//
//                    register = snapshot.getValue(Register.class);
//
//                    Toast.makeText(TimeSheetsActivity.this, "User Key = "+ snapshot.getKey(), Toast.LENGTH_SHORT).show();
//
//                    if(snapshot.getKey().toString().equals(userId)){
//                    Toast.makeText(TimeSheetsActivity.this, "register"+ snapshot.toString(), Toast.LENGTH_SHORT).show();
//
//                        if(Objects.equals(register.getWeek_number(), "1")){
//
//                            if(Objects.equals(register.getDay(), "Monday")){
//                                monday_date_week_1.setText(register.getDate());
//                                monday_in_week_1.setText(register.getTime_in());
//                                monday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if(Objects.equals(register.getDay(), "Tuesday")){
//                                tuesday_date_week_1.setText(register.getDate());
//                                tuesday_in_week_1.setText(register.getTime_in());
//                                tuesday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if(Objects.equals(register.getDay(), "Wednesday")){
//                                wednesday_date_week_1.setText(register.getDate());
//                                wednseday_in_week_1.setText(register.getTime_in());
//                                wednesday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if(Objects.equals(register.getDay(), "Thursday")){
//                                thursday_date_week_1.setText(register.getDate());
//                                thursday_in_week_1.setText(register.getTime_in());
//                                thursday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if(Objects.equals(register.getDay(), "Friday")){
//                                friday_date_week_1.setText(register.getDate());
//                                friday_in_week_1.setText(register.getTime_in());
//                                friday_out_week_1.setText(register.getTime_out());
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };

//        tl = (TableLayout) findViewById(R.id.maintable);
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//

//        databaseReference = firebaseDatabase.getReference().child("TimeSheet");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
////                Log.e("Count" , "" + dataSnapshot.getChildrenCount());
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//
////                    Log.i("register",snapshot.toString());
//
//                    Register register = snapshot.getValue(Register.class);
//
//                    if(snapshot.getKey().equals(userId)) {
//
//                        Toast.makeText(TimeSheetsActivity.this, "User Key = "+ snapshot.getKey(), Toast.LENGTH_SHORT).show();
//
////                        databaseReference = firebaseDatabase.getReference().child("TimeSheet").child(snapshot.getKey())
////                                .child("December").child("Week1").child()
//
////                        if (snapshot.child("February").getValue())) {
//                            if (Objects.equals(snapshot.getKey(), "February")) {
//
//                            Toast.makeText(TimeSheetsActivity.this, "Am in", Toast.LENGTH_SHORT).show();
//
//
//                            if (Objects.equals(snapshot.child(""), "Monday")) {
//                                monday_date_week_1.setText(register.getDate());
//                                monday_in_week_1.setText(register.getTime_in());
//                                monday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if (Objects.equals(snapshot.getKey(), "Tuesday")) {
//                                tuesday_date_week_1.setText(register.getDate());
//                                tuesday_in_week_1.setText(register.getTime_in());
//                                tuesday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if (Objects.equals(snapshot.getKey(), "Wednesday")) {
//                                wednesday_date_week_1.setText(register.getDate());
//                                wednseday_in_week_1.setText(register.getTime_in());
//                                wednesday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if (Objects.equals(snapshot.getKey(), "Thursday")) {
//                                thursday_date_week_1.setText(register.getDate());
//                                thursday_in_week_1.setText(register.getTime_in());
//                                thursday_out_week_1.setText(register.getTime_out());
//                            }
//
//                            if (Objects.equals(snapshot.getKey(), "Friday")) {
//                                friday_date_week_1.setText(register.getDate());
//                                friday_in_week_1.setText(register.getTime_in());
//                                friday_out_week_1.setText(register.getTime_out());
//                            }
//                        }
//                    }
////                    String id = register.getId();
////                    String name= register.getName();
//
//                    // Then add the value you require to add in your ArrayList
////                    week_number.add(register.getWeek_number());
////                    weekdays.add(register.getDay());
////                    time_in.add(register.getTime_in());
////                    time_out.add(register.getTime_out());
////                    formattedDate.add(register.getDate());
//
////                    Toast.makeText(TimeSheetsActivity.this, ""+ register, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
         name = (TextView)findViewById(R.id.name);
         location = (TextView)findViewById(R.id.location);
         month = (TextView)findViewById(R.id.month);
//
//
//        month.setText("Month: February");
////        month.setText("Month: " + register.getMonth());
//        name.setText("Name: " + user.getName() + " " + user.getLastName());
//        location.setText(String.format("Location: %s", user.getFacility()));
////        addHeaders();
////        addData();
//    }

        //   This function add the headers to the table
//    public void addHeaders(){
//
//        /** Create a TableRow dynamically **/
//        tr = new TableRow(this);
//        tr.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.MATCH_PARENT));
//
//        ShapeDrawable border = new ShapeDrawable(new RectShape());
//        border.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
//        border.getPaint().setColor(Color.WHITE);
//
//
//        /** Creating a TextView to add to the row **/
//        TextView companyTV = new TextView(this);
//        companyTV.setText("Week " + week_number);
//        companyTV.setTextColor(Color.RED);
//        companyTV.setBackground(border);
//        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        companyTV.setPadding(5, 5, 5, 0);
//        tr.addView(companyTV);	// Adding textView to tablerow.
//
//        /** Creating another textview **/
//        TextView valueTV = new TextView(this);
//        valueTV.setText("Date");
//        valueTV.setTextColor(Color.RED);
//        valueTV.setPadding(5, 5, 5, 0);
//        valueTV.setBackground(border);
//        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(valueTV); // Adding textView to tablerow.
//
//        TextView valueTV1 = new TextView(this);
//        valueTV1.setText("Time in");
//        valueTV1.setTextColor(Color.RED);
//        valueTV1.setPadding(5, 5, 5, 0);
//        valueTV1.setBackground(border);
//        valueTV1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(valueTV1); // Adding textView to tablerow.
//
//        TextView valueTV2 = new TextView(this);
//        valueTV2.setText("Time out");
//        valueTV2.setTextColor(Color.RED);
//        valueTV2.setPadding(5, 5, 5, 0);
//        valueTV2.setBackground(border);
//        valueTV2.setGravity(Gravity.CENTER_HORIZONTAL);
//        valueTV2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(valueTV2); // Adding textView to tablerow.
//
//
//
//        // Add the TableRow to the TableLayout
//        tl.addView(tr, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//        tl.setBackground(border);
//
//        // we are adding two textviews for the divider because we have two columns
//        tr = new TableRow(this);
//        tr.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        /** Creating another textview **/
//        TextView divider = new TextView(this);
//        divider.setTextColor(Color.GREEN);
//        divider.setPadding(5, 0, 0, 0);
//        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider); // Adding textView to tablerow.
//
//        TextView divider2 = new TextView(this);
//        divider2.setTextColor(Color.GREEN);
//        divider2.setPadding(5, 0, 0, 0);
//        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        tr.addView(divider2); // Adding textView to tablerow.
//
//        // Add the TableRow to the TableLayout
//        tl.addView(tr, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//    }

//     This function add the data to the table
//    public void addData(){
//
////        if(weekdays.equals("Monday")){
////            addHeaders();
////        }
//
////        for (int i = 0; i < weekdays.length; i++)
////        {
//        // Create a TableRow dynamically
//        tr = new TableRow(this);
//        tr.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        ShapeDrawable border = new ShapeDrawable(new RectShape());
//        border.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
//        border.getPaint().setColor(Color.GRAY);
//
//        // Creating a TextView to add to the row
//        companyTV = new TextView(this);
//        companyTV.setText(" " + weekdays + "       ");
//        companyTV.setBackground(border);
//        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        companyTV.setPadding(5, 0, 0, 0);
//        companyTV.setTextColor(Color.BLACK);
//        tr.addView(companyTV);	// Adding textView to tablerow.
//
//        // Creating another textView
//        valueTV = new TextView(this);
//        valueTV.setText("  " + formattedDate + "      ");
//        companyTV.setPadding(5, 0, 0, 0);
//        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        valueTV.setBackground(border);
//        valueTV.setTextColor(Color.BLACK);
//        tr.addView(valueTV); // Adding textView to tablerow.
//
//        valueTV = new TextView(this);
//        valueTV.setText("  " + time_in + "      ");
//        companyTV.setPadding(5, 0, 0, 0);
//        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        valueTV.setBackground(border);
//        valueTV.setTextColor(Color.BLACK);
//        tr.addView(valueTV); // Adding textView to tablerow.
//
//
//        valueTV = new TextView(this);
//        valueTV.setText("  "  + time_out);
//        companyTV.setPadding(5, 0, 0, 0);
//        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
//        valueTV.setBackground(border);
//        valueTV.setTextColor(Color.BLACK);
//        tr.addView(valueTV); // Adding textView to tablerow.
//
//            // Add the TableRow to the TableLayout
//            tl.addView(tr, new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.MATCH_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
////        }
//    }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int posotion, long id) {
        monthSelected = monthList[posotion];
        getTimeSheetData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void getTimeSheetData(){



        databaseReference = firebaseDatabase.getReference().child("TimeSheet").child(userId).child(monthSelected);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.e("Count" , "" + dataSnapshot.getChildrenCount());

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

//                    Log.i("register",snapshot.toString());

                    Register register = snapshot.getValue(Register.class);

//                    if(snapshot.getKey().equals(userId)) {

                        Toast.makeText(TimeSheetsActivity.this, "User Key = "+ snapshot.getKey(), Toast.LENGTH_SHORT).show();

//                        databaseReference = firebaseDatabase.getReference().child("TimeSheet").child(snapshot.getKey())
//                                .child("December").child("Week1").child()

//                        if (snapshot.child("February").getValue())) {
//                        if (Objects.equals(snapshot.getKey(), "February")) {

                            Toast.makeText(TimeSheetsActivity.this, "Am in", Toast.LENGTH_SHORT).show();

                            //Week 1
                    if (Objects.equals(register.getWeek_number(), "1")) {

                        if (Objects.equals(register.getDay(), "Monday")) {
                            monday_date_week_1.setText(register.getDate());
                            monday_in_week_1.setText(register.getTime_in());
                            monday_out_week_1.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Tuesday")) {
                            tuesday_date_week_1.setText(register.getDate());
                            tuesday_in_week_1.setText(register.getTime_in());
                            tuesday_out_week_1.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Wednesday")) {
                            wednesday_date_week_1.setText(register.getDate());
                            wednseday_in_week_1.setText(register.getTime_in());
                            wednesday_out_week_1.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Thursday")) {
                            thursday_date_week_1.setText(register.getDate());
                            thursday_in_week_1.setText(register.getTime_in());
                            thursday_out_week_1.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Friday")) {
                            friday_date_week_1.setText(register.getDate());
                            friday_in_week_1.setText(register.getTime_in());
                            friday_out_week_1.setText(register.getTime_out());

                        }
                    }

                    //Week 2
                    if (Objects.equals(register.getWeek_number(), "2")) {

                        if (Objects.equals(register.getDay(), "Monday")) {
                            monday_date_week_2.setText(register.getDate());
                            monday_in_week_2.setText(register.getTime_in());
                            monday_out_week_2.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Tuesday")) {
                            tuesday_date_week_2.setText(register.getDate());
                            tuesday_in_week_2.setText(register.getTime_in());
                            tuesday_out_week_2.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Wednesday")) {
                            wednesday_date_week_2.setText(register.getDate());
                            wednseday_in_week_2.setText(register.getTime_in());
                            wednesday_out_week_2.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Thursday")) {
                            thursday_date_week_2.setText(register.getDate());
                            thursday_in_week_2.setText(register.getTime_in());
                            thursday_out_week_2.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Friday")) {
                            friday_date_week_2.setText(register.getDate());
                            friday_in_week_2.setText(register.getTime_in());
                            friday_out_week_2.setText(register.getTime_out());

                        }
                    }

                    //Week 3
                    if (Objects.equals(register.getWeek_number(), "3")) {

                        if (Objects.equals(register.getDay(), "Monday")) {
                            monday_date_week_3.setText(register.getDate());
                            monday_in_week_3.setText(register.getTime_in());
                            monday_out_week_3.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Tuesday")) {
                            tuesday_date_week_3.setText(register.getDate());
                            tuesday_in_week_3.setText(register.getTime_in());
                            tuesday_out_week_3.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Wednesday")) {
                            wednesday_date_week_3.setText(register.getDate());
                            wednseday_in_week_3.setText(register.getTime_in());
                            wednesday_out_week_3.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Thursday")) {
                            thursday_date_week_3.setText(register.getDate());
                            thursday_in_week_3.setText(register.getTime_in());
                            thursday_out_week_3.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Friday")) {
                            friday_date_week_3.setText(register.getDate());
                            friday_in_week_3.setText(register.getTime_in());
                            friday_out_week_3.setText(register.getTime_out());

                        }
                    }

                    //Week 4
                    if (Objects.equals(register.getWeek_number(), "4")) {

                        if (Objects.equals(register.getDay(), "Monday")) {
                            monday_date_week_4.setText(register.getDate());
                            monday_in_week_4.setText(register.getTime_in());
                            monday_out_week_4.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Tuesday")) {
                            tuesday_date_week_4.setText(register.getDate());
                            tuesday_in_week_4.setText(register.getTime_in());
                            tuesday_out_week_4.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Wednesday")) {
                            wednesday_date_week_4.setText(register.getDate());
                            wednseday_in_week_4.setText(register.getTime_in());
                            wednesday_out_week_4.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Thursday")) {
                            thursday_date_week_4.setText(register.getDate());
                            thursday_in_week_4.setText(register.getTime_in());
                            thursday_out_week_4.setText(register.getTime_out());
                        }

                        if (Objects.equals(register.getDay(), "Friday")) {
                            friday_date_week_4.setText(register.getDate());
                            friday_in_week_4.setText(register.getTime_in());
                            friday_out_week_4.setText(register.getTime_out());

                        }
                    }
//                    String id = register.getId();
//                    String name= register.getName();

                    // Then add the value you require to add in your ArrayList
//                    week_number.add(register.getWeek_number());
//                    weekdays.add(register.getDay());
//                    time_in.add(register.getTime_in());
//                    time_out.add(register.getTime_out());
//                    formattedDate.add(register.getDate());

//                    Toast.makeText(TimeSheetsActivity.this, ""+ register, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        month.setText("Month:" + monthSelected);
        name.setText("Name: " + user.getName() + " " + user.getLastName());
        location.setText(String.format("Location: %s", user.getFacility()));

    }
}
