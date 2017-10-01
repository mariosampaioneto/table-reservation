package com.mfactory.tablereservation.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.module.customer.list.CustomerListActivity;
import com.mfactory.tablereservation.module.table.grid.TableGridActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CustomerListTest {

    @Rule
    public ActivityTestRule<CustomerListActivity> mActivityRule = new ActivityTestRule<>(CustomerListActivity.class);

    private Solo solo;

    @Before
    public void setup() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                mActivityRule.getActivity());
    }

    @Test
    public void listIsFilled() {
        RecyclerView recyclerView = navigateToCustomerListActivity();

        assertTrue("Recyclerview must have customers.", recyclerView.getAdapter().getItemCount() > 0);
    }

    @Test
    public void searchIsWorking() {
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recyclerview);
        solo.clickOnView(solo.getView(R.id.action_search));
        solo.typeText(0, "Nelson Mandela");

        assertTrue("Filtered result count must be == 1", recyclerView.getAdapter().getItemCount() == 1);
    }

    @Test
    public void clickIsWorking() {
        RecyclerView recyclerView = navigateToCustomerListActivity();

        View listItem = recyclerView.getChildAt(0);
        solo.clickOnView(listItem);

        solo.assertCurrentActivity("Choosing a customer must call TableGridActivity", TableGridActivity.class);
    }

    private RecyclerView navigateToCustomerListActivity() {
        solo.unlockScreen();
        solo.waitForActivity(CustomerListActivity.class);
        solo.waitForView(solo.getView(R.id.recyclerview));
        return (RecyclerView) solo.getView(R.id.recyclerview);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
