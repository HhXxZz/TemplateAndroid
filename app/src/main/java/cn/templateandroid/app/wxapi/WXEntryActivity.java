package cn.templateandroid.app.wxapi;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.templateandroid.app.R;

/**
 * Created by Administrator on 2017/3/10.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                /*获取token*/
                SendAuth.Resp sendResp = (SendAuth.Resp) baseResp;
                String code = sendResp.code;
                Log.d("Tag", "code\t" + code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:

                break;
            default:

                break;
        }
    }
}
