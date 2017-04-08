package cn.templateandroid.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.lang.ref.WeakReference;

import cn.templateandroid.app.R;
import cn.templateandroid.app.base.BaseActivity;
import cn.templateandroid.app.ui.fragment.SimplePage;

/**
 * Created by Administrator on 2017/3/7.
 *
 * fragment的载体activity
 */

public class SimpleActivity extends BaseActivity {
    public static final String TAG_NAME = SimpleActivity.class.getSimpleName();

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    private static final String TAG = "FLAG_TAG";
    protected WeakReference<Fragment> mFragment;
    protected int mPageValue = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_fragment;
    }

    @Override
    protected void initView() {
        if (mPageValue == -1) {
            mPageValue = getIntent().getIntExtra(BUNDLE_KEY_PAGE, 0);
        }
        initFromIntent(mPageValue, getIntent());
    }

    @Override
    protected void initInjector() {

    }

    private void initFromIntent(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException( "you must provide a page info to display");
        }
        SimplePage page = SimplePage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:"+ pageValue);
        }

        actionBar.setTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.fl_container, fragment, TAG);
            trans.commitAllowingStateLoss();
            mFragment = new WeakReference<>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("generate fragment error. by value:" + pageValue);
        }
    }

    @Override
    protected boolean hasBackBtn() {
        return true;
    }
}
