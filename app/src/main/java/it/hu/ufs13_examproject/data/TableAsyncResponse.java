package it.hu.ufs13_examproject.data;

import java.util.ArrayList;

import it.hu.ufs13_examproject.model.Table;

public interface TableAsyncResponse {
    void processTerminated(ArrayList<Table> data);
    void processFailed(Exception e);
}
