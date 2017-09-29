package com.mfactory.tablereservation.network.services;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import retrofit2.Retrofit;

@Module
public class ServiceModule {

    @Reusable
    @Provides
    Services provideServices(Retrofit retrofit) {
        return retrofit.create(Services.class);
    }
}
