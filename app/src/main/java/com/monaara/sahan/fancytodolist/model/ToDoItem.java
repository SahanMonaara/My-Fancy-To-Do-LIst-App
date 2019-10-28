package com.monaara.sahan.fancytodolist.model;


import java.util.ArrayList;

public class ToDoItem {
    private int id;
    private String title, date, details,completeStatus,finishStatus;
    private ArrayList listOfItems;

     public ToDoItem() {
    }

    public ToDoItem(int id, String title, String date, String details, String completeStatus, String finishStatus) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.details = details;
        this.completeStatus = completeStatus;
        this.finishStatus = finishStatus;
    }

    public ToDoItem(String title, String date, String completeStatus, String finishStatus) {
        this.title = title;
        this.date = date;
        this.completeStatus = completeStatus;
        this.finishStatus = finishStatus;
    }

    public ToDoItem(String title, String date, ArrayList items, String not_completed, String not_finished) {
        this.title = title;
        this.date = date;
        this.completeStatus = not_completed;
        this.finishStatus = not_finished;
        this.listOfItems = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }
}