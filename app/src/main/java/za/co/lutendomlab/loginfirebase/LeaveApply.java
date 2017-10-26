package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class LeaveApply extends AppCompatActivity {
    private EditText name;
    private EditText lastName;
    private  EditText leaveType;
    private  EditText leaveForm;
    private  EditText leaveTo;
    private  EditText numberOfDays;
    private  EditText ConditionOfPayments;
    private  EditText address;
    private  EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_apply);

        //getSupportActionBar().setTitle("Leave Form");

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText)findViewById(R.id.name);
        lastName = (EditText)findViewById(R.id.lastName);
//        leaveType = (EditText)findViewById(R.id.leaveType);
//        leaveForm = (EditText)findViewById(R.id.leaveForm);
//        leaveTo = (EditText)findViewById(R.id.leaveTo);
        numberOfDays = (EditText)findViewById(R.id.lastName);
        ConditionOfPayments = (EditText)findViewById(R.id.ConditionOfPayments);
        address = (EditText)findViewById(R.id.address);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
    }


    public void Send(View view)  {

        String Message = Massege();
        String headerMessage = "Leave Form";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only emails apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String [] {"veronica@Mlba.co.za","khuthazdo@mlab.co.za"});
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

    public void Cancel(View view){
        Toast.makeText(LeaveApply.this,"Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }

    public String Massege(){


        String priceMessage = "Name: " + name.getText().toString();
        priceMessage += "\nLast Name: " + lastName.getText().toString();
        priceMessage += " \nType of leave: " + leaveType.getText().toString();
        priceMessage += "\nTaking leave from: " + leaveForm.getText().toString();
        priceMessage += " To: " + leaveTo.getText().toString();
        priceMessage += "\nNumber of days: " + numberOfDays.getText().toString();
        priceMessage += " \nCondition of payment: " + ConditionOfPayments.getText().toString();
        priceMessage += "\nAddress during leave: "+ address.getText().toString();
        priceMessage += "Phone number during leave: "+ phoneNumber.getText().toString();

        return priceMessage;
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
}
