package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;
import za.co.lutendomlab.loginfirebase.timeSheet.TimeSheetsActivity;

public class AdminOption extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] roleList = {"Facilitator","Admin"};
    String[] statusList = {"Disable", "Able"};
    String role;
    String status;

    Button save_role;
    Button cancel_role;
    Spinner role_spinner;

    Spinner status_spinner;
    Button cancel_admin;
    Button save_admin;

    User user;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_roles);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUserId());

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        save_role = (Button)findViewById(R.id.save_role);
        cancel_role = (Button)findViewById(R.id.cancel_role);
        role_spinner = (Spinner) findViewById(R.id.role_spinner);
        role_spinner.setOnItemSelectedListener(this);

        cancel_admin = (Button)findViewById(R.id.cancel_admin);
        save_admin = (Button)findViewById(R.id.save_admin);
        status_spinner = (Spinner) findViewById(R.id.status_spinner);
        status_spinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,roleList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        role_spinner.setAdapter(aa);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter a = new ArrayAdapter(this,android.R.layout.simple_spinner_item,statusList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        status_spinner.setAdapter(a);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        role = roleList[position];
        status = statusList[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    public void Assign(View view){

        save_role.setVisibility(View.VISIBLE);
        cancel_role.setVisibility(View.VISIBLE);
        role_spinner.setVisibility(View.VISIBLE);
    }

    public void SaveRole(View view){

        String keyUser = user.getUserId();
        String selectedRole = role;

        User user2 = new User(keyUser, role);
        user2.setRole(selectedRole);
        databaseReference.child("role").setValue(user2.getRole());

        save_role.setVisibility(View.GONE);
        cancel_role.setVisibility(View.GONE);
        role_spinner.setVisibility(View.GONE);
    }

    public void TimeSheet(View v){

        Intent intent = new Intent(AdminOption.this, TimeSheetsActivity.class);
        intent.putExtra("userProfile", user);
        startActivity(intent);
    }

    public void CancelRole(View view){

        save_role.setVisibility(View.GONE);
        cancel_role.setVisibility(View.GONE);
        role_spinner.setVisibility(View.GONE);
    }

    public void CancelStatus(View view){

        status_spinner.setVisibility(View.GONE);
        save_admin.setVisibility(View.GONE);
        cancel_admin.setVisibility(View.GONE);
    }

    public void ChangeStatus(View view){

        status_spinner.setVisibility(View.VISIBLE);
        save_admin.setVisibility(View.VISIBLE);
        cancel_admin.setVisibility(View.VISIBLE);

    }

    public void SaveStatus(View view){

        String keyUser = user.getUserId();
        String selectedStatus = status;

        User user1 = new User(keyUser, selectedStatus);
        databaseReference.child("status").setValue(user1.getStatus());

        status_spinner.setVisibility(View.GONE);
        save_admin.setVisibility(View.GONE);
        cancel_admin.setVisibility(View.GONE);
    }

    public void MakaCallAdmin(View view) {

//        String phoneNumbur = user.getPhoneNumber();
        String phoneNumbur = "111111111";
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                Uri.fromParts("tel", phoneNumbur, null));
        startActivity(phoneIntent);
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
