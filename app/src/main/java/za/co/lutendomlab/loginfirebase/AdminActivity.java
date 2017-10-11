package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth =FirebaseAuth.getInstance();

    }
    public void LogOut(View view){

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void Option(View view){
        Toast.makeText(this, "scscsc", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AdminActivity.this, AdminOption.class);

        Toast.makeText(this, "scscsc", Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}
