package com.mfactory.tablereservation.jobs;

import com.firebase.jobdispatcher.JobService;

import timber.log.Timber;


public class ReservationCleanerJob extends JobService {

    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");
        Timber.d("################################################");

        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}