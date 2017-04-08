package cn.templateandroid.app.listener;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface RequestCallBack<T> {

    void onSuccess(T data);

    void onError(String errorMsg);

}
