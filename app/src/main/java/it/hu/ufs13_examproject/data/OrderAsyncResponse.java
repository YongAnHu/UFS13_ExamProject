package it.hu.ufs13_examproject.data;

import java.util.ArrayList;

import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Table;

public interface OrderAsyncResponse {
    void processTerminated(ArrayList<Order> data);
    void processFailed(Exception e);
}
