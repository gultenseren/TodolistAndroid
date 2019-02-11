package com.todolist.model;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author GultenSeren
 */
public class ListNames {

    public int list_id, user_id;
   public String list_name;

    public ListNames() {
    }

    public ListNames(int list_id, int user_id, String list_name) {
        this.list_id = list_id;
        this.user_id = user_id;
        this.list_name = list_name;
    }

    public ListNames(int list_id, String list_name) {
        this.list_id = list_id;
        this.list_name = list_name;
    }

    public ListNames(String list_name) {
        this.list_name = list_name;
    }

    public int getList_id() {
        return list_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getList_name() {
        return list_name;
    }




    public void setList_id(int list_id) {
        this.list_id = list_id;
    }


    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public void setList_name(String list_name) {
        this.list_name = list_name;
    }




}
