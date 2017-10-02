package com.mfactory.tablereservation;

import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.module.customer.list.CustomerListContract;
import com.mfactory.tablereservation.module.customer.list.CustomerListPresenter;
import com.mfactory.tablereservation.repository.CustomerRepository;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerListTest {

    @Mock
    private CustomerListContract.View view;
    private CustomerListPresenter presenter;

    @Mock
    private CustomerRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new CustomerListPresenter(repository);
        presenter.bindView(view);
    }

    @Test
    public void listRequestedSuccessfully() throws Exception {
        List<Customer> mockList = new ArrayList<>();
        mockList.add(new Customer(1, "Mário", "Sampaio"));

        when(repository.getCustomers()).thenAnswer(new Answer<Maybe<List<Customer>>>() {
            @Override
            public Maybe<List<Customer>> answer(InvocationOnMock invocation) throws Throwable {
                return Maybe.just(mockList);
            }
        });
        presenter.requestCustomerList();
        verify(view, times(1)).setCustomers(mockList);
        verify(view, times(1)).showContentLayout();
    }

    @Test
    public void listRequestedWithError() throws Exception {
        when(repository.getCustomers()).thenAnswer(new Answer<Maybe<Exception>>() {
            @Override
            public Maybe<Exception> answer(InvocationOnMock invocation) throws Throwable {
                return Maybe.error(new Exception());
            }
        });
        presenter.requestCustomerList();
        verify(view, times(1)).showErrorLayout();
    }

    @After
    public void tearDown() {
        presenter.unbindView();
    }
}