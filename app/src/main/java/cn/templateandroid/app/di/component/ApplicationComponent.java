package cn.templateandroid.app.di.component;

import android.content.Context;


import cn.templateandroid.app.di.module.ApplicationModule;
import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerApp;
import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}