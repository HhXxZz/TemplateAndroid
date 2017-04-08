package cn.templateandroid.app.di.module;

import android.app.Activity;
import android.content.Context;


import cn.templateandroid.app.di.scope.ContextLife;
import cn.templateandroid.app.di.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/19.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context ProvideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity ProvideActivity() {
        return mActivity;
    }
}
