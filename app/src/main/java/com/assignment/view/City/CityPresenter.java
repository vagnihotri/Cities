package com.assignment.view.City;

import android.util.Log;

import com.assignment.repository.AppRepository;
import com.assignment.repository.model.City;
import com.assignment.repository.network.AppNetworkService;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by agni on 24/01/18.
 */

public class CityPresenter implements CityContract.Presenter {

    private static final String TAG = CityPresenter.class.getSimpleName();
    private Subscription subscription;
    private AppRepository appRepository;
    private CityContract.View view;

    public CityPresenter(AppRepository appRepository, CityContract.View view){
        this.appRepository = appRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadCities();
    }

    @Override
    public void unSubscribe() {
        if (subscription != null && subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public void loadCities() {
        subscription = appRepository.getCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        view.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<City> cities) {
                        view.showCities(cities);
                    }
                });
    }

    @Override
    public void loadCitiesFromNetwork() {
        new AppNetworkService().getCities().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Complete");
                        view.showComplete();
                        loadCities();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.toString());
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<City> posts) {

                    }
                });
    }
}