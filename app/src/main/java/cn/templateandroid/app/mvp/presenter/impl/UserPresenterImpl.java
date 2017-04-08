package cn.templateandroid.app.mvp.presenter.impl;

import javax.inject.Inject;

import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.mvp.biz.UserBiz;
import cn.templateandroid.app.mvp.biz.impl.UserBizImpl;
import cn.templateandroid.app.mvp.presenter.UserPresenter;
import cn.templateandroid.app.mvp.presenter.base.BasePresenterImpl;
import cn.templateandroid.app.mvp.view.UserView;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserPresenterImpl extends BasePresenterImpl<UserView,HotMovieBean> implements UserPresenter {
    private UserBiz userBiz;

    /**
     * 接口回调
     * dagger注解
     * @param userBizImpl
     */
    @Inject
    public UserPresenterImpl(UserBizImpl userBizImpl){
        userBiz = userBizImpl;
    }

    /**
     *  创建时自动获取数据，在BaseActivity、BaseFragment调用
     */
    @Override
    public void onCreate() {
        //getUserinfo
        userBiz.login(this);
    }

    /**
     *
     * @param data
     */
    @Override
    public void onSuccess(HotMovieBean data) {
        super.onSuccess(data);
        mView.onLoginSuccess(data);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
    }

    /**
     * 登录方法
     */
    public void loginPre(){
        userBiz.login(this);
    }

}
