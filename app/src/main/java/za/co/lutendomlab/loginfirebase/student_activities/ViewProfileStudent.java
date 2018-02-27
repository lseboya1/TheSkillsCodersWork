package za.co.lutendomlab.loginfirebase.student_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;

public class ViewProfileStudent extends AppCompatActivity {


    private TextView name;
    private TextView lastName;
    private TextView phoneNumber;
    private TextView email;
    private TextView facility;
    private TextView role;
    FirebaseUser firebaseUser;

    String keyUser;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    User user;

    private ImageView profile_Pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_one);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        keyUser = firebaseAuth.getCurrentUser().getUid();

        firebaseUser = firebaseAuth.getCurrentUser();

        profile_Pic=(ImageView)findViewById(R.id.profile_picture);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(keyUser);

        name = (TextView)findViewById(R.id.name);
        lastName = (TextView)findViewById(R.id.lastName);
        phoneNumber = (TextView)findViewById(R.id.phoneNumber);
        email = (TextView)findViewById(R.id.email);
        facility = (TextView)findViewById(R.id.facility);
        role = (TextView)findViewById(R.id.role);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

                name.setText(user.getName());
                lastName.setText(user.getLastName());
                email.setText(user.getEmail());
                phoneNumber.setText(user.getPhoneNumber());
                facility.setText(user.getFacility());
                role.setText(user.getRole());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(firebaseUser.getPhotoUrl() != null){
            String url = firebaseUser.getPhotoUrl().toString();
            Glide.with(getApplicationContext()).load(url).into(profile_Pic);
        }
    }

    public void EditProfile(View view){
        finish();
        startActivity(new Intent(ViewProfileStudent.this, UpdateProfileActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
//        startActivity(new Intent(ViewProfileStudent.this,HomeScreenUser.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}
