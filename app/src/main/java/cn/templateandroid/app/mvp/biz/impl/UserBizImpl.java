package cn.templateandroid.app.mvp.biz.impl;

import javax.inject.Inject;

import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.io.HttpUtils;
import cn.templateandroid.app.io.api.TestApi;
import cn.templateandroid.app.listener.RequestCallBack;
import cn.templateandroid.app.mvp.biz.UserBiz;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserBizImpl implements UserBiz {

    @Inject
    public UserBizImpl(){

    }

    @Override
    public void login(final RequestCallBack<HotMovieBean> callBack){
         HttpUtils.getInstance().getHttpServer(TestApi.class).getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //切断发送器  d.dispose();
                    }
                    @Override
                    public void onNext(HotMovieBean value) {
                        callBack.onSuccess(value);
                    }
                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                        //完成调用
                    }
                });
    }
}
