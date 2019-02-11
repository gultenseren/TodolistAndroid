package com.todolist.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author GultenSeren
 */
public class Task implements Serializable {

   public int task_id,list_id;
   public String task_name,task_description,task_deadline,task_status;

    public Task() {
    }



    public Task(int task_id, int list_id, String task_name, String task_description, String task_deadline, String task_status) {
        this.task_id = task_id;
        this.list_id = list_id;
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_deadline = task_deadline;
        this.task_status = task_status;
    }




    public int getTask_id() {
        return task_id;
    }

    public int getList_id() {
        return list_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_description() {
        return task_description;
    }

    public String getTask_deadline() {
        return task_deadline;
    }

    public String getTask_status() {
        return task_status;
    }



    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }


    public void setList_id(int list_id) {
        this.list_id = list_id;
    }


    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }


    public void setTask_description(String task_description) {
        this.task_description = task_description;
    }


    public void setTask_deadline(String task_deadline) {
        this.task_deadline = task_deadline;
    }


    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }


}
