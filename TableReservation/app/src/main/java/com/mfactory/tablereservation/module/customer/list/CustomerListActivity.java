package com.mfactory.tablereservation.module.customer.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.module.base.BaseActivity;
import com.mfactory.tablereservation.module.table.grid.TableGridActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by mario_1a on 29/09/17.
 */

public class CustomerListActivity extends BaseActivity implements CustomerListContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    CustomerListPresenter customerListPresenter;

    private CustomerListAdapter customerListAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerListActivity.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        handleButterknife();
        getMainComponent().inject(this);
        customerListPresenter.bindView(this);
        setupToolbar(getString(R.string.customer_list_screen_title));

        customerListPresenter.requestCustomerList();
    }

    @Override
    public void setupToolbar(String title) {
        super.setupToolbar(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customer_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        setupSearchView(item, searchView);
        return true;
    }

    private void setupRecyclerViewCustomers() {
        customerListAdapter = new CustomerListAdapter();
        customerListAdapter.setOnCustomerClickedListener((position, customer) -> {
            TableGridActivity.start(CustomerListActivity.this, customer);
        });
        mRecyclerView.setAdapter(customerListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setupSearchView(MenuItem item, SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customerListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void errorRetryBtnPressed() {
        customerListPresenter.requestCustomerList();
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
    public void setCustomers(List<Customer> customers) {
        setupRecyclerViewCustomers();
        customerListAdapter.setCustomers(customers);
    }
}