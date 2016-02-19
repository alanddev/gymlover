package com.alanddev.gymlover.model;

import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 21/01/2016.
 */
public class TransactionSumGroup {

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    private String image;
    private String name;
    private float calo;
    private float weight;
    private float time;


}
