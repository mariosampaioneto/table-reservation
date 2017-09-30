package com.mfactory.tablereservation.module.table.grid;


import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.model.Table;

import java.util.List;

public interface TableGridContract {

    interface Presenter {

        void bindView(TableGridContract.View view);

        void unbindView();

        void requestTables();

        void updateTables(Customer customer, Table table, List<Table> tables);

        void onTableClicked(Table table, Customer customer);

    }

    interface View {

        void showErrorLayout();

        void showContentLayout();

        void showLoadingLayout();

        void setTables(List<Table> tables);

        void showUpdateTableSuccessMessage(Table table, Customer customer);

        void showUpdateTableFailureMessage(Table table, Customer customer);

        void showTableReservationConfirmationDialog(Table table, Customer customer);

        void showTableUnavailableDialog(Table table);
    }

}
