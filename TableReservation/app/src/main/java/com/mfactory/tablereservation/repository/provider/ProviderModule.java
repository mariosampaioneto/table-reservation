package com.mfactory.tablereservation.repository.provider;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module
public class ProviderModule {

    @Reusable
    @Provides
    CustomerProvider provideCustomerProvider() {
        return new CustomerProvider();
    }

    @Reusable
    @Provides
    TableProvider provideTableProvider() {
        return new TableProvider();
    }

}