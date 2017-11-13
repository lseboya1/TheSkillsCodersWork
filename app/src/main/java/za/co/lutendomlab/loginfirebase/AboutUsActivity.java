package za.co.lutendomlab.loginfirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by codeTribe on 11/10/2017.
 */

public class AboutUsActivity extends AppCompatActivity{

    TextView info_luu;
    TextView info_itu;
    TextView info_koketso;
    TextView info_lindo;
    TextView info_samule;
    TextView info_thabang;
    TextView information;

    String my_information = "Geofencing provides a plethora of applications for administrator, especially" +
            " in the workforce management category. It is specifically applied to attendance and time " +
            "functionality, geofencing can allow an administrator to establish geographical areas to " +
            "limit where the users (the administrator, the facilitator and the students) are allowed " +
            "to punch in or out.\n" +
            "\n" +
            "Geotimesheet app allows all users to register and login in their accounts in the system." +
            " Any smartphone with Android operating system can be turned into a mobile time track clock" +
            " by downloading the Geotimesheet app, so it makes tracking time and attendance for travelling" +
            " users facile. Users can only clock in and out for their shifts. Now more than ever, students " +
            "can expect self-service tools with easy-to-use resources from their facilitators and administrator," +
            " so providing a time, attendance, leave forms, sending a message, and beneficial portal on" +
            " their smartphone device can help meet that growing demand.\n" +
            "\n" +
            "The functions which can be performed by the users are: to clock in and out for working" +
            " hours, the students time hours, view students monthly schedule, leave forms and sending messages.\n";

    String my_infor_luu = "Lutendo Seboya \n" +
            "Central University of Technology,free state \n" +
            "N.dip Information Technology(software)\n" +
            "Developer(front and back-end)";

    String my_infor_itu = "Itumeleng Mashao \n" +
            "Tshwane University of Technology\n" +
            "N.dip Information Technology (software)\n" +
            "Developer (front-end and back-end )";

    String my_infor_koketso = "Koketso Magoro \n" +
            "Mamelodi High School \n" +
            "Matric certificate\n" +
            "Researcher";

    String my_infor_lindo = "Lindokuhle Mathibela \n" +
            " Tshwane North College \n" +
            "Preparing Early Childhood Development(certificate)\n" +
            "Scrum Master";

    String my_infor_salome = "Salome Tjale \n" +
            "Tshwane University of technology \n" +
            "N.dip Information Technology (web application and development)\n" +
            "Designer";

    String my_infor_thabang = "Thabang   \n" +
            "Tshwane University of technology \n" +
            "N.dip Information Technology\n" +
            "Designer";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        information = (TextView)findViewById(R.id.information);
        information.setText(my_information);

        info_luu = (TextView)findViewById(R.id.info_luu);
        info_luu.setText(my_infor_luu);

        info_itu = (TextView)findViewById(R.id.info_itu);
        info_itu.setText(my_infor_itu);

        info_koketso = (TextView)findViewById(R.id.info_koketso);
        info_koketso.setText(my_infor_koketso);

        info_lindo = (TextView)findViewById(R.id.info_lindo);
        info_lindo.setText(my_infor_lindo);

        info_samule = (TextView)findViewById(R.id.info_salume);
        info_samule.setText(my_infor_salome);

        info_thabang = (TextView)findViewById(R.id.info_thabang);
        info_thabang.setText(my_infor_thabang);
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
