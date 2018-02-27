package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;
import za.co.lutendomlab.loginfirebase.adapter_and_object.UserAdapter;

public class ListViewFacilitatorsActivity extends AppCompatActivity {

    private ListView listView;
    private TextView txtTotalNumber;
    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    List<User> allUsers = new ArrayList<>();
    User user;
    Toolbar toolbar;

    Context context;
    int counter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTotalNumber = (TextView) findViewById(R.id.txtTotalNumber);
//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setVisibility(View.VISIBLE);

        txtTotalNumber = (TextView)findViewById(R.id.txtTotalNumber);

        firebaseAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("User");

        childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allUsers.clear();
//                userAdapter = new UserAdapter(context, R.layout.model, allUsers);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.i("Ygritte", snapshot.toString());

                    user  = snapshot.getValue(User.class);

                    if("Facilitator".equals(user.getRole())) {
                        allUsers.add(user);
                    }
                }

                txtTotalNumber.setText(""+ counter);

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
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(childEventListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        counter = 0;
        databaseReference.removeEventListener(childEventListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}