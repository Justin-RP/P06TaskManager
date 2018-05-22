package com.example.a16022916.p06taskmanager;

import java.io.Serializable;

public class Item implements Serializable{

    int id, time;
    String name,description;

    public Item(int id, String name, String description, int time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
