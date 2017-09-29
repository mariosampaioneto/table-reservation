package com.mfactory.tablereservation.model;

import com.google.gson.annotations.SerializedName;
import com.mfactory.tablereservation.model.base.BaseModel;

public class Customer extends BaseModel {

    private int id;
    @SerializedName("customerFirstName")
    private String firstName;
    @SerializedName("customerLastName")
    private String lastName;

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}