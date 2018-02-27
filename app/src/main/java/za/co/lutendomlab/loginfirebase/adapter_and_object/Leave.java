package za.co.lutendomlab.loginfirebase.adapter_and_object;

import java.util.Map;

/**
 * Created by codeTribe on 11/14/2017.
 */

public class Leave {

    String name;
    String lastName;
    String leaveAddres;
    String leaveType;
    String conditionType;
    String fromDate;
    String toDate;
    long noDays;
    String phoneNumber;

    public Leave() {

    }

    public Leave(String name, String lastName, String leaveAddres, String leaveType, String conditionType, String fromDate, String toDate, long noDays, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.leaveAddres = leaveAddres;
        this.leaveType = leaveType;
        this.conditionType = conditionType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.noDays = noDays;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Leave setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public Leave setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Leave setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getLeaveAddres() {
        return leaveAddres;
    }

    public Leave setLeaveAddres(String leaveAddres) {
        this.leaveAddres = leaveAddres;
        return this;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public Leave setLeaveType(String leaveType) {
        this.leaveType = leaveType;
        return this;
    }

    public String getConditionType() {
        return conditionType;
    }

    public Leave setConditionType(String conditionType) {
        this.conditionType = conditionType;
        return this;
    }

    public String getFromDate() {
        return fromDate;
    }

    public Leave setFromDate(String fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public String getToDate() {
        return toDate;
    }

    public Leave setToDate(String toDate) {
        this.toDate = toDate;
        return this;
    }

    public long getNoDays() {
        return noDays;
    }

    public Leave setNoDays(long noDays) {
        this.noDays = noDays;
        return this;
    }
}
