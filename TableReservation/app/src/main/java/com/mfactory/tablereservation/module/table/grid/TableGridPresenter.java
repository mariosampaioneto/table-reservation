package com.mfactory.tablereservation.module.table.grid;


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
    public void getTables() {
        tableRepository.getTables()
                .subscribe(tables -> {
                    view.setTables(tables);
                    view.showContentLayout();
                }, throwable -> {
                    view.showErrorLayout();
                });
    }

    @Override
    public void updateTables(List<Table> tables) {
        tableRepository.updateTables(tables)
                .subscribe(tables1 -> {
                    view.showUpdateTableSuccessMessage();
                    view.close();
                }, throwable -> {
                    view.showUpdateTableFailureMessage();
                });
    }

}
