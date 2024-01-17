package it.hu.ufs13_examproject.data;

import java.util.ArrayList;

import it.hu.ufs13_examproject.model.MenuCategory;

public interface MenuAsyncResponse {
    void processTerminated(ArrayList<Object> data);
    void processFailed(Exception e);
}
