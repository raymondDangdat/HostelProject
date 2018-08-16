package com.example.raymond.hostel;

public class StudentInformation {
    private String matNo;
    private String department;

    //default constructor
    public StudentInformation() {

    }

    public StudentInformation(String matNo, String department) {
        this.matNo = matNo;
        this.department = department;
    }

    public String getMatNo() {
        return matNo;
    }

    public void setMatNo(String matNo) {
        this.matNo = matNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
