package com.mfactory.tablereservation.module.table.grid;


import com.mfactory.tablereservation.model.Table;

import java.util.List;

public interface TableGridContract {

    interface Presenter {

        void bindView(TableGridContract.View view);

        void unbindView();

        void getTables();

        void updateTables(List<Table> tables);

    }

    interface View {

        void showErrorLayout();

        void showContentLayout();

        void showLoadingLayout();

        void setTables(List<Table> tables);

        void showUpdateTableSuccessMessage();

        void showUpdateTableFailureMessage();

        void close();

    }

}
