package cn.templateandroid.app.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import cn.templateandroid.app.R;
import cn.templateandroid.app.base.BaseActivity;
import cn.templateandroid.app.base.BaseApplication;
import cn.templateandroid.app.bean.HotMovieBean;
import cn.templateandroid.app.bean.News;
import cn.templateandroid.app.rxbus.BusCodeConstants;
import cn.templateandroid.app.mvp.presenter.impl.UserPresenterImpl;
import cn.templateandroid.app.mvp.view.UserView;
import cn.templateandroid.app.ui.fragment.SimplePage;
import cn.templateandroid.app.rxbus.RxBus;
import cn.templateandroid.app.utils.UIHelper;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends BaseActivity implements UserView {
    @BindView(R.id.email_sign_in_button)
    Button btn_login;

    @BindView(R.id.btn_wx_login)
    Button btn_wx;
    @BindView(R.id.btn_qq_login)
    Button btn_qq;
    @BindView(R.id.btn_weibo_login)
    Button btn_weibo;
    @Inject
    UserPresenterImpl presenter;
    Disposable disposable;

    //QQ
    private static final String APP_ID = "1106035012";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener loginListener;
    private UserInfo mUserInfo;

    //WX
    private IWXAPI iwxapi;

    //weibo
    private static final String APP_KEY = "669755816";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    private AuthInfo mAuthInfo;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mPresenter = presenter;
        mPresenter.attachView(this);
        btn_login.setOnClickListener(this);
        btn_wx.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        btn_weibo.setOnClickListener(this);
       // presenter.loginPre();
        initBus();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                //UIHelper.showSimplePage(this, SimplePage.REGISTER);

                break;
            case R.id.btn_wx_login:

                break;
            case R.id.btn_qq_login:
                initQQLogin();
                break;
            case R.id.btn_weibo_login:
                initWeibo();
                break;
            default:
        }
    }


    private void initQQLogin(){
        mTencent = Tencent.createInstance(APP_ID, BaseApplication.getInstance());
        loginListener = new BaseUiListener();
        mTencent.login(LoginActivity.this,"all",loginListener);
    }

    private void initWXLogin(){
        iwxapi = WXAPIFactory.createWXAPI(LoginActivity.this,"APP_ID",true);
        iwxapi.registerApp("APP_ID");
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
    }

    private void initWeibo(){
        mAuthInfo = new AuthInfo(this, APP_KEY, "http://www.sina.com",null);
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
        mSsoHandler.authorize(new AuthListener());
    }

    @Override
    public void onLoginSuccess(HotMovieBean hotMovieBean) {
        Logger.d(hotMovieBean);
    }


    private void initBus() {
        RxBus.getInstance().toObservable(BusCodeConstants.GEGISTER_CONSTANTS,News.class)
                .subscribe(this::onNext);
    }


    private void onNext(News value) {
        Log.i("GEGISTER",value.getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止订阅，防止内存泄漏
        if(disposable != null){
            disposable.dispose();
        }
    }

    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object o) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e("login", "response:" + o);
            JSONObject obj = (JSONObject) o;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e("login","登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("login","登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("login","登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(final Bundle values) {
            // 从 Bundle 中解析 Token
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = Oauth2AccessToken.parseAccessToken(values);
                    //从这里获取用户输入的 电话号码信息
                    String  phoneNum =  mAccessToken.getPhoneNum();
                    if (mAccessToken.isSessionValid()) {
                        // 显示 Token
                       // updateTokenView(false);

                        // 保存 Token 到 SharedPreferences
                        AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                        Toast.makeText(LoginActivity.this,
                               "成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 以下几种情况，您会收到 Code：
                        // 1. 当您未在平台上注册的应用程序的包名与签名时；
                        // 2. 当您注册的应用程序包名与签名不正确时；
                        // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                        String code = values.getString("code");
                        String message ="失败";
                        if (!TextUtils.isEmpty(code)) {
                            message = message + "\nObtained the code: " + code;
                        }
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    "取消", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}

