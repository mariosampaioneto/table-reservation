package com.mfactory.tablereservation;

import android.app.Application;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobTrigger;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.mfactory.tablereservation.jobs.ReservationCleanerJob;
import com.orhanobut.hawk.Hawk;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

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
                .setService(ReservationCleanerJob.class)
                // uniquely identifies the job
                .setTag("my-unique-tag")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(periodicTrigger(20, 1))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();

        dispatcher.mustSchedule(reservationCleanerJob);
    }

    public JobTrigger periodicTrigger(int frequency, int tolerance) {
        return Trigger.executionWindow(frequency - tolerance, frequency);
    }

    public MainComponent getComponent() {
        return component;
    }
}