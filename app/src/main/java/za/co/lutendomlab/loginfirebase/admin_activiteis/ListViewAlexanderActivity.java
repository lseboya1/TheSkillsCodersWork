package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ListViewAlexanderActivity extends android.support.v4.app.Fragment {

    private ListView listView;
    private TextView txtTotalNumber;

    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;

    List<User> allUsers = new ArrayList<>();
    User user;

    Context context;
    int counter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.activity_list_of_users, container, false);
        firebaseAuth = FirebaseAuth.getInstance();


        txtTotalNumber = (TextView)rootview.findViewById(R.id.txtTotalNumber);
//        context = getApplicationContext();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("User");

        childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.i("Ygritte", snapshot.toString());

                    user  = snapshot.getValue(User.class);
                    if("Codetribe Alexandra".equals(user.getFacility()) && "Student".equals(user.getRole())) {
                        allUsers.add(user);
                        counter++;
                    }
                }

                txtTotalNumber.setText(""+ counter);

                userAdapter = new UserAdapter(context,R.layout.model,allUsers);
                listView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        listView = (ListView) rootview.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = allUsers.get(position);
                Intent intent = new Intent(context,AdminOptionStudents.class);
                intent.putExtra("userProfile",user);
                startActivity(intent);
            }
        });


        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        counter = 0;
        databaseReference.addValueEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
}
