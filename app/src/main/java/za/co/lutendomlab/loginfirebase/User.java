package za.co.lutendomlab.loginfirebase;

/**
 * Created by codeTribe on 8/24/2017.
 */

public class User {


    String name;
    long staffNO;
    String email;

    public User() {

    }


    public void setName(String name)
    {

        this.name= name;
    }

    public void setStaffNO(long staffNO)
    {
        this.staffNO =staffNO;
    }

    public long getStaffNO(){return staffNO;}

    public String getName(){return  name;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
