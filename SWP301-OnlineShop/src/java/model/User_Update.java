/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Hoang Quang
 */
public class User_Update {

    private int uid;
    private String email;
    private String updateBy; // name of admin that edited user
    private Date updateDate; //getdate now
    private int userId; // id of user 
    private String fullname; // name of user....
    private boolean gender;
    private String mobile;
    private String address;

    public User_Update() {
    }

    public User_Update(int uid, String email, String updateBy, Date updateDate, int userId, String fullname, boolean gender, String mobile, String address) {
        this.uid = uid;
        this.email = email;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
        this.userId = userId;
        this.fullname = fullname;
        this.gender = gender;
        this.mobile = mobile;
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    
}
