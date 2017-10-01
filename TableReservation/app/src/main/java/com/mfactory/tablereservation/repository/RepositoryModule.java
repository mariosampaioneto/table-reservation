package com.mfactory.tablereservation.repository;


import com.mfactory.tablereservation.network.services.Services;
import com.mfactory.tablereservation.repository.provider.CustomerLocalDataProvider;
import com.mfactory.tablereservation.repository.provider.TableLocalDataProvider;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class RepositoryModule {

    @Provides
    @Reusable
    public CustomerRepository provideCustomerRepository(CustomerLocalDataProvider provider, Services services) {
        return new CustomerRepository(provider, services);
    }

    @Provides
    @Reusable
    public TableRepository provideTableRepository(TableLocalDataProvider provider, Services services) {
        return new TableRepository(provider, services);
    }

}
