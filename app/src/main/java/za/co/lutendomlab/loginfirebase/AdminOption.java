package za.co.lutendomlab.loginfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by codetribe on 10/10/2017.
 */

public class AdminOption extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] facilityList = {"Student","Facilitator","Admin"};
    String facility;
    Button save;
    Spinner spin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_roles_activity);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,facilityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        facility = facilityList[position];

        Toast.makeText(getApplicationContext(),"Selected" + facility, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    public void Assign(View view){
        save = (Button)findViewById(R.id.save);
        save.setVisibility(View.VISIBLE);
        spin.setVisibility(View.VISIBLE);
    }

    public void Save(View view){

        save = (Button)findViewById(R.id.save);
        save.setVisibility(View.GONE);
        spin.setVisibility(View.GONE);
    }
}
