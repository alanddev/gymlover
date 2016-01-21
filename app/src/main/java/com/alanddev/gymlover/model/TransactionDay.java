package com.alanddev.gymlover.model;

import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 21/01/2016.
 */
public class TransactionDay {
    private Date display_date;
    private String displayStr;
    private float calo;
    private List<Transaction> items;

    public Date getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(Date display_date) {
        this.display_date = display_date;
    }

    public String getDisplayStr() {
        return displayStr;
    }

    public void setDisplayStr(String displayStr) {
        this.displayStr = displayStr;
    }

    public float getCalo() {
        return calo;
    }

    public void setCalo(float calo) {
        this.calo = calo;
    }

    public List<Transaction> getItems() {
        return items;
    }

    public void setItems(List<Transaction> items) {
        this.items = items;
    }
}
