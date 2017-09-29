package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        message = (EditText)findViewById(R.id.message);
        header = (EditText)findViewById(R.id.header);
    }

    public void Send(View view){

        String Message = message.getText().toString();
        String headerMessage = header.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only emails apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL,new String [] {"khuthadzo@mlab.co.za","veronica@mlab.co.za"});
        intent.putExtra(Intent.EXTRA_SUBJECT, headerMessage);
        intent.putExtra(Intent.EXTRA_TEXT, Message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void Cancel(View view){
        Toast.makeText(SendMessage.this,"Cancelled",Toast.LENGTH_SHORT).show();
        finish();
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
