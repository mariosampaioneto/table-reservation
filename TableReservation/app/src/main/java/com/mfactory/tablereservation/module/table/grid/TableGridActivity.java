package com.mfactory.tablereservation.module.table.grid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.module.base.BaseActivity;
import com.mfactory.tablereservation.utils.DialogUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by mario_1a on 29/09/17.
 */

public class TableGridActivity extends BaseActivity implements TableGridContract.View {

    public static final String EXTRA_CUSTOMER = "EXTRA_CUSTOMER";

    @BindView(R.id.customer_name_container)
    ConstraintLayout mCustomerNameContainer;

    @BindView(R.id.customer_name)
    TextView mCustomerName;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    TableGridPresenter tableMapPresenter;

    @Inject
    DialogUtils dialogUtils;

    private TableGridAdapter tableMapAdapter;

    Customer mCustomer;

    public static void start(Context context, Customer customer) {
        Intent intent = new Intent(context, TableGridActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_CUSTOMER, customer);
        intent.putExtras(extras);
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

        setupCustomer();
    }

    private void loadExtras() {
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CUSTOMER)) {
            mCustomer = (Customer) getIntent().getSerializableExtra(EXTRA_CUSTOMER);
        }
    }

    public void setupRecyclerViewTables() {
        tableMapAdapter = new TableGridAdapter();
        tableMapAdapter.setOnItemClickListener((view, table, position) -> tableMapPresenter.onTableClicked(table, mCustomer));
        mRecyclerView.setAdapter(tableMapAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void setupCustomer() {
        if (mCustomer != null) {
            mCustomerName.setText(mCustomer.getFullName());
            tableMapPresenter.requestTables();
        } else {
            showErrorLayout();
        }
    }

    @Override
    public void errorRetryBtnPressed() {
        tableMapPresenter.requestTables();
    }

    @Override
    public void showErrorLayout() {
        mCustomerNameContainer.setVisibility(View.GONE);
        setDisplayedChild(mViewFlipper, mErrorViewConstraintLayout);
    }

    @Override
    public void showContentLayout() {
        mCustomerNameContainer.setVisibility(View.VISIBLE);
        setDisplayedChild(mViewFlipper, mRecyclerView);
    }

    @Override
    public void showLoadingLayout() {
        setDisplayedChild(mViewFlipper, mLoadingView);
    }

    @Override
    public void setTables(List<Table> tables) {
        setupRecyclerViewTables();
        tableMapAdapter.setTables(tables);
    }

    @Override
    public void showUpdateTableSuccessMessage(Table table, Customer customer) {
        Snackbar.make(mRecyclerView,
                String.format(getString(R.string.table_book_success_text), table.getNumber(), customer.getFullName()),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUpdateTableFailureMessage(Table table, Customer customer) {
        dialogUtils.showSimpleDialog(mContext,
                getString(R.string.table_book_failure_title),
                String.format(getString(R.string.table_book_failure_text), table.getNumber(), customer.getFullName()),
                getString(R.string.table_book_success_btn));
    }

    @Override
    public void showTableReservationConfirmationDialog(Table table, Customer customer) {
        dialogUtils.showChooseOptionDialog(mContext,
                getString(R.string.table_confirmation_title),
                String.format(getString(R.string.table_confirmation_text), table.getNumber(), customer.getFullName()),
                getString(R.string.table_confirmation_positive_btn),
                (dialog, which) -> tableMapPresenter.updateTables(mCustomer, table, tableMapAdapter.getTables()),
                getString(R.string.table_confirmation_negative_btn),
                null,
                false
        );
    }

    @Override
    public void showTableUnavailableDialog() {
        dialogUtils.showSimpleDialog(mContext,
                getString(R.string.table_unavailable_dialog_title),
                getString(R.string.table_unavailable_dialog_text),
                getString(R.string.table_unavailable_dialog_btn));
    }
}