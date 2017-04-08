package cn.templateandroid.app.mvp.presenter.base;

import android.support.annotation.NonNull;

import cn.templateandroid.app.mvp.view.base.BaseView;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface BasePresenter {
    void onCreate();

    void attachView(@NonNull BaseView view);

    void onDestroy();
}
