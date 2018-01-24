package com.assignment.repository.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.assignment.repository.AppDataStore;
import com.assignment.repository.model.City;
import com.assignment.repository.model.CityStorIOContentResolverDeleteResolver;
import com.assignment.repository.model.CityStorIOContentResolverGetResolver;
import com.assignment.repository.model.CityStorIOContentResolverPutResolver;
import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by agni on 24/01/18.
 */

public class CityDao implements AppDataStore {

    private StorIOContentResolver storIOContentResolver;

    @Inject
    public CityDao(@NonNull Context context) {
        storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(context.getContentResolver())
                .addTypeMapping(City.class, ContentResolverTypeMapping.<City>builder()
                        .putResolver(new CityStorIOContentResolverPutResolver())
                        .getResolver(new CityStorIOContentResolverGetResolver())
                        .deleteResolver(new CityStorIOContentResolverDeleteResolver())
                        .build()
                ).build();
    }


    @Override
    public Observable<List<City>> getCities(int limit, int offset) {
        return storIOContentResolver.get()
                .listOfObjects(City.class)
                .withQuery(Query.builder().uri(Contract.City.CONTENT_URI).build())
                .prepare()
                .asRxObservable();
    }

    public void saveCitiesToDatabase(List<City> cities) {
        storIOContentResolver.put()
                .objects(cities)
                .prepare()
                .executeAsBlocking();
    }
}
