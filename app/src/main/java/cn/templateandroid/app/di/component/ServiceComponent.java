package cn.templateandroid.app.di.component;

import android.content.Context;


import cn.templateandroid.app.di.module.ServiceModule;
import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerService;
import dagger.Component;

/**
 * Created by Eric on 2017/1/19.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
