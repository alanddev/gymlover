package com.alanddev.gymlover.model;

import java.util.List;

/**
 * Created by ANLD on 07/01/2016.
 */
public class WorkoutExerDay extends Model{
    private int day;
    private List<WorkoutExerDetail> items;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<WorkoutExerDetail> getItems() {
        return items;
    }

    public void setItems(List<WorkoutExerDetail> items) {
        this.items = items;
    }
}
