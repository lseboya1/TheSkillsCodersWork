package za.co.lutendomlab.loginfirebase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import static java.security.AccessController.getContext;


public class LeaveApply extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    private EditText name;
    private EditText lastName;
    private  EditText leaveType;
    private  EditText leaveForm;
    private  EditText leaveTo;
    private  EditText numberOfDays;
    private  EditText ConditionOfPayments;
    private  EditText address;
    private  EditText phoneNumber;
    Spinner spin2;
    Spinner spin;
    String typeOfLeaveSelected;
    String leaveConditionSelected;

    String[] typeOfLeave ={"Sick Leave","Vacation","Special Leave" , "etc"};
    String[] leaveCondition ={"Without","Half","Full"};
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        surname = intent.getStringExtra("surname");
        user = intent.getStringExtra("name");
        From_date_text = (TextView)findViewById(R.id.From_date_text);
        to_date_text = (TextView)findViewById(R.id.to_date_text);
        number_of_days = (TextView)findViewById(R.id.number_of_days);

        spin =(Spinner)findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(LeaveApply.this);

        spin2 =(Spinner)findViewById(R.id.simpleSpinnerPayments);
        spin2.setOnItemSelectedListener(LeaveApply.this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeOfLeave);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,leaveCondition);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(a);



        name = (EditText)findViewById(R.id.name);
        name.setText(user);
        lastName = (EditText)findViewById(R.id.lastName);
//        leaveType = (EditText)findViewById(R.id.leaveType);
//        leaveForm = (EditText)findViewById(R.id.leaveForm);
//        leaveTo = (EditText)findViewById(R.id.leaveTo);
        lastName.setText(surname);
        numberOfDays = (EditText)findViewById(R.id.lastName);
//        ConditionOfPayments = (EditText)findViewById(R.id.ConditionOfPayments);
        address = (EditText)findViewById(R.id.address);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);



        datePickerFrom =(Button) findViewById(R.id.dialog_date_datePickerFrom);
        datePickerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "from_date");
            }
        });




        datePickerTo=(Button)findViewById(R.id.dialog_date_datePickerTo);
        datePickerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "to_date");
            }
        });

    }

    public  static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {



        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            String mytag = getTag();
            if ("from_date".equals(mytag)) {
                processFromDate( month, day, year);
                dateFrom =month +"/" +day +"/"+year ;
                From_date_text.setText(dateFrom);
            } else if ("to_date".equals(mytag)) {
                processToDate( month, day, year);
                dateTo =month +"/" +day +"/"+year ;
                to_date_text.setText(dateTo);

                calculateDays();
            }
        }

        public void calculateDays(){

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

                Log.e("HERE", "HERE: " + dayDifference);

            } catch (Exception exception) {
                Log.e("DIDN'T WORK", "exception " + exception);
            }

//            TextView number_of_days = (TextView)findViewById(R.id.number_of_days);
            number_of_days.setText("Number Of Days: " + dayDifference + " Days");
        }

    }

    public static void processFromDate( int year, int month, int day) {


    }

    public static void processToDate( int year, int month, int day) {

    }

    public void Send(View view)  {


        String Message = inforMassege();
        String headerMessage = "Leave Form";


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only emails apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String [] {"veronica@mlab.co.za","khuthazdo@mlab.co.za"});
        intent.putExtra(Intent.EXTRA_SUBJECT, headerMessage);
        intent.putExtra(Intent.EXTRA_TEXT, Message);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();

      //convert to pdf thanks
    Document doc = new Document() ;
    String outpath = Environment.getExternalStorageDirectory()+"/leave.pdf";
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(outpath));
            doc.open();
           doc.add(new Paragraph(Message));
           intent.putExtra(Intent.EXTRA_TEXT, doc.add(new Paragraph(Message)));
            doc.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public String inforMassege(){

        String Message = "Name: " + name.getText().toString();
        Message += "\nLast Name: " + lastName.getText().toString();
        Message += " \nType of leave: " + typeOfLeaveSelected.toString();
        Message += "\nTaking leave from: " + dateFrom;
        Message += " To: " + dateTo;
        Message += "\nNumber of days: " + number_of_days.getText().toString();
        Message += " \nCondition of payment: " + leaveConditionSelected.toString();
        Message += "\nAddress during leave: "+ address.getText().toString();
        Message += "\nPhone number during leave: "+ phoneNumber.getText().toString();

//

        return Message;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i,long l) {


        Spinner spinner = (Spinner) parent;

        if(spinner.getId()==R.id.simpleSpinner)
        {
            typeOfLeaveSelected = typeOfLeave[i];
        }
        else if(spinner.getId()==R.id.simpleSpinnerPayments)
        {

            leaveConditionSelected= leaveCondition[i];
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
