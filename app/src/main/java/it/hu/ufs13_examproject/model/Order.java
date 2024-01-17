package it.hu.ufs13_examproject.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    private Date timestamp;
    private ArrayList<Row> items;

    public Order() {}
    public Order(Date timestamp, ArrayList<Row> items) {
        this.timestamp = timestamp;
        this.items = items;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public String getTimestampStr() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
        return format.format(timestamp);
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp.toDate();
    }

    public ArrayList<Row> getItems() {
        return items;
    }

    public void setItems(ArrayList<Row> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "timestamp=" + timestamp +
                ", items=" + items +
                '}';
    }
}
