package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class SignupActivity extends AppCompatActivity{
    private EditText etName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirmPassword;
    private TextView Staff_number;
    private Button btnSignIn;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private StaffNumberGenerator staffNumberGenerator;
    private long generatedStaffNumber;
    // Write a message to the database

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null)
        {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),HomeScreenUser.class));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        Staff_number = (TextView) findViewById(R.id.Staff_number);
        inputConfirmPassword = (EditText)findViewById(R.id.re_password);
        etName =(EditText)findViewById(R.id.name);
        autoStaffNumberGanerator();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String re_password = inputConfirmPassword.getText().toString().trim();
                final String name =etName.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password too short",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(re_password)){
                    Toast.makeText(getApplicationContext(),"Password do not match",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Registering, please wait...");
                progressDialog.show();

                //create user
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

//                                String uid = task.getResult().getUser().getUid();
//
//                                //saving name
//                                //FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                //DatabaseReference myRef = database.getReference().child("User");
//                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User");
//                                User user = new User();
//                                user.setStaffNO(generatedStaffNumber);
//                                user.setName(name);
//                                user.setEmail(inputEmail.getText().toString());
//                                String id = myRef.push().getKey();
//                                myRef.child("users").child(uid).setValue(user);

                                //////----------------------------------------
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User");
                                User user = new User();
                                user.setStaffNO(generatedStaffNumber);
                                user.setName(name);
                                myRef.child(task.getResult().getUser().getUid()).setValue(user);
                                Toast.makeText(getApplicationContext(),"createUserWithEmail:onComplete:",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                                autoStaffNumberGaneratorIncreament();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    finish();
                                    startActivity(new Intent(SignupActivity.this, HomeScreenUser.class));
                            }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void autoStaffNumberGanerator() {

        staffNumberGenerator = new StaffNumberGenerator(this);
        generatedStaffNumber = staffNumberGenerator.generateSaffNumber();

        if(generatedStaffNumber > 0) {
            this.Staff_number.setText("Staff No: " + generatedStaffNumber);
        }
        else {this.Staff_number.setText("Staff No: " + staffNumberGenerator.DEFAULT_SAFF_NUMBER);
        }
    }

    public void autoStaffNumberGaneratorIncreament(){

        staffNumberGenerator.saveNewStaffNumber((int)  ++this.generatedStaffNumber);
    }
}
