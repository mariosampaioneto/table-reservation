package com.mfactory.tablereservation.service;

import com.mfactory.tablereservation.repositories.TableRepository;
import com.mfactory.tablereservation.service.base.BaseService;

import javax.inject.Inject;

import timber.log.Timber;


public class ReservationCleanerService extends BaseService {

    public static final String TAG = "RESERVATION_CLEANER_TASK";

    @Inject
    TableRepository tableRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        getMainComponent().inject(this);
    }

    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        Timber.d("### Cleaning all tables reservation ###");
        tableRepository.fetchTables().subscribe();

        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}