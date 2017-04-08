package cn.templateandroid.app.di.component;

import android.app.Activity;
import android.content.Context;


import cn.templateandroid.app.di.module.FragmentModule;
import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerFragment;
import cn.templateandroid.app.ui.fragment.RegisterFragment;
import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(RegisterFragment fragment);
}
