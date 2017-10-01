package com.mfactory.tablereservation.repository.provider;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class LocalDataProviderModule {

    @Reusable
    @Provides
    CustomerLocalDataProvider provideCustomerProvider() {
        return new CustomerLocalDataProvider();
    }

    @Reusable
    @Provides
    TableLocalDataProvider provideTableProvider() {
        return new TableLocalDataProvider();
    }

}