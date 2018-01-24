package com.assignment.view.city;

import com.assignment.repository.AppRepository;
import com.assignment.repository.model.City;
import com.assignment.repository.network.AppNetworkService;

import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;

/**
 * Created by agni on 24/01/18.
 */

public class CityPresenter implements CityContract.Presenter {

    private Subscription subscription;
    private AppRepository appRepository;
    private CityContract.View view;
    private Scheduler processScheduler, androidScheduler;

    public CityPresenter(AppRepository appRepository, CityContract.View view,
                         Scheduler processScheduler, Scheduler androidScheduler){
        this.appRepository = appRepository;
        this.view = view;
        //testing
        this.processScheduler = processScheduler;
        this.androidScheduler = androidScheduler;
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
        subscription = appRepository.getCities(0,0)
                .observeOn(androidScheduler)
                .subscribeOn(processScheduler)
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        view.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<City> cities) {
                        view.showCities(cities);
                    }
                });
    }

    @Override
    public void loadCitiesFromNetwork(int limit, int offset) {
        new AppNetworkService().getCities(limit, offset)
                .observeOn(androidScheduler)
                .subscribeOn(processScheduler)
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onCompleted() {
                        view.showComplete();
                        loadCities();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.toString());
                    }

                    @Override
                    public void onNext(List<City> posts) {
                        view.showComplete();
                    }
                });
    }
}
