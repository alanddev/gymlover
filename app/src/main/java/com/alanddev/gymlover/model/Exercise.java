package com.alanddev.gymlover.model;

/**
 * Created by ANLD on 30/12/2015.
 */
public class Exercise extends Model{
    private int id;
    private String name;
    private int exgroup_id;
    private String exgroup_name;
    private String image;
    private String description;
    private String videolink;

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

    public int getExgroup_id() {
        return exgroup_id;
    }

    public void setExgroup_id(int exgroup_id) {
        this.exgroup_id = exgroup_id;
    }

    public String getExgroup_name() {
        return exgroup_name;
    }

    public void setExgroup_name(String exgroup_name) {
        this.exgroup_name = exgroup_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }
}
