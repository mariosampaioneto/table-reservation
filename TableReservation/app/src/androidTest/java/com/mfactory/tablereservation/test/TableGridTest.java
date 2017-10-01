package com.mfactory.tablereservation.test;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mfactory.tablereservation.R;
import com.mfactory.tablereservation.model.Customer;
import com.mfactory.tablereservation.module.customer.list.CustomerListActivity;
import com.mfactory.tablereservation.module.table.grid.TableGridActivity;
import com.mfactory.tablereservation.repository.provider.TableLocalDataProvider;
import com.orhanobut.hawk.Hawk;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TableGridTest {

    public TableGridTest() {
    }

    @Rule
    public ActivityTestRule<CustomerListActivity> mActivityRule = new ActivityTestRule<>(CustomerListActivity.class);

    private Solo solo;

    @Before
    public void setup() {
        //cleaning tables local cache
        Hawk.delete(TableLocalDataProvider.TAG);

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                mActivityRule.getActivity());
    }

    @Test
    public void listIsFilled() {
        navigateToTableGridActivity();
        solo.waitForView(solo.getView(R.id.recyclerview));
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recyclerview);

        assertTrue("Recyclerview must have tables.", recyclerView.getAdapter().getItemCount() > 0);
    }

    @Test
    public void reservationIsWorking() {
        navigateToTableGridActivity();
        solo.waitForView(solo.getView(R.id.recyclerview));
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recyclerview);

        View listItem = recyclerView.getChildAt(0);
        solo.clickOnView(listItem);

        assertTrue("Confirmation dialog", solo.searchText("Confirm booking table"));

        solo.clickOnText("YES");

        assertTrue("Booking success", solo.searchText("booked successfully"));
    }

    private void navigateToTableGridActivity() {
        solo.unlockScreen();
        solo.waitForActivity(CustomerListActivity.class);
        solo.waitForView(solo.getView(R.id.recyclerview));

        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recyclerview);
        View listItem = recyclerView.getChildAt(0);

        solo.clickOnView(listItem);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
