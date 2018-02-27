package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import za.co.lutendomlab.loginfirebase.for_all_users.AboutUsActivity;
import za.co.lutendomlab.loginfirebase.for_all_users.LoginActivity;
import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;
import za.co.lutendomlab.loginfirebase.adapter_and_object.UserAdapter;

/**
 * Created by codeTribe on 11/10/2017.
 */

public class FacilitatorMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;
    private TextView txtTotalNumber;
    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    List<User> allUsers = new ArrayList<>();
    User user;
    FirebaseUser firebaseUser;
    Toolbar toolbar;
    String faciliity;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_facilitator);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTotalNumber = (TextView) findViewById(R.id.txtTotalNumber);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setVisibility(View.VISIBLE);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtTotalNumber = (TextView)findViewById(R.id.txtTotalNumber);

        firebaseAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("User");

        firebaseUser = firebaseAuth.getCurrentUser();
        childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userAdapter = new UserAdapter(context, R.layout.model, allUsers);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    user  = snapshot.getValue(User.class);

                    if("Codetribe TIH".equals(user.getFacility()) && "Student".equals(user.getRole())) {
                        allUsers.add(user);
                    }
                    else {
                        return;
                    }
                }


                userAdapter = new UserAdapter(context,R.layout.model,allUsers);
                listView.setAdapter(userAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        User user = allUsers.get(position);
                        Intent intent = new Intent(context,AdminOption.class);
                        intent.putExtra("userProfile",user);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.facilitator, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){

            case R.id.about:
                startActivity(new Intent(FacilitatorMainActivity.this, AboutUsActivity.class));
                break;

            case R.id.update_profile:
                Intent intent = new Intent(FacilitatorMainActivity.this, ViewProfileAdmin.class);
                startActivity(intent);
                break;

            case R.id.logout:
                SignOut();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }

    public void SignOut() {

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}