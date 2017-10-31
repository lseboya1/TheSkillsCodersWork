package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import za.co.lutendomlab.loginfirebase.timeSheet.TimeSheetsActivity;

public class AdminOption extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] roleList = {"Student","Facilitator","Admin"};
    String role;
    Button save;
    Button cancel;
    Spinner spin;
    User user;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_roles_activity);
//
        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,roleList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        role = roleList[position];

        Toast.makeText(getApplicationContext(),"Selected" + role, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    public void Assign(View view){
        save = (Button)findViewById(R.id.save);
        save.setVisibility(View.VISIBLE);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setVisibility(View.VISIBLE);
        spin.setVisibility(View.VISIBLE);
    }

    public void Save(View view){

        save = (Button)findViewById(R.id.save);
        save.setVisibility(View.GONE);
        spin.setVisibility(View.GONE);
    }

    public void TimeSheet(View v){

        Intent intent = new Intent(AdminOption.this, TimeSheetsActivity.class);
        intent.putExtra("userProfile", user);
        startActivity(intent);
    }

    public void Cancel(View view){

//        firebaseAuth.signOut();

        save.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        spin.setVisibility(View.GONE);
    }

    public void Disable(View view){

    }
}
