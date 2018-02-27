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
import android.widget.ImageView;
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
import za.co.lutendomlab.loginfirebase.adapter_and_object.User;
import za.co.lutendomlab.loginfirebase.adapter_and_object.UserAdapter;
import za.co.lutendomlab.loginfirebase.admin_activiteis.AdminOptionStudents;

public class ListViewActivity extends android.support.v4.app.Fragment {

    private ListView listView;
    private TextView txtTotalNumber;
    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;
    int counter = 0;
    int counter1 = 0;
    List<User> allUsers = new ArrayList<>();
    User user;
    ImageView call;
    TextView total;

    Context context;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View rootview = inflater.inflate(R.layout.activity_list_of_users, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
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

                    if("Codetribe TIH".equals(user.getFacility()) && "Student".equals(user.getRole()) && "Able".equals(user.getStatus())) {
                        allUsers.add(user);
                        counter++;

                       // Toast.makeText(context, "number : " + counter, Toast.LENGTH_SHORT).show();
                    }else
                    if("Codetribe TIH".equals(user.getFacility()) && "Student".equals(user.getRole()) && "Disable".equals(user.getStatus())) {

                        counter1++;
                    }
                }
                userAdapter = new UserAdapter(context,R.layout.model,allUsers);
                listView.setAdapter(userAdapter);


                txtTotalNumber = (TextView)rootview.findViewById(R.id.txtTotalNumber);

                txtTotalNumber.setText("You have "+counter+ " active and "+counter1+" deactivated students");
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
        databaseReference.addValueEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        counter = 0;
        counter1 = 0;
        databaseReference.removeEventListener(childEventListener);
    }
}
