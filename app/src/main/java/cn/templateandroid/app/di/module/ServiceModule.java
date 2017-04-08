package cn.templateandroid.app.di.module;

import android.app.Service;
import android.content.Context;


import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/19.
 */
@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
