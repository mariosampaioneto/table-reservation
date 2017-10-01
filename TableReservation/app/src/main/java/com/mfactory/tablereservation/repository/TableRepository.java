package com.mfactory.tablereservation.repository;

import com.mfactory.tablereservation.model.Table;
import com.mfactory.tablereservation.network.services.Services;
import com.mfactory.tablereservation.repository.provider.TableLocalDataProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TableRepository {

    private TableLocalDataProvider provider;
    private Services services;

    @Inject
    public TableRepository(TableLocalDataProvider provider, Services services) {
        this.provider = provider;
        this.services = services;
    }

    public Flowable<List<Table>> updateTables(List<Table> tables) {
        return provider.replace(tables);
    }

    public Maybe<List<Table>> getTables() {
        Flowable<List<Table>> local = getTablesLocal();
        Flowable<List<Table>> remote = getTablesRemote();

        return Flowable.concatArray(local, remote)
                .filter(list -> !list.isEmpty())
                .firstElement();
    }

    public Flowable<List<Table>> resetLocalCache() {
        return getTablesRemote();
    }

    private Flowable<List<Table>> getTablesLocal() {
        return provider.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Flowable<List<Table>> getTablesRemote() {
        return services.getTableMap()
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(reservations -> {
                    List<Table> tables = new ArrayList<>();
                    for (int i = 0; i < reservations.size(); i++) {
                        Boolean reservation = reservations.get(i);
                        Table table = new Table();
                        table.setNumber(i + 1);
                        table.setAvailable(!reservation);
                        tables.add(table);
                    }
                    return tables;
                })
                .flatMap(tables -> provider.replace(tables));
    }

}
