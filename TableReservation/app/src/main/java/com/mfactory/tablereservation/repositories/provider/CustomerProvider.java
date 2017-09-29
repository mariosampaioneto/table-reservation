package com.mfactory.tablereservation.repositories.provider;

import com.mfactory.tablereservation.model.Customer;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class CustomerProvider {

    private static final String TAG = CustomerProvider.class.getSimpleName();

    public Flowable<List<Customer>> update(List<Customer> customers) {
        return Flowable.create(e -> {
            try {
                Hawk.delete(TAG);
                Hawk.put(TAG, customers);
                e.onNext(customers);
                e.onComplete();
            } catch (Exception exception) {
                e.onError(exception);
            }
        }, BackpressureStrategy.DROP);
    }

    public Flowable<List<Customer>> add(List<Customer> customers) {
        return Flowable.create(e -> {
            try {
                Hawk.put(TAG, customers);
                e.onNext(customers);
                e.onComplete();
            } catch (Exception exception) {
                e.onError(exception);
            }
        }, BackpressureStrategy.DROP);
    }

    public Flowable<List<Customer>> getAll() {
        List<Customer> customers = Hawk.get(TAG);
        if (customers != null) {
            return Flowable.just(customers);
        }
        return Flowable.just(new ArrayList<>());
    }

    public Flowable<Void> delete() {
        return Flowable.create(e -> {
            try {
                Hawk.delete(TAG);
                e.onNext(null);
                e.onComplete();
            } catch (Exception exception) {
                e.onError(exception);
            }
        }, BackpressureStrategy.DROP);
    }

}
