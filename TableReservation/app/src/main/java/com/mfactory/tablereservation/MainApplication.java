package com.mfactory.tablereservation;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

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

    public MainComponent getComponent() {
        return component;
    }
}