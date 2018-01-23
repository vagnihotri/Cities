package com.assignment.dagger;

import com.assignment.repository.network.AppNetworkService;
import com.assignment.view.City.CityActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by agni on 24/01/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {
    void inject(CityActivity activity);
    void inject(AppNetworkService appNetworkService);
}
