package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import za.co.lutendomlab.loginfirebase.timeSheet.TimeSheetsActivity;

public class AdminOptionStudents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

        User user;
        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;


    Spinner simpleSpinner;
    Button cancel_student;
    Button save_student;
    private  String status;
    String[] statusList = {"Disable", "Able"};

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_role_student);

            Intent intent = getIntent();
            user = intent.getParcelableExtra("userProfile");

            firebaseAuth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUserId());

            cancel_student = (Button) findViewById(R.id.cancel_student);
            save_student = (Button) findViewById(R.id.save_student);

            simpleSpinner = (Spinner)findViewById(R.id.simpleSpinner);
            simpleSpinner.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,statusList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            simpleSpinner.setAdapter(aa);

        }

        public void MakaCall(View view) {

                String phoneNumbur = user.getPhoneNumber();
//                String phoneNumbur = "111111111";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", phoneNumbur, null));
                startActivity(phoneIntent);
        }

        public void TimeSheet(View v) {

                Intent intent = new Intent(AdminOptionStudents.this, TimeSheetsActivity.class);
                intent.putExtra("userProfile", user);
                startActivity(intent);
        }

        public void Save(View view) {

            String keyUser = user.getUserId();

            String selectedStatus = status;

            User user1 = new User(keyUser, selectedStatus);
            databaseReference.child("status").setValue(user1.getStatus());

            cancel_student.setVisibility(View.GONE);
            simpleSpinner.setVisibility(View.GONE);
            save_student.setVisibility(View.GONE);

        }

        public void Cancel(View view){

                cancel_student.setVisibility(View.GONE);
                simpleSpinner.setVisibility(View.GONE);
                save_student.setVisibility(View.GONE);

        }

        public void ChangeStatus(View view){

                cancel_student.setVisibility(View.VISIBLE);
                simpleSpinner.setVisibility(View.VISIBLE);
                save_student.setVisibility(View.VISIBLE);
        }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        status = statusList[position];
    }

    @Override
    public void onNothingSelected (AdapterView < ? > arg0){
// TODO Auto-generated method stub
    }
}