package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private EditText etName;
    private EditText lastName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirmPassword;
    private EditText phoneNumber;
    private Button btnSignIn;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private  String facility;

    String[] facilityList = {"Codetribe TIH","Codetibe Soweto","Codetribe Tembisa","Codetribe Alexandra"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfirmPassword = (EditText)findViewById(R.id.re_password);
        etName =(EditText)findViewById(R.id.name);
        lastName =(EditText)findViewById(R.id.lastName);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        Spinner spinner = (Spinner) findViewById(R.id.simpleSpinner);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinner = (Spinner) findViewById(R.id.simpleSpinner);
        spinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,facilityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        Spinner spin =(Spinner)findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(SignupActivity.this);

        progressDialog = new ProgressDialog(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

//        if(auth.getCurrentUser() !=null)
//        {
//            //profile activity here
//            finish();
//            startActivity(new Intent(getApplicationContext(),HomeScreenUser.class));
//        }

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
                final String phone = phoneNumber.getText().toString().trim();
                final String name =etName.getText().toString().trim();
                final String lName =lastName.getText().toString().trim();
                final String Status = "Able";

                if(etName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(lastName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your last name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter your phone number",Toast.LENGTH_SHORT).show();
                    return;
                }


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


                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User");
                                User user = new User();
                                user.setRole("Student");
                                user.setLastName(lName);
                                user.setFacility(facility);
                                user.setPhoneNumber(phone);
                                user.setStatus(Status);
//                                user.setPhoneNumber(phone);
                                user.setEmail(auth.getCurrentUser().getEmail());
                                user.setName(auth.getCurrentUser().getUid(),name );

                                boolean is_admin = isAdmin(auth.getCurrentUser().getEmail());
                                if (is_admin) {
                                    user.setRole("Facilitator");
                                } else {
                                    user.setRole("Student");
                                }

                                myRef.child(task.getResult().getUser().getUid()).setValue(user);
                                Toast.makeText(getApplicationContext(),"createUserWithEmail:onComplete:",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();


                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    finish();

                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));


                              startActivity(new Intent(SignupActivity.this, HomeScreenUser.class));
                                    startActivity(new Intent(SignupActivity.this, HomeScreenUser.class));
                                    if (is_admin) {
                                        Intent intent = new Intent(SignupActivity.this, AdminActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(SignupActivity.this, HomeScreenUser.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            }
                        });
            }
        });
    }

    public boolean isAdmin(String emailAddress) {

        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailAddress);
        System.out.println(emailAddress +" : "+ matcher.matches());
        if (matcher.matches()) {
            String[] email = emailAddress.split("@", 2);

            if ("mlab.co.za".equalsIgnoreCase(email[1])) {
                return true;
            }
            else
                return false;
        }
        return false;
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
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

        facility = facilityList[position];
    }

        @Override
        public void onNothingSelected (AdapterView < ? > arg0){
// TODO Auto-generated method stub
        }

        public void EmailVarification(){

            final FirebaseUser user = auth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            // Re-enable button
//                            findViewById(R.id.verify_email_button).setEnabled(true);

                            if (task.isSuccessful()) {
                                //link will be sent to the email
                                Toast.makeText(SignupActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            } else {
//                                Log.e(TAG, "sendEmailVerification", task.getException());Toast.makeText(SignupActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
