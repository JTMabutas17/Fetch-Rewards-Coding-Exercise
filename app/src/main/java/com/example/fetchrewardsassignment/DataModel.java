package com.example.fetchrewardsassignment;

import com.google.gson.annotations.SerializedName;

/**
 * DataModel is the model class for each JSON object.
 *
 * @author Justin Mabutas
 */
public class DataModel {
    @SerializedName("id")
    private int id;
    @SerializedName("listId")
    private int listId;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
