package com.mfactory.tablereservation.network.services;

import com.mfactory.tablereservation.model.Customer;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface Services {

    @GET("customer-list.json")
    Flowable<List<Customer>> getCustomerList();

    @GET("table-map.json")
    Flowable<List<Boolean>> getTableMap();
}