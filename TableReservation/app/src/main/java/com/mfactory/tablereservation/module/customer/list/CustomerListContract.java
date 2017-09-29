package com.mfactory.tablereservation.module.customer.list;


import com.mfactory.tablereservation.model.Customer;

import java.util.List;

public interface CustomerListContract {

    interface Presenter {

        void bindView(CustomerListContract.View view);

        void unbindView();

        void requestCustomerList();

    }

    interface View {

        void showErrorLayout();

        void showContentLayout();

        void showLoadingLayout();

        void setCustomers(List<Customer> customers);

    }

}
