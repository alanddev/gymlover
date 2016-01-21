package com.alanddev.gymlover.model;

import java.util.List;

/**
 * Created by ANLD on 21/01/2016.
 */
public class Transactions {
    private String title;
    private float calo;
    private List<TransactionDay> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public List<TransactionDay> getItems() {
        return items;
    }

    public void setItems(List<TransactionDay> items) {
        this.items = items;
    }
}
