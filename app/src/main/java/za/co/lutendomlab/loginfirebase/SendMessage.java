package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessage extends AppCompatActivity {


    private EditText message;
    private EditText header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        getSupportActionBar().setTitle("Send Notification");

        message = (EditText)findViewById(R.id.message);
        header = (EditText)findViewById(R.id.header);
    }

    public void Send(View view){
        //Toast.makeText(SendMessage.this,"Message successfully sent",Toast.LENGTH_SHORT).show();

        String Message = message.getText().toString();
        String headerMessage = message.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only emails apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String [] {"khuthadzo@mlab.co.za","veronica@mlab.co.za"});
        intent.putExtra(Intent.EXTRA_SUBJECT, headerMessage);
        intent.putExtra(Intent.EXTRA_TEXT, Message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        finish();
    }

    public void Cancel(View view){
        Toast.makeText(SendMessage.this,"Cancelled",Toast.LENGTH_SHORT).show();
        finish();
    }
}
