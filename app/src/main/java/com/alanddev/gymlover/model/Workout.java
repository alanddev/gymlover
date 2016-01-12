package com.alanddev.gymlover.model;

import com.alanddev.gymlover.util.Constant;

import java.util.List;

/**
 * Created by ANLD on 06/01/2016.
 */
public class Workout extends Model{
    private int id;
    private String name;
    private String image;
    private String desc;
    private int week;
    private int uses;
    private List<WorkoutExerDay> workoutExerDays;


    public Workout(){

    }

    public Workout(String name, String image, String desc, int week){
        this.name = name;
        this.image = image;
        this.desc= desc;
        this.week = week;
        this.uses = Constant.WORKOUT_NEW;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getUses() {
        return uses;
    }

    public void setUses(int uses) {
        this.uses = uses;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<WorkoutExerDay> getWorkoutExerDays() {
        return workoutExerDays;
    }

    public void setWorkoutExerDays(List<WorkoutExerDay> workoutExerDays) {
        this.workoutExerDays = workoutExerDays;
    }
}
