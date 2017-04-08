package cn.templateandroid.app.mvp.view;

import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.mvp.view.base.BaseView;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface UserView extends BaseView {
    void onLoginSuccess(HotMovieBean hotMovieBean);
}
