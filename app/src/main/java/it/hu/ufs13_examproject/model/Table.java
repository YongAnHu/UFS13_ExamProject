package it.hu.ufs13_examproject.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {
    private String id;
    private String name;
    private int num;
    private ArrayList<Order> orders;

    public Table() {}
    public Table(String name, int num) {
        this.name = name;
        this.num = num;
        orders = new ArrayList<>();
    }
    public Table(String id, String name, int num) {
        this.id = id;
        this.name = name;
        this.num = num;
        orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    private String getAll() {
        return name + " " + num;
    }

    @NonNull
    @Override
    public String toString() {
        return getAll();
    }
}
