package com.mfactory.tablereservation.module.table.grid;


import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.repositories.TableRepository;

import java.util.List;

import javax.inject.Inject;

public class TableGridPresenter implements TableGridContract.Presenter {

    private TableGridContract.View view;

    private TableRepository tableRepository;

    @Inject
    public TableGridPresenter(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void bindView(TableGridContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void onTableClicked(Table table, Customer customer) {
        if (table.isAvailable()) {
            view.showTableReservationConfirmationDialog(table, customer);
        } else {
            view.showTableUnavailableDialog(table);
        }
    }

    @Override
    public void requestTables() {
        view.showLoadingLayout();
        tableRepository.getTables()
                .subscribe(tables -> {
                    view.setTables(tables);
                    view.showContentLayout();
                }, throwable -> {
                    view.showErrorLayout();
                });
    }

    @Override
    public void updateTables(Customer customer, Table table, List<Table> tables) {
        table.setAvailable(false);
        table.setCustomer(customer);
        tables.set(table.getNumber() - 1, table);

        tableRepository.updateTables(tables)
                .subscribe(tables1 -> {
                    view.showUpdateTableSuccessMessage(table, customer);
                    requestTables();
                }, throwable -> {
                    view.showUpdateTableFailureMessage(table, customer);
                });
    }

}
