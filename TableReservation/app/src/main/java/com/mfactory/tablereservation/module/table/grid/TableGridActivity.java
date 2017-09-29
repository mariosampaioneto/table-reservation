package com.mfactory.tablereservation.module.table.grid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.module.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by mario_1a on 29/09/17.
 */

public class TableGridActivity extends BaseActivity implements TableGridContract.View {

    public static final String EXTRA_CUSTOMER = "EXTRA_CUSTOMER";

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    TableGridPresenter tableMapPresenter;

    Customer mCustomer;

    public static void start(Context context, Customer customer) {
        Intent intent = new Intent(context, TableGridActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_CUSTOMER, customer);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_grid);
        handleButterknife();
        getMainComponent().inject(this);
        tableMapPresenter.bindView(this);
        setupToolbar(getString(R.string.table_grid_screen_title));
        loadExtras();
    }

    private void loadExtras() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CUSTOMER)) {
            mCustomer = (Customer) getIntent().getSerializableExtra(EXTRA_CUSTOMER);
        }
    }

    @Override
    public void showErrorLayout() {
        setDisplayedChild(mViewFlipper, mErrorViewConstraintLayout);
    }

    @Override
    public void showContentLayout() {
        setDisplayedChild(mViewFlipper, mRecyclerView);
    }

    @Override
    public void showLoadingLayout() {
        setDisplayedChild(mViewFlipper, mLoadingView);
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
