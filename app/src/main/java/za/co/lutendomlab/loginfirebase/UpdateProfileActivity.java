package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by codetribe on 8/23/2017.
 */

public class UpdateProfileActivity extends AppCompatActivity{


    private EditText name;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;
    private EditText password;

    private String nameinput;
    private String lastNameinput;
    private String phoneNumberinput;
    private String emailinput;
    private String passwordinput;


    String keyUser;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

//        Intent intent = getIntent();
//        keyUser =  intent.getStringExtra("User_KEY");

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userId);

        name = (EditText)findViewById(R.id.name);
        lastName = (EditText)findViewById(R.id.lastName);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
    }


    public void updateUser(){

        nameinput = name.getText().toString().trim();
        lastNameinput = lastName.getText().toString().trim();
        phoneNumberinput = phoneNumber.getText().toString().trim();
        emailinput = email.getText().toString().trim();
        passwordinput = password.getText().toString().trim();

        User user = new User(keyUser, nameinput , lastNameinput, phoneNumberinput,emailinput);

        databaseReference.child("").setValue(user.getName());
        databaseReference.child("").setValue(user.getLastName());
        databaseReference.child("").setValue(user.getPhoneNumber());
        databaseReference.child("").setValue(user.getEmail());
    }

    public void Save(View view){
        updateUser();
    }
}
