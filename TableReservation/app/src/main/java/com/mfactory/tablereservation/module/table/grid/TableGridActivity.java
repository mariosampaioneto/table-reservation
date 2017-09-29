package com.mfactory.tablereservation.module.table.grid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.module.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mario_1a on 29/09/17.
 */

public class TableGridActivity extends BaseActivity implements TableGridContract.View {

    @Inject
    TableGridPresenter tableMapPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, TableGridActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        handleButterknife();
        getMainComponent().inject(this);
        tableMapPresenter.bindView(this);
        setupToolbar(getString(R.string.table_grid_screen_title));
    }

    @Override
    public void showErrorLayout() {

    }

    @Override
    public void showContentLayout() {

    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void setTables(List<Table> tables) {

    }

    @Override
    public void showUpdateTableSuccessMessage() {

    }

    @Override
    public void showUpdateTableFailureMessage() {

    }

    @Override
    public void close() {

    }
}
