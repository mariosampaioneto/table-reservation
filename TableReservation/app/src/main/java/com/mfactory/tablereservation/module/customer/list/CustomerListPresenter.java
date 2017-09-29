package com.mfactory.tablereservation.module.customer.list;

import com.mfactory.tablereservation.repositories.CustomerRepository;

import javax.inject.Inject;

import timber.log.Timber;

public class CustomerListPresenter implements CustomerListContract.Presenter {

    private CustomerListContract.View view;

    private CustomerRepository customerRepository;

    @Inject
    public CustomerListPresenter(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void bindView(CustomerListContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void requestCustomerList() {
        customerRepository.getCustomers()
                .subscribe(customers -> {
                    view.setCustomers(customers);
                    view.showContentLayout();
                }, throwable -> {
                    Timber.e(throwable, throwable.getMessage());
                    view.showErrorLayout();
                });
    }
}
