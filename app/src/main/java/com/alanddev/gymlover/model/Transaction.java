package com.alanddev.gymlover.model;

/**
 * Created by ANLD on 30/12/2015.
 */
public class Transaction extends Model {

    private int id;
    private int exericise;
    private String date;
    private int repeat;
    private float weight;
    private float time;
    private float calo;
    private String note;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExericise() {
        return exericise;
    }

    public void setExericise(int exericise) {
        this.exericise = exericise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
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

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }





    public Transaction(){

    }

    public Transaction(String date, int exericise, int repeat,  float weight, float time,float calo, String note){
        this.exericise = exericise;
        this.date = date;
        this.calo = calo;
        this.weight = weight;
        this.repeat = repeat;
        this.time = time;
        this.note = note;
    }



}

