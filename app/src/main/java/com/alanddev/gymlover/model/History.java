package com.alanddev.gymlover.model;

/**
 * Created by ANLD on 30/12/2015.
 */
public class History extends Model {

    private int id;
    private int user_id;
    private float height;
    private float weight;
    private float fat;
    private String date;

    public History(){

    }

    public History(int user_id, float height, float weight, float fat){
        this.user_id = user_id;
        this.height = height;
        this.weight = weight;
        this.fat = fat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }






}

