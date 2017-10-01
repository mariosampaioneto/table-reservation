package com.mfactory.tablereservation.repository;


import com.mfactory.tablereservation.network.services.Services;
import com.mfactory.tablereservation.repository.provider.CustomerProvider;
import com.mfactory.tablereservation.repository.provider.TableProvider;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class RepositoryModule {

    @Provides
    @Reusable
    public CustomerRepository provideCustomerRepository(CustomerProvider provider, Services services) {
        return new CustomerRepository(provider, services);
    }

    @Provides
    @Reusable
    public TableRepository provideTableRepository(TableProvider provider, Services services) {
        return new TableRepository(provider, services);
    }

}
