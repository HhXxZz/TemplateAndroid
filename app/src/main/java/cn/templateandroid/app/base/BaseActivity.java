package cn.templateandroid.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.templateandroid.app.AppManager;
import cn.templateandroid.app.di.component.ActivityComponent;
import cn.templateandroid.app.di.component.DaggerActivityComponent;
import cn.templateandroid.app.di.module.ActivityModule;
import cn.templateandroid.app.mvp.presenter.base.BasePresenter;

/**
 * Created by Administrator on 2017/3/6.
 */

public abstract class BaseActivity<T extends BasePresenter> extends BaseToolBarActivity implements View.OnClickListener {
    protected ActivityComponent mActivityComponent;
    protected T mPresenter;
    public Activity mActivity;
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        AppManager.getAppManager().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        //初始化dagger
        initActivityComponent();
        initInjector();
        initView();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    /**
     * 拿到布局文件
     * @return
     */
    protected abstract int getLayoutId();

    /**
     *  初始化view
     */
    protected abstract void initView();

    /**
     *  初始化 注解
     */
    protected abstract void initInjector();

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {

    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(BaseApplication.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
