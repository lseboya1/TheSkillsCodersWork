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
    private TextView Staff_number;
    private Button btnSignIn;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private StaffNumberGenerator staffNumberGenerator;
    private long generatedStaffNumber;
    private  String facility;

    String[] facilityList={"Codetribe TIH","Codetibe Soweto","Codetribe Tembisa"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);




        Spinner spin =(Spinner)findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(SignupActivity.this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,facilityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);


        progressDialog = new ProgressDialog(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null)
        {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),HomeScreenUser.class));
        }

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        Staff_number = (TextView) findViewById(R.id.Staff_number);
        inputConfirmPassword = (EditText)findViewById(R.id.re_password);
        etName =(EditText)findViewById(R.id.name);
        lastName =(EditText)findViewById(R.id.lastName);
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
                final String lName =lastName.getText().toString().trim();

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

                                boolean is_admin = isAdmin(auth.getCurrentUser().getEmail());
                                if (is_admin) {
                                    user.setRole("Facilitator");
                                } else {
                                    user.setRole("Student");
                                }
                                user.setEmail(auth.getCurrentUser().getEmail());
                                user.setName(auth.getCurrentUser().getUid(),name );

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

                                } else {
                                    finish();

                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));




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



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        facility =facilityList[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
