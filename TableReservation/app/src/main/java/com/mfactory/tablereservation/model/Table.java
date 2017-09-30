package com.mfactory.tablereservation.model;

import com.mfactory.tablereservation.model.base.BaseModel;

/**
 * Created by mario_1a on 29/09/17.
 */

public class Table extends BaseModel {

    private int number;
    private Boolean available;
    private Customer customer;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isAvailable() {
        boolean available = false;

        if (this.available != null) {
            available = this.available;
        }

        return available;
    }
}
