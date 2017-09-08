package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private EditText  newPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        newPassword = (EditText)findViewById(R.id.newPassword);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    public void Change(View view){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null && !newPassword.getText().toString().trim().equals("")){
            if(newPassword.getText().toString().trim().length() < 6){
                newPassword.setError("Password too short, enter minimum 6 characters");
                progressDialog.dismiss();
            }else {
                user.updatePassword(newPassword.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ChangePassword.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    progressDialog.dismiss();
                                }else {
                                    Toast.makeText(ChangePassword.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        }else if(newPassword.getText().toString().trim().equals("")){
            newPassword.setError("Enter password");
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
