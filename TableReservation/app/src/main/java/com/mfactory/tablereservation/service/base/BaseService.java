package com.mfactory.tablereservation.service.base;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mfactory.tablereservation.MainApplication;
import com.mfactory.tablereservation.MainComponent;

public class BaseService extends JobService {

    protected MainComponent getMainComponent() {
        return ((MainApplication) getApplication()).getComponent();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
