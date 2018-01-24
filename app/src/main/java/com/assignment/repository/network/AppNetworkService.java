package com.assignment.repository.network;

import com.assignment.MainApplication;
import com.assignment.repository.AppDataStore;
import com.assignment.repository.database.CityDao;
import com.assignment.repository.model.City;
import com.assignment.repository.model.Payload;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
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
    public Observable<List<City>> getCities(int limit, int offset) {
        return retrofit.create(CityService.class)
                .getCities(limit, offset)
                .flatMap(new Func1<Payload, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> call(Payload payload) {
                        cityDao.saveCitiesToDatabase(payload.objects);
                        return cityDao.getCities(0, 0);
                    }
                });
    }

    private interface CityService {
        @GET("city")
        Observable<Payload> getCities(@Query("limit") int limit, @Query("offset") int offset);
    }
}
