package cn.templateandroid.app.ui.fragment;

import android.view.View;
import android.widget.Button;

import cn.templateandroid.app.R;
import cn.templateandroid.app.base.BaseFragment;
import cn.templateandroid.app.bean.News;
import cn.templateandroid.app.rxbus.BusCodeConstants;
import cn.templateandroid.app.rxbus.RxBus;
import cn.templateandroid.app.utils.UIHelper;

/**
 * Created by Administrator on 2017/3/7.
 */

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private Button button;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {
        button = (Button) view.findViewById(R.id.email_sign_in_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                News news = new News();
                news.setName("888");
                news.setAge("666");
                RxBus.getInstance().post(BusCodeConstants.LOGIN_CONSTANTS,news);
                RxBus.getInstance().post(BusCodeConstants.GEGISTER_CONSTANTS,news);
                UIHelper.showSimplePage(getActivity(),SimplePage.TEST);
                break;
            default:
        }
    }
}
