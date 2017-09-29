package com.mfactory.tablereservation.repositories;


import com.mfactory.tablereservation.network.services.Services;
import com.mfactory.tablereservation.repositories.provider.CustomerProvider;
import com.mfactory.tablereservation.repositories.provider.TableProvider;

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
