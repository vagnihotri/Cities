package com.assignment.repository;

import com.assignment.repository.database.CityDao;
import com.assignment.repository.model.City;
import com.assignment.repository.network.AppNetworkService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by agni on 24/01/18.
 */

public class AppRepository implements AppDataStore {

    private CityDao cityDao;
    private AppNetworkService appNetworkService;

    @Inject
    public AppRepository(CityDao cityDao, AppNetworkService appNetworkService){
        this.appNetworkService = appNetworkService;
        this.cityDao = cityDao;
    }

    @Override
    public Observable<List<City>> getCities(final int limit, final int offset) {
        return cityDao.getCities(limit, offset).flatMap(new Func1<List<City>, Observable<List<City>>>() {
            @Override
            public Observable<List<City>> call(List<City> cities) {
                if(cities != null && !cities.isEmpty()) {
                    return Observable.just(cities);
                } else {
                    return appNetworkService.getCities(limit, offset);
                }
            }
        });
    }
}
