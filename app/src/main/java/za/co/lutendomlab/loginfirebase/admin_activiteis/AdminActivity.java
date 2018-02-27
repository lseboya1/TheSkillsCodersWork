package za.co.lutendomlab.loginfirebase.admin_activiteis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import za.co.lutendomlab.loginfirebase.adapter_and_object.CircleTransform;
import za.co.lutendomlab.loginfirebase.for_all_users.AboutUsActivity;
import za.co.lutendomlab.loginfirebase.for_all_users.LoginActivity;
import za.co.lutendomlab.loginfirebase.R;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String userID;
    private DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    NavigationView navigationView;
    FirebaseUser user;
    ImageView imageView;
    TextView email;
    DrawerLayout drawer;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setSubtitle(R.string.unofficial);
//        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        email = navigationView.getHeaderView(0).findViewById(R.id.student_email);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        user = firebaseAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("User").child(userID);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (user.getPhotoUrl() != null) {
            String url = user.getPhotoUrl().toString();
//            Glide.with(context).load(url).into(profile_Pic);
            Glide.with(getApplicationContext())
                    .load(url)
                    .asBitmap()
                    .transform(new CircleTransform(getApplicationContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
        email.setText(user.getEmail());
    }

    public void Facilitators(){

        Intent intent = new Intent(this, ListViewFacilitatorsActivity.class);
        startActivity(intent);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id){

            case R.id.facilitators:
                Facilitators();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){

            case R.id.about:
                startActivity(new Intent(AdminActivity.this, AboutUsActivity.class));
                break;

            case R.id.update_profile:
                Intent intent = new Intent(AdminActivity.this, ViewProfileAdmin.class);
                startActivity(intent);
                break;

            case R.id.leave_forms:
                startActivity(new Intent(AdminActivity.this,LeaveFormListActivity.class));
                break;

            case R.id.logout:
                SignOut();
                break;

            case R.id.student:
                startActivity(new Intent(AdminActivity.this,DeactivatedStudents.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    public void SignOut() {

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}