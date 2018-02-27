package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import za.co.lutendomlab.loginfirebase.R;


public class ViewLeaveFormAvivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplay_leave);
    }

    public void Approve(View view) {

        Toast.makeText(this, "Approved", Toast.LENGTH_SHORT).show();

    }

    public void Disapprove(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewLeaveFormAvivity.this);
        // Setting Dialog Title
        alertDialog.setTitle(" Reason of declining");

        // Setting Dialog Message
//        alertDialog.setMessage("You want to sign IN ot OUT.?");

        alertDialog.setPositiveButton("Send \t", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ViewLeaveFormAvivity.this, "Declined", Toast.LENGTH_SHORT).show();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}
