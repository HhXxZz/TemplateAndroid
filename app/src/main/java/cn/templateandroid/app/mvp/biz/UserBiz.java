package cn.templateandroid.app.mvp.biz;

import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.listener.RequestCallBack;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface UserBiz {
    void login(RequestCallBack<HotMovieBean> callBack);
}
