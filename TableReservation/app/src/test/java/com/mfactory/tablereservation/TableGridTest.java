package com.mfactory.tablereservation;

import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.module.customer.list.CustomerListContract;
import com.mfactory.tablereservation.module.customer.list.CustomerListPresenter;
import com.mfactory.tablereservation.module.table.grid.TableGridContract;
import com.mfactory.tablereservation.module.table.grid.TableGridPresenter;
import com.mfactory.tablereservation.repository.CustomerRepository;
import com.mfactory.tablereservation.repository.TableRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TableGridTest {

    @Mock
    private TableGridContract.View view;
    private TableGridPresenter presenter;

    @Mock
    private TableRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new TableGridPresenter(repository);
        presenter.bindView(view);
    }

    @Test
    public void tableGridRequestedSuccessfully() throws Exception {
        List<Table> mockList = new ArrayList<>();
        mockList.add(new Table(1, true, new Customer(1, "Mário", "Sampaio")));

        when(repository.getTables()).thenAnswer(new Answer<Maybe<List<Table>>>() {
            @Override
            public Maybe<List<Table>> answer(InvocationOnMock invocation) throws Throwable {
                return Maybe.just(mockList);
            }
        });
        presenter.requestTables();
        verify(view, times(1)).setTables(mockList);
        verify(view, times(1)).showContentLayout();
    }

    @Test
    public void tableGridRequestedWithError() throws Exception {
        when(repository.getTables()).thenAnswer(new Answer<Maybe<Exception>>() {
            @Override
            public Maybe<Exception> answer(InvocationOnMock invocation) throws Throwable {
                return Maybe.error(new Exception());
            }
        });
        presenter.requestTables();
        verify(view, times(1)).showErrorLayout();
    }

    @Test
    public void availableTableClicked() throws Exception {
        Table mockTable = new Table(1, true, null);
        Customer mockCustomer = new Customer(1, "Mário", "Sampaio");

        presenter.onTableClicked(mockTable, mockCustomer);
        verify(view, times(1)).showTableReservationConfirmationDialog(mockTable, mockCustomer);
    }

    @Test
    public void unavailableTableClicked() throws Exception {
        Table mockTable = new Table(1, false, null);
        Customer mockCustomer = new Customer(1, "Mário", "Sampaio");

        presenter.onTableClicked(mockTable, mockCustomer);
        verify(view, times(1)).showTableUnavailableDialog(mockTable);
    }

    @After
    public void tearDown() {
        presenter.unbindView();
    }
}