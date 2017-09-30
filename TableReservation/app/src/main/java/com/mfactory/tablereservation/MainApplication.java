package com.mfactory.tablereservation;

import android.app.Application;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.mfactory.tablereservation.service.ReservationCleanerService;
import com.orhanobut.hawk.Hawk;

import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MINUTES;

public class MainApplication extends Application {

    private MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        if (BuildConfig.DEBUG) {
            initTimber();
        }
        initDagger();
        initHawk();
        initJobScheduler();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initDagger() {
        component = DaggerMainComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initHawk() {
        Hawk.init(this).build();
    }

    private void initJobScheduler() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));

        Job reservationCleanerJob = dispatcher.newJobBuilder()
                .setService(ReservationCleanerService.class)
                .setTag(ReservationCleanerService.TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow((int) MINUTES.toSeconds(10) - 1, (int) MINUTES.toSeconds(10)))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        dispatcher.mustSchedule(reservationCleanerJob);
    }

    public MainComponent getComponent() {
        return component;
    }
}