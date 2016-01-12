package com.alanddev.gymlover.model;

import java.util.List;

/**
 * Created by ANLD on 07/01/2016.
 */
public class WorkoutExerWeek extends Model{
    private int week;
    private List<WorkoutExerDay> items;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public List<WorkoutExerDay> getItems() {
        return items;
    }

    public void setItems(List<WorkoutExerDay> items) {
        this.items = items;
    }
}
