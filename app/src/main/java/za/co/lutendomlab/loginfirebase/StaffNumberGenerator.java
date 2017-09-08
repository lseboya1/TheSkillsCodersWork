package za.co.lutendomlab.loginfirebase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by codeTribe on 8/29/2017.
 */

public class StaffNumberGenerator {


    private Context context;
    private SharedPreferences sharedPreferences;
    public final int DEFAULT_SAFF_NUMBER = 6547800;

    public StaffNumberGenerator(Context context){
        this.context = context;
    }

    public long generateSaffNumber() {
        long newStaffNumber = 0;

        this.sharedPreferences = this.context.getSharedPreferences("za.ac.lutendomlab", Context.MODE_PRIVATE);
        newStaffNumber = this.sharedPreferences.getInt("StudentNumber", DEFAULT_SAFF_NUMBER);

        return newStaffNumber;
    }

    public void saveNewStaffNumber(int newsTAFFNumber){

        this.sharedPreferences = this.context.getSharedPreferences("za.ac.lutendomlab", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("StudentNumber", newsTAFFNumber);
        editor.commit();

    }

}
