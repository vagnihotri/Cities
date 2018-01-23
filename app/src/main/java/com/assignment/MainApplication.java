package com.assignment;

import android.app.Application;

import com.assignment.dagger.ApplicationComponent;
import com.assignment.dagger.ApplicationModule;
import com.assignment.dagger.DaggerApplicationComponent;
import com.assignment.dagger.DataModule;

/**
 * Created by agni on 24/01/18.
 */

public class MainApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule("https://api.aasaanjobs.com/api/v4/"))
                .build();
    }


    public static ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

}