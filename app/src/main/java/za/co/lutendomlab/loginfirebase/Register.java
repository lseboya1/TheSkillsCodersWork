package za.co.lutendomlab.loginfirebase;

/**
 * Created by codetribe on 10/2/2017.
 */

public class Register {

    String date;
    String day;
    String month;
    String time_in;
    String time_out;
    String week_number;

    public Register(String date, String day, String month, String time_in, String time_out, String week_number) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.time_in = time_in;
        this.time_out = time_out;
        this.week_number = week_number;
    }

    public Register(){}


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }

    public String getWeek_number() {
        return week_number;
    }

    public void setWeek_number(String week_number) {
        this.week_number = week_number;
    }
}