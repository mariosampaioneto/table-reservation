package com.mfactory.tablereservation.repository.provider;

import com.mfactory.tablereservation.model.Table;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class TableLocalDataProvider {

    public static final String TAG = "TABLE_CACHE";

    public Flowable<List<Table>> replace(List<Table> tables) {
        return Flowable.create(e -> {
            try {
                Hawk.delete(TAG);
                Hawk.put(TAG, tables);
                e.onNext(tables);
                e.onComplete();
            } catch (Exception exception) {
                e.onError(exception);
            }
        }, BackpressureStrategy.DROP);
    }

    public Flowable<List<Table>> getAll() {
        List<Table> tables = Hawk.get(TAG);
        if (tables != null) {
            return Flowable.just(tables);
        }
        return Flowable.just(new ArrayList<>());
    }
}
