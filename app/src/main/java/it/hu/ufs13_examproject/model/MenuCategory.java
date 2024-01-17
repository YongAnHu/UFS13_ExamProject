package it.hu.ufs13_examproject.model;

import java.util.ArrayList;
import java.util.Map;

public class MenuCategory {
    private String name;
    private ArrayList<Item> items;

    public MenuCategory() {}
    public MenuCategory(String name, ArrayList<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Item getItem(String id) {
        Item response = null;

        for (Item el:
             items) {
            if (el.getId().equals(id)) {
                response = el;
            }
        }

        return response;
    }

    // TODO: Add implementation for DB addition
    public boolean addItemToList(Item item) {
        items.add(item);
        return true;
    }
    public void addItemToList(ArrayList<?> items) {
        this.items = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Map row = (Map) items.get(i);
            this.items.add(new Item(
                    (String) row.get("id"),
                    (String) row.get("name"),
                    (String) row.get("description"),
                    (ArrayList<String>) row.get("allergens"),
                    (String) row.get("image"),
                    Double.parseDouble(row.get("price").toString())
            ));
        }
    }

    // TODO: Add implementation for DB removal
    public boolean removeItemFromList(String id) {
        items.removeIf(item -> item.getId().equals(id));
        return true;
    }
}
