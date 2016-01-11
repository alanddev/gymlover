package com.alanddev.gymlover.model;

import java.util.Date;

/**
 * Created by ANLD on 30/12/2015.
 */
public class User extends Model {

    private int id;
    private String name;
    private int gender;
    private float height;
    private float weight;
    private float fat;
    private String img;
    private String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }



    public User(){

    }

    public User(String name, String birthday, int gender, float height, float weight, float fat,String imagePath){
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.fat = fat;
        this.img = imagePath;
        this.birthday = birthday;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}

