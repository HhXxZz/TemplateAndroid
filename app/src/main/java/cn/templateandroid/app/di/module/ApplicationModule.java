package cn.templateandroid.app.di.module;

import android.content.Context;


import cn.templateandroid.app.base.BaseApplication;
import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerApp;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/19.
 */
@Module
public class ApplicationModule {
    private BaseApplication mApplication;

    public ApplicationModule(BaseApplication application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
