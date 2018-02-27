package za.co.lutendomlab.loginfirebase.student_activities;

import android.content.Context;
import android.content.SharedPreferences;

public class TimeTable {

    private Context context;
    private String mySharedPreferences =  "mySharedPreferences";
    public final String date = "00/00/0000";
    public final String DEFAULT_SIGNED = "Sign_out";

    public TimeTable(Context context){
        this.context = context;
    }

    public String getDate() {

        String newDate = "00:00";

        SharedPreferences sharedPreferences = this.context.getSharedPreferences(mySharedPreferences, Context.MODE_PRIVATE);
        newDate = sharedPreferences.getString(date,newDate);

        return newDate;
    }

    public void saveNewDate(String newDate){

        SharedPreferences sharedPreferences = this.context.getSharedPreferences(mySharedPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(newDate, date);
        editor.commit();

    }

    public void saveSigned(String signed){

//        this.sharedPreferences = this.context.getSharedPreferences("za.ac.lutendomlab", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Sign", signed);
//        editor.commit();
    }

    public String getSigned(){

        String singed = "";
//
//        this.sharedPreferences = this.context.getSharedPreferences("za.ac.lutendomlab", Context.MODE_PRIVATE);
//        singed = this.sharedPreferences.getString("Sign", DEFAULT_SIGNED);
//
        return singed;
    }
}