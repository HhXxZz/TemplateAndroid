package cn.templateandroid.app.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.utils.AutoUtils;

import cn.templateandroid.app.R;

/**
 * Created by Administrator on 2017/3/6.
 */

public class BaseToolBarActivity extends AutoLayoutActivity {
    protected LinearLayout linearLayout;
    protected ActionBar actionBar;
    private Toolbar toolbar;
    private LinearLayout mainview;
    private LinearLayout contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayout = new LinearLayout(this);
        LayoutInflater.from(this).inflate(R.layout.activity_toolbar, linearLayout,
                true);
        contentView = (LinearLayout) linearLayout.findViewById(R.id.ll_content);
        toolbar = (Toolbar) linearLayout.findViewById(R.id.toolbar);


        initToolBar();
        initContentView();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(!hasToolBar()){
            if(Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
        }
        if(hasBackBtn()){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initContentView() {
        mainview = new LinearLayout(this);
        mainview.setOrientation(LinearLayout.VERTICAL);
    }

    /**
     *
     * @param layoutResID
     */
    public void setContentView(@LayoutRes int layoutResID) {
        mainview.addView(linearLayout,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        LayoutInflater.from(this).inflate(layoutResID,contentView,true);
        AutoUtils.autoSize(mainview);
        super.setContentView(mainview, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置是否有返回按钮
     * @param f
     */
    public void setBackBtn(boolean f){
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(f);
        }
    }

    /**
     * 是否有返回按钮
     * @return
     */
    protected boolean hasBackBtn() {
        return false;
    }

    /**
     * 设置toolbar 标题
     * @return
     */
    protected int setToolBarTitle() {
        return R.string.app_name;
    }

    /**
     activity是否有toolbar
     */
    protected boolean hasToolBar(){
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
