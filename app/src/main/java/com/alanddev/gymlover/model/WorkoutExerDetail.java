package com.alanddev.gymlover.model;

/**
 * Created by ANLD on 07/01/2016.
 */
public class WorkoutExerDetail extends Model{
    private int id;
    private int workid;
    private int exerid;
    private String desc;
    private int day;
    private int set;
    private int repeat;
    private float weight;
    private float time;


    public WorkoutExerDetail(){

    }

    public WorkoutExerDetail(int workid, int exerid, String desc, int day, int set, int repeat, float weight, float time){
        this.workid = workid;
        this.exerid = exerid;
        this.desc = desc;
        this.day = day;
        this.set = set;
        this.repeat = repeat;
        this.weight = weight;
        this.time = time;
    }


    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkid() {
        return workid;
    }

    public void setWorkid(int workid) {
        this.workid = workid;
    }

    public int getExerid() {
        return exerid;
    }

    public void setExerid(int exerid) {
        this.exerid = exerid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
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
}
