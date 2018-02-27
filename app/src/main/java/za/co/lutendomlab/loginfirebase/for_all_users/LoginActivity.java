package za.co.lutendomlab.loginfirebase.for_all_users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by codetribe on 8/23/2017.
 */

import com.google.firebase.database.ValueEventListener;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;
import za.co.lutendomlab.loginfirebase.admin_activiteis.AdminActivity;
import za.co.lutendomlab.loginfirebase.admin_activiteis.FacilitatorMainActivity;
import za.co.lutendomlab.loginfirebase.student_activities.HomeScreenUser;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;
    private Button btnSignup;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    FirebaseUser user;
    private Button btnReset;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference().child("User");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view now
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        //getSupportActionBar().setTitle("Login");

        //get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        if (user != null) {
//            Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
//            startActivity(intent);
            progressDialog.setMessage("Logging in. Please wait...");
            progressDialog.show();
            navigateToUserScreen(user.getUid());
//            Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
//            startActivity(intent);

//            navigateToUserScreen(user.getUid());
//            progressDialog.setMessage("Logging in. Please wait...");
//            progressDialog.show();

        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


    }

    public void LogIn(View view) {

        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {

            inputEmail.setError("Enter email address!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password!");
            return;
        }
        progressDialog.setMessage("Logging in. Please wait...");
        progressDialog.show();

        //authentication user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressDialog.dismiss();

                        if (!task.isSuccessful()) {
                            //there was an error
                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed, task.getException().getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            navigateToUserScreen(task.getResult().getUser().getUid());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void navigateToUserScreen(final String user_id) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                    User user = dataSnapshot.getValue(User.class);
//                    if ("Facilitator".equals(user.getRole())) {
//                        Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
//                        startActivity(intent);
//                        finish();

//                public void navigateToUserScreen (String user_id){
//                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
//                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                        Intent intent = new Intent(LoginActivity.this, NavigationDrawer.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
                User user1 = dataSnapshot.getValue(User.class);

//                if ("Able".equals(user.getStatus())) {

                if ("Admin".equals(user1.getRole())) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();

                } else
                    if ("Student".equals(user1.getRole())) {
                    Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                }else if ("Facilitator".equals(user1.getRole())) {
                    Intent intent = new Intent(LoginActivity.this, FacilitatorMainActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();

                }
//            }else{
//                    Toast.makeText(LoginActivity.this, "Your Account is not active. Please contact your HR ", Toast.LENGTH_SHORT).show();
//                }
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}