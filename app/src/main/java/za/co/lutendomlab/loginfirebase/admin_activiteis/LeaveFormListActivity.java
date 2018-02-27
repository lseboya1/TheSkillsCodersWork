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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import za.co.lutendomlab.loginfirebase.R;
import za.co.lutendomlab.loginfirebase.adapter_and_object.Leave;
import za.co.lutendomlab.loginfirebase.adapter_and_object.LeaveAdapter;

public class LeaveFormListActivity extends AppCompatActivity {

    private ListView listView;
    private LeaveAdapter leaveAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    List<Leave> allLeaves = new ArrayList<>();
    Leave leave;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.listView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("LeaveApply");

        childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                leaveAdapter = new LeaveAdapter(context, R.layout.model, allLeaves);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.i("Ygritte", snapshot.toString());

                    leave  = snapshot.getValue(Leave.class);

                    allLeaves.add(leave);
                }

                leaveAdapter = new LeaveAdapter(context,R.layout.activity_leave_lis,allLeaves);
                listView.setAdapter(leaveAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LeaveFormListActivity.this,ViewLeaveFormAvivity.class));
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