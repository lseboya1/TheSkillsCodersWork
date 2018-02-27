package za.co.lutendomlab.loginfirebase.student_activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import za.co.lutendomlab.loginfirebase.adapter_and_object.Leave;
import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;


public class LeaveApply extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText name;
    private EditText lastName;
    private EditText leaveType;
    private EditText leaveForm;
    private EditText leaveTo;
    private EditText numberOfDays;
    private EditText ConditionOfPayments;
    private EditText address;
    private EditText phoneNumber;
    Spinner spin2;
    Spinner spin;
    String typeOfLeaveSelected;
    String leaveConditionSelected;

    String[] typeOfLeave = {"Select leave ", "Sick Leave", "Vacation", "Family Responsibility Leave", "etc"};
    String[] leaveCondition = {"Select payment condition", "Without", "Half", "Full"};
    Button datePickerFrom;
    Button datePickerTo;
    String user;
    String surname;
    static String dayDifference;
    static String dateFrom;
    static String dateTo;

    static TextView From_date_text;
    static TextView to_date_text;
    static TextView number_of_days;
    Button button3;

    String userID;
    String nam;
    String sur;
    long noDays;
    String leaveTyp;
    String payType;
    String addres;
    String phon;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        surname = intent.getStringExtra("surname");
        user = intent.getStringExtra("name");

        button3 = (Button)findViewById(R.id.button3);
        From_date_text = (TextView) findViewById(R.id.From_date_text);
        to_date_text = (TextView) findViewById(R.id.to_date_text);
        number_of_days = (TextView) findViewById(R.id.number_of_days);

        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(LeaveApply.this);

        spin2 = (Spinner) findViewById(R.id.simpleSpinnerPayments);
        spin2.setOnItemSelectedListener(LeaveApply.this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeOfLeave);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leaveCondition);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a);


        name = (EditText) findViewById(R.id.name);
        name.setText(user);
        lastName = (EditText) findViewById(R.id.lastName);
//        leaveType = (EditText)findViewById(R.id.leaveType);
//        leaveForm = (EditText)findViewById(R.id.leaveForm);
//        leaveTo = (EditText)findViewById(R.id.leaveTo);
        lastName.setText(surname);
        numberOfDays = (EditText) findViewById(R.id.lastName);
//        ConditionOfPayments = (EditText)findViewById(R.id.ConditionOfPayments);
        address = (EditText) findViewById(R.id.address);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);


        datePickerFrom = (Button) findViewById(R.id.dialog_date_datePickerFrom);
        //datePickerFrom.setBackgroundColor(R.color.colorAccent);
        datePickerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "from_date");
            }
        });


        datePickerTo = (Button) findViewById(R.id.dialog_date_datePickerTo);
        datePickerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "to_date");
            }
        });

//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference().child("User").child(userID);
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//
//                if(Objects.equals(user.getStatus(), "Disable")){
//                    button3.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            //return new DatePickerDialog(getActivity(), this, year, month, day);
            DatePickerDialog picker = new DatePickerDialog(getActivity(), this, year, month, day);

            DatePickerDialog picker1 = new DatePickerDialog(getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day);
            picker1.getDatePicker().setMinDate(c.getTime().getTime());

//            calculateDays();
            return picker1;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            String mytag = getTag();
            if ("from_date".equals(mytag)) {
                processFromDate(month, day, year);
                dateFrom = (month+1) + "/" + day + "/" + year;
                From_date_text.setText(dateFrom);
            } else if ("to_date".equals(mytag)) {
                processToDate(month, day, year);
                dateTo = (month+1) + "/" + day + "/" + year;
                to_date_text.setText(dateTo);

                calculateDays();
            }
        }

        public void calculateDays() {

            try {
                //Dates to compare
                String CurrentDate = dateFrom;
                String FinalDate = dateTo;

                Date date1;
                Date date2;

                SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");

                //Setting dates
                date1 = dates.parse(CurrentDate);
                date2 = dates.parse(FinalDate);

                //Comparing dates
                long difference = Math.abs(date1.getTime() - date2.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                //Convert long to String
                dayDifference = Long.toString(differenceDates);

            } catch (Exception exception) {
                Log.e("DIDN'T WORK", "exception " + exception);
            }

//            TextView number_of_days = (TextView)findViewById(R.id.number_of_days);
            number_of_days.setText("Number Of Days: " + dayDifference + " Days");
        }

    }

    public static void processFromDate(int year, int month, int day) {


    }

    public static void processToDate(int year, int month, int day) {

    }

    public void Send(View view) {

        nam = name.getText().toString();
        sur = lastName.getText().toString();
        noDays = Integer.parseInt(dayDifference);
        leaveTyp = typeOfLeaveSelected.toString();
        payType = leaveConditionSelected.toString();
        addres = address.getText().toString();
        phon = phoneNumber.getText().toString();


        if (leaveConditionSelected == "Select payment condition") {
            Toast.makeText(this, "Please select the payment condition!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (typeOfLeaveSelected == "Select leave ") {
            Toast.makeText(this, "Please select type of leave!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (From_date_text.getText().toString().isEmpty()) {
            Toast.makeText(this, "You didn't pick a DATE FROM", Toast.LENGTH_SHORT).show();
            return;
        }
        if (to_date_text.getText().toString().isEmpty()) {
            Toast.makeText(this, "You didn't pick a DATE TO", Toast.LENGTH_SHORT).show();
            return;
        }

        String Message = inforMassege();
        String headerMessage = "Leave Form";


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only emails apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"veronica@mlab.co.za", "khuthadzo@mlab.co.za"});
        intent.putExtra(Intent.EXTRA_SUBJECT, headerMessage);
        intent.putExtra(Intent.EXTRA_TEXT, Message);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();

        //convert to pdf thanks
        Document doc = new Document();
        String outpath = Environment.getExternalStorageDirectory() + "/leave.pdf";
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
            doc.open();
            doc.add(new Paragraph(Message));
            intent.putExtra(Intent.EXTRA_TEXT, doc.add(new Paragraph(Message)));
            doc.close();

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        Leave leave = new Leave(nam, sur, addres, leaveTyp, payType, dateFrom, dateTo, noDays, phon);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String leave_id = database.getReference().child("LeaveApply").child(userID).push().getKey();
        databaseReference = database.getReference().child("LeaveApply").child(userID);//.child(leave_id);
        databaseReference.setValue(leave);
    }

    public String inforMassege() {

        String Message = "Name: " + nam;
        Message += "\nLast Name: " + sur;
        Message += " \nType of leave: " + leaveTyp;
        Message += "\nTaking leave from: " + dateFrom;
        Message += " To: " + dateTo;
        Message += "\nNumber of days: " + noDays;
        Message += " \nCondition of payment: " + payType;
        Message += "\nAddress during leave: " + addres;
        Message += "\nPhone number during leave: " + phon;
        return Message;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.simpleSpinner) {
            typeOfLeaveSelected = typeOfLeave[i];
        } else if (spinner.getId() == R.id.simpleSpinnerPayments) {

            leaveConditionSelected = leaveCondition[i];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
