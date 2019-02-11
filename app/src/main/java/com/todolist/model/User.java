package com.todolist.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 *
 * @author GultenSeren
 */

public class User implements Serializable{

    @SerializedName("id")
    public int user_id;
    public String user_name,user_password,user_email;


    public User() {
    }


    public User(String user_name, String user_email) {
        this.user_name = user_name;
        this.user_email = user_email;
    }


    public User(int user_id, String user_name, String user_password, String user_email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
    }


    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_email() {
        return user_email;
    }



    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }


    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }




}
