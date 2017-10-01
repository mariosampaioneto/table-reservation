package com.mfactory.tablereservation.repository;


import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.network.services.Services;
import com.mfactory.tablereservation.repository.provider.CustomerLocalDataProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomerRepository {

    private CustomerLocalDataProvider provider;
    private Services services;

    @Inject
    public CustomerRepository(CustomerLocalDataProvider provider, Services services) {
        this.provider = provider;
        this.services = services;
    }

    public Maybe<List<Customer>> getCustomers() {
        Flowable<List<Customer>> local = getCustomersLocal();
        Flowable<List<Customer>> remote = getCustomersRemote();

        return Flowable.concatArray(local, remote)
                .filter(list -> !list.isEmpty())
                .firstElement();
    }

    private Flowable<List<Customer>> getCustomersLocal() {
        return provider.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Flowable<List<Customer>> getCustomersRemote() {
        return services.getCustomerList()
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(customers -> provider.replace(customers));
    }

}
