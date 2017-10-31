package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private TextView textViewUserEmail;
    private TextView textViewUserName;
    String userID;
    String userName;
    String surname;
    String role;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    Context context;
    List<User> allUsers = new ArrayList<>();

    FirebaseUser user;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        user = firebaseAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("User").child(userID);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                role = user.getRole();

                userName = user.getName();
                surname =user.getLastName();
                textViewUserName.setText("Name: " + userName + ", " + surname);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//
//                if (userID.equals(dataSnapshot.child("userId").getValue().toString())) {
//
////                    Register reg = dataSnapshot.getValue(Register.class);
//
//                    //  textViewUserName.setText("Name: "+user.getName());
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//
//        textViewUserName = (TextView)findViewById(R.id.textViewName);
//        textViewUserEmail = (TextView)findViewById(R.id.textViewEmail);
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        user = firebaseAuth.getCurrentUser();
//        if(user == null){
//
//        }else {
//
//        }
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference().child("User");
//
//        userID = user.getUid();
//
//        Toast.makeText(context, userID, Toast.LENGTH_SHORT).show();
//
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                if(userID.equals(dataSnapshot.child("userId").getValue().toString())){
//
//                    User user = dataSnapshot.getValue(User.class);
//                    role = user.getRole();
//
//                    textViewUserName.setText("Name: " + user.getName());
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

//        textViewUserEmail.setText("Email: " + user.getEmail());

        textViewUserName = (TextView)findViewById(R.id.textViewName);
        textViewUserEmail = (TextView)findViewById(R.id.textViewEmail);
        textViewUserEmail.setText("Email: "+ user.getEmail());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void SignOut(View view){

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void Facilitators(View view){

        Intent intent = new Intent(this, ListViewFacilitatorsActivity.class);
        startActivity(intent);
    }


    /**
     * Tab
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putString("facility","TIH");
                    ListViewActivity listViewActivity = new ListViewActivity();
                    listViewActivity.setArguments(bundle);
                    return listViewActivity;
                case 1:
                    return new ListViewSowetoActivity();

                case 2:
                    return new ListViewTembisaActivity();

                case 3:
                    return new ListViewAlexanderActivity();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TIH";
                case 1:
                    return "Soweto";
                case 2:
                    return "Tembisa";
                case 3:
                    return "Alexandra";
            }
            return null;
        }
    }
}