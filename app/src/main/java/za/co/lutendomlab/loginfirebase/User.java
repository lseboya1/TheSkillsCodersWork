package za.co.lutendomlab.loginfirebase;

/**
 * Created by codeTribe on 8/24/2017.
 */

public class User {

    String userId;
    String name;
    String lastName;
    long staffNO;
    String email;
    String role;
    String facility;


    public User() {}

    public void setName(String userId, String name) {
        this.name= name;
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setStaffNO(long staffNO)
    {
        this.staffNO =staffNO;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public long getStaffNO(){return staffNO;}

    public String getName(){return  name;}

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
