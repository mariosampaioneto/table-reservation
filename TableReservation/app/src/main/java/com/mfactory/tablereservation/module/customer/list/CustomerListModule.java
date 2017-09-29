package com.mfactory.tablereservation.module.customer.list;

import com.mfactory.tablereservation.repositories.CustomerRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomerListModule {

    @Provides
    @Singleton
    public CustomerListContract.Presenter provideCustomerListPresenter(CustomerRepository customerRepository) {
        return new CustomerListPresenter(customerRepository);
    }

}
