package cn.templateandroid.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.templateandroid.app.di.component.DaggerFragmentComponent;
import cn.templateandroid.app.di.component.FragmentComponent;
import cn.templateandroid.app.di.module.FragmentModule;
import cn.templateandroid.app.mvp.presenter.base.BasePresenter;

/**
 * Created by Administrator on 2017/3/6.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    protected FragmentComponent mFragmentComponent;
    protected T mPresenter;

    private View mFragmentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(BaseApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    protected abstract void initView(View view);
    
    protected abstract int getLayoutId();
    protected abstract void initInjector();
}
