package com.assignment.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by agni on 24/01/18.
 */

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application mApplication) {
        this.application = mApplication;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
