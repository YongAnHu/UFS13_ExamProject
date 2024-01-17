package it.hu.ufs13_examproject.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Row implements Serializable {
    private String id_item;
    private String name;

    public Row() {}
    public Row(String id_item, String name) {
        this.id_item = id_item;
        this.name = name;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
