package za.co.lutendomlab.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    user = snapshot.getValue(User.class);

                    if ("Facilitator".equals(user.getRole())) {
                        allUsers.add(user);
                        counter++;
                        Toast.makeText(context, "Total = " + counter, Toast.LENGTH_SHORT).show();
                    }
                }

                txtTotalNumber.setText("" + counter);

                userAdapter = new UserAdapter(context, R.layout.model, allUsers);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.i("Ygritte", snapshot.toString());

                    user  = snapshot.getValue(User.class);

                    if("Facilitator".equals(user.getRole())) {
                        allUsers.add(user);
                        counter++;
                    }
                }

                txtTotalNumber.setText(""+ counter);

                userAdapter = new UserAdapter(context,R.layout.model,allUsers);
                listView.setAdapter(userAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        User user = allUsers.get(position);
                        String phoneNumbur = "0790420795";
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                Uri.fromParts("tel", phoneNumbur, null));
                        startActivity(phoneIntent);
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
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        databaseReference.removeEventListener(childEventListener);
//    }
}