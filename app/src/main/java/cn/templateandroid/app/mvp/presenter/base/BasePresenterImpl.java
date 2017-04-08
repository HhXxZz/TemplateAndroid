package cn.templateandroid.app.mvp.presenter.base;

import android.support.annotation.NonNull;

import cn.templateandroid.app.listener.RequestCallBack;
import cn.templateandroid.app.mvp.view.base.BaseView;

/**
 * Created by Administrator on 2017/3/8.
 */

public class BasePresenterImpl<T extends BaseView,E> implements BasePresenter,RequestCallBack<E> {
    protected T mView;

    @Override
    public void onSuccess(E data) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView = (T) view;
    }

    @Override
    public void onDestroy() {

    }
}
