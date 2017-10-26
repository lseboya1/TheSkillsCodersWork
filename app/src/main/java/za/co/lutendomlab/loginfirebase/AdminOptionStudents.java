package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
<<<<<<< HEAD
import android.net.Uri;
=======
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

<<<<<<< HEAD
import za.co.lutendomlab.loginfirebase.timeSheet.TimeSheetsActivity;
=======
import za.co.lutendomlab.loginfirebase.timesheets.TimeSheet;

>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000

/**
 * Created by codetribe on 10/20/2017.
 */

<<<<<<< HEAD
public class AdminOptionStudents extends AppCompatActivity {
=======
public class AdminOptionStudents extends AppCompatActivity{
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000

        User user;

        FirebaseAuth firebaseAuth;

<<<<<<< HEAD
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_admin_role_student);
//
                Intent intent = getIntent();
                user = intent.getParcelableExtra("userProfile");

                Toast.makeText(this, "user " + user.getName(), Toast.LENGTH_SHORT).show();

        }

        public void TimeSheet(View v) {

                Intent intent = new Intent(AdminOptionStudents.this, TimeSheetsActivity.class);
                intent.putExtra("userProfile", user);
                startActivity(intent);
        }

        public void Disable(View view) {
        }

        public void MakaCall(View view) {

                user.getPhoneNumber();
                String phoneNumbur = "0790420795";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", phoneNumbur, null));
                startActivity(phoneIntent);
        }
}
=======
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_role_student);
//
        Intent intent = getIntent();
        user = intent.getParcelableExtra("userProfile");

        Toast.makeText(this, "user "+ user.getName(), Toast.LENGTH_SHORT).show();

        }

public void TimeSheet(View v){

        Intent intent = new Intent(AdminOptionStudents.this, TimeSheet.class);
        intent.putExtra("userProfile", user);
        startActivity(intent);
        }

        public void Disable(View view){

        }
        }
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
