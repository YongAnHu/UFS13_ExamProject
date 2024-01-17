package it.hu.ufs13_examproject.model;

import androidx.databinding.BaseObservable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Item {
    private String id;
    private String name;
    private String description;
    private ArrayList<String> allergens;
    private String image;
    private double price;

    public Item() {}
    public Item(String id, String name, String description, ArrayList<String> allergens, String image, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.allergens = allergens;
        this.image = image;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(ArrayList<String> allergens) {
        this.allergens = allergens;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
