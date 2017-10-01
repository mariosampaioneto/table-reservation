package com.mfactory.tablereservation;

import com.mfactory.tablereservation.module.customer.list.CustomerListActivity;
import com.mfactory.tablereservation.module.customer.list.CustomerListModule;
import com.mfactory.tablereservation.module.table.grid.TableGridActivity;
import com.mfactory.tablereservation.network.NetworkModule;
import com.mfactory.tablereservation.network.services.ServiceModule;
import com.mfactory.tablereservation.repository.RepositoryModule;
import com.mfactory.tablereservation.repository.provider.ProviderModule;
import com.mfactory.tablereservation.service.ReservationCleanerService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        ProviderModule.class,
        ServiceModule.class,
        CustomerListModule.class
//        TableMapModule.class
})
public interface MainComponent {

    void inject(CustomerListActivity activity);

    void inject(TableGridActivity activity);

    void inject(ReservationCleanerService service);

}