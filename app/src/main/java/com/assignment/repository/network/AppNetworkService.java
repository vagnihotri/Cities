package com.assignment.repository.network;

import android.util.Log;

import com.assignment.MainApplication;
import com.assignment.repository.AppDataStore;
import com.assignment.repository.database.CityDao;
import com.assignment.repository.model.City;
import com.assignment.repository.model.Payload;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by agni on 24/01/18.
 */

public class AppNetworkService implements AppDataStore {
    @Inject
    Retrofit retrofit;

    @Inject
    CityDao cityDao;

    public AppNetworkService() {
        MainApplication.getAppComponent().inject(this);
    }


    @Override
    public Observable<List<City>> getCities() {
        Log.d("REMOTE","Loaded from remote");

        return retrofit.create(CityService.class)
                .getCities()
                .flatMap(new Func1<Payload, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> call(Payload payload) {
                        return Observable.just(payload.objects);
                    }
                })
                .doOnNext(new Action1<List<City>>() {
                    @Override
                    public void call(List<City> cities) {
                        cityDao.saveCitiesToDatabase(cities);
                    }
                });
    }

    private interface CityService {
        @GET("city")
        Observable<Payload> getCities();
    }
}
