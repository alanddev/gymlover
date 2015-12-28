package com.alanddev.gymlover.helper;

import android.database.Cursor;


import com.alanddev.gymlover.model.Model;

import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public interface IDataSource {
    public void open();
    public void close();
    public Model create(Model data);
    public void update(Model data);
    public int getCount();
    public List<Model> getAll();
    public Model get(String query);
    public List<Model> getAll(String query);
    public Model cursorTo(Cursor cursor);

}
