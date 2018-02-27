package za.co.lutendomlab.loginfirebase.adapter_and_object;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.util.Map;

public class User implements Parcelable {

    public Map<String, String> user;

    String userId;
    String name;
    String lastName;
    String email;
    String role;
    String facility;
    String phoneNumber;
    String status;
    int numberOfLeave;
    String profileUrl;

    protected User(Parcel in) {
        userId = in.readString();
        name = in.readString();
        lastName = in.readString();
        email = in.readString();
        role = in.readString();
        facility = in.readString();
        phoneNumber = in.readString();
        profileUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

    public User(String userId, String name, String lastName, String email, String role, String facility, String phoneNumber) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.facility = facility;
        this.phoneNumber = phoneNumber;
    }

    public User(String userId, String name, String lastName, String email, String phoneNumber,String profileUrl) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileUrl = profileUrl;
    }

    public User(String role) {
        this.role = role;
    }

    public User() {}

    public User(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public void setName(String name) {
            this.name = name;
        }

    public void setName(String userId, String name) {
            this.name = name;
            this.userId = userId;
        }

    public String getRole() {
            return role;
        }

    public void setRole(String role) {
            this.role = role;
        }

    public String getFacility() {
            return facility;
        }

    public void setFacility(String facility) {
            this.facility = facility;
        }

    public String getName() {
            return name;
        }

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

    @Override
    public int describeContents() {
            return 0;
        }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userId);
            dest.writeString(name);
            dest.writeString(lastName);
            dest.writeString(email);
            dest.writeString(role);
            dest.writeString(facility);
            dest.writeString(phoneNumber);
            dest.writeInt(numberOfLeave);
        }

    public String getPhoneNumber() {
            return phoneNumber;
        }

    public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfileUrl() {
            return profileUrl;
        }

    public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }

    public int getNumberOfLeave() {
        return numberOfLeave;
    }

    public void setNumberOfLeave(int numberOfLeave) {
        this.numberOfLeave = numberOfLeave;
    }
}
