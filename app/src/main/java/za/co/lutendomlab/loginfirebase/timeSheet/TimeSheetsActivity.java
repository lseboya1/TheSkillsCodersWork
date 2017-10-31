package za.co.lutendomlab.loginfirebase.timeSheet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.User;

public class TimeSheetsActivity extends AppCompatActivity {

    TableLayout tl;
    TableRow tr;
    TextView companyTV;
    TextView valueTV;

    String weekdays [];
    String formattedDate;
    String time_out;
    String time_in;
    int week_number = 1;


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_sheet);

        tl = (TableLayout) findViewById(R.id.maintable);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");


        TextView name = (TextView)findViewById(R.id.name);
        TextView location = (TextView)findViewById(R.id.location);
        TextView month = (TextView)findViewById(R.id.month);


        month.setText("Month: Septembe/October");
        name.setText("Name: " + user.getName() + " " + user.getLastName());
        location.setText(String.format("Location: %s", user.getFacility()));
        addHeaders();
        addData();
    }

    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        border.getPaint().setColor(Color.WHITE);


        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(this);
        companyTV.setText("Week " + week_number);
        companyTV.setTextColor(Color.RED);
        companyTV.setBackground(border);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);	// Adding textView to tablerow.

        /** Creating another textview **/
        TextView valueTV = new TextView(this);
        valueTV.setText("Date");
        valueTV.setTextColor(Color.RED);
        valueTV.setPadding(5, 5, 5, 0);
        valueTV.setBackground(border);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.

        TextView valueTV1 = new TextView(this);
        valueTV1.setText("Time in");
        valueTV1.setTextColor(Color.RED);
        valueTV1.setPadding(5, 5, 5, 0);
        valueTV1.setBackground(border);
        valueTV1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV1); // Adding textView to tablerow.

        TextView valueTV2 = new TextView(this);
        valueTV2.setText("Time out");
        valueTV2.setTextColor(Color.RED);
        valueTV2.setPadding(5, 5, 5, 0);
        valueTV2.setBackground(border);
        valueTV2.setGravity(Gravity.CENTER_HORIZONTAL);
        valueTV2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV2); // Adding textView to tablerow.



        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        tl.setBackground(border);

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(this);
        divider.setTextColor(Color.GREEN);
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this);
        divider2.setTextColor(Color.GREEN);
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){

//        if(weekdays.equals("Monday")){
//            addHeaders();
//        }

//        for (int i = 0; i < weekdays.length; i++)
//        {
        // Create a TableRow dynamically
        tr = new TableRow(this);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        ShapeDrawable border = new ShapeDrawable(new RectShape());
        border.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        border.getPaint().setColor(Color.GRAY);

        // Creating a TextView to add to the row
        companyTV = new TextView(this);
        companyTV.setText(" " + weekdays + "       ");
        companyTV.setBackground(border);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(5, 0, 0, 0);
        companyTV.setTextColor(Color.BLACK);
        tr.addView(companyTV);	// Adding textView to tablerow.

        // Creating another textView
        valueTV = new TextView(this);
        valueTV.setText("  " + formattedDate + "      ");
        companyTV.setPadding(5, 0, 0, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        valueTV.setBackground(border);
        valueTV.setTextColor(Color.BLACK);
        tr.addView(valueTV); // Adding textView to tablerow.

        valueTV = new TextView(this);
        valueTV.setText("  " + time_in + "      ");
        companyTV.setPadding(5, 0, 0, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        valueTV.setBackground(border);
        valueTV.setTextColor(Color.BLACK);
        tr.addView(valueTV); // Adding textView to tablerow.


        valueTV = new TextView(this);
        valueTV.setText("  "  + time_out);
        companyTV.setPadding(5, 0, 0, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        valueTV.setBackground(border);
        valueTV.setTextColor(Color.BLACK);
        tr.addView(valueTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
//        }
    }
}
