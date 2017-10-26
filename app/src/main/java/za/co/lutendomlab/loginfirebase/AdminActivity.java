package za.co.lutendomlab.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private TextView textViewUserEmail;
    private TextView textViewUserName;
    String userID;
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

        textViewUserName = (TextView)findViewById(R.id.textViewName);
        textViewUserEmail = (TextView)findViewById(R.id.textViewEmail);


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
                    return "Alexander";
            }
            return null;
        }
    }
}