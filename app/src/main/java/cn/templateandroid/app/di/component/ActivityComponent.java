package cn.templateandroid.app.di.component;

import android.app.Activity;
import android.content.Context;


import cn.templateandroid.app.di.module.ActivityModule;
import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerActivity;
import cn.templateandroid.app.ui.activity.LoginActivity;
import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();


    void inject(LoginActivity activity);

}
