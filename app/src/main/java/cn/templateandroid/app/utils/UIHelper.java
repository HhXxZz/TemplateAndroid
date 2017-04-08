package cn.templateandroid.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.templateandroid.app.ui.activity.SimpleActivity;
import cn.templateandroid.app.ui.fragment.SimplePage;

/**
 * Created by Administrator on 2017/3/7.
 * 页面辅助类
 */

public class UIHelper {

    /**
     * 跳转到fragment，不携带参数
     * @param context
     * @param page
     */
    public static void showSimplePage(Context context, SimplePage page) {
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtra(SimpleActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    /**
     * 跳转到fragment，携带参数bundle
     * @param context
     * @param page
     * @param bundle
     */
    public static void showSimplePage(Context context, SimplePage page, Bundle bundle) {
        Intent intent = new Intent(context, SimpleActivity.class);
        intent.putExtra(SimpleActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleActivity.BUNDLE_KEY_ARGS,bundle);
        context.startActivity(intent);
    }
}
